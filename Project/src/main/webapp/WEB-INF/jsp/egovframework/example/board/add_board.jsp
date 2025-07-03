
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>게시글 작성</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
<style>
body {
	background: #f8f9fa;
}

.main-wrap {
	display: flex;
	gap: 24px;
	max-width: 1100px;
	margin: 32px auto 0 auto;
}

.profile-card {
	width: 260px;
	background: #fff;
	border-radius: 16px;
	box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
	padding: 28px 20px 20px 20px;
	text-align: center;
	border: 1px solid #f0f0f0;
	min-width: 220px;
	display: flex;
	flex-direction: column;
	align-items: center;
}

.profile-img {
	width: 72px;
	height: 72px;
	border-radius: 50%;
	object-fit: cover;
	background: #eaeaea;
	margin-bottom: 10px;
}

.username {
	font-weight: bold;
	font-size: 1.15rem;
	margin-bottom: 2px;
}

.useremail, .joindate {
	color: #b1b1b1;
	font-size: 0.97rem;
	margin-bottom: 2px;
}

.side-menu {
	list-style: none;
	padding: 0;
	margin-top: 36px;
	width: 100%;
}

.side-menu li {
	padding: 13px 0;
	font-size: 1.09rem;
	color: #222;
	display: flex;
	align-items: center;
	justify-content: center;
	cursor: pointer;
	transition: background 0.15s, color 0.13s;
	border-radius: 9px;
	margin-bottom: 6px;
	font-weight: 500;
}

.side-menu li.active, .side-menu li:hover {
	background: #f7f7f7;
	color: #218859;
}

.board-wrap {
	flex: 1;
	background: #fff;
	border-radius: 16px;
	box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
	padding: 36px 38px 38px 38px;
	min-width: 0;
}

.category-tabs {
	display: flex;
	gap: 32px;
	margin-bottom: 23px;
	border-bottom: 2.5px solid #eee;
}

.category-tab {
	font-size: 1.12rem;
	color: #aaa;
	padding: 13px 6px 10px 6px;
	background: none;
	border: none;
	border-bottom: 2.5px solid transparent;
	font-weight: 600;
	cursor: pointer;
	transition: .11s;
}

.category-tab.active {
	color: #118e6a;
	border-bottom: 2.5px solid #1e8a57;
}

.category-badge {
	background: #e8f8f1;
	color: #118e6a;
	font-size: 1rem;
	border-radius: 7px;
	padding: 3px 15px 4px 15px;
	margin-right: 10px;
}

.post-btns {
	margin-top: 38px;
}

.post-btns .btn {
	min-width: 90px;
}

@media ( max-width : 991px) {
	.main-wrap {
		flex-direction: column;
		gap: 10px;
		max-width: 99vw;
	}
	.profile-card, .board-wrap {
		width: 96%;
		max-width: 540px;
		margin: 0 auto;
	}
	.board-wrap {
		padding: 18px 5vw 15px 5vw;
	}
	.category-tabs {
		gap: 12px;
		font-size: 1.04rem;
	}
}
</style>
</head>
<body>
	<jsp:include page="/common/header.jsp" />

	<div class="main-wrap">
		<!-- 회원 정보 카드 -->
		<div class="profile-card">
			<img
				src="${member.profileImagePath != null ? member.profileImagePath : '/img/profile-default.png'}"
				class="profile-img" alt="프로필 이미지">
			<div class="username">${member.name}</div>
			<div class="useremail">이메일: ${member.email}</div>
			<div class="joindate">가입일: ${member.joinDate}</div>
			<ul class="side-menu">
				<li><i class="bi bi-person"></i> 내 정보 수정</li>
				<li><i class="bi bi-box-arrow-in-right"></i> 로그아웃</li>
			</ul>
		</div>

		<!-- 게시글 작성 폼 -->
		<div class="board-wrap">
			<div class="board-title">
				<span class="icon">🍽️</span> <span>게시글 작성</span>
			</div>

			<form id="addForm" name="addForm" method="post"
				action="/board/add.do">
				<input type="hidden" name="csrf" value="${sessionScope.CSRF_TOKEN}">
				<input type="hidden" id="category" name="category" value="">

				<!-- 제목 입력 -->
				<div class="mb3">
					<label for="title" class="form-label">제목</label> <input type="text"
						class="form-control" id="title" name="title"
						placeholder="제목을 입력하세요" required />
				</div>

				<!-- 내용 입력 -->
				<div class="mb3">
					<label for="content" class="form-label">내용</label>
					<textarea class="form-control" id="content" name="content" rows="5"
						placeholder="내용을 입력하세요" required></textarea>
				</div>

				<!-- 카테고리 선택 -->
				<div class="category-tabs">
					<button type="button" class="category-tab"
						onclick="selectCategory('korean')">한식</button>
					<button type="button" class="category-tab"
						onclick="selectCategory('western')">양식</button>
					<button type="button" class="category-tab"
						onclick="selectCategory('chinese')">중식</button>
					<button type="button" class="category-tab"
						onclick="selectCategory('japanese')">일식</button>
					<button type="button" class="category-tab"
						onclick="selectCategory('dessert')">디저트</button>
				</div>

				<!-- 사진 업로드 (선택) -->
				<div class="mb3">
					<label for="imgPath" class="form-label">사진 업로드</label> <input
						type="file" class="form-control" id="imgPath" name="imgPath" />
				</div>

				<div class="post-btns">
					<button type="submit" class="btn btn-success btn-sm">저장</button>
					<a href="/board.do" class="btn btn-secondary btn-sm">목록</a>
				</div>
			</form>
		</div>
	</div>

	<script>
		// 카테고리 선택 함수
		function selectCategory(category) {
			// 선택된 카테고리를 폼에 넣어주기
			document.getElementById("category").value = category;

			// 선택된 카테고리 활성화
			var tabs = document.querySelectorAll(".category-tab");
			tabs.forEach(function(tab) {
				tab.classList.remove("active");
			});

			document.querySelector(".category-tab[onclick='selectCategory(\""
					+ category + "\")']").classList.add("active");
		}
	</script>

	<jsp:include page="/common/footer.jsp" />
</body>
</html>
