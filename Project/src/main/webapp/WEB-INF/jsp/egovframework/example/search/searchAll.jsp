<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>CheForest 통합 검색</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="/css/searchAll.css">
  <link rel="stylesheet" href="/css/sidebar.css">
  <link rel="stylesheet" href="/css/header.css">
  <link rel="stylesheet" href="/css/footer.css">
  
</head>
<body>
  <!-- 헤더는 무조건 맨 위에 한 번만! -->
  <jsp:include page="/common/header.jsp" />

  <div class="main-wrap">
    <!-- 왼쪽: 사이드바 -->
    <jsp:include page="/common/sidebar.jsp" />

    <!-- 오른쪽: 본문 영역 -->
    <div class="main-content">
      <div class="container">
        <!-- 통합 검색 타이틀 -->
        <div style="margin-bottom: 30px;">
          <h3 style="font-weight:700;color:#2db183;margin-bottom:0;">
            CheForest <span style="color:#36474f;">통합 검색</span>
          </h3>
        </div>

        <!-- 레시피 검색 결과 박스 -->
        <div class="search-section card-style">
          <div class="section-header">
            <span class="section-title">레시피 검색결과</span>
            	<c:if test="${fn:length(recipeList) >= 8}">
    				<a href="/recipe/recipe.do?categoryKr=${categoryKr}&searchKeyword=${searchKeyword}&pageIndex=1" class="more-link">더 보기</a>
 			 	</c:if>
          </div>
          <div class="recipe-grid">
            <c:forEach var="recipe" items="${recipeList}">
              <div class="recipe-card">
                <a href="/recipe/view.do?recipeId=${recipe.id}">
                  <img src="${recipe.thumbnail}" alt="썸네일" class="recipe-thumb-img"/>
                  <div class="recipe-title"><b>${recipe.title}</b></div>
                </a>
              </div>
            </c:forEach>
            <c:if test="${empty recipeList}">
              <div class="no-recipe-msg">검색 결과가 없습니다.</div>
            </c:if>
          </div>
        </div>

        <!-- 게시판 검색 결과 박스 -->
        <div class="search-section card-style">
          <div class="section-header">
            <span class="section-title">게시판 검색결과</span>
           	<c:if test="${fn:length(boardList) >= 8}">
    		<a href="/board/board.do?category=${category}&searchKeyword=${searchKeyword}&pageIndex=1" class="more-link">더 보기</a>
  			</c:if>
          </div>
          <div class="recipe-grid">
            <c:forEach var="board" items="${boardList}">
              <div class="recipe-card">
                <a href="/board/view.do?boardId=${board.id}">
               
                  <img src="${empty board.thumbnail ? '/images/no-image.png' : board.thumbnail}" alt="썸네일" class="recipe-thumb-img"/>
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
    </div>
  </div>
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
