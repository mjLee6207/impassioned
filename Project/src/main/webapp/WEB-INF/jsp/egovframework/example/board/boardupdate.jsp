<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>요리 게시글 작성</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/boardupdate.css">
    <link rel="stylesheet" href="/css/sidebar.css" />
   
    
</head>
<body>
<jsp:include page="/common/header.jsp" />

<div class="container container-box">
<jsp:include page="/common/sidebar.jsp" />
        <div class="write-box">
        <h3 class="mb-4">🍳 요리 게시글 수정</h3>
        <form id="addForm" action="${pageContext.request.contextPath}/board/edition.do" method="post" enctype="multipart/form-data">
        <!-- 컨트롤러 확인용 -->
        <input type="hidden" name="boardId" value="${boardVO.boardId}" />
        <!-- 7/7일 리다이렉트용 정보들(민중) -->
		<input type="hidden" name="searchKeyword" value="${param.searchKeyword}" />
		<input type="hidden" name="pageIndex" value="${param.pageIndex}" />
		<input type="hidden" name="category" value="${param.category}" />
            <!-- 카테고리 선택 -->
            <label for="category" class="form-label">카테고리</label>
            <select class="form-select" id="category" name="category" required>
			    <option value="" disabled ${empty boardVO.category ? 'selected' : ''}>카테고리를 선택하세요</option>
			    <option value="한식" ${boardVO.category == '한식' ? 'selected' : ''}>한식</option>
			    <option value="중식" ${boardVO.category == '중식' ? 'selected' : ''}>중식</option>
			    <option value="일식" ${boardVO.category == '일식' ? 'selected' : ''}>일식</option>
			    <option value="양식" ${boardVO.category == '양식' ? 'selected' : ''}>양식</option>
			    <option value="디저트" ${boardVO.category == '디저트' ? 'selected' : ''}>디저트</option>
            </select>
				<!-- 제목 -->
				<label for="title" class="form-label">제목</label>
				<input type="text" class="form-control" id="title" name="title"
				       value="${boardVO.title}" maxlength="100" required />
				
				<!-- 재료준비 -->
				<label for="ingredients" class="form-label">재료준비</label>
				<textarea class="form-control" id="prepare" name="prepare" rows="3"
				          maxlength="1000" required>${boardVO.prepare}</textarea>
				
				<!-- 조리법 -->
				<label for="instructions" class="form-label">조리법</label>
				<textarea class="form-control" id="content" name="content" rows="6"
				          maxlength="10000" required>${boardVO.content}</textarea>
            <!-- 사진 파일 업로드 -->
            <label for="image" class="form-label">사진 업로드</label>
            <input type="file" class="form-control" id="image" name="image" accept="image/*" />
            <!-- 버튼 영역 -->
            <div class="btn-row">
                <button type="submit" class="btn btn-submit" onclick="fn_save()">수정하기</button>
                <button type="button" class="btn btn-delete" onclick="fn_delete()">삭제하기</button>
                <button type="button" class="btn btn-cancel" onclick="history.back()">돌아가기</button>
            </div>
        </form>
    </div>
</div>
<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
function fn_save() {
    $("#addForm").attr("action","<c:out value='/board/edit.do' />")
    			.submit();
}

function fn_delete() {
    if (confirm("정말 삭제하시겠습니까? 복구되지 않습니다.")) {
        $("#addForm").attr("action","<c:out value='/board/delete.do' />").submit();
    }
}
</script>
</body>
</html>
