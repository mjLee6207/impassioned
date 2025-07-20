<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        crossorigin="anonymous">
        <jsp:include page="/common/header2.jsp" />
</head>
<body>

<div class="logo">
	<img src="<%=request.getContextPath()%>/images/home/main.png" alt="메인로고">
</div><!-- 로고 들어가야되는 위치 -->
<div class="search-bar">
    <form id="mainSearchForm" action="/search/all.do" method="get" autocomplete="off">
  <input type="text" id="searchKeyword" name="keyword" placeholder="원하는 레시피를 검색해보세요 !" />
</form>
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

<!-- 한식 -->
<div class="section-title">🍽️ 한식 레시피</div>
<div class="recipes">
  <c:forEach var="recipe" items="${koreanRecipe}">
    <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
      <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
      <p class="title">${recipe.titleKr}</p>
    </a>
  </c:forEach>
</div>

<!-- 양식 -->
<div class="section-title">🍽️ 양식 레시피</div>
<div class="recipes">
  <c:forEach var="recipe" items="${westernRecipe}">
    <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
      <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
      <p class="title">${recipe.titleKr}</p>
    </a>
  </c:forEach>
</div>

<!-- 중식 -->
<div class="section-title">🍽️ 중식 레시피</div>
<div class="recipes">
  <c:forEach var="recipe" items="${chineseRecipe}">
    <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
      <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
      <p class="title">${recipe.titleKr}</p>
    </a>
  </c:forEach>
</div>

<!-- 일식 -->
<div class="section-title">🍽️ 일식 레시피</div>
<div class="recipes">
  <c:forEach var="recipe" items="${japaneseRecipe}">
    <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
      <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
      <p class="title">${recipe.titleKr}</p>
    </a>
  </c:forEach>
</div>

<!-- 디저트 -->
<div class="section-title">🍽️ 디저트 레시피</div>
<div class="recipes">
  <c:forEach var="recipe" items="${dessertRecipe}">
    <a class="recipe" href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipe.recipeId}">
      <img src="${recipe.thumbnail}" alt="${recipe.titleKr}">
      <p class="title">${recipe.titleKr}</p>
    </a>
  </c:forEach>
</div>

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
</script>
</body>
</html>
