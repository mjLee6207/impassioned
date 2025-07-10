<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사이드바</title>
    <link rel="stylesheet" href="/css/sidebar.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</head>
<body>
<c:set var="member" value="${sessionScope.loginUser}" />

<div class="profile-card">
    <img src="${member != null && member.profile != null ? member.profile : '/img/profile-default.png'}" class="profile-img" alt="프로필 사진">

    <c:choose>
        <c:when test="${member != null}">
        <!-- 7/7 hyj 사용자 닉네임, 이름만 간결히 뜨도록 코드 수정 -->
            <div class="username"><b>${member.nickname}</b></div>
            <div class="useremail">${member.email}</div>
            <div class="joindate">
                가입일:
                <fmt:formatDate value="${member.joinDate}" pattern="yyyy-MM-dd" />
            </div>
            <div class="card-menu">
                <button class="card-menu-btn edit-btn" type="button">
                    <i class="bi bi-person"></i>내 정보 수정
                </button>
                <button class="card-menu-btn logout-btn" type="button">
                    <i class="bi bi-box-arrow-right"></i>로그아웃
                </button>
            </div>
        </c:when>
        <c:otherwise>
            <div class="username text-secondary" style="margin-bottom:16px;">로그인이 필요합니다</div>
            <div class="card-menu">
                <button class="card-menu-btn login-btn" type="button">
                    <i class="bi bi-box-arrow-in-right"></i>로그인
                </button>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script>
  document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".edit-btn").forEach(function(btn) {
      btn.addEventListener("click", function () {
        location.href = "/mypage/mycorrection.do";
      });
    });
    document.querySelectorAll(".logout-btn").forEach(function(btn) {
      btn.addEventListener("click", function () {
        location.href = "/member/logout.do";
      });
    });
    document.querySelectorAll(".login-btn").forEach(function(btn) {
      btn.addEventListener("click", function () {
        location.href = "/member/login.do";
      });
    });
  });
</script>
</body>
</html>
