<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1>결재 상세내역</h1>

${vo}

<ul>
<li>tid : ${vo.tid}</li>
<li>status : ${vo.status}</li>
<li>partner_order_id : ${vo.partnerOrderId}</li>
<li>partner_user_id : ${vo.partnerUserId}</li>

<li>결재 유형 =
<c:choose>
<c:when test="${vo.paymentMethodType == 'MONEY'}">현금</c:when>
<c:otherwise>카드</c:otherwise>
</c:choose>
</li>

<li>상품명 = ${vo.itemName}</li>

<li>결제 시작시각 =
<fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd E HH:mm:ss"/>
</li>
<li>결제 승인시각 =
<fmt:formatDate value="${vo.approvedAt}" pattern="yyyy-MM-dd E HH:mm:ss"/>
</li>
<li>결제 취소시각 =
<fmt:formatDate value="${vo.canceledAt}" pattern="yyyy-MM-dd E HH:mm:ss"/>
</li>

<li>결제금액 : 
<fmt:formatNumber  value="${vo.amount.total}" pattern="#,##0"></fmt:formatNumber>
원</li>
<li>부가세 :
<fmt:formatNumber  value="${vo.amount.vat}" pattern="#,##0"></fmt:formatNumber>
원</li>
<li>실상품가 : 
<fmt:formatNumber  value="${vo.amount.total - vo.amount.vat}" pattern="#,##0"></fmt:formatNumber>
원</li>

</ul>
