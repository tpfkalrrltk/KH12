<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>결재내역(계층형)</h1>

<!-- 전체 목록 -->
<c:forEach var="paymentListVO" items="${list}">
	<div style="border: 1px solid black; margin: 30px 0px; padding: 10px">
		<!-- 대표 정보 -->
		<div style="border: 1px solid blue; padding: 10px">${paymentListVO.paymentDto}</div>
		<!-- 상세 목록 정보-->
		<div style="border: 1px solid red; padding: 10px; margin-top: 10px">
			<c:forEach var="paymentDetailDto"
				items="${ paymentListVO.paymentDetailList}">
				<div style="border: 1px solid gray; padding: 10px; margin-top: 10px">
					${paymentDetailDto}
					<br>
					<a href="cancel?paymentDetailNo=${paymentDetailDto.paymentDetailNo}">개별내역취소</a>
					</div>
			</c:forEach>
		</div>
	</div>
</c:forEach>