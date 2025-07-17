<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>CheForest 메인페이지</title>
    <link rel="stylesheet" href="/css/home.css" />
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/footer.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        crossorigin="anonymous">
</head>
<body>
<jsp:include page="/common/header2.jsp" />

<div class="logo">CheForest</div>
<div class="search-bar">
    <input type="text" id="searchKeyword" placeholder="원하는 레시피를 검색해보세요 !" />
</div>

<!-- 오늘의 추천 레시피(수정X) -->
<div class="section-title">🍽️ 오늘의 추천 레시피</div>
<div class="recipes">
    <a class="recipe" href="https://www.naver.com" target="_blank">
        <img src="<%=request.getContextPath()%>/images/home/라자냐.jpg" alt="라자냐">
        <p class="title">바질 크림소스 라자냐</p>
    </a>
    <a class="recipe" href="https://www.naver.com" target="_blank">
        <img src="<%=request.getContextPath()%>/images/home/비프웰링턴.jpg" alt="비프웰링턴">
        <p class="title">비프웰링턴과 단호박무스</p>
    </a>
    <a class="recipe" href="https://www.naver.com" target="_blank">
        <img src="<%=request.getContextPath()%>/images/home/무스.jpg" alt="무스">
        <p class="title">크림치즈 라즈베리 무스</p>
    </a>
    <a class="recipe" href="https://www.naver.com" target="_blank">
        <img src="<%=request.getContextPath()%>/images/home/양갈비.jpg" alt="양갈비">
        <p class="title">양갈비스테이크&소스6종</p>
    </a>
    <a class="recipe" href="https://www.naver.com" target="_blank">
        <img src="<%=request.getContextPath()%>/images/home/라자냐.jpg" alt="라자냐">
        <p class="title">바질 크림소스 라자냐</p>
    </a>
</div>

<!-- 한식 슬라이드 -->
<div class="section-title">🍽️ 한식 레시피</div>
<div class="slide-recipes" data-category="korean">
  <div class="slide-list">
    <c:forEach var="recipe" items="${koreanRecipe}">
      <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
        <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
        <p class="title">${recipe.titleKr}</p>
      </a>
    </c:forEach>
  </div>
</div>

<!-- 양식 슬라이드 -->
<div class="section-title">🍽️ 양식 레시피</div>
<div class="slide-recipes" data-category="western">
  <div class="slide-list">
    <c:forEach var="recipe" items="${westernRecipe}">
      <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
        <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
        <p class="title">${recipe.titleKr}</p>
      </a>
    </c:forEach>
  </div>
</div>

<!-- 중식 슬라이드 -->
<div class="section-title">🍽️ 중식 레시피</div>
<div class="slide-recipes" data-category="chinese">
  <div class="slide-list">
    <c:forEach var="recipe" items="${chineseRecipe}">
      <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
        <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
        <p class="title">${recipe.titleKr}</p>
      </a>
    </c:forEach>
  </div>
</div>

<!-- 일식 슬라이드 -->
<div class="section-title">🍽️ 일식 레시피</div>
<div class="slide-recipes" data-category="japanese">
  <div class="slide-list">
    <c:forEach var="recipe" items="${japaneseRecipe}">
      <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
        <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
        <p class="title">${recipe.titleKr}</p>
      </a>
    </c:forEach>
  </div>
</div>

<!-- 디저트 슬라이드 -->
<div class="section-title">🍽️ 디저트 레시피</div>
<div class="slide-recipes" data-category="dessert">
  <div class="slide-list">
    <c:forEach var="recipe" items="${dessertRecipe}">
      <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
        <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
        <p class="title">${recipe.titleKr}</p>
      </a>
    </c:forEach>
  </div>
</div>

<jsp:include page="/common/footer.jsp" />

<script>
document.addEventListener("DOMContentLoaded", function () {
    const perPage = 5;
    const slideWidth = 224;
    const slideDuration = 3000;

    document.querySelectorAll('.slide-recipes').forEach(function (box, idx) {
        const track = box.querySelector('.slide-list');
        const items = track.querySelectorAll('.recipe');
        const total = items.length;
        const maxPage = Math.max(1, total - perPage + 1);

        track.style.width = `${total * slideWidth}px`;

        let currentPage = 0;
        function goToPage(page) {
            const move = page * slideWidth;
            track.style.transform = `translateX(-${move}px)`;
            currentPage = page;
        }
        goToPage(0);

        if (total > perPage) {
            setInterval(function () {
                let nextPage = (currentPage + 1) % maxPage;
                goToPage(nextPage);
            }, slideDuration);
        }
    });
});
</script>
</body>
</html>
