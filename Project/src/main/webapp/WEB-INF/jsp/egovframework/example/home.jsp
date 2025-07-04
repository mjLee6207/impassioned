<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>CheForest 메인페이지</title>
    <!-- 공통 스타일시트 연결 -->
    <link rel="stylesheet" href="/css/home.css" />
       <link rel="stylesheet" href="/css/style.css">
              <link rel="stylesheet" href="/css/footer.css">
</head>
<body>
<jsp:include page="/common/header.jsp" />
    <!-- ✅ 상단 로그인/로그아웃/마이페이지 영역 -->
    <div class="top-bar">
<c:choose>
  <c:when test="${not empty sessionScope.loginUser}">
    <p>${sessionScope.loginUser.nickname}님 환영합니다!</p>
    <a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
    
  </c:when>
  <c:otherwise>
    <a href="${pageContext.request.contextPath}/member/login.do">로그인</a>
    <a href="${pageContext.request.contextPath}/member/login.do">회원가입</a>
  </c:otherwise>
</c:choose>
    </div>

    <!-- ✅ 사이트 로고 영역 -->
    <div class="logo">CheForest</div>

    <!-- ✅ 검색창 -->
    <div class="search-bar">
        <input type="text" placeholder="레시피를 검색해보세요!">
    </div>

    <!-- ✅ 오늘의 추천 레시피 제목 -->
    <div class="section-title">👨‍🍳 오늘의 추천 레시피</div>

    <!-- ✅ 추천 레시피 카드 목록 -->
    <div class="recipes">
        <!-- 예시 레시피 카드 1 -->
        <a class="recipe" href="https://www.naver.com" target="_blank">
            <img src="<%= request.getContextPath() %>/img/07_final_quiz_cloud(맑음).gif" alt="">
            <p class="title">바질 크림소스 라자냐</p>
        </a>

        <!-- 예시 레시피 카드 2 -->
        <a class="recipe" href="https://www.naver.com" target="_blank">
            <img src="<%= request.getContextPath() %>/img/비프웰링턴.jpg" alt="비프웰링턴">
            <p class="title">단호박 무스를 곁들인 비프웰링턴</p>
        </a>

        <!-- 예시 레시피 카드 3 -->
        <a class="recipe" href="https://www.naver.com" target="_blank">
            <img src="<%= request.getContextPath() %>/img/무스.jpg" alt="무스">
            <p class="title">크림치즈 라즈베리 무스</p>
        </a>

        <!-- 예시 레시피 카드 4 -->
        <a class="recipe" href="https://www.naver.com" target="_blank">
            <img src="<%= request.getContextPath() %>/img/스테이크.jpg" alt="스테이크">
            <p class="title">양갈비 스테이크와 소스 6종</p>
        </a>
    </div>
<!-- 만들어놓은 꼬리말 jsp -->
<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
