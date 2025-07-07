<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>CheForest 메인페이지</title>
<!-- 공통 스타일시트 연결 -->
<link rel="stylesheet" href="/css/home.css" />
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/footer.css">

<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 	부트스트랩 css  -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<!-- 	개발자 css -->
<link rel="stylesheet" href="/css/style.css">

</head>
<body>
	<!-- ✅ 상단 로그인/로그아웃/마이페이지 영역 -->
	<div class="top-bar">
		<c:choose>
			<c:when test="${not empty sessionScope.loginUser}">
				<p>${sessionScope.loginUser.nickname}님환영합니다!</p>
				<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
				<a href="${pageContext.request.contextPath}/mypage/mypage.do">마이페이지</a>

			</c:when>
			<c:otherwise>
				<!-- hyj 7/7 영문변환 및 &nbsp; 추가 -->
				<a href="${pageContext.request.contextPath}/member/login.do">LOGIN</a>&nbsp;&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/member/login.do">JOIN
					US</a>&nbsp;&nbsp;&nbsp;
  </c:otherwise>
		</c:choose>
	</div>

	<!-- ✅ 사이트 로고 영역 -->
	<div class="logo">CheForest</div>

	<!-- ✅ 검색창 -->
	<div class="search-bar">
		<input type="text" placeholder="레시피를 검색해보세요!">
	</div>

	<!-- ✅ 오늘의 추천 레시피 제목 -->
	<div class="section-title">👨‍🍳 오늘의 추천 레시피</div>

	<!-- ✅ 추천 레시피 카드 목록 -->
	<div class="recipes">
		<!-- 예시 레시피 카드 1 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/라자냐.jpg" alt="라자냐">
			<p class="title">바질 크림소스 라자냐</p>
		</a>

		<!-- 예시 레시피 카드 2 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/비프웰링턴.jpg" alt="비프웰링턴">
			<p class="title">비프웰링턴과 단호박무스</p>
		</a>

		<!-- 예시 레시피 카드 3 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/무스.jpg" alt="무스">
			<p class="title">크림치즈 라즈베리 무스</p>
		</a>

		<!-- 예시 레시피 카드 4 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/양갈비.jpg" alt="스테이크">
			<p class="title">양갈비 스테이크와 소스 6종</p>
	</div>

	<!-- ✅ 오늘의 추천 레시피 제목 -->
	<div class="section-title">👨‍🍳 한식 레시피</div>

	<!-- ✅ 추천 레시피 카드 목록 -->
	<div class="recipes">
		<!-- 예시 레시피 카드 1 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/부찌.jpg" alt="부대찌개">
			<p class="title">부대찌개</p>
		</a>

		<!-- 예시 레시피 카드 2 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/비빔밥.jpg" alt="비빔밥">
			<p class="title">비빔밥</p>
		</a>

		<!-- 예시 레시피 카드 3 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/깍두기.jpg" alt="깍두기">
			<p class="title">깍두기</p>
		</a>

		<!-- 예시 레시피 카드 4 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/꼬막.jpg" alt="꼬막">
			<p class="title">꼬막비빔밥</p>
			
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/김치.jpg" alt="김치">
			<p class="title">배추김치</p>
		</a>
	</div>

	<div class="section-title">👨‍🍳 양식 레시피</div>

	<div class="recipes">

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/라자냐.jpg" alt="라자냐">
			<p class="title">바질 크림소스 라자냐</p>
		</a>

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/비프웰링턴.jpg" alt="비프웰링턴">
			<p class="title">단호박 무스를 곁들인 비프웰링턴</p>
		</a>

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/무스.jpg" alt="무스">
			<p class="title">크림치즈 라즈베리 무스</p>
		</a>

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/양갈비.jpg" alt="스테이크">
			<p class="title">양갈비 스테이크와 소스 6종</p>
			
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/양갈비.jpg" alt="스테이크">
			<p class="title">양갈비 스테이크와 소스 6종</p>
		</a>
	</div>

	<div class="section-title">👨‍🍳 중식 레시피</div>

	<div class="recipes">
		<!-- 예시 레시피 카드 1 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/라자냐.jpg" alt="라자냐">
			<p class="title">바질 크림소스 라자냐</p>
		</a>

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/비프웰링턴.jpg" alt="비프웰링턴">
			<p class="title">단호박 무스를 곁들인 비프웰링턴</p>
		</a>

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/무스.jpg" alt="무스">
			<p class="title">크림치즈 라즈베리 무스</p>
		</a>

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/양갈비.jpg" alt="스테이크">
			<p class="title">양갈비 스테이크와 소스 6종</p>
			
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/양갈비.jpg" alt="스테이크">
			<p class="title">양갈비 스테이크와 소스 6종</p>
		</a>
	</div>

	<div class="section-title">👨‍🍳 일식 레시피</div>

	<div class="recipes">

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/라자냐.jpg" alt="라자냐">
			<p class="title">바질 크림소스 라자냐</p>
		</a>

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/비프웰링턴.jpg" alt="비프웰링턴">
			<p class="title">단호박 무스를 곁들인 비프웰링턴</p>
		</a>
		
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/무스.jpg" alt="무스">
			<p class="title">크림치즈 라즈베리 무스</p>
		</a>

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/양갈비.jpg" alt="스테이크">
			<p class="title">양갈비 스테이크와 소스 6종</p>
			
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/양갈비.jpg" alt="스테이크">
			<p class="title">양갈비 스테이크와 소스 6종</p>
		</a>

		</div>
		<!-- 만들어놓은 꼬리말 jsp -->
		<jsp:include page="/common/footer.jsp"></jsp:include>
		
		<!-- 7월 7일 2시20분 엔터키 검색 메인페이지 추가  -->
		<script>
    // 엔터키로 검색
    document.addEventListener("DOMContentLoaded", () => {
        const input = document.querySelector("#searchKeyword");
        input?.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                input.form?.submit();
            }
        });
    });
</script>
</body>
</html>
