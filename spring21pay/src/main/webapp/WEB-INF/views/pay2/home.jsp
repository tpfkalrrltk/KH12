<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>상품을 기반으로 한 구매(DB포함)</h1>

<ul>
<c:forEach var="productDto" items="${list}">
<li>${productDto. productName}(${productDto.productPrice} 원)
<a href="test2/purchase?productNo=${productDto.productNo}">구매하기</a></li>
</c:forEach></ul>