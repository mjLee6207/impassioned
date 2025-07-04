<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>요리 게시글 작성</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/boardwrite.css">
    <link rel="stylesheet" href="/css/sidebar.css" />

    
</head>
<body>
<jsp:include page="/common/header.jsp" />

<div class="container container-box">
<jsp:include page="/common/sidebar.jsp" />
        <div class="write-box">
        <h3 class="mb-4">🍳 요리 게시글 작성</h3>
        <form action="${pageContext.request.contextPath}/board/writeProcess.do" method="post" enctype="multipart/form-data">
            <!-- 카테고리 선택 -->
            <label for="category" class="form-label">카테고리</label>
            <select class="form-select" id="category" name="category" required>
                <option value="" selected disabled>카테고리를 선택하세요</option>
                <option value="한식">한식</option>
                <option value="중식">중식</option>
                <option value="일식">일식</option>
                <option value="양식">양식</option>
                <option value="디저트">디저트</option>
            </select>
            <!-- 제목 -->
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" maxlength="100" required />

            <!-- 재료준비 -->
            <label for="ingredients" class="form-label">재료준비</label>
            <textarea class="form-control" id="ingredients" name="ingredients" rows="3" maxlength="1000" placeholder="예: 달걀 2개, 양파 1개, 소금 약간 등" required></textarea>

            <!-- 조리법 -->
            <label for="instructions" class="form-label">조리법</label>
            <textarea class="form-control" id="instructions" name="instructions" rows="6" maxlength="10000" placeholder="조리 과정을 단계별로 자세히 적어주세요." required></textarea>

            <!-- 사진 파일 업로드 -->
            <label for="image" class="form-label">사진 업로드</label>
            <input type="file" class="form-control" id="image" name="image" accept="image/*" />
            <!-- 버튼 영역 -->
            <div class="btn-row">
                <button type="submit" class="btn btn-submit">등록하기</button>
                <button type="button" class="btn btn-cancel" onclick="history.back()">작성취소</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
