<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<!-- 댓글과 관련된 처리를 할수 있도록 jQuery 코드를 구현 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	$(function() {
		//목표 : 댓글등록을 누르면 입력정보로 ajax 통신을 통해 댓글 등록 처리
		//(주의) form은 전송이 되면 안된다

		$(".reply-insert-form").submit(function(e) {
			//this == e.target == 폼(form)

			//입력검사 코드(skip)

			//기본이벤트 차단
			e.preventDefault();

			//비통기 통신 발생
			$.ajax({
				//url:"http://localhost:8080/rest/reply/insert",
				url : window.contextPath+"/rest/reply/insert",
				method : "post",
				//data:{ replyOrigin : ? , replyContent : ? },
				data : $(e.target).serialize(),
				success : function(response) {
					//console.log("성공");
					$("[name=replyContent]").val("");//입력창 초기화
					loadList();//목록갱신
				}
			});
		});

		//목록은 페이지가 로딩되면 바로 불러오도록 구현한다.
		//- 등록이 완료된 경우 불러오도록 구현한다.
		//- 여러 군데서 사용할 수 있도록 함수 형태로 구현한다.
		//- 목록을 모두 지우고 전부 다 새로 불러오도록 구현한다.
		loadList();

		//목록을 불러온뒤 추가로 해야할 것
		// - 내글에만 수정/삭제 버튼이 나오도록 처리
		// - 게시글 작성자가 쓴 댓글에 추가 표시
		// - 수정버튼을 누르면 화면에 변화를 주도록 처리
		// - 삭제버튼을 누르면 확인창 출력후 삭제하도록 처리

		function loadList() {

			//Javascript로 boardNo라는 이름의 파라미터 값 읽기
			var params = new URLSearchParams(location.search);
			var no = params.get("board_no");

			//(중요)로그인한 사용자의 정보를 EL을 이용하여 저장(매우 위험한 코드)
			var memberId = "${sessionScope.name}";

			//비동기 통신으로 화면 갱신
			$
					.ajax({
						//url:"http://localhost:8080/rest/reply/list",
						url : window.contextPath+"/rest/reply/list",
						method : "post",
						data : {
							replyOrigin : no
						},
						success : function(response) {

							//화면 청소
							//$(".reply-list").remove();//자기 자신까지 삭제(하면안됨!)
							$(".reply-list").empty();//자기 자신을 제외한 내부 코드 삭제

							//response는 댓글 목록(JSON)
							console.log(response);
							for (var i = 0; i < response.length; i++) {
								var reply = response[i];

								var template = $("#reply-template").html();
								var htmlTemplate = $.parseHTML(template);

								//작성자를 표시할 때 다음과 같이 로직을 추가
								//- 탈퇴한 사용자는 빈칸이 아니라 "탈퇴한사용자"로 처리

								$(htmlTemplate).find(".replyWriter").text(
										reply.replyWriter || "탈퇴한 사용자");
								$(htmlTemplate).find(".replyContent").text(
										reply.replyContent);
								$(htmlTemplate).find(".replyTime").text(
										reply.replyTime);

								//내가 작성한 댓글이 아니라면
								if (memberId.length = 0 || memberId != reply.replyWriter) {
									//버튼 삭제
									$(htmlTemplate).find(".w-25").empty();
								}

								//만드는 시점에 이벤트 설정
								// -반복문의 데이터 사용 불가(위치가 다름)
								// -지금과 같이 버튼 내부에 태그가 더 있을때,
								//	-this 와 e.target은 다를수있다.
								// -(this)는 주인공 (e.target)은 실제 대상
								$(htmlTemplate).find(".btn-delete").attr(
										"data-reply-no", reply.replyNo);
								$(htmlTemplate).find(".btn-delete").click(
										function(e) {

											//var replyNo = $(this).data("reply-no");
											//var replyNo = $(e.target).data("reply-no");
											var replyNo = $(this).attr(
													"data-reply-no");
											$.ajax({
												url : window.contextPath+"/rest/reply/delete",
												method : "post",
												data : {
													replyNo : replyNo
												},
												success : function(response) {
													loadList();
												}
											});
										});

								//수정버튼을 누르면...?
								// - 편집상태의 템플릿을 만들어서 추가
								// - 전환시 작성된 값들을 입력창으로 이동시켜야함
								// - 전송가능한 form과 취소 버튼을 구현
								// - 수정시 서버로 글번호와 글내용만 전달하면됨
								$(htmlTemplate)
										.find(".btn-edit")
										.attr("data-reply-no", reply.replyNo)
										.click(
												function() {
													var editTemplate = $(
															"#reply-edit-template")
															.html();
													var editHtmlTemplate = $
															.parseHTML(editTemplate);

													//value 설정
													var replyNo = $(this).attr(
															"data-reply-no");
													var replyContent = $(this)
															.parents(
																	".view-container")
															.find(
																	".replyContent")
															.text();
													$(editHtmlTemplate).find(
															"[name=replyNo]")
															.val(replyNo);
													$(editHtmlTemplate)
															.find(
																	"[name=replyContent]")
															.val(replyContent);

													//취소 버튼에 대한 처리 구현
													$(editHtmlTemplate)
															.find(".btn-cancel")
															.click(
																	function() {
																		//this == 취소버튼
																		$(this)
																				.parents(
																						".edit-container")
																				.prev(
																						".view-container")
																				.show();
																		$(this)
																				.parents(
																						".edit-container")
																				.remove();

																	})

													//완료(등록) 버튼 처리
													// -editHtmlTemplate 자체가 form이므로 추가 탐색을 하지 않음
													$(editHtmlTemplate)
															.submit(
																	function(e) {
																		//검사코드(미입력)

																		//기본 이벤트 차단
																		e
																				.preventDefault();

																		$
																				.ajax({
																					url : window.contextPath+"/rest/reply/edit",
																					method : "post",
																					//		data:{replyNo : ? ,replyContent : ?},
																					data : $(
																							e.target)
																							.serialize(),
																					success : function(
																							response) {
																						loadList();
																					}
																				});
																	});

													//화면 배치
													$(this)
															.parents(
																	".view-container")
															.hide()
															.after(
																	editHtmlTemplate);

												});

								$(".reply-list").append(htmlTemplate);
							}
						},
					});
		}
	});
