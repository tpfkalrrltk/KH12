<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>가장 일반적인 결제</h1>

<div class="product-list">
	<c:forEach var="productDto" items="${list}">
		<div class="product-item">
			<label><input type="checkbox" name="productNo" value="${productDto.productNo}">
				[${productDto.productNo}] ${productDto.productName}
				(${productDto.productPrice}원)</label> <input type="number" name="qty" min="1"
				value="1">
		</div>

	</c:forEach>
	<button type="button" class="purchase-btn">구매하기</button>
</div>
<div class="body"></div>
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	$(function() {
		$(".purchase-btn").click(function() {
			var form = $("<form>").attr("action","test3/purchase").attr("method","post");

			var count = 0;
			
			$(".product-item").each(function(index, tag){
				//현재 위치의 체크박스가 체크 되어있다면 상품번호와 상품수량을 불러와서 form에 추가하겠다.
				var checked = $(this).find("[name=productNo]").prop("checked");
				if(checked ==false) return;
				
				var productNo =$(this).find("[name=productNo]").val();
				var qty=$(this).find("[name=qty]").val();
				//console.log(productNo, qty);
				
				$("<input>").attr("name","product["+count+"].productNo").attr("type","hidden")
				.val(productNo)
				.appendTo(form);
				
				$("<input>").attr("name","product["+count+"].qty").attr("type","hidden")
				.val(qty)
				.appendTo(form);
				
				count++;
			})
			
			$(".body").append(form);
			form.submit();//form 전송해라!
			
		});
	});
</script>