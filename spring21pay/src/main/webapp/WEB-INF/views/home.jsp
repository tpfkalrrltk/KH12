<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/5.3.2/lux/bootstrap.min.css" rel="stylesheet">

<jsp:include page="/WEB-INF/views/template/Header.jsp"></jsp:include>
<div class="container-fluid mt-5">
<div class="row">
<div class="col-4 offset-4">
<h1 class="btn btn-lg btn-primary  w-100">결재를 배웁시다</h1>
</div> 
<div class="col-4 form-group offset-4 mt-5">
<c:choose>
	<c:when test="${sessionScope.name ==null}">
	<form action="login" method="post">
	<input type="text" name="memberId" placeholder="아이디" class="form-label w-100"><br><br>
		<input type="password" name="memberPw" placeholder="비밀번호"  class="form-label w-100"><br><br>
		<button type="submit" class="btn btn-primary w-100">로그인</button>
	</form>
	</c:when>
	<c:otherwise>
		<h3>${sessionScope.name}으로 로그인 중...</h3>
		<a href="logout" class="btn  btn-primary  w-100">로그아웃</a>
	</c:otherwise>
</c:choose>
</div>
</div>
<div class="row mt-5 offset-3">
<div class="col-3">
<h2>
	<a href="pay/test1" class="btn btn-info w-100">첫번째 예제</a>
</h2>
</div>
<div class="col-3">
<h2>
	<a href="pay/test2"class="btn btn-info  w-100">두번째 예제</a>
</h2>
</div>
<div class="col-3">
<h2>
	<a href="pay/test3"class="btn btn-info  w-100">세번째 예제</a>
</h2>
</div>
</div>







</div>