</script>

<c:if test="${sessionScope.name != null}">
<script>
	//좋아요 처리
	//[1] 페이지가 로드되면 비동기 통신으로 좋아요 상태를 체크하여 하트 생성
	//[2] 하트에 클릭 이벤트를 설정하여 좋아요 처리가 가능하도록 구현
	$(function(){
		var params = new URLSearchParams(location.search);
		var boardNo = params.get("board_no");
		
		$.ajax({
			url:window.contextPath+"/rest/like/check",
			method:"post",
			data:{boardNo : boardNo},
			success:function(response){
				//response는 {"check":true, "count":0} 형태의 JSON이다.
				if(response.check) {
					$(".fa-heart").removeClass("fa-solid fa-regular")
										.addClass("fa-solid");
				}
				else {
					$(".fa-heart").removeClass("fa-solid fa-regular")
										.addClass("fa-regular");
				}
				//전달받은 좋아요 개수를 하트뒤에 span에 출력
				$(".fa-heart").next("span").text(response.count);
			}
		});
		
		//[2]
		$(".fa-heart").click(function(){
			$.ajax({
				url:window.contextPath+"/rest/like/action",
				method:"post",
				data: {boardNo : boardNo},
				success:function(response){
					//response는 {"check":true, "count":0} 형태의 JSON이다.
					if(response.check) {
						$(".fa-heart").removeClass("fa-solid fa-regular")
											.addClass("fa-solid");
					}
					else {
						$(".fa-heart").removeClass("fa-solid fa-regular")
											.addClass("fa-regular");
					}
					//전달받은 좋아요 개수를 하트뒤에 span에 출력
					$(".fa-heart").next("span").text(response.count);
				}
			});
		});
	});
</script>
</c:if>


<script id="reply-template" type="text/template">

	<div class="row flex-container view-container">
		<div class="w-75">
			<div class="row left">
				<h5 class="replyWriter">작성자</h5>
			</div>
			<div class="row left">
				<pre class="replyContent">내용</pre>
			</div>
			<div class="row left">
				<span class="replyTime">yyyy-MM-dd HH:mm:ss</span>
			</div>
		</div>
		<div class="w-25">
			<div class="row left">
				<button class="btn  btn-edit">
					<i class="fa-solid fa-edit"></i>수정
				</button>
			</div>
			<div class="row left ">
				<button class="btn btn-negative btn-delete">
					<i class="fa-solid fa-trash"></i>삭제
				</button>
			</div>
		</div>
	</div>
<hr>
</script>

