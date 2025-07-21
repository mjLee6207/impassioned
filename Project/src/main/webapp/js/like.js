function initLikeButton({ likeType, boardId, recipeId, memberIdx }) {
  const $btn       = $("#likeBtn");
  const $countText = $("#likeCountText");

  // 🚀 수정된 countText 업데이트 헬퍼
  function renderCount(raw) {
    // 1) 문자열을 정수로 변환
    const n = parseInt(raw, 10);
    // 2) NaN 이거나 음수면 0으로 보정
    const safe = (isNaN(n) || n < 0) ? 0 : n;

    if (likeType === "BOARD") {
      // 게시판: “좋아요 n개”
      $countText.html(`좋아요 <span>${safe}</span>개`);
    } else {
      // 레시피: 숫자만
      $countText.text(safe);
    }
  }

  // 1) 초기 좋아요 수 불러오기
  $.get("/countLike.do", { likeType, boardId, recipeId }, renderCount);

  // 2) 로그인됐으면 상태 확인
  if (memberIdx) {
    $.get(
      "/checkLike.do",
      { likeType, boardId, recipeId, memberIdx },
      function (res) {
        if (res === true || res === "true") {
          $btn.text("♥").addClass("liked");
        }
      }
    );
  }

  // 3) 클릭 이벤트
  $btn.on("click", function () {
    if (!memberIdx) {
      alert("로그인 후 이용해주세요 😊");
      const redirectUrl = encodeURIComponent(location.pathname + location.search);
      return (location.href = "/member/login.do?redirect=" + redirectUrl);
    }
    if (likeType === "RECIPE" && (!recipeId || !String(recipeId).trim())) {
      console.warn("🚫 recipeId 없음 → 좋아요 요청 막음");
      return;
    }

    const isLiked = $btn.hasClass("liked");
    const url     = isLiked ? "/cancelLike.do" : "/addLike.do";

    $.ajax({
      url,
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify({ likeType, boardId, recipeId, memberIdx }),
      success: function () {
        // 하트 토글
        $btn.text(isLiked ? "♡" : "♥").toggleClass("liked");
        // 다시 count 불러오기
        $.get("/countLike.do", { likeType, boardId, recipeId }, renderCount);
      }
    });
  });
}
