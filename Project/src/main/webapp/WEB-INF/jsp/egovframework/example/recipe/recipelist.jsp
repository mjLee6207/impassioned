<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>레시피 리스트</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/recipelist.css">
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/pagination.css">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />


<div class="main-flex">
    <!-- 사이드바 영역 -->
    <div class="sidebar">
        <jsp:include page="/common/sidebar.jsp" />
    </div>

    <!-- 본문 컨텐츠 영역 -->
    <div class="content-area">

        <!-- 카테고리 탭 (게시판 동일) -->
        <div class="category-tabs">
            <a href="${pageContext.request.contextPath}/recipe/recipe.do"
               class="category-tab${empty param.categoryKr ? ' active' : ''}">전체</a>
            <a href="${pageContext.request.contextPath}/recipe/recipe.do?categoryKr=한식&pageIndex=1"
               class="category-tab${param.categoryKr eq '한식' ? ' active' : ''}">한식</a>
            <a href="${pageContext.request.contextPath}/recipe/recipe.do?categoryKr=양식&pageIndex=1"
               class="category-tab${param.categoryKr eq '양식' ? ' active' : ''}">양식</a>
            <a href="${pageContext.request.contextPath}/recipe/recipe.do?categoryKr=중식&pageIndex=1"
               class="category-tab${param.categoryKr eq '중식' ? ' active' : ''}">중식</a>
            <a href="${pageContext.request.contextPath}/recipe/recipe.do?categoryKr=일식&pageIndex=1"
               class="category-tab${param.categoryKr eq '일식' ? ' active' : ''}">일식</a>
            <a href="${pageContext.request.contextPath}/recipe/recipe.do?categoryKr=디저트&pageIndex=1"
               class="category-tab${param.categoryKr eq '디저트' ? ' active' : ''}">디저트</a>
        </div>

        <!-- 검색창 -->
        <form action="${pageContext.request.contextPath}/recipe/recipe.do"
              method="get" class="search-area">
            <input type="hidden" name="categoryKr" value="${param.categoryKr}" />
            <input type="text" class="search-input" id="searchKeyword"
                   name="searchKeyword"
                   value="${empty param.searchKeyword ? '' : param.searchKeyword}"
                   placeholder="레시피명으로 검색">
            <button type="submit" class="search-btn">
                <div class="sbtn">
                    <i class="bi bi-search"></i>
                </div>
            </button>
        </form>
        <!-- 레시피 리스트 영역 -->
        <div class="recipe-list-section">
            <div class="recipe-grid">
                <c:forEach var="recipeList" items="${recipeList}">
                    <div class="recipe-card">
                        <a href="${pageContext.request.contextPath}/recipe/view.do?recipeId=${recipeList.recipeId}&categoryKr=${param.categoryKr}&pageIndex=${pageIndex}">
                            <img src="${recipeList.thumbnail}"
                        	     loading="lazy"
                                 alt="썸네일" class="recipe-thumb-img" />
                            <div class="recipe-title">
                                <b>${recipeList.titleKr}</b>
                            </div>
                        </a>
                    </div>
                </c:forEach>
                 <c:if test="${empty recipeList}">
                    <div class="no-recipe-msg">레시피가 없습니다.</div>
                </c:if> 
            </div>
        </div>

        <!-- 페이지네이션 -->
        <div class="flex-center">
            <ul class="pagination" id="pagination"></ul>
        </div>
    </div>
</div>
<!-- 페이지네이션 플러그인 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/twbs-pagination@1.4.2/jquery.twbsPagination.min.js"></script>

<script>
$('#pagination').twbsPagination({
    totalPages: ${paginationInfo.totalPageCount},
    startPage: parseInt("${paginationInfo.currentPageNo}"),
    visiblePages: 10,
    initiateStartPageClick: false,
    first: '&laquo;',
    prev: '&lt;',
    next: '&gt;',
    last: '&raquo;',
    onPageClick: function (event, page) {
        var params = new URLSearchParams(window.location.search);
        params.set('pageIndex', page);
        window.location.search = params.toString();
    }
});

// 카테고리 select 유지 JS (boardlist.js와 동일)
document.addEventListener("DOMContentLoaded", function () {
    const hiddenCategoryInput = document.querySelector("input[name='categoryKr']");
    const currentCategory = new URLSearchParams(window.location.search).get("categoryKr");
    if (hiddenCategoryInput && currentCategory) {
        hiddenCategoryInput.value = currentCategory;
    }
});
</script>
<!-- 꼬리말 jsp include-->
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
