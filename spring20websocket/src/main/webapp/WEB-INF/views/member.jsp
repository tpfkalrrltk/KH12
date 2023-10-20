<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 전용 웹소켓</title>
</head>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css">
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(function() {
	//목표 : 연결버튼을 누르면 웹소켓 연결 생성, 종료버튼을 누르면 생성한 연결 종료
	
		var uri = "ws://localhost:8080/ws/member";
		window.socket = new WebSocket(uri);
		
		
		socket.onmessage = function(e){
			//console.log(e.data);
			$("<div>").text(e.data).appendTo("body")
			
			Toastify({
				  text: e.data,
				  duration: 4000,
				 // destination: "https://github.com/apvarun/toastify-js",
				  newWindow: true,
				  close: true,
				  gravity: "bottom", // `top` or `bottom`
				  position: "right", // `left`, `center` or `right`
				  stopOnFocus: true, // Prevents dismissing of toast on hover
				  style: {
				    background: "linear-gradient(to right, #00b09b, #96c93d)",
				  },
				  onClick: function(){} // Callback after click
				}).showToast();
			
		}
		
		
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

</script>
<body>
<H1>회원 전용 웹소켓 예제</H1>



<hr>

<input type="text" class="message-input">
<button type="button" class="send-btn">전송</button>





</body>

</html>