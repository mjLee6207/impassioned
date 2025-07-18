<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>CheForest 통합 검색</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="/css/recipelist.css">
    <link rel="stylesheet" href="/css/searchAll.css">
	
</head>
<body>
  <jsp:include page="/common/header.jsp" />

  <div style="height: 40px;"></div>
  <div class="container" style="max-width: 1050px; margin: 0 auto;">

    <!-- 레시피 검색 결과 -->
    <div class="search-section">
      <div class="section-header">
        <span class="section-title">레시피 검색결과</span>
        <c:if test="${not empty recipeList}">
          <a href="/recipe/searchAll.do?keyword=${param.keyword}" class="more-link">더 보기</a>
        </c:if>
      </div>
      <div class="recipe-grid">
        <c:forEach var="recipe" items="${recipeList}" varStatus="status">
          <div class="recipe-card">
            <a href="/recipe/view.do?recipeId=${recipe.recipeId}">
              <img src="${recipe.thumbnail}" alt="썸네일" class="recipe-thumb-img"/>
              <div class="recipe-title"><b>${recipe.titleKr}</b></div>
            </a>
          </div>
        </c:forEach>
        <c:if test="${empty recipeList}">
          <div class="no-recipe-msg">검색 결과가 없습니다.</div>
        </c:if>
      </div>
    </div>

    <!-- 게시판 검색 결과 -->
    <div class="search-section">
      <div class="section-header">
        <span class="section-title">게시판 검색결과</span>
        <c:if test="${not empty boardList}">
          <a href="/board/searchAll.do?keyword=${param.keyword}" class="more-link">더 보기</a>
        </c:if>
      </div>
      <div class="recipe-grid">
        <c:forEach var="board" items="${boardList}" varStatus="status">
          <div class="recipe-card">
            <a href="/board/view.do?boardId=${board.boardId}">
              <img src="${board.thumbnail}" alt="썸네일" class="recipe-thumb-img"/>
              <div class="recipe-title"><b>${board.title}</b></div>
            </a>
          </div>
        </c:forEach>
        <c:if test="${empty boardList}">
          <div class="no-recipe-msg">검색 결과가 없습니다.</div>
        </c:if>
      </div>
    </div>
  </div>

  <jsp:include page="/common/footer.jsp" />
</body>
</html>