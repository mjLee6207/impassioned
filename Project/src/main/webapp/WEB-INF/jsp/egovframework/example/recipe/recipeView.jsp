<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
   <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/recipeview.css" />
<title>레시피 페이지_CheForest</title>
</head>
<body>
<jsp:include page="/common/header.jsp" />

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
    <img src="http://www.foodsafetykorea.go.kr/uploadimg/cook/10_00671_1.png" alt="요리 이미지" width="400px" class="recipe-img" />
  </div>
  <div class="recipe-title-outer">
    <div class="recipe-cat-badge">한식</div>
    <div class="recipe-title-main">표고버섯 감자찜</div>
    
  </div>
</div>
</div>
  <!-- 재료(토글, 2단 표) -->
<div class="recipe-card">
  <div class="section-title" style="display:flex; align-items:center; justify-content:space-between;">
    <span>재료</span>
    <button id="toggle-ingredients" class="category-tab" type="button">숨기기</button>
  </div>
  <div id="ingredients-box">
   <div class="ingredient-table-2col-wrap">
  <%-- <table class="ingredient-table-2col">
    <!-- 재료표 2단분리 스타일 -->
    <colgroup>
      <col style="width: 25%;">
      <col style="width: 15%;">
      <col style="width: 25%;">
      <col style="width: 15%;">
    </colgroup>
    <tbody>
      <tr>
        <td class="ingredient-name">밀가루</td>
        <td class="ingredient-amount">2컵</td>
        <td class="ingredient-name">설탕</td>
        <td class="ingredient-amount">1/2컵</td>
      </tr>
      <tr>
        <td class="ingredient-name">우유</td>
        <td class="ingredient-amount">1/2컵</td>
        <td class="ingredient-name">버터</td>
        <td class="ingredient-amount">30g</td>
      </tr>
  
    <c:forEach var="i" begin="0" end="${fn:length(재료)/2 - 1}">
      <tr>
        <td class="ingredient-name">${재료[i]}</td>
        <td class="ingredient-amount">${계량[i]}</td>
        <td class="ingredient-divider"></td>
        <td class="ingredient-name">${재료[i + fn:length(재료)/2]}</td>
        <td class="ingredient-amount">${계량[i + fn:length(재료)/2]}</td>
      </tr>
    </c:forEach>
  </tbody>
</table> --%>

</div>
  </div>
</div>



  <!-- 조리법 -->
  <div class="recipe-card">
    <div class="section-title">조리법</div>
    <div class="recipe-content">

    	<!-- RecipeID 671 표고버섯감자찜 임시데이터 -->
		1. 표고버섯은 따뜻한 물에 불린다. <br>
		2. 감자는 강판에 간다.<br>
		3. 고수는 잘게 다진다.<br>
		4. 당근은 곱게 다지고, 양파와 홍고추도 다진다.<br>
		5. 감자에 다진 당근과 양파, 홍고추를 섞는다.<br>
		6. 불린 표고버섯은 기둥을 자르고 바닥에 녹말가루를 묻혀 ?번을 넣고 김이오른 찜통에 약 10분 정도 찐다.<br>

    </div>
  </div>
</div>
</div>
<!-- 재료_토글 -->
<script>
const toggleBtn = document.getElementById('toggle-ingredients');
const box = document.getElementById('ingredients-box');

toggleBtn.onclick = function() {
  const isHidden = box.style.display === 'none';
  box.style.display = isHidden ? 'block' : 'none';
  toggleBtn.innerText = isHidden ? '숨기기' : '보이기';
};
// 초기상태: 보여진 상태라면 "숨기기"가 맞음!
</script>
</body>
</html>