<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    .like-wrap {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 10px;
    }
    .likeButton {
      font-size: 32px;
      border: none;
      background: none;
      cursor: pointer;
      color: gray;
    }
    .likeButton.liked {
      color: red;
    }
    .likeCount {
      margin-top: 6px;
      font-size: 14px;
      color: black;
    }
  </style>
</head>
<body>

  <%-- 로그인 여부 체크 (전역) --%>
  <%
    Object sessionUser = session.getAttribute("memberIdx");
    int memberIdx = (sessionUser == null) ? -1 : Integer.parseInt(sessionUser.toString());
  %>

  <script>
    const memberIdxGlobal = <%= memberIdx %>;
    if (memberIdxGlobal === -1) {
      alert("로그인 후 이용 가능한 기능입니다.");
    }
  </script>

  <%-- 예시 게시글 1개 --%>
  <div class="post" data-board-id="123">
    <div class="like-wrap">
      <button class="likeButton" data-board-id="123" data-member-idx="<%= memberIdx %>">♡</button>
      <div class="likeCount" data-board-id="123">좋아요</div>
    </div>
  </div>

  <script>
    $(document).ready(function () {
      $(".likeButton").each(function () {
        const $btn = $(this);
        const boardId = $btn.data("board-id");
        const memberIdx = $btn.data("member-idx");

        // 🔒 로그인 안 한 경우 버튼 비활성화
        if (memberIdx === -1 || memberIdx === "-1") {
          $btn.prop("disabled", true);
          return;
        }

        // ♥ 상태 확인
        $.ajax({
          url: "/checkLike.do",
          type: "GET",
          data: { boardId, memberIdx },
          success: function (exists) {
            if (exists === true || exists === "true") {
              $btn.text("♥").addClass("liked");
            }
          }
        });

        // 좋아요 수 표시
        $.ajax({
          url: "/countLike.do",
          type: "GET",
          data: { boardId },
          success: function (count) {
            $(`.likeCount[data-board-id=${boardId}]`).text("좋아요 수: " + count);
          }
        });

        // 클릭 이벤트
        $btn.on("click", function () {
          const isLiked = $btn.text() === "♥";
          const url = isLiked ? "/cancelLike.do" : "/addLike.do";

          $.ajax({
            url: url,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ boardId, memberIdx }),
            success: function () {
              $btn.text(isLiked ? "♡" : "♥").toggleClass("liked");

              $.ajax({
                url: "/countLike.do",
                type: "GET",
                data: { boardId },
                success: function (count) {
                  $(`.likeCount[data-board-id=${boardId}]`).text("좋아요 수: " + count);
                }
              });
            }
          });
        });
      });
    });
  </script>
</body>
</html>
