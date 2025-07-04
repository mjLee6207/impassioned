<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 목록</title>
    <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/boardlist.css" />
    <!-- Bootstrap, Bootstrap-icons 등 필요시 추가 -->
</head>
<body>
<div style="display:flex; gap:36px; max-width:1400px; margin:40px auto;">
    <!-- 사이드바 (포함) -->
    <jsp:include page="/common/sidebar.jsp"></jsp:include>

    <!-- 본문 영역 -->
    <div style="flex:1;">
        <!-- 카테고리 탭  -->
        <div class="category-tabs" style="margin-bottom:20px;">
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
                <c:forEach var="post" items="${topPosts}">
                    <div class="top-post-card">
                        <a href="${pageContext.request.contextPath}/post/view.do?id=${post.id}">
                            <img src="${post.thumbnailPath}" class="top-thumb-img" alt="썸네일" />
                            <div class="top-title"><b>${post.title}</b></div>
                            <div class="top-author">${post.author}</div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- 최신 게시글 테이블 -->
        <div style="margin-top:38px;">
            <table class="post-table" style="width:100%;">
                <thead>
                <tr>
                    <th style="width:60%;">제목</th>
                    <th style="width:14%;">작성자</th>
                    <th style="width:13%;">작성일</th>
                    <th style="width:13%;">조회수</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="post" items="${boards}">
                    <tr>
                        <td style="text-align:left;">
                            <a href="${pageContext.request.contextPath}/post/view.do?id=${post.boardId}" class="post-title-link">${post.title}</a>
                        </td>
                        <td>${post.title}</td>
                        <td>${post.writerIdx}</td>
                        <td>${post.writeDate}</td>
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
</script>

</body>
</html>