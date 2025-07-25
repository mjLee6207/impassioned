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

</head>
<body>
	<jsp:include page="/common/header2.jsp" />


	<!-- ✅ 사이트 로고 영역 -->
	<div class="logo">CheForest</div>

	<!-- ✅ 검색창 -->
	<div class="search-bar">
		<input type="text" placeholder="원하는 레시피를 검색해보세요 !">
	</div>

	<!-- ✅ 오늘의 추천 레시피 제목 -->
	<div class="section-title">🍽️ 오늘의 추천 레시피</div>

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
			<p class="title">양갈비 스테이크와 소스 6종</p></a> <a class="recipe"
			href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/라자냐.jpg" alt="라자냐">
			<p class="title">바질 크림소스 라자냐</p>
		</a>
	</div>


	<!-- ✅ 오늘의 추천 레시피 제목 -->
	<div class="section-title">🍽️ 한식 레시피</div>

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

	<div class="section-title">🍽️ 양식 레시피</div>

	<div class="recipes">

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/카나페.jpg" alt="카나페">
			<p class="title">훈제연어카나페</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/오믈렛.jpg" alt="오믈렛">
			<p class="title">오믈렛</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/토마토.jpg" alt="토마토">
			<p class="title">토마토파스타</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/스테이크.jpg" alt="스테이크">
			<p class="title">스테이크</p>

		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/라자냐.jpg" alt="라자냐">
			<p class="title">라자냐</p>
		</a>
	</div>

	<div class="section-title">🍽️ 중식 레시피</div>

	<div class="recipes">
		<!-- 예시 레시피 카드 1 -->
		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/마파두부.jpg" alt="마파두부">
			<p class="title">마파두부</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/동파육.jpg" alt="동파육">
			<p class="title">동파육</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/중화새우볶음밥.jpg" alt="중화새우볶음밥">
			<p class="title">중화새우볶음밥</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/딤섬.jpg" alt="딤섬">
			<p class="title">딤섬</p>

		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/해신탕.jpg" alt="해신탕">
			<p class="title">해신탕</p>
		</a>
	</div>

	<div class="section-title">🍽️ 일식 레시피</div>

	<div class="recipes">

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/히츠마.jpg" alt="히츠마">
			<p class="title">히츠마부시(장어덮밥)</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/돈카츠.jpg" alt="돈카츠">
			<p class="title">돈카츠</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/타마고야끼.jpg" alt="타마고야끼">
			<p class="title">타마고야끼</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/오니기리.jpg" alt="오니기리">
			<p class="title">오니기리</p>

		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/야끼소바.jpg" alt="야끼소바">
			<p class="title">야끼소바</p>
		</a>

	</div>

	<div class="section-title">🍽️ 디저트 레시피</div>

	<div class="recipes">

		<a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/브라우니.jpg" alt="브라우니">
			<p class="title">브라우니</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/스콘.jpg" alt="스콘">
			<p class="title">스콘</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/초코칩쿠키.jpg" alt="초코칩쿠키">
			<p class="title">초코칩쿠키</p>
		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/피칸파이.jpg" alt="피칸파이">
			<p class="title">피칸파이</p>

		</a> <a class="recipe" href="https://www.naver.com" target="_blank"> <img
			src="<%=request.getContextPath()%>/images/home/레몬파운드.jpg" alt="레몬파운드">
			<p class="title">레몬파운드케이크</p>
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
