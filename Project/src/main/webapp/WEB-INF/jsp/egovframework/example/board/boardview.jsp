<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<head>
<meta charset="UTF-8">
<title>요리 게시글 상세조회</title>
<link rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/sidebar.css" />
<link rel="stylesheet" href="/css/boardview.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
	<jsp:include page="/common/header.jsp" />
</head>
<body>
	
	<div class="main-wrap">
		<!-- 사이드바 -->
		<div class="sidebar-wrap">
			<jsp:include page="/common/sidebar.jsp" />
		</div>
    <!-- 게시글 상세 -->
    <div class="board-wrap">
        <!-- 타이틀 -->
        <div class="board-title">
            <span class="icon">🍽️</span>
            <span id="board-title-text">
                <c:choose>
                    <c:when test="${board.category eq '한식'}">한식 게시판</c:when>
                    <c:when test="${board.category eq '양식'}">양식 게시판</c:when>
                    <c:when test="${board.category eq '중식'}">중식 게시판</c:when>
                    <c:when test="${board.category eq '일식'}">일식 게시판</c:when>
                    <c:when test="${board.category eq '디저트'}">디저트 게시판</c:when>
                    <c:otherwise>게시판</c:otherwise>
                </c:choose>
            </span>
        </div>
        <!-- 카테고리 탭 -->
        <div class="category-tabs">
            <div class="category-tab${empty board.category ? ' active' : ''}" onclick="moveCategory('')">전체</div>
            <div class="category-tab${board.category eq '한식' ? ' active' : ''}" onclick="moveCategory('한식')">한식</div>
            <div class="category-tab${board.category eq '양식' ? ' active' : ''}" onclick="moveCategory('양식')">양식</div>
            <div class="category-tab${board.category eq '중식' ? ' active' : ''}" onclick="moveCategory('중식')">중식</div>
            <div class="category-tab${board.category eq '일식' ? ' active' : ''}" onclick="moveCategory('일식')">일식</div>
            <div class="category-tab${board.category eq '디저트' ? ' active' : ''}" onclick="moveCategory('디저트')">디저트</div>
        </div>
        <!-- 상단 정보 스타일 수정 금지 -->
        <div class="board-title-row">
    <span class="board-title-main">🧑🏻‍🍳${board.title}</span>
    <div class="board-title-info">
        <span class="category-badge">
            <c:choose>
                <c:when test="${board.category eq '한식'}">한식</c:when>
                <c:when test="${board.category eq '양식'}">양식</c:when>
                <c:when test="${board.category eq '중식'}">중식</c:when>
                <c:when test="${board.category eq '일식'}">일식</c:when>
                <c:when test="${board.category eq '디저트'}">디저트</c:when>
            </c:choose>
        </span>
        작성자: <b>${board.nickname}</b>
        | 작성일: ${board.writeDate}
        | 조회수: ${board.viewCount}
    </div>
</div>

        <!-- 상세 내용 -->
        <div class="post-section-title">재료준비</div>
        <div class="post-content">
            <c:out value="${board.prepare}" escapeXml="false"/>
        </div>
        <div class="post-section-title">조리법</div>
        <div class="post-content">
            <c:out value="${board.content}" escapeXml="false"/>
        </div>
        <c:if test="${not empty fileList}">
    <div class="post-section-title">사진</div>
    <div class="post-image-list">
        <c:forEach var="file" items="${fileList}">
            <img src="/file/download.do?fileId=${file.fileId}" 
                 alt="요리사진" 
                 class="post-img-multi" />
        </c:forEach>
    </div>
