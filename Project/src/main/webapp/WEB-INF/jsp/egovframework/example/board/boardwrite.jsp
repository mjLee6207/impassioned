<%@ page contentType="text/html; charset=UTF-8"%>
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

<div class="main-wrap">
    <div class="sidebar-wrap">
        <jsp:include page="/common/sidebar.jsp"/>
    </div>
    <div class="board-wrap">
        <div class="write-box">
            <h3 class="mb-4"><i class="bi bi-search"></i> 요리 게시글 작성</h3>
            <form action="${pageContext.request.contextPath}/board/add.do" method="post" enctype="multipart/form-data">
                <!-- 카테고리 -->
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
                <label for="prepare" class="form-label">재료준비</label>
                <textarea class="form-control" id="prepare" name="prepare" rows="3" maxlength="1000" placeholder="예: 달걀 2개, 양파 1개, 소금 약간 등" required></textarea>
                <!-- 조리법 -->
                <label for="content" class="form-label">조리법</label>
                <textarea class="form-control" id="content" name="content" rows="6" maxlength="10000" placeholder="조리 과정을 단계별로 자세히 적어주세요." required></textarea>
                <!-- 사진 파일 업로드 -->
                <label for="images" class="form-label">사진 업로드</label>
                <input type="file" class="form-control" id="images" name="images" accept="image/*" multiple />
                <div id="imagePreviews" class="d-flex flex-wrap mt-2 gap-2"></div>
                <!-- 버튼 영역 -->
                <div class="btn-row">
                    <button type="submit" class="btn btn-submit">등록하기</button>
                    <button type="button" class="btn btn-cancel" onclick="history.back()">작성취소</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
$(function() {
    const imagePreviews = $('#imagePreviews');
    let selectedFiles = [];

    $('#images').on('change', function(event) {
        selectedFiles = Array.from(event.target.files);
        refreshPreviews();
    });

    function removeFileAtIndex(index) {
        selectedFiles.splice(index, 1);
        updateFileInput();
        refreshPreviews();
    }

    function updateFileInput() {
        const dt = new DataTransfer();
        selectedFiles.forEach(file => dt.items.add(file));
        $('#images')[0].files = dt.files;
    }

    function refreshPreviews() {
        imagePreviews.empty();
        selectedFiles.forEach((file, i) => {
            const reader = new FileReader();
            reader.onload = function(e) {
                const wrapper = $('<div class="position-relative" style="display:inline-block;">');
                const img = $('<img>').attr('src', e.target.result).css({width:'120px',height:'90px',objectFit:'cover',borderRadius:'10px',border:'1px solid #ddd'});
                const btn = $('<button type="button" aria-label="삭제" style="position:absolute;top:3px;right:3px;background:#fff;border-radius:50%;border:1px solid #ccc;width:24px;height:24px;padding:0;">&times;</button>');
                btn.on('click', function() { removeFileAtIndex(i); });
                wrapper.append(img).append(btn);
                imagePreviews.append(wrapper);
            };
            reader.readAsDataURL(file);
        });
    }
});
</script>
</body>
</html>
