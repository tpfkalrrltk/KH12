<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ko">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>

    <!-- 아이콘 사용을 위한 Font Awesome 6 CDN -->
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/5.3.2/lux/bootstrap.min.css" rel="stylesheet">
    <link href="test.css" rel="stylesheet">
    
    <style>
    	.btn-userlist {
    		display: none;
    	}
    	.message-list {
    		height: 65vh;
    		overflow-y: scroll;
    		padding-bottom: 15px;
    	}
    	::-webkit-scrollbar {
		    width: 1px; /* 스크롤바 너비 */
		    background-color: black
		}
		::-webkit-scrollbar-thumb {
		    background: var(--bs-secondary); /* 스크롤바 색상 */
		}
    
    	@media screen and (max-width:768px) {
	    	.client-list {
	    		position: fixed;
	    		top:0;
	    		left:-250px;
	    		bottom:0;
	    		width:250px;
	    		z-index: 9999999;
	    		padding-top: 90px;
	    		transition:left 0.2s ease-out;
	    	}
	    	.client-list.active {
	    		left:0;
	    	}
	    	.btn-userlist {
	    		display:block;
	    		position: fixed;
	    		top:1em;
	    		right:1em;
	    	}
    	}
    </style>
</head>

<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-10 offset-md-1">
				
				<div class="row mt-4">
					<div class="col">
						<h1>
							전체 채팅
							<button class="btn btn-secondary btn-userlist">
								<i class="fa-solid fa-users"></i>
							</button>
						</h1>
					</div>
				</div>
				
				<div class="row mt-4">
					<div class="col-md-4 client-list"></div>
					<div class="col-md-8">
						
						<div class="row">
							<div class="col">
								<div class="input-group">
									<input type="text" class="form-control message-input" placeholder="메세지 내용 작성">
									<button type="button" class="btn btn-primary send-btn">
										<i class="fa-regular fa-paper-plane"></i>
										보내기
									</button>
								</div>
							</div>
						</div>
						
						<!-- 메세지 표시 영역 -->
						<div class="row mt-4">
							<div class="col message-list"></div>
						</div>
						
					</div>
				</div>
				
            </div>
        </div>        
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- 웹소켓 서버가 SockJS일 경우 페이지에서도 SockJS를 사용해야 한다 -->
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
	<script>
		//연결 생성
		window.socket = new SockJS("${pageContext.request.contextPath}/ws/sockjs");
		//연결 후 해야할 일들을 콜백함수로 지정(onopen, onclose, onerror, onmessage)
		window.socket.onmessage = function(e){
			//console.log(e);
			var data = JSON.parse(e.data);
			//console.log(data);
			
			//사용자가 접속하거나 종료했을 때 서버에서 오는 데이터로 목록을 갱신
			//사용자가 메세지를 보냈을 때 서버에서 이를 전체에게 전달한다
			//data.clients에 회원 목록이 있다
			if(data.clients) {//목록 처리
				$(".client-list").empty();
			
				var ul = $("<ul>").addClass("list-group");
				for(var i=0; i < data.clients.length; i++) {
					$("<li>")
						.addClass("list-group-item d-flex justify-content-between align-items-center")
						.text(data.clients[i].memberId)
						.append(
							$("<span>").addClass("badge bg-primary badge-pill")
											.text(data.clients[i].memberLevel)		
						)
						.appendTo(ul);
				}
				ul.appendTo(".client-list");
			}
			else if(data.content) {//메세지 처리
				var memberId;
				if(data.dm == true) {//DM이라면
					if(data.target){//target이 있다면(내가 DM을 보내서 찍히는 메세지라면)
						memberId = $("<strong>").text(data.target + " 님에게 보낸 DM");
					}
					else{//target이 없다면(내가 DM을 받아서 찍히는 메세지라면)
						memberId = $("<strong>").text(data.memberId + " 님으로부터의 DM");
					}
					memberId = $("<strong>").text(data.memberId + " 님으로부터의 DM");
				}
				else {//DM이 아니라면
					memberId = $("<strong>").text(data.memberId);
				}
				var memberLevel = $("<span>").text(data.memberLevel)
															.addClass("badge bg-primary badge-pill ms-2");
				var content = $("<div>").text(data.content);
				
				//메세지를 화면에 추가
				$("<div>").addClass("border border-secondary rounded p-2 mt-2")
								.append(memberId)
								.append(memberLevel)
								.append("<hr>")
								.append(content)
								.appendTo(".message-list");
				
				//스크롤바를 맨 아래로 이동
				$(".message-list").scrollTop($(".message-list")[0].scrollHeight);
			}
		};
		
		//메세지를 전송하는 코드
		//- 메세지가 @로 시작하면 DM으로 처리(아이디 유무 검사정도 하면 좋고)
		//- @아이디 메세지
		$(".send-btn").click(function(){
			var text = $(".message-input").val();
			if(text.length == 0) return;
			
			//window.socket.send(text);//일반 텍스트 형식으로 보낼 때
			
			if(text.startsWith("@")) {//@로 시작하면
				var space = text.indexOf(" ");
				if(space == -1) return;
				
				var obj = {
					target: text.substring(1, space),
					content: text.substring(space+1)
				};
				var str = JSON.stringify(obj);//객체를 JSON 문자열로 변환
				window.socket.send(str);//JSON 형식으로 보낼 때
				$(".message-input").val("");
			}
			else {
				var obj = {
					content:text
				};
				var str = JSON.stringify(obj);//객체를 JSON 문자열로 변환
				window.socket.send(str);//JSON 형식으로 보낼 때
				$(".message-input").val("");
			}
			
		});
		
		//.btn-userlist를 누르면 사용자 목록에 active를 붙였다 떼었다 하도록 처리
		$(".btn-userlist").click(function(){
			$(".client-list").toggleClass("active");
		});
	</script>
</body>

</html>   
    














	