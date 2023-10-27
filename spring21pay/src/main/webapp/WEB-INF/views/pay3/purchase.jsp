<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/Header.jsp"></jsp:include>
<h1>구매내역 확인</h1>

<!-- 보여주는 부분 -->
<c:forEach var="confirmVO" items="${list}">
<div class="purchase-item">
	[${confirmVO.productDto.productNo}] 
	${confirmVO.productDto.productName}
	- 
	${confirmVO.productDto.productPrice}원 
	(구매수량 : ${confirmVO.purchaseVO.qty}개)
	-
	총 ${confirmVO.total}원
</div>
</c:forEach>
<hr>
<h2>총 ${total} 원</h2>

<!-- 전송되는 부분 -->
<form method="post">
<c:forEach var="confirmVO" items="${list}" varStatus="stat">
<input type="hidden" name="product[${stat.index}].productNo" value="${confirmVO.productDto.productNo}">
<input type="hidden" name="product[${stat.index}].qty" value="${confirmVO.purchaseVO.qty}">
</c:forEach>
<button type="submit" class="btn btn-primary">구매하기</button>

</form>