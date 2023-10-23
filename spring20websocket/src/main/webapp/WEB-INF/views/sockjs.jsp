<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<title>SockJS</title>

<h1>SockJS를 적용한 웹소켓 예제</h1>
<!-- 웹소켓 서버가 SockJS일 경우 페이지내에서도 SockJS를 사용해야한다. -->
<!-- JQuery CDN -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- SockJS CDN -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>

<script>

//	window.socket=new WebSocket(주소); 이건 ws
//	window.socket=new SockJS(주소); 이건 http

//연결 생성
window.socket=new SockJS("${pageContext.request.contextPath}/ws/sockjs");
//연결 이후에 해야할 일들을 콜백 함수로 지정(onopen, onclose, onerror, onmessage)
window.socket.onmessage = function(e){
	console.log(e);
};

	
	</script>