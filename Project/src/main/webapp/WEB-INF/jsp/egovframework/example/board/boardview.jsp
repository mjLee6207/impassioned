<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<!-- 이거지우면 좋아요 안되요 저 집에갈거에여... 7월 8일 강승태 수정 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<head>
    <meta charset="UTF-8">
    <title>요리 게시글 상세조회</title>
    <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/boardview.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</head>
<body>
<jsp:include page="/common/header.jsp" />
<div class="main-wrap">
    <!-- 사이드바 -->
    <div class="sidebar-wrap">
        <jsp:include page="/common/sidebar.jsp"/>
    </div>

    <!-- 게시글 상세 -->
    <div class="board-wrap">
        <!-- 타이틀 -->
        <div class="board-title">
            <span class="icon">🍽️</span>
            <span id="board-title-text">
                <c:choose>
                    <c:when test="${board.category eq 'korean'}">한식 게시판</c:when>
                    <c:when test="${board.category eq 'western'}">양식 게시판</c:when>
                    <c:when test="${board.category eq 'chinese'}">중식 게시판</c:when>
                    <c:when test="${board.category eq 'japanese'}">일식 게시판</c:when>
                    <c:when test="${board.category eq 'dessert'}">디저트 게시판</c:when>
                    <c:otherwise>게시판</c:otherwise>
                </c:choose>
            </span>
        </div>
        <!-- 카테고리 탭 -->
        <div class="category-tabs">
            <div class="category-tab${board.category eq 'korean' ? ' active' : ''}" onclick="moveCategory('korean')">한식</div>
            <div class="category-tab${board.category eq 'western' ? ' active' : ''}" onclick="moveCategory('western')">양식</div>
            <div class="category-tab${board.category eq 'chinese' ? ' active' : ''}" onclick="moveCategory('chinese')">중식</div>
            <div class="category-tab${board.category eq 'japanese' ? ' active' : ''}" onclick="moveCategory('japanese')">일식</div>
            <div class="category-tab${board.category eq 'dessert' ? ' active' : ''}" onclick="moveCategory('dessert')">디저트</div>
        </div>
        <!-- 상단 정보 -->
        <div style="margin-bottom:18px;">
            <span class="category-badge">
                <c:choose>
                    <c:when test="${board.category eq 'korean'}">한식</c:when>
                    <c:when test="${board.category eq 'western'}">양식</c:when>
                    <c:when test="${board.category eq 'chinese'}">중식</c:when>
                    <c:when test="${board.category eq 'japanese'}">일식</c:when>
                    <c:when test="${board.category eq 'dessert'}">디저트</c:when>
                </c:choose>
            </span>
            작성자: <b>${board.nickname}</b>
            | 작성일: ${board.writeDate}
            | 조회수: ${board.viewCount}
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
        <c:if test="${not empty board.thumbnail}">
            <div class="post-section-title">사진</div>
            <img src="${board.thumbnail}" alt="요리사진" class="post-img"/>
        </c:if>

        <!-- ❤️ 좋아요 버튼 -->
        <div class="like-btn-wrap" style="text-align:center; margin-top:20px;">
            <button type="button" class="like-btn" id="likeBtn" data-board-id="${board.boardId}" data-member-idx="${loginUser.memberIdx}">♡</button>
            <span class="like-count" id="likeCountText">0</span>
        </div>

        <!-- 🔒 POST 방식 삭제를 위한 숨겨진 form -->
        <form id="deleteForm" action="${pageContext.request.contextPath}/board/delete.do" method="post" style="display:none;">
            <input type="hidden" name="boardId" value="${board.boardId}" />
            <input type="hidden" name="category" value="${board.category}" />
        </form>


        <!-- ====== 버튼 영역 (목록/수정/삭제) ====== -->
        <div class="post-btns" style="margin-top: 10px;">
            <a href="/board/board.do?category=${board.category}" class="btn btn-secondary btn-sm">목록</a>
            <c:if test="${loginUser.memberIdx eq board.writerIdx}">
                <a href="/board/edition.do?boardId=${board.boardId}" class="btn btn-success btn-sm">수정</a>

<!-- 삭제버튼 중복해서 들어가있음 7월 8일 9시 53분 강승태 수정   -->
              <a href="/board/delete.do?boardId=${board.boardId}" class="btn btn-danger btn-sm"
                   onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a> 
            </c:if>
        </div>

        <!-- ========== 댓글영역 ==========
             ★ post-btns의 닫는 </div> 태그 "바로 아래"에 위치해야 함 ★
        -->
        <div class="comment-section mt-4">
            <h6 class="mb-2">댓글 <span>(${fn:length(reviews)})</span></h6>
            <c:choose>
                <c:when test="${empty loginUser}">
                    <div class="comment-login-notice">
                        댓글을 남기시려면 <a href="/member/login.do" class="btn btn-dark btn-sm">로그인</a> 해주세요
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="comment-write-wrap">
                        <form action="/board/review/add.do" method="post" class="comment-form">
                            <input type="hidden" name="boardId" value="${board.boardId}">
                            <textarea name="content" class="comment-textarea" id="commentContent"
                                      maxlength="300" required placeholder="댓글을 입력하세요"
                                      oninput="updateCharCount();"></textarea>
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
                        <div class="comment-content">${review.content}</div>
                        <div class="comment-meta">
                            <span class="comment-nickname">${review.nickname}</span>
                            <span class="comment-date">${review.writeDate}</span>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${fn:length(reviews) == 0}">
                    <div class="comment-empty">아직 댓글이 없습니다.</div>
                </c:if>
            </div>
        </div>
        <!-- //댓글영역 -->
    </div>
</div>
<script>
    // 탭 클릭 시 해당 카테고리 게시판 목록으로 이동
    function moveCategory(category) {
        window.location.href = '/board/board.do?category=' + category;
    }
    function likePost() {
        alert('좋아요가 눌렸습니다!');
    }
    // 댓글 글자 수 카운트
    function updateCharCount() {
        const textarea = document.getElementById("commentContent");
        if (textarea) {
            document.getElementById("charCount").innerText = textarea.value.length;
        }
    }
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

    $(document).ready(function () {
        const $btn = $("#likeBtn");
        const boardId = $btn.data("board-id");
        const memberIdx = $btn.data("member-idx");

        $.get("/countLike.do", { boardId }, function (count) {
            $("#likeCountText").text(count);
        });

        if (!memberIdx) {
            $btn.prop("disabled", true);
            return;
        }

        $.get("/checkLike.do", { boardId, memberIdx }, function (res) {
            if (res === true || res === "true") {
                $btn.text("♥").addClass("liked");
            }
        });

        $btn.on("click", function () {
            const isLiked = $btn.text() === "♥";
            const url = isLiked ? "/cancelLike.do" : "/addLike.do";

            if (!memberIdx || memberIdx === "undefined" || memberIdx === "null") {
                alert("로그인 후 이용해주세요 😊");
                const redirectUrl = encodeURIComponent(location.pathname + location.search);
                location.href = "/member/login.do?redirect=" + redirectUrl;
                return;
            }

            $.ajax({
                url,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({ boardId, memberIdx }),
                success: function () {
                    $btn.text(isLiked ? "♡" : "♥").toggleClass("liked");
                    $.get("/countLike.do", { boardId }, function (count) {
                        $("#likeCountText").text(count);
                    });
                }
            });
        });
    });
</script>
</body>
</html>
