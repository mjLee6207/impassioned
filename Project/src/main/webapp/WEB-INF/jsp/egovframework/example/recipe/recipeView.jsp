<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레시피 페이지_CheForest</title>
   <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/recipeview.css" />
    <jsp:include page="/common/header.jsp" />
</head>
<body>
<c:set var="currPageIndex" value="${empty param.pageIndex ? 1 : param.pageIndex}" />
<div class="main-wrap">
	<!-- 사이드바 영역 -->
		<div class="sidebar-wrap">
			<jsp:include page="/common/sidebar.jsp" />
		</div>
		
		<!-- 오른쪽 컨텐츠 영역 -->
		<div class="content-wrap">
		
  <!-- 상단: 이미지 + 제목 + 카테고리 -->
<div class="recipe-card-top">
  <div class="recipe-top-row">
  <div class="recipe-img-outer">
    <img src=${recipeVO.thumbnail} alt="요리 이미지" width="400px" class="recipe-img" />
  </div>
  <!-- 목록보기 버튼 : 주석처리 -->
  <%-- <a href="${pageContext.request.contextPath}/recipe/recipe.do?categoryKr=${param.categoryKr}&pageIndex=${currPageIndex}" class="tolist-btn">목록</a> --%>

   <div class="like-btn-wrap">
    <div class="like-btn-wrap">
      <button type="button" class="like-btn" id="likeBtn"
data-recipe-id="${not empty recipeVO.recipeId ? recipeVO.recipeId : ''}"
              data-member-idx="${loginUser.memberIdx}">♡</button>
      <div class="like-count-text" id="likeCountText">좋아요 0개</div>
    </div>
</div>


  <div class="recipe-title-outer">
    <div class="recipe-cat-badge">${recipeVO.categoryKr}</div>
    <div class="recipe-title-main">${recipeVO.titleKr}</div>
  </div>
</div>
</div>
  <!-- 재료(토글) -->
<div class="recipe-card">
  <div class="section-title" style="display:flex; align-items:center; justify-content:space-between;">
    <span>재료</span>
    <button id="toggle-ingredients" class="category-tab" type="button">숨기기</button>
  </div>
  <div id="ingredients-box">
   <div class="ingredient-table-2col-wrap">
   ${recipeVO.ingredientKr}
</div>
  </div>
</div>

  <!-- 조리법 -->
  <div class="recipe-card">
    <div class="section-title">조리법</div>
    <div class="recipe-content">
		${recipeVO.instructionKr}
    </div>
  </div>
</div>
</div>
<!-- 재료_토글 -->

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/like.js"></script>

<script>
const toggleBtn = document.getElementById('toggle-ingredients');
const box = document.getElementById('ingredients-box');

toggleBtn.onclick = function() {
  const isHidden = box.style.display === 'none';
  box.style.display = isHidden ? 'block' : 'none';
  toggleBtn.innerText = isHidden ? '숨기기' : '보이기';
};

$(document).ready(function () {
	  const recipeId = $("#likeBtn").data("recipe-id");
	  const memberIdx = $("#likeBtn").data("member-idx");

	  console.log("🔥 recipeId:", recipeId);  // 여기에 꼭 찍어보기

	  initLikeButton({
	    likeType: "RECIPE",
	    recipeId: String(recipeId),     // ✅ 이 부분을 수정!
	    memberIdx
	  });
	});

</script>
<!-- 꼬리말 jsp include-->
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>