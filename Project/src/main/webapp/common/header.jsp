<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%-- 🆕 요청 URI와 쿼리스트링 가져오기 --%>
<c:set var="uri"
	value="${empty requestScope['javax.servlet.forward.request_uri'] ? pageContext.request.requestURI : requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="query"
	value="${empty requestScope['javax.servlet.forward.query_string'] ? pageContext.request.queryString : requestScope['javax.servlet.forward.query_string']}" />
<c:choose>
	<c:when test="${not fn:contains(uri, '/WEB-INF')}">
		<c:set var="fullUrl" value="${uri}${query != null ? '?' : ''}${query}" />
	</c:when>
	<c:otherwise>
		<c:set var="fullUrl" value='/' />
	</c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>header</title>
<link rel="icon" type="image/png" href="/images/favicon.png">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/header.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
	rel="stylesheet">
<link rel="stylesheet" href="/css/style.css">

<script>
        function goLogin() {
            const redirect = location.pathname + location.search;
            location.href = '/member/login.do?redirect=' + encodeURIComponent(redirect);
        }
        function goLogout() {
            const redirect = location.pathname + location.search;
            location.href = '/member/logout.do?redirect=' + encodeURIComponent(redirect);
        }
    </script>
</head>
<body>

	<div class="main-navbar-bg">
		<nav class="main-navbar">
			<!-- 왼쪽 로고 + 메뉴 -->
			<div class="navbar-left">
				<a href="http://localhost:8080/" class="main-logo"> <!-- 로고 이미지 -->
					<div class="logoi">
						<img src="<%=request.getContextPath()%>/images/home/header.png"
							alt="메인로고">
					</div>
				</a>
				<div class="main-menu">
					<!-- Recipe 드롭다운 -->
					<div class="dropdown" id="dropdown-recipe">

						<a class="dropdown-toggle"
							href="${pageContext.request.contextPath}/recipe/recipe.do">레시피</a>

						<div class="dropdown-menu">

							<a class="dropdown-item"
								href="/recipe/recipe.do?categoryKr=한식&pageIndex=1">한식<span
								class="eng"> |　Korean</span></a> <a class="dropdown-item"
								href="/recipe/recipe.do?categoryKr=양식&pageIndex=1">양식<span
								class="eng"> |　Western</span></a> <a class="dropdown-item"
								href="/recipe/recipe.do?categoryKr=중식&pageIndex=1">중식<span
								class="eng"> |　Chinese</span></a> <a class="dropdown-item"
								href="/recipe/recipe.do?categoryKr=일식&pageIndex=1">일식<span
								class="eng"> |　Japanese</span></a> <a class="dropdown-item"
								href="/recipe/recipe.do?categoryKr=디저트&pageIndex=1">디저트<span
								class="eng"> |　Dessert</span></a>

						</div>
					</div>

					<!-- ✅ Board 드롭다운 (클릭 시 전체 게시판 이동) -->
					<div class="dropdown" id="dropdown-board">
						<a class="dropdown-toggle"
							href="${pageContext.request.contextPath}/board/board.do">게시판</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" 
								href="/board/board.do?category=한식">한식<span class="eng"> |　Korean</span></a> <a class="dropdown-item"
								href="/board/board.do?category=양식">양식<span class="eng"> |　Western</span></a><a class="dropdown-item"
								href="/board/board.do?category=중식">중식<span class="eng"> |　Chinese</span></a><a class="dropdown-item"
								href="/board/board.do?category=일식">일식<span class="eng"> |　Japanese</span></a><a class="dropdown-item"
								href="/board/board.do?category=디저트">디저트<span class="eng"> |　Dessert</span></a>
						</div>
					</div>

					<!-- Event -->
					<div class="dropdown" id="dropdown-event">
						<button class="dropdown-toggle" type="button">이벤트</button>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="/event/test.do">레시피 추천</a>
							<!-- <a
								class="dropdown-item" href="/event/recipe">Recipe Event</a> -->
						</div>
					</div>

					<!-- Support -->
					<div class="dropdown" id="dropdown-qna">
						<button class="dropdown-toggle" type="button">고객지원</button>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="/guide.do">홈페이지 가이드</a> <a
								class="dropdown-item" href="/qna.do">Q&A</a>
						</div>
					</div>
				</div>
			</div>

			<!-- 오른쪽 검색창 + 로그인/로그아웃 -->
			<div class="navbar-right">
				<div class="navbar-search">
					<form action="/search/all.do" method="get" autocomplete="off">
						<input type="text" name="keyword" class="ssearch-box"
							placeholder="통합 검색" value="${param.keyword}">
						<button class="ssearch-btn" type="submit">
							<i class="bi bi-search"></i>
						</button>
					</form>
				</div>

				<c:choose>
					<c:when test="${not empty sessionScope.loginUser}">
						<c:url var="mypageUrl" value="/mypage/mypage.do" />
						<button class="head-mypage-btn"
							onclick="location.href='${mypageUrl}'">마이페이지</button>
						<button class="head-logout-btn" type="button" onclick="goLogout()">로그아웃</button>
					</c:when>
					<c:otherwise>
						<button class="login-btn" type="button" onclick="goLogin()">로그인</button>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- 햄버거(앱) 메뉴 : 모바일에서만 보임 -->
			<div class="app-menu-container">
				<button class="app-menu-toggle" onclick="toggleAppMenu()">
					<i class="bi bi-list"></i>
				</button>
				<div class="app-menu-dropdown" id="appMenuDropdown">
					<c:choose>
						<c:when test="${not empty sessionScope.loginUser}">
							<a href="/mypage/mypage.do">마이페이지</a>
							<a href="javascript:void(0);" onclick="goLogout()">로그아웃</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0);" onclick="goLogin()">로그인</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</nav>
	</div>

	<!-- ✅ 드롭다운 동작 -->
	<script>
    const dropdowns = document.querySelectorAll('.dropdown');
    dropdowns.forEach(dropdown => {
        dropdown.addEventListener('mouseenter', function () {
            dropdowns.forEach(dd => {
                if (dd !== this) dd.querySelector('.dropdown-menu').classList.remove('show');
            });
            this.querySelector('.dropdown-menu').classList.add('show');
        });
        dropdown.addEventListener('mouseleave', function () {
            this.querySelector('.dropdown-menu').classList.remove('show');
        });

        const toggle = dropdown.querySelector('.dropdown-toggle');
        if (toggle.tagName === 'A') {
            toggle.addEventListener('click', function (e) {
                if (dropdown.id !== 'dropdown-board' && dropdown.id !== 'dropdown-recipe') {
                    e.preventDefault(); // ❗Board 외에는 기본 링크 막고 토글만
                }
                dropdowns.forEach(dd => {
                    if (dd !== dropdown) dd.querySelector('.dropdown-menu').classList.remove('show');
                });
                dropdown.querySelector('.dropdown-menu').classList.toggle('show');
            });
        }
    });

    // 바깥 클릭 시 닫기
    document.body.addEventListener('click', function (e) {
        if (!e.target.closest('.dropdown')) {
            dropdowns.forEach(dd => dd.querySelector('.dropdown-menu').classList.remove('show'));
        }
    });
    // 공백 검색시 빈 값 공백 방지
    document.querySelector('.navbar-search form').addEventListener('submit', function(e) {
        const value = this.keyword.value.trim();
        if (!value) {
          alert('검색어를 입력하세요!');
          this.keyword.focus();
          e.preventDefault();
        }
      });
    function toggleAppMenu() {
    	  const menu = document.getElementById('appMenuDropdown');
    	  menu.classList.toggle('show');
    	}
    	// 메뉴 밖 클릭시 닫힘
    	document.addEventListener('click', function(event) {
    	  const menu = document.getElementById('appMenuDropdown');
    	  const btn = document.querySelector('.app-menu-toggle');
    	  if (!menu || !btn) return;
    	  if (!menu.contains(event.target) && !btn.contains(event.target)) {
    	    menu.classList.remove('show');
    	  }
    	});
</script>
</body>
</html>