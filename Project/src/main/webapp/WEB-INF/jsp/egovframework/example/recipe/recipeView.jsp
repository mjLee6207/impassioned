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
  <link rel="stylesheet" href="/css/like-override.css" />  <!-- 이게 최종 적용됩니다 -->
  
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
            <img src="${recipeVO.thumbnail}" alt="요리 이미지" width="400px" class="recipe-img" />
          </div>
          
          <!-- ★ 제목과 카테고리 뱃지: recipe-top-row 안으로 이동 ★ -->
          <div class="recipe-title-outer">
            <div class="recipe-cat-badge">${recipeVO.categoryKr}</div>
            <div class="recipe-title-main">${recipeVO.titleKr}</div>
          </div>
        </div>
        
        <!-- ★ like-btn-wrap 은 그대로 recipe-card-top 바로 자식 ★ -->
        <div class="like-btn-wrap">
          <!-- 하트 버튼 -->
          <button 
            type="button" 
            class="like-btn" 
            id="likeBtn"
            data-recipe-id="${not empty recipeVO.recipeId ? recipeVO.recipeId : ''}"
            data-member-idx="${loginUser.memberIdx}"
          >
            ♡
          </button>
          <!-- 숫자만 0 으로 -->
          <span class="like-count-text" id="likeCountText">0</span>
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
			  <ul style="list-style:none; padding-left:0;">
			    <c:forEach var="item" items="${recipeVO.ingredientDisplayList}">
			      <li>${item}</li>
			    </c:forEach>
			  </ul>
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
      const recipeId  = $("#likeBtn").data("recipe-id");
      const memberIdx = $("#likeBtn").data("member-idx");

      console.log("🔥 recipeId:", recipeId);

      initLikeButton({
        likeType: "RECIPE",
        recipeId: String(recipeId),
        memberIdx
      });
    });
  </script>

  <!-- 꼬리말 jsp include-->
  <jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
