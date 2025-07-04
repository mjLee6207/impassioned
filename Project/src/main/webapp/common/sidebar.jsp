<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="member" value="${sessionScope.loginUser}" />

<div class="profile-card">
    <img src="${member.profile != null ? member.profile : '/img/profile-default.png'}" class="profile-img" alt="프로필 사진">
    
    <div class="username">이름: <b>${member.nickname}</b></div>
    <div class="useremail">이메일: ${member.email}</div>
    
    <div class="joindate">
        가입일:
        <fmt:formatDate value="${member.joinDate}" pattern="yyyy-MM-dd" />
    </div>
    
    <div class="card-menu">
        <button class="card-menu-btn mypage-btn" type="button"><i class="bi bi-person"></i>내 정보 수정</button> 
        <button class="card-menu-btn logout-btn" type="button"><i class="bi bi-box-arrow-right"></i>로그아웃</button>
    </div>
</div>

<!-- 로그아웃 버튼 동작 -->
<script>
  document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".logout-btn").addEventListener("click", function () {
      location.href = "/member/logout.do";  // 로그아웃 경로로 이동
    });

    // 내 정보 수정 → 마이페이지 이동
    document.querySelector(".mypage-btn").addEventListener("click", function () {
      location.href = "/mypage/mypage.do"; // 🔁 실제 마이페이지 URL로 수정
    });
  });
</script>
</body>
</html>