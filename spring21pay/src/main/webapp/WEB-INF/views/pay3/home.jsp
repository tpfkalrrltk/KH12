<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/views/template/Header.jsp"></jsp:include>


<div class="container-fluid ms-5 mt-5">

<div class="row m-5">
<div class="col-4 offset-4"><h1 class="btn btn-primary w-100">가장 일반적인 결제</h1></div>


</div>

<div class="row">
<div class="col-3 offset-3 fw-bold mb-3">상품명</div>
<div class="col-2  fw-bold mb-3">금액</div>
<div class="col-2  fw-bold mb-3">수량</div>
</div>
<div class="product-list">
	<c:forEach var="productDto" items="${list}">
		<div class="product-item form-check form-switch">
		<div class="row ">
		<div class="col-3 offset-3 fw-bold align-content-start"><label><input type="checkbox" name="productNo" value="${productDto.productNo}" class="form-check-input">
				[${productDto.productNo}] ${productDto.productName} </label></div>
		<div class="col-2  fw-bold align-content-start">	${productDto.productPrice}원</div>
			<div class="col-2 fw-bold align-content-start"><input type="number" name="qty" min="1"
				value="1" class="form-label " style="width: 50px"></div>
			 </div>
		</div>

	</c:forEach>
	<div class="col-6 offset-3 mt-5 mb-5">
	<button type="button" class="purchase-btn btn btn-primary w-100">구매하기</button>
	</div>
	
</div>

</div>

<div class="col-5">
<div class="body"></div>
</div>
</div>




</div>




<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	$(function() {
		$(".purchase-btn").click(function() {
			var form = $("<form>").attr("action","test3/purchase").attr("method","get");

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