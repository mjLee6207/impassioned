<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>요리정보 카테고리 게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/boardwrite.css">
    <link rel="stylesheet" href="/css/sidebar.css" />
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="main-flex">
    <!-- 사이드바 영역 -->
    <jsp:include page="/common/sidebar.jsp" />

    <!-- 본문 컨텐츠 영역 -->
    <div class="content-area">
        <!-- 탭 -->
        <div class="category-tabs">
            <div class="category-tab active">한식</div>
            <div class="category-tab">양식</div>
            <div class="category-tab">중식</div>
            <div class="category-tab">일식</div>
            <div class="category-tab">디저트</div>
        </div>

        <!-- 검색창 -->
        <form action="${pageContext.request.contextPath}/board/board.do" method="get" style="display: flex; justify-content: flex-end; align-items: center; margin-bottom: 20px;">
            <input type="text" class="search-input" 
                   id="searchKeyword" name="searchKeyword"
                   value="${criteria.searchKeyword}"
                   placeholder="제목으로검색" style="border-radius:8px; border:1.1px solid #dde1e8; padding:8px 12px; font-size:1.07rem;">
            <button type="submit" class="search-btn" style="margin-left:8px; color:var(--main-green); border:none; background:none; font-size:1.2rem;">
                <i class="bi bi-search"></i>
            </button>
        </form>

        <!-- 인기게시글 영역 -->
        <div class="popular-posts-section">
            <div class="popular-posts-title">인기게시글</div>
            <div class="top-posts-row">
                <c:forEach var="board" items="${topPosts}">
                    <div class="top-post-card">
                        <a href="${pageContext.request.contextPath}/board/view.do?boardId=${board.boardId}">
                            <img src="${board.thumbnail}" class="top-thumb-img" alt="썸네일" />
                            <div class="top-title"><b>${board.title}</b></div>
                            <div class="top-author">${board.nickname}</div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- 검색 영역 -->
        <div class="search-area">
            <input type="text" id="searchInput" class="search-input" placeholder="한식 게시글 검색..." onkeyup="filterTable()">
            <button class="search-btn" onclick="filterTable()"><i class="bi bi-search"></i></button>
        </div>

        <!-- 인기게시글 제목 -->
        <div class="section-title">인기게시글</div>

        <!-- 좋아요 많은 TOP4 가로 썸네일 (모바일 중앙정렬) -->
        <div class="top-posts-row">
            <div class="top-post-card">
                <a href="#"><img src="./4594_13631_0621.jpg" class="top-thumb-img" alt="썸네일">
                    <div class="top-title"><b>${board.title}</b></div>
                    <div class="top-author">${board.nickname}</div>
                </a>
            </div>
            <div class="top-post-card">
                <a href="#"><img src="https://via.placeholder.com/140x100?text=%EC%8D%B4%EB%84%A4%EC%9D%BC" class="top-thumb-img" alt="썸네일">
                    <div class="top-title"><b>${board.title}</b></div>
                    <div class="top-author">${board.nickname}</div>
                </a>
            </div>
            <div class="top-post-card">
                <a href="#"><img src="https://via.placeholder.com/140x100?text=%EC%8D%B4%EB%84%A4%EC%9D%BC" class="top-thumb-img" alt="썸네일">
                    <div class="top-title"><b>${board.title}</b></div>
                    <div class="top-author">${board.nickname}</div>
                </a>
            </div>
            <div class="top-post-card">
                <a href="#"><img src="https://via.placeholder.com/140x100?text=%EC%8D%B4%EB%84%A4%EC%9D%BC" class="top-thumb-img" alt="썸네일">
                    <div class="top-title"><b>${board.title}</b></div>
                    <div class="top-author">${board.nickname}</div>
                </a>
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

        <!-- 여기: 페이지번호 -->
        <div class="flex-center">
            <ul class="pagination" id="pagination"></ul>
        </div>
    </div>
</div>

<script>
    // 엔터키 쳤을때 검색되도록 만든거 
    document.addEventListener("DOMContentLoaded", () => {
        const input = document.querySelector("#searchKeyword");
        input?.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                e.preventDefault(); // form 중복 제출 방지
                input.form?.submit(); // form 전송 실행
            }
        });
    });

    // 검색 필터 (최신글 테이블만)
    function filterTable() {
        const keyword = document.getElementById('searchInput').value.toLowerCase();
        const trs = document.querySelectorAll('.post-table tbody tr');
        trs.forEach(tr => {
            const rowText = tr.innerText.toLowerCase();
            tr.style.display = rowText.includes(keyword) ? "" : "none";
        });
    }
</script>

</body>
</html>