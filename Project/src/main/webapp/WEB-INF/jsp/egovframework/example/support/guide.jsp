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
<style>
.pdf-wrapper {
	width: 100%;
	max-width: 1100px;
	margin: 0 auto;
	border: 1px solid #ccc;
	border-radius: 8px;
	overflow: hidden;
}

.pdf-wrapper iframe {
	width: 100%;
	height: 80vh; /* ✅ 브라우저 높이의 80% 사용: 모바일 대응 좋음 */
	border: none;
}

@media ( max-width : 768px) {
	.pdf-wrapper iframe {
		height: 70vh; /* 모바일은 조금 더 줄여줌 */
	}
}
</style>

</head>
<body>
	<div class="main-wrap">
		<!-- 사이드바 -->
		<div class="sidebar-wrap">
			<jsp:include page="/common/sidebar.jsp" />
		</div>

<div class="container container-box" style="margin-top: 32px;">
	<div style="display: flex; flex-direction: column; align-items: left; gap: 24px;">
		<h1 style="color: #1e8a57;">🌲CheForest Guide-line</h1>

		<div class="pdf-wrapper">
			<iframe src="/pdf/CheForest.pdf"></iframe>
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

	    // ✅ 좋아요 수는 로그인 여부 상관없이 항상 표시
	    $.get("/countLike.do", { boardId }, function (count) {
	        $("#likeCountText").html("좋아요 <span>" + count + "</span>개");
	    });

	    // ✅ 로그인된 경우에만 상태 확인
	    if (memberIdx && memberIdx !== "undefined" && memberIdx !== "null") {
	        $.get("/checkLike.do", { boardId, memberIdx }, function (res) {
	            if (res === true || res === "true") {
	                $btn.text("♥").addClass("liked");
	            }
	        });
	    }

	    // ✅ 클릭 이벤트는 항상 등록하고, 내부에서 로그인 여부 확인
	    $btn.on("click", function () {
	        if (!memberIdx || memberIdx === "undefined" || memberIdx === "null") {
	            alert("로그인 후 이용해주세요 😊");
	            const redirectUrl = encodeURIComponent(location.pathname + location.search);
	            location.href = "/member/login.do?redirect=" + redirectUrl;
	            return;
	        }

	        const isLiked = $btn.text() === "♥";
	        const url = isLiked ? "/cancelLike.do" : "/addLike.do";

	        $.ajax({
	            url,
	            type: "POST",
	            contentType: "application/json",
	            data: JSON.stringify({ boardId, memberIdx }),
	            success: function () {
	                $btn.text(isLiked ? "♡" : "♥").toggleClass("liked");

	                // ✅ 좋아요 수 새로고침
	                $.get("/countLike.do", { boardId }, function (count) {
	                    $("#likeCountText").html("좋아요 <span>" + count + "</span>개");
	                });
	            }
	        });
	    });
	});
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

</body>
</html>