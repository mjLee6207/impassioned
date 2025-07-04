<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <div class="top-title"><b>${post.title}</b></div>
                    <div class="top-author">${post.author}</div>
                </a>
            </div>
            <div class="top-post-card">
                <a href="#"><img src="https://via.placeholder.com/140x100?text=%EC%8D%B4%EB%84%A4%EC%9D%BC" class="top-thumb-img" alt="썸네일">
                    <div class="top-title"><b>${post.title}</b></div>
                    <div class="top-author">${post.author}</div>
                </a>
            </div>
            <div class="top-post-card">
                <a href="#"><img src="https://via.placeholder.com/140x100?text=%EC%8D%B4%EB%84%A4%EC%9D%BC" class="top-thumb-img" alt="썸네일">
                    <div class="top-title"><b>${post.title}</b></div>
                    <div class="top-author">${post.author}</div>
                </a>
            </div>
            <div class="top-post-card">
                <a href="#"><img src="https://via.placeholder.com/140x100?text=%EC%8D%B4%EB%84%A4%EC%9D%BC" class="top-thumb-img" alt="썸네일">
                    <div class="top-title"><b>${post.title}</b></div>
                    <div class="top-author">${post.author}</div>
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
                <tr>
                    <td style="text-align:left;"><a href="#" class="post-title-link">${post.title}</a></td>
                    <td>${post.author}</td>
                    <td>${post.createdDate}</td>
                    <td>${post.viewCount}</td>
                </tr>
                <tr>
                    <td style="text-align:left;"><a href="#" class="post-title-link">${post.title}</a></td>
                    <td>${post.author}</td>
                    <td>${post.createdDate}</td>
                    <td>${post.viewCount}</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<script>
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

