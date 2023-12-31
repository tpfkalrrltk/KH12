<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<h2>게시글 목록</h2>
<style>
td{text-align: center; background-color: silver;}
th{text-align: center; background-color: graytext;}
body {
	background-color: #778ca3;padding: 30px;
}
</style>
<div align="right">
<button style="background-color: #778cb9" ><h3><a href="write" >게시글 등록</a></h3></button>
</div><br><br>

<table border="1" style="background-color: black;" >
	<tr>
		<th>게시글 번호</th>
		<th>게시글 작성자</th>
		<th>게시글 이름</th>
		<th>게시글 조회수</th>
		<th>게시글 좋아요</th>
		<th>게시글 댓글수</th>
		<th>게시글 작성일</th>
		<th>게시글 수정일</th>
	</tr>
	<c:forEach var="boardDto" items="${list}">
	<tr>
		<td>${boardDto.board_no}</td>
		<td>${boardDto.board_writer}</td>
		<td><a href="detail?board_no=${boardDto.board_no}">${boardDto.board_title}</a></td>
		<td>${boardDto.board_readcount}</td>
		<td>${boardDto.board_likecount}</td>
		<td>${boardDto.board_replycount}</td>
		<td><fmt:formatDate value="${boardDto.board_ctime}" pattern="yyyy/MM/dd hh:mm:ss" /></td>
		<td><fmt:formatDate value="${boardDto.board_utime}" pattern="yyyy/MM/dd hh:mm:ss" /></td>
	</tr>
	</c:forEach>
</table>
<br><br>
<div>
<form name="search" method="post">
  <select name="search" >
      <option value="title">제목</option>
      <option value="writer">작성자</option>
  </select>
  
  <input type="text" name="keyword" />
  
  <button type="button">검색</button>
  </form>
 </div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>