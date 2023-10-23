<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>SockJS</title>


<!-- 웹소켓 서버가 SockJS일 경우 페이지내에서도 SockJS를 사용해야한다. -->
<!-- JQuery CDN -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- SockJS CDN -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>

<body>
<div class="container-fluld">

<div class="row mt-4">
<div class="col">
<h1>전체 채팅</h1>
</div>
</div>

<div class="row mt-4">
<div class="col-4 client-list"></div>
<div class="col-8"></div>
</div>

</div>
</body>


<script>
	//	window.socket=new WebSocket(주소); 이건 ws
	//	window.socket=new SockJS(주소); 이건 http

	//연결 생성
	window.socket = new SockJS("${pageContext.request.contextPath}/ws/sockjs");
	//연결 이후에 해야할 일들을 콜백 함수로 지정(onopen, onclose, onerror, onmessage)
	window.socket.onmessage = function(e) {
		console.log(e);
		var data = JSON.parse(e.data);
		console.log(data);

		//사용자가 접속하거나 종료했을 때 서버에서 오는 데이터로 목록을 갱신
		//사용자가 메세지를 보냈을 때 서버에서 이를 전체게에 전달한다
		//data.clients에 회원 목록이 있다.

		if (data.clients) { //목록 처리
			$(".client-list").empty();
			for (var i = 0; i < data.clients.length; i++) {
				$("<div>").text(data.clients[i].memberId).appendTo(
						".client-list")
			}
		} else if (data.content) {//메세지 처리
			var memberId = $("<span>").text(data.memberId);
			var memberlevel = $("<span>").text(data.memberlevel);
			var content = $("<div>").text(data.content);

			$("<div>").append(memberId).append(memberlevel).append(content)
					.appendTo(".message-list");
		}
	};
	
	$(".send-btn").click(function() {
		var text = $(".message-input").val();
		if (text.length == 0)return;


		window.socket.send(text);
		$(".message-input").val("");
	})
</script>

<h1>SockJS를 적용한 웹소켓 예제</h1>

<input type="text" class="message-input">
<button type="button" class="send-btn">전송</button>

<div class="client-list"></div>
<div class="message-list"></div>

