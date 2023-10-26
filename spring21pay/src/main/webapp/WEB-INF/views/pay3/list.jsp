<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>조회</h1>


<c:forEach var="paymentDto" items="${list}">
<div style=" border:  1px solid black; margin: 30px 0px">
${paymentDto}
</div>
</c:forEach>