<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹소켓</title>
</head>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	$(function() {
		//목표 : 연결버튼을 누르면 웹소켓 연결 생성, 종료버튼을 누르면 생성한 연결 종료
		$(".connect-btn").click(function() {
			var uri = "ws://localhost:8080/ws/group";
			window.socket = new WebSocket(uri);

		});
		$(".disconnect-btn").click(function() {
			window.socket.close();
		});

		//전송버튼을 클릭하면 입력한 메세지를 가져와서 서버로 전달
		$(".send-btn").click(function() {
			// var input = document.querySelector(".massage-input").value;
			var input = $(".message-input").val();
			if (input.length == 0)
				return;

			window.socket.send(input);
			$(".message-input").val("");
		});
	});
</script>
<body>
	<H1>그룹 웹소켓 예제</H1>

	<button type="button" class="connect-btn">연결</button>
	<button type="button" class="disconnect-btn">종료</button>

	<hr>

	<input type="text" class="message-input">
	<button type="button" class="send-btn">전송</button>
</body>

</html>