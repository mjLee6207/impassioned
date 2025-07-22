<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>CheForest 메인페이지</title>
<link rel="icon" type="image/png" href="/images/favicon.png">
<link rel="stylesheet" href="/css/home.css" />
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/footer.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
<!-- slick slider css/js -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<jsp:include page="/common/header2.jsp" />
</head>
<body>

	<div class="logo">
		<img src="<%=request.getContextPath()%>/images/home/main.png"
			alt="메인로고">
	</div>
	<!-- 로고 들어가야되는 위치 -->
	<!-- 메뉴바 추가 -->
	<div class="search-bar">
		<form id="mainSearchForm" action="/search/all.do" method="get"
			autocomplete="off">
			<input type="text" id="searchKeyword" name="keyword"
				placeholder="원하는 레시피를 검색해보세요 !" />
		</form>
	</div>
	 <!-- 메뉴바 추가 -->
	 <div class="home-shortcut-bar">
		<a href="/recipe/recipe.do">레시피</a> <span>|</span> <a
			href="/board/board.do">게시판</a> <span>|</span> <a
			href="/event/test.do">이벤트</a>
	</div>  
	<div class="scroll-down-wrapper">
		<button class="scroll-down-btn" onclick="scrollToRecipes()">▼</button>
	</div>

	<hr class="hr1">
	<!-- 오늘의 추천 레시피(수정X) -->
	<section id="recipe-section">
		<div class="recipe-block">
		<div class="recipe-block popular-recipe-section">
			<div class="block-inner">
				<div class="section-title">
					<img src="/images/favicon.png" class="section-icon" alt="메인로고">&nbsp;인기
					레시피
				</div>
				<div class="recipes slider">
					<c:forEach var="recipe" items="${bestRecipes}">
						<a class="recipe"
							href="/recipe/view.do?recipeId=${recipe.recipeId}"> <img
							src="${recipe.thumbnail}" alt="${recipe.titleKr}">
							<p class="title">${recipe.titleKr}</p>
							<p class="like">❤ ${recipe.likeCount}</p>
						</a>
					</c:forEach>
				</div>
			</div>
			</div>
		</div>
		<!-- 한식 -->
		<div class="recipe-block">
			<div class="block-inner">
				<div
					class="section-title d-flex justify-content-between align-items-center">
					<span><img src="/images/favicon.png" class="section-icon"
						alt="메인로고">&nbsp;한식 레시피</span> <a
						href="/recipe/recipe.do?categoryKr=한식&pageIndex=1"
						class="more-link">+</a>
				</div>
				<div class="recipes">
					<c:forEach var="recipe" items="${koreanRecipe}">
						<a class="recipe"
							href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
							<img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
							<p class="title">${recipe.titleKr}</p>
						</a>
					</c:forEach>
				</div>
			</div>

		</div>
		<!-- 양식 -->
		<div class="recipe-block">
			<div class="block-inner">
				<div
					class="section-title d-flex justify-content-between align-items-center">
					<span><img src="/images/favicon.png" class="section-icon"
						alt="메인로고">&nbsp;양식 레시피</span> <a
						href="/recipe/recipe.do?categoryKr=양식&pageIndex=1"
						class="more-link">+</a>
				</div>
				<div class="recipes">
					<c:forEach var="recipe" items="${westernRecipe}">
						<a class="recipe"
							href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
							<img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
							<p class="title">${recipe.titleKr}</p>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- 중식 -->
		<div class="recipe-block">
			<div class="block-inner">
				<div
					class="section-title d-flex justify-content-between align-items-center">
					<span><img src="/images/favicon.png" class="section-icon"
						alt="메인로고">&nbsp;중식 레시피</span> <a
						href="/recipe/recipe.do?categoryKr=중식&pageIndex=1"
						class="more-link">+</a>
				</div>
				<div class="recipes">
					<c:forEach var="recipe" items="${chineseRecipe}">
						<a class="recipe"
							href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
							<img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
							<p class="title">${recipe.titleKr}</p>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- 일식 -->
		<div class="recipe-block">
			<div class="block-inner">
				<div
					class="section-title d-flex justify-content-between align-items-center">
					<span><img src="/images/favicon.png" class="section-icon"
						alt="메인로고">&nbsp;일식 레시피</span> <a
						href="/recipe/recipe.do?categoryKr=일식&pageIndex=1"
						class="more-link">+</a>
				</div>
				<div class="recipes">
					<c:forEach var="recipe" items="${japaneseRecipe}">
						<a class="recipe"
							href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
							<img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
							<p class="title">${recipe.titleKr}</p>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
		<!-- 디저트 -->
		<div class="recipe-block">
			<div class="block-inner">
				<div
					class="section-title d-flex justify-content-between align-items-center">
					<span><img src="/images/favicon.png" class="section-icon"
						alt="메인로고">&nbsp;디저트 레시피</span> <a
						href="/recipe/recipe.do?categoryKr=디저트&pageIndex=1"
						class="more-link">+</a>
				</div>
				<div class="recipes">
					<c:forEach var="recipe" items="${dessertRecipe}">
						<a class="recipe"
							href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
							<img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
							<p class="title">${recipe.titleKr}</p>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="/common/footer.jsp" />


	<script>
		document.addEventListener("DOMContentLoaded", function() {
			const form = document.getElementById("mainSearchForm");
			const input = document.getElementById("searchKeyword");

			// 엔터키 시 submit 되도록(버튼 없어도 됨) - 기본 동작이니 생략 가능

			// submit시 공백이면 얼럿
			form.addEventListener("submit", function(e) {
				if (!input.value.trim()) {
					alert("검색어를 입력하세요!");
					input.focus();
					e.preventDefault();
				}
			});
		});
		$(document).ready(function() {
			$('.recipes.slider').slick({
				slidesToShow : 5,
				slidesToScroll : 1,
				arrows : false,
				dots : false,
				autoplay : true,
				autoplaySpeed : 2500,
				infinite : true,
				responsive : [ {
					breakpoint : 1024,
					settings : {
						slidesToShow : 3
					}
				}, {
					breakpoint : 768,
					settings : {
						slidesToShow : 2
					}
				}, {
					breakpoint : 480,
					settings : {
						slidesToShow : 1
					}
				} ]
			});
		});

		function scrollToRecipes() {
			const target = document.getElementById("recipe-section");
			if (target) {
				target.scrollIntoView({
					behavior : "smooth"
				});
			}
		}
	</script>
</body>
</html>
