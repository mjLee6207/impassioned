<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="icon" type="image/png" href="/images/favicon.png">
<meta charset="UTF-8">
<title>Footer</title>
<link rel="stylesheet" href="/css/footer.css">
</head>
<body>
	<div class="footer">
		<hr class="footer-divider">

		<div class="footer-content-row">
			<!-- 왼쪽 정보 -->
			<div class="footer-bottom-half">
				<h3>고객센터 1544-9970</h3>
				<!-- <div class="contact-number">1544-9970</div> hyj 고객센터와 같은 줄에 배치 -->
				<p>오전 10시 ~ 오후 5시 (주말, 공휴일 제외)</p>
				<p>법인명(상호): (주)CheForest | 사업자등록번호: 315-88-01684 | 벤처기업: 제
					20200310103호</p>
				<p>특허 제 10-250704호 | 통신판매업신고: 2222-부산진구-2222 | 개인정보보호책임자: 이민중</p>
				<p>주소: 부산 부산진구 범천동 869-28 | 대표이사: 이진수</p>
				<p>
					제휴/협업 문의: <a href="mailto:mjLee6207@gmail.com">mjLee6207@gmail.com</a>
					| 채용문의: <a href="mailto:mjLee6207@gmail.com">mjLee6207@gmail.com</a>
				</p>
				<p class="legal">
					주식회사 CheForest 전자상거래법에 따른 통신판매중개업자입니다.<br> 중개하는 통신판매에 관하여 통신판매의
					당사자가 아니며, 책임을 지지 않습니다.<br> 정산/환불 등에 대한 책임은 (주)CheForest가 지고 있음을
					알려드립니다.<br> 담당자: 이민중 / 연락처: 1544-9970
				</p>
			</div>

			<!-- 오른쪽 작업자 이미지 -->
			<div class="footer-workers">
				<div class="circle">
					<img src="${pageContext.request.contextPath}/images/2조사진/민중.png"
						class="circle-img" alt="민중" />
				</div>
				<div class="circle">
					<img src="${pageContext.request.contextPath}/images/2조사진/승태.png"
						class="circle-img" alt="승태" />
				</div>
				<div class="circle">
					<img src="${pageContext.request.contextPath}/images/2조사진/유준.png"
						class="circle-img" alt="유준" />
				</div>
				<div class="circle">
					<img src="${pageContext.request.contextPath}/images/2조사진/윤주.png"
						class="circle-img" alt="윤주" />
				</div>
				<div class="circle">
					<img src="${pageContext.request.contextPath}/images/2조사진/장호.png"
						class="circle-img" alt="장호" />
				</div>
				<div class="circle">
					<img src="${pageContext.request.contextPath}/images/2조사진/진수.png"
						class="circle-img" alt="진수" />
				</div>
			</div>
		</div>
	</div>
	<!-- 맨 위로 버튼 -->
    <button id="scrollTopBtn" class="scroll-top-btn" title="맨 위로">
      △ Top
    </button>
    <script>
  // 버튼 클릭 시 맨 위로 스크롤
  document.getElementById("scrollTopBtn").onclick = function() {
    window.scrollTo({top: 0, behavior: 'smooth'});
  }
  // 스크롤 내릴 때 버튼 보이게
  window.addEventListener('scroll', function() {
    var btn = document.getElementById("scrollTopBtn");
    if(window.scrollY > 200){
      btn.style.display = "block";
    } else {
      btn.style.display = "none";
    }
  });
</script>
</body>
</html>
