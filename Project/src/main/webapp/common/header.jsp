<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>header</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/header.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">

    
</head>
<body>
<div class="main-navbar-bg">
    <nav class="main-navbar">
        <!-- 왼쪽 로고 + 메뉴 -->
        <div class="navbar-left">
            <a href="http://localhost:8080/" class="main-logo">
                <span>🍽️ CheForest             
                </span>
            </a>
            <div class="main-menu">
                <!-- 레시피 드롭다운 -->
                <div class="dropdown" id="dropdown-recipe">
                    <button class="dropdown-toggle" type="button">Recipe</button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="/recipe.do?category=한식">한식<span class="eng"> |　Korean</span></a>
                        <a class="dropdown-item" href="/recipe.do?category=양식">양식<span class="eng"> |　Western</span></a>
                        <a class="dropdown-item" href="/recipe.do?category=중식">중식<span class="eng"> |　Chinese</span></a>
                        <a class="dropdown-item" href="/recipe.do?category=일식">일식<span class="eng"> |　Japanese</span></a>
                        <a class="dropdown-item" href="/recipe.do?category=디저트">디저트<span class="eng"> |　Dessert</span></a>
                    </div>
                </div>
                <!-- 게시판 드롭다운 -->
                <div class="dropdown" id="dropdown-board">
                    <button class="dropdown-toggle" type="button">Board</button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="/board/board.do?category=한식">한식<span class="eng"> |　Korean</span></a>
                        <a class="dropdown-item" href="/board/board.do?category=양식">양식<span class="eng"> |　Western</span></a>
                        <a class="dropdown-item" href="/board/board.do?category=중식">중식<span class="eng"> |　Chinese</span></a>
                        <a class="dropdown-item" href="/board/board.do?category=일식">일식<span class="eng"> |　Japanese</span></a>
                        <a class="dropdown-item" href="/board/board.do?category=디저트">디저트<span class="eng"> |　Dessert</span></a>
                    </div>
                </div>
                <!-- Event 드롭다운 -->
                <div class="dropdown" id="dropdown-event">
                    <button class="dropdown-toggle" type="button">Event</button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="/event/coupon">레시피 추천</a>
                        <a class="dropdown-item" href="/event/recipe">Recipe Event</a>
                    </div>
                </div>
                <!-- Q&A 드롭다운 -->
                <div class="dropdown" id="dropdown-qna">
                    <button class="dropdown-toggle" type="button">Support</button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="/guide.do">홈페이지 가이드</a>
                        <a class="dropdown-item" href="/qna/support">Q&A</a>

                    </div>
                </div>
                <!-- Michelin 드롭다운 고도화-->
                <!-- <div class="dropdown" id="dropdown-michelin">
                    <button class="dropdown-toggle" type="button">Michelin</button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="/michelin/seoul">서울</a>
                        <a class="dropdown-item" href="/michelin/kyungi">경기</a>
                        <a class="dropdown-item" href="/michelin/busan">부산</a>
                        <a class="dropdown-item" href="/michelin/daegu">대구</a>
                        <a class="dropdown-item" href="/michelin/daejeon">대전</a>
                    </div>
                </div> -->
            </div>
        </div>
        <!-- 오른쪽: 검색창 + 로그인 -->
      <div class="navbar-right">
       <div class="navbar-search">
        <form action="/search.do" method="get" autocomplete="off">
            <input type="text" name="q" class="search-box" placeholder="Search">
            <button class="ssearch-btn" type="submit"><i class="bi bi-search"></i></button>
        </form>
    </div>

  <c:choose>
    <%-- 로그인한 경우 --%>
    <c:when test="${not empty sessionScope.loginUser}">
       <%--  <span class="welcome-msg">${sessionScope.loginUser.nickname}님 환영합니다.</span> --%>
        <%-- 마이페이지 버튼 --%>
        <c:url var="mypageUrl" value="/mypage/mypage.do"/>
        <button class="head-mypage-btn" onclick="location.href='${mypageUrl}'">MYPAGE</button>
        <%-- 로그아웃 버튼 --%>
        <c:url var="logoutUrl" value="/member/logout.do"/>
        <button class="head-logout-btn" onclick="location.href='${logoutUrl}'">LOGOUT</button>
    </c:when>
    <%-- 로그인하지 않은 경우 --%>
    <c:otherwise>
        <c:url var="loginUrl" value="/member/login.do"/>
        <button class="login-btn" onclick="location.href='${loginUrl}'">LOGIN</button>
    </c:otherwise>
</c:choose>
</div>

    </nav>
</div>
<script>
    // 드롭다운 중복 방지: 하나만 열림
    const dropdowns = document.querySelectorAll('.dropdown');
    dropdowns.forEach(dropdown => {
        dropdown.addEventListener('mouseenter', function() {
            dropdowns.forEach(dd => {
                if(dd !== this) dd.querySelector('.dropdown-menu').classList.remove('show');
            });
            this.querySelector('.dropdown-menu').classList.add('show');
        });
        dropdown.addEventListener('mouseleave', function() {
            this.querySelector('.dropdown-menu').classList.remove('show');
        });
        // 모바일(터치) 대응: 클릭시에도 하나만 열림
        dropdown.querySelector('.dropdown-toggle').addEventListener('click', function(e){
            e.preventDefault();
            dropdowns.forEach(dd => {
                if(dd !== dropdown) dd.querySelector('.dropdown-menu').classList.remove('show');
            });
            dropdown.querySelector('.dropdown-menu').classList.toggle('show');
        });
    });
    // 바깥 클릭시 드롭다운 닫기
    document.body.addEventListener('click', function(e){
        if(!e.target.closest('.dropdown')) {
            dropdowns.forEach(dd => dd.querySelector('.dropdown-menu').classList.remove('show'));
        }
    });
</script>
</body>
</html>
