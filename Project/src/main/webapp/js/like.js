function initLikeButton({ likeType, boardId, recipeId, memberIdx }) {
    const $btn = $("#likeBtn");
    const $countText = $("#likeCountText");

    const targetId = likeType === "BOARD" ? boardId : recipeId;

    // ✅ 좋아요 수는 항상 표시
    $.get("/countLike.do", { likeType, boardId, recipeId }, function (count) {
        $countText.html("좋아요 <span>" + count + "</span>개");
    });

    // ✅ 로그인된 경우만 상태 확인
    if (memberIdx) {
        $.get("/checkLike.do", { likeType, boardId, recipeId, memberIdx }, function (res) {
            if (res === true || res === "true") {
                $btn.text("♥").addClass("liked");
            }
        });
    }

    // ✅ 클릭 이벤트
    $btn.on("click", function () {
        if (!memberIdx) {
            alert("로그인 후 이용해주세요 😊");
            const redirectUrl = encodeURIComponent(location.pathname + location.search);
            location.href = "/member/login.do?redirect=" + redirectUrl;
            return;
        }

        if (likeType === "RECIPE" && (!recipeId || String(recipeId).trim() === "")) {
            console.warn("🚫 recipeId 없음 → 좋아요 요청 막음");
            return;
        }

        const isLiked = $btn.hasClass("liked"); // ✅ 클래스 기반으로 판별
        const url = isLiked ? "/cancelLike.do" : "/addLike.do";

        console.log("✅ 현재 상태:", isLiked ? "♥ 취소" : "♡ 등록");
        console.log("✅ 요청 URL:", url);

        $.ajax({
            url,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                likeType,
                boardId,
                recipeId,
                memberIdx,
            }),
            success: function () {
                $btn.text(isLiked ? "♡" : "♥").toggleClass("liked");

                // 수 다시 불러오기
                $.get("/countLike.do", { likeType, boardId, recipeId }, function (count) {
                    $countText.html("좋아요 <span>" + count + "</span>개");
                    console.log("🔥 좋아요 수:", count);
                });
            }
        });
    });
}
