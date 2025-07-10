<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <div class="username"><b>${member.nickname}</b></div>
            <div class="useremail">${member.email}</div>
            <div class="joindate">
                가입일:
                <fmt:formatDate value="${member.joinDate}" pattern="yyyy-MM-dd" />
            </div>
            <div class="card-menu">
                <%-- [마이페이지/내 정보 수정] 버튼 분기 --%>
                <c:choose>
                    <c:when test="${fn:contains(pageContext.request.requestURI, '/mypage')}">
                        <button class="card-menu-btn edit-btn" type="button"
                            onclick="location.href='/mypage/mycorrection.do'">
                            <i class="bi bi-person"></i>내 정보 수정
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button class="card-menu-btn mypage-btn" type="button"
                            onclick="location.href='/mypage/mypage.do'">
                            <i class="bi bi-person"></i>마이페이지
                        </button>
                    </c:otherwise>
                </c:choose>

                <button class="card-menu-btn logout-btn" type="button"
                    onclick="location.href='/member/logout.do'">
                    <i class="bi bi-box-arrow-right"></i>로그아웃
                </button>
            </div>
        </c:when>
        <c:otherwise>
            <div class="username text-secondary" style="margin-bottom:16px;">로그인이 필요합니다</div>
            <div class="card-menu">
                <button class="card-menu-btn login-btn" type="button"
                    onclick="location.href='/member/login.do'">
                    <i class="bi bi-box-arrow-in-right"></i>로그인
                </button>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