<script id="reply-edit-template" type="text/template">
<form class="reply-edit-form edit-container">
<input type="hidden" name="replyNo" value="??">
	<div class="row flex-container">
		<div class="w-75">
			<textarea name="replyContent" class="form-input w-100" rows="4"></textarea>
		</div>

		<div class="w-25">
			<div class="row">
				<button class=" row btn btn-positive" type="submit">전송</button>
			</div>
			<div class="row">
				<button class=" row btn btn-negative btn-cancel" type="button">취소</button>
			</div>

		</div>
	</div>
</form>
</script>

<style>
td {
	text-align: center;
	background-color: silver;
}

th {
	text-align: center;
	background-color: graytext;
}

body {
	background-color: #778ca3;
	padding-left: 100px;
	padding-top: 30px;
}
 .note-viewer{  
 line-height: 2;  !important}
</style>






<h2>게시글 상세조회</h2>
<div align="right">




	<%-- 회원일경우 글쓰기,수정,삭제이 나와야한다 --%>
	<c:if test="${sessionScope.name != null}">
		<button style="background-color: #7700c9">
			<a href="updateLike?board_no=${boardDto.board_no}"><font
				color="ffffff" size="18">Like</font></a>
		</button>
		<button style="background-color: #778cb9">
			<a href="list">게시글 목록</a>
		</button>
		<button style="background-color: #778cb9">
			<!-- 			답글쓰기는 board_parent를 기본으로 하여서 작성글을 해야한다. -->
			<a href="write?board_parent=${boardDto.board_no}">답글 쓰기</a>

		</button>

		<%-- 수정 삭제는 소유자일경우에만 나와야한다. --%>
		<c:if test="${sessionScope.name == boardDto.board_writer}">
			<button style="background-color: #778cb9">
				<a href="edit?board_no=${boardDto.board_no}">게시글 수정</a>
			</button>
			<button style="background-color: #778cb9">
				<a href="delete?board_no=${boardDto.board_no}">게시글 삭제</a>
			</button>
		</c:if>
	</c:if>
</div>
<h3>게시글 정보</h3>
<br>
<table border="1" style="background-color: black;">
	<tr>
		<th>게시글 번호</th>
		<th>게시글 작성자</th>
		<th>게시글 이름</th>
	</tr>

	<tr>
		<td>${boardDto.board_no}</td>
		<td>${boardDto.getBoardWriterString()}<%-- 탈퇴한 사용자가 아닐경우 옆에 닉네임을 추가로 출력 --%>
			<c:if test="${writerDto !=null }">
		,닉네임 : ${writerDto.memberNickname}</c:if>
		</td>
		<td>${boardDto.board_title}</td>

	</tr>


</table>
<br>
<h3>게시글 기타정보</h3>
<br>
<table border="1" style="background-color: black;" width="900">
	<tr>
		<th>게시글 조회수</th>
		<th>게시글 좋아요</th>
		<th>게시글 댓글수</th>
		<th>게시글 작성일</th>
		<th>게시글 수정일</th>
		<th>게시글 그룹</th>
	</tr>
	<tr>
		<td>${boardDto.board_readcount}</td>
		<td><i class="fa-regular fa-heart red"></i><span>?</span></td>
		<td>${boardDto.board_replycount}</td>
		<td><fmt:formatDate value="${boardDto.board_ctime}"
				pattern="y년 M월 d일 E요일 a h시 m분 s초" /></td>
		<td><fmt:formatDate value="${boardDto.board_utime}"
				pattern="y년 M월 d일 E요일 a h시 m분 s초" /></td>
		<td>${boardDto.board_group}</td>
	</tr>
</table>

<h3>내용</h3>
<table border="1" style="background-color: black;" width="700">
	<tr>
		<th>게시글 내용</th>
	</tr>
	<tr>
		<td class="note-viewer">${boardDto.board_content}</td>
	</tr>

</table>
<br>
<%--댓글 목록이 표시될 영역 --%>
<div class="row left">
	<h1>댓글 목록</h1>
</div>
<div class="reply-list row left"></div>


<%-- 댓글과 관련된 화면이 작성될 위치 --%>
<c:if test="${sessionScope.name !=null }">
	<form class="reply-insert-form" method="post">
		<input type="hidden" name="replyOrigin" value="${boardDto.board_no}">
		<div class="row">
			<textarea class="form-input w-100" name="replyContent" rows="4"></textarea>
		</div>
		<div class="row">
			<button class="btn btn-positive w-100">
				<i class="fa-solid fa-pen"></i>댓글등록
			</button>
		</div>
	</form>

</c:if>




<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>