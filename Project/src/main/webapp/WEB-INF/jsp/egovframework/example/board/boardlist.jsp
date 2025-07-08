<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://egovframework.gov/ctl/ui" prefix="ui" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>요리정보 카테고리 게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/boardlist.css">
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/pagination.css">


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


			<!-- 카테고리 탭 -->
			<div class="category-tabs">
				<a href="${pageContext.request.contextPath}/board/board.do?category=한식"
					class="category-tab${param.category eq '한식' || empty param.category ? ' active' : ''}">한식</a>
				<a href="${pageContext.request.contextPath}/board/board.do?category=양식"
					class="category-tab${param.category eq '양식' ? ' active' : ''}">양식</a>
				<a href="${pageContext.request.contextPath}/board/board.do?category=중식"
					class="category-tab${param.category eq '중식' ? ' active' : ''}">중식</a>
				<a href="${pageContext.request.contextPath}/board/board.do?category=일식"
					class="category-tab${param.category eq '일식' ? ' active' : ''}">일식</a>
				<a href="${pageContext.request.contextPath}/board/board.do?category=디저트"
					class="category-tab${param.category eq '디저트' ? ' active' : ''}">디저트</a>
			</div>

			<!-- 글쓰기 버튼 -->
			<c:choose>
				<c:when test="${empty sessionScope.loginUser}">
					<!-- 비로그인 시: 로그인 페이지로 redirect 파라미터 포함 이동 -->
					<a href="${pageContext.request.contextPath}/member/login.do?redirect=/board/add.do"
					   class="write-btn">
						<i class="bi bi-pencil-square"></i> 글쓰기
					</a>
				</c:when>
				<c:otherwise>
					<!-- 로그인 시: 바로 글쓰기 페이지 이동 -->
					<a href="${pageContext.request.contextPath}/board/add.do"
					   class="write-btn">
						<i class="bi bi-pencil-square"></i> 글쓰기
					</a>
				</c:otherwise>
			</c:choose>
			
			<!-- 검색창 -->
			<form action="${pageContext.request.contextPath}/board/board.do"
				method="get" class="search-area">
				
				   <!-- ✅ 현재 게시판 카테고리 유지 -->
				   <!-- 카테고리별검새긱능 추가 - 7월 8일 승태  -->
    <input type="hidden" name="category" value="${param.category}" />
				<!-- 7/7 인풋태그안에 검색창 null 제거 (민중) -->
				<input type="text" class="search-input" id="searchKeyword"
				       name="searchKeyword"
				       value="${empty param.searchKeyword ? '' : param.searchKeyword}"
				       placeholder="제목으로 검색">
				<button type="submit" class="search-btn">
					<i class="bi bi-search"></i>
				</button>
			</form>
			
			<!-- 인기게시글 영역 (여기만 c:choose로 대체) -->
			<div class="popular-posts-section">
				<div class="popular-posts-title">
					<h5>Best Post</h5>
				</div>
				<div class="top-posts-row">
					<c:choose>
						<c:when test="${not empty bestPosts}">
							<c:forEach var="board" items="${bestPosts}">
								<div class="top-post-card">
									<a href="${pageContext.request.contextPath}/board/view.do?boardId=${board.boardId}">
										<img src="${board.thumbnail}" class="top-thumb-img" alt="썸네일" />
										<div class="top-title">
											<b>${board.title}</b>
										</div>
										<div class="top-author">${board.nickname}</div>
									</a>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<div style="color: #aaa; font-size: 1rem;">인기 게시글이 아직 없습니다.</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
        <!-- 최신글 테이블 -->
        <table class="post-table">
            <thead>
            <tr>
                <th style="width:60%;">제목</th>
                <th style="width:14%;">작성자</th>
                <th style="width:13%;">작성일</th>
                <th style="width:13%;">조회수</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="board" items="${boards}">
                <tr>
                    <td style="text-align:left;">
                        <a href="${pageContext.request.contextPath}/board/view.do?boardId=${board.boardId}" class="post-title-link">${board.title}</a>
                    </td>
                    <td>${board.nickname}</td>
                    <td><fmt:formatDate value="${board.writeDate}" pattern="yyyy-MM-dd"/></td>
                    <td>${board.viewCount}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 페이지네이션 -->
        <!-- 페이지 이동용 폼 (전용!) -->
		<form id="searchForm" method="get" action="${pageContext.request.contextPath}/board/board.do">
		    <input type="hidden" id="pageIndex" name="pageIndex" value="${paginationInfo.currentPageNo}" />
		    <input type="hidden" name="category" value="${param.category}" />
		    <input type="hidden" name="searchKeyword" value="${param.searchKeyword}" />
		</form>


		<!-- 페이지네이션 -->
	<div class="flex-center">
    <ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_link_page"/>
    
   
</div>
    </div>
</div>

			<!-- 페이지네이션 -->
			<div class="flex-center">
				<ul class="pagination" id="pagination"></ul>
			</div>
		</div>
	</div>

	<script>
    // 엔터키로 검색 - 초기 추가: 강승태 
    document.addEventListener("DOMContentLoaded", () => {
        const input = document.querySelector("#searchKeyword");
        input?.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                input.form?.submit();
            }
        });
    });
    

    // 페이지네이션
    function fn_link_page(pageNo){
        var form = document.getElementById('searchForm');
        form.pageIndex.value = pageNo;
        form.submit();
    }
</script>

<script>
document.addEventListener('DOMContentLoaded', function () {
    // 처음으로
    document.querySelectorAll('.flex-center .first a').forEach(function(a){
        a.innerHTML = '<i class="bi bi-chevron-bar-left"></i>';
        a.setAttribute('title', '처음');
    });
    // 이전
    document.querySelectorAll('.flex-center .prev a').forEach(function(a){
        a.innerHTML = '<i class="bi bi-chevron-left"></i>';
        a.setAttribute('title', '이전');
    });
    // 다음
    document.querySelectorAll('.flex-center .next a').forEach(function(a){
        a.innerHTML = '<i class="bi bi-chevron-right"></i>';
        a.setAttribute('title', '다음');
    });
    // 맨끝으로
    document.querySelectorAll('.flex-center .last a').forEach(function(a){
        a.innerHTML = '<i class="bi bi-chevron-bar-right"></i>';
        a.setAttribute('title', '맨끝');
    });
});
</script> 
<!-- 카테고리별 검색을 위해 추가: 7월 8일 강승태  -->
<script>
  document.addEventListener("DOMContentLoaded", function () {
    const hiddenCategoryInput = document.querySelector("input[name='category']");
    const currentCategory = new URLSearchParams(window.location.search).get("category");
    if (hiddenCategoryInput && currentCategory) {
      hiddenCategoryInput.value = currentCategory;
    }
  });
</script>

</body>
</html>