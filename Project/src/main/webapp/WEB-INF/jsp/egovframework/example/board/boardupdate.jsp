<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>요리 게시글 수정</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/boardupdate.css">
    <jsp:include page="/common/header.jsp" />
</head>
<body>


<div class="main-wrap">
   <div class="sidebar-wrap">
        <jsp:include page="/common/sidebar.jsp"/>
    </div>
    <div class="board-wrap">
    <div class="write-box">
        <h3 class="mb-4">🍳 요리 게시글 수정</h3>
        <form id="addForm" action="${pageContext.request.contextPath}/board/edition.do" method="post" enctype="multipart/form-data">
            <input type="hidden" name="boardId" value="${boardVO.boardId}" />
            <input type="hidden" name="searchKeyword" value="${param.searchKeyword}" />
            <input type="hidden" name="pageIndex" value="${not empty param.pageIndex ? param.pageIndex : 1}" />
            <input type="hidden" name="deleteImageIds" id="deleteImageIds" value=""/>
            <!-- 카테고리 -->
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
            <input type="text" class="form-control" id="title" name="title" value="${boardVO.title}" maxlength="100" required />
            <!-- 재료준비 -->
            <label for="prepare" class="form-label">재료준비</label>
            <textarea class="form-control" id="prepare" name="prepare" rows="3" maxlength="1000" required>${boardVO.prepare}</textarea>
            <!-- 조리법 -->
            <label for="content" class="form-label">조리법</label>
            <textarea class="form-control" id="content" name="content" rows="6" maxlength="10000" required>${boardVO.content}</textarea>

            <!-- 기존 이미지(썸네일 포함) 리스트 출력 -->
            <c:if test="${not empty fileList}">
                <label class="form-label mt-3">현재 등록된 이미지</label>
                <div class="existing-images d-flex flex-wrap gap-2 mb-2">
                    <c:forEach var="file" items="${fileList}" varStatus="status">
                        <div class="existing-image-wrapper position-relative" style="display:inline-block;">
                            <img src="/file/download.do?fileId=${file.fileId}" style="width:120px;height:90px;object-fit:cover;border-radius:10px;border:1px solid #ddd;">
                            <button type="button" class="img-delete-btn btn-delete-existing" data-file-id="${file.fileId}">&times;</button>
                            <c:if test="${status.first}"><span class="badge bg-success" style="position:absolute;bottom:3px;left:3px;">썸네일</span></c:if>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- 새 이미지 업로드 -->
            <label for="images" class="form-label mt-2">사진 업로드</label>
            <input type="file" class="form-control" id="images" name="images" accept="image/*" multiple />
            <div id="imagePreviews" class="d-flex flex-wrap mt-2 gap-2"></div>
            <!-- 버튼 영역 -->
            <div class="btn-row">
                <button type="submit" class="submit_btn" onclick="fn_save()">수정하기</button>
                <button type="button" class="delete_btn" onclick="fn_delete()">삭제하기</button>
                <button type="button" class="cancel_btn" onclick="history.back()">돌아가기</button>
            </div>
        </form>
    </div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
function fn_save() {
    $("#addForm").attr("action","<c:out value='/board/edit.do' />").submit();
}
function fn_delete() {
    if (confirm("정말 삭제하시겠습니까? 복구되지 않습니다.")) {
        $("#addForm").attr("action","<c:out value='/board/delete.do' />").submit();
    }
}

$(function() {
    // 기존 이미지 삭제 관리
    let deleteImageIds = [];
    $('.btn-delete-existing').on('click', function() {
        const fileId = $(this).data('file-id');
        if(confirm("이 이미지를 삭제하시겠습니까?")) {
            if(!deleteImageIds.includes(fileId)) {
                deleteImageIds.push(fileId);
            }
            $(this).closest('.existing-image-wrapper').remove();
            $('#deleteImageIds').val(deleteImageIds.join(','));
        }
    });

    // 새 이미지 업로드 미리보기
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
                const btn = $('<button type="button" class="img-delete-btn" aria-label="삭제">&times;</button>');
                btn.on('click', function() { removeFileAtIndex(i); });
                wrapper.append(img).append(btn);
                imagePreviews.append(wrapper);
            };
            reader.readAsDataURL(file);
        });
    }

    // ✅ 조리법 textarea 번호 자동 입력 기능
    const textarea = $('#content');

    textarea.on('keydown', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();

            const val = textarea.val();
            const selectionStart = this.selectionStart;
            const selectionEnd = this.selectionEnd;

            const before = val.substring(0, selectionStart);
            const after = val.substring(selectionEnd);

            const lines = before.split('\n');
            const lastLine = lines[lines.length - 1];

            const match = lastLine.match(/^(\d+)\.\s?/);
            let nextNumber = 1;

            if (match) {
                nextNumber = parseInt(match[1], 10) + 1;
            } else {
                // 마지막 줄까지 번호가 있는 줄만 필터링해서 가장 마지막 번호 추출
                const numberedLines = lines.filter(line => /^\d+\.\s/.test(line));
                if (numberedLines.length > 0) {
                    const lastNumberedLine = numberedLines[numberedLines.length - 1];
                    const m = lastNumberedLine.match(/^(\d+)\.\s?/);
                    if (m) {
                        nextNumber = parseInt(m[1], 10) + 1;
                    }
                }
            }

            const insertText = '\n' + nextNumber + '. ';
            textarea.val(before + insertText + after);
            this.selectionStart = this.selectionEnd = selectionStart + insertText.length;
        }
    });

    textarea.on('focus', function () {
        if ($(this).val().trim() === '') {
            $(this).val('1. ');
        }
    });
});
</script>
<!-- 꼬리말 jsp include-->
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
