<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>요리 게시글 상세조회</title>
    <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/post.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</head>
<body>
<div class="main-wrap">
    <%-- 사이드바 포함 --%>
    <jsp:include page="/common/sidebar.jsp"></jsp:include>
    <!-- 게시글 상세 -->
    <div class="board-wrap">
        <!-- 타이틀 -->
        <div class="board-title">
            <span class="icon">🍽️</span>
            <span id="board-title-text">
                <c:choose>
                    <c:when test="${post.category eq 'korean'}">한식 게시판</c:when>
                    <c:when test="${post.category eq 'western'}">양식 게시판</c:when>
                    <c:when test="${post.category eq 'chinese'}">중식 게시판</c:when>
                    <c:when test="${post.category eq 'japanese'}">일식 게시판</c:when>
                    <c:when test="${post.category eq 'dessert'}">디저트 게시판</c:when>
                    <c:otherwise>게시판</c:otherwise>
                </c:choose>
            </span>
        </div>
        <!-- 카테고리 탭 -->
        <div class="category-tabs">
            <div class="category-tab${post.category eq 'korean' ? ' active' : ''}" onclick="moveCategory('korean')">한식</div>
            <div class="category-tab${post.category eq 'western' ? ' active' : ''}" onclick="moveCategory('western')">양식</div>
            <div class="category-tab${post.category eq 'chinese' ? ' active' : ''}" onclick="moveCategory('chinese')">중식</div>
            <div class="category-tab${post.category eq 'japanese' ? ' active' : ''}" onclick="moveCategory('japanese')">일식</div>
            <div class="category-tab${post.category eq 'dessert' ? ' active' : ''}" onclick="moveCategory('dessert')">디저트</div>
        </div>
        <!-- 상단 정보 -->
        <div style="margin-bottom:18px;">
            <span class="category-badge">
                <c:choose>
                    <c:when test="${post.category eq 'korean'}">한식</c:when>
                    <c:when test="${post.category eq 'western'}">양식</c:when>
                    <c:when test="${post.category eq 'chinese'}">중식</c:when>
                    <c:when test="${post.category eq 'japanese'}">일식</c:when>
                    <c:when test="${post.category eq 'dessert'}">디저트</c:when>
                </c:choose>
            </span>
            작성자: <b>${post.author}</b>
            | 작성일: ${post.createdDate}
            | 조회수: ${post.viewCount}
        </div>
        <!-- 상세 내용 -->
        <div class="post-section-title">재료준비</div>
        <div class="post-content">
            <c:out value="${post.ingredient}" escapeXml="false"/>
        </div>
        <div class="post-section-title">조리법</div>
        <div class="post-content">
            <c:out value="${post.content}" escapeXml="false"/>
        </div>
        <c:if test="${not empty post.imgPath}">
            <div class="post-section-title">사진</div>
            <img src="${post.imgPath}" alt="요리사진" class="post-img"/>
        </c:if>
        <!-- 버튼 -->
        <div class="post-btns">
            <a href="/board.do?category=${post.category}" class="btn btn-secondary btn-sm">목록</a>
            <c:if test="${loginUser eq post.author}">
                <a href="/post/edit.do?id=${post.id}" class="btn btn-success btn-sm">수정</a>
                <a href="/post/delete.do?id=${post.id}" class="btn btn-danger btn-sm"
                   onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
            </c:if>
        </div>
    </div>
</div>
<script>
    // 탭 클릭시 해당 카테고리 게시판 목록으로 이동
    function moveCategory(category) {
        window.location.href = '/board.do?category=' + category;
    }
</script>
</body>
</html>