</c:if>
<!-- 7월 8일 12시 좋아요 UI수정 - 강승태  -->
			<!-- ❤️ 좋아요 버튼 -->
			<div class="like-btn-wrap">
				<button type="button" class="like-btn" id="likeBtn"
					data-board-id="${board.boardId}"
					data-member-idx="${loginUser.memberIdx}">♡</button>

				<div class="like-count-text" id="likeCountText">좋아요 0개</div>
			</div>
			<!-- 🔒 POST 방식 삭제를 위한 숨겨진 form -->
			<form id="deleteForm"
				action="${pageContext.request.contextPath}/board/delete.do"
				method="post" style="display: none;">
				<input type="hidden" name="boardId" value="${board.boardId}" /> <input
					type="hidden" name="category" value="${board.category}" />
			</form>


        <!-- ====== 버튼 영역 (목록/수정/삭제) ====== -->
        <div class="post-btns" style="margin-top: 10px;">
            <a href="/board/board.do?category=${board.category}" class="btn btn-secondary btn-sm">목록</a>
            <c:if test="${loginUser.memberIdx eq board.writerIdx}">
             <a href="/board/edition.do?boardId=${board.boardId}" class="btn btn-success btn-sm">수정</a>
				<!-- 삭제버튼 중복해서 들어가있음 7월 8일 9시 53분 강승태 수정   -->
				  <form action="${pageContext.request.contextPath}/board/delete.do" method="post" style="display:inline;">
				    <input type="hidden" name="boardId" value="${board.boardId}" />
				    <input type="hidden" name="category" value="${board.category}" />
				    <input type="hidden" name="searchKeyword" value="${param.searchKeyword}" />
				    <input type="hidden" name="pageIndex" value="${not empty param.pageIndex ? param.pageIndex : 1}" />
				    <button type="submit" class="btn btn-danger btn-sm"
				        onclick="return confirm('정말 삭제하시겠습니까?');">삭제</button>
				</form> 
            </c:if>            
            <c:if test="${loginUser != null && fn:toUpperCase(loginUser.role) eq 'ADMIN'}">
			  <form action="${pageContext.request.contextPath}/board/adminDelete.do" method="post" style="display:inline;">
			    <input type="hidden" name="boardId" value="${board.boardId}" />
			    <input type="hidden" name="category" value="${board.category}" />
			    <input type="hidden" name="searchKeyword" value="${param.searchKeyword}" />
			    <input type="hidden" name="pageIndex" value="${not empty param.pageIndex ? param.pageIndex : 1}" />
			    <button type="submit" class="btn btn-danger btn-sm"
			        onclick="return confirm('⚠️ 정말 이 게시글을 관리자 권한으로 삭제하시겠습니까?');">
			      관리자 강제 삭제
			    </button>
			  </form>
			</c:if>
        </div>


			<!-- ========== 댓글영역 ==========
             ★ post-btns의 닫는 </div> 태그 "바로 아래"에 위치해야 함 ★
        -->
			<div class="comment-section mt-4">
				<h6 class="mb-2">
					댓글 <span>(${fn:length(reviews)})</span>
				</h6>
				<c:choose>
					<c:when test="${empty loginUser}">
						<div class="comment-login-notice">
							댓글을 남기시려면 <a href="/member/login.do" class="btn btn-dark btn-sm">로그인</a>
							해주세요
						</div>
					</c:when>
					<c:otherwise>
						<div class="comment-write-wrap">
							<form action="/board/review/add.do" method="post"
								class="comment-form">
								<input type="hidden" name="boardId" value="${board.boardId}">
								<textarea name="content" class="comment-textarea"
									id="commentContent" maxlength="300" required
									placeholder="댓글을 입력하세요" oninput="updateCharCount();"></textarea>
								<button type="submit" class="comment-submit-btn">댓글등록</button>
							</form>
							<div class="comment-char-count">
								<span id="charCount">0</span> / 300
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="comment-list">
					 <c:forEach var="review" items="${reviews}">
        <div class="comment-item">
            <div class="comment-row">
                <div class="comment-content" id="reviewContent${review.reviewId}">${review.content}</div>
                <div class="comment-meta">
                    <span class="comment-nickname">${review.nickname}</span>
                    <span class="comment-date">${review.writeDate}</span>
                </div>
                
                <!-- 본인 댓글에만 수정/삭제 버튼 노출 -->
	<c:if test="${loginUser != null && loginUser.memberIdx == review.writerIdx}">
  <div class="comment-btn-group">
    <!-- ✅ 1. '수정' 버튼: 폼이 아니라 자바스크립트 함수 호출 -->
   <button type="button" class="comment-edit-btn"
    onclick="showEditForm('${review.reviewId}')">
  수정
</button>
    <!-- 삭제 폼(그대로 유지) -->
    <form action="/board/review/delete.do" method="post" style="display:inline;">
      <input type="hidden" name="reviewId" value="${review.reviewId}" />
      <input type="hidden" name="boardId" value="${board.boardId}" />
      <button type="submit" class="comment-delete-btn">삭제</button>
    </form>
  </div>
  <!-- 인라인 수정폼 (초기 숨김) -->
  <form id="editForm${review.reviewId}" action="/board/review/edit.do" method="post" style="display:none; margin-top:5px;">
    <input type="hidden" name="reviewId" value="${review.reviewId}" />
    <input type="hidden" name="boardId" value="${board.boardId}" />
    <textarea name="content" id="editContent${review.reviewId}">${review.content}</textarea>
    <button type="submit">저장</button>
    <button type="button" onclick="hideEditForm(${review.reviewId})">취소</button>
  </form>
</c:if>
				</div>
			</div>
			<!-- //댓글영역 -->
			</c:forEach>
					<c:if test="${fn:length(reviews) == 0}">
						<div class="comment-empty">아직 댓글이 없습니다.</div>
						</c:if>
		</div>
	</div>
	</div>
	</div>
	
	<script>
    // 탭 클릭 시 해당 카테고리 게시판 목록으로 이동

    function moveCategory(category) {
        window.location.href = '/board/board.do?category=' + category;
    }

    function updateCharCount() {
        const textarea = document.getElementById("commentContent");
        if (textarea) {
            document.getElementById("charCount").innerText = textarea.value.length;
        }
    }
    
    
    $(document).ready(function () {
        initLikeButton({
          likeType: "BOARD",
          boardId: "${board.boardId}",
          memberIdx: "${loginUser.memberIdx}"
        });
      });
</script>

	<!-- 스크립트 -->
	<script>
	function moveCategory(category) {
	    window.location.href = '/board/board.do?category=' + category;
	}

	function fn_delete() {
	    if (confirm("정말 삭제하시겠습니까? 복구되지 않습니다.")) {
	        document.getElementById("deleteForm").submit();
	    }
	}
	
	/* 댓글 수정 */
	function showEditForm(reviewId) {
	    document.getElementById('reviewContent' + reviewId).style.display = 'none';
	    document.getElementById('editForm' + reviewId).style.display = '';
	    // 인풋에 값 넣기 (textarea의 value를 리뷰 본문에서 가져오기)
	    var origin = document.getElementById('reviewContent' + reviewId).innerText;
	    document.getElementById('editContent' + reviewId).value = origin;
	}

  function hideEditForm(reviewId) {
    document.getElementById('reviewContent' + reviewId).style.display = '';
    document.getElementById('editForm' + reviewId).style.display = 'none';
  }
  
</script>
   <script src="/js/like.js"></script>
	<!-- 꼬리말 jsp include-->
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>