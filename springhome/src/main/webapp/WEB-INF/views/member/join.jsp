<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>


<div class="container w-400 ">
<form action="join"method="post" >

<div class="container w-500">
        <div class="row">
            <h1>회원 가입</h1>
        </div>
        <div class="row left">
            <!-- 
                라벨에는 for를 이용하여 특정 대상을 연결시킬 수 있다 
                for="대상ID"
                체크박스 등도 선택하게 만들 수 있다(디자인적으로 활용)
            -->
            <label for="id-input">아이디 <span class="important">*</span></label>
            <input type="text" name="memberId" class="form-input w-100"
                        id="id-input"
                        placeholder="영문 소문자+숫자 5~20자 이내">
        </div>
        <div class="row left">
            <label>비밀번호 <span class="important">*</span></label>
            <input type="password" name="memberPw" class="form-input w-100"
                        placeholder="영문 대소문자+숫자+특수문자 반드시 포함 8~16자">
        </div>
        <div class="row left">
            <label>닉네임 <span class="important">*</span></label>
            <input type="text" name="memberNickname" class="form-input w-100"
                        placeholder="한글 또는 숫자 2~10자 이내">
        </div>
        <div class="row left">
            <label>이메일</label>
            <input type="email" name="memberEmail" class="form-input w-100"
                        placeholder="testuser@kh.com" required>
        </div>
        <div class="row left">
            <label>연락처</label>
            <input type="tel" name="memberContact" class="form-input w-100"
                        placeholder="- 제외하고 입력">
        </div>
        <div class="row left">
            <label>생년월일</label>
            <input type="date" name="memberBirth" class="form-input w-100">
        </div>
        <div class="row left">
            <label class="mb-10" style="display: block;">주소</label>
            <input type="text" class="form-input" name="memberPost" placeholder="우편번호" style="width:8em;">
            <button class="btn">우편번호 찾기</button>
            <input type="text" class="form-input w-100 mt-10" name="memberAddr1" placeholder="기본주소">
            <input type="text" class="form-input w-100 mt-10" name="memberAddr2" placeholder="상세주소">
        </div>
        <div class="row">
            <button class="btn btn-positive w-100">회원가입</button>
        </div>
    </div>
</form></div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>