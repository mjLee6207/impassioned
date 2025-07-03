<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>좋아요 테스트</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h2>좋아요 테스트</h2>
    <button id="likeButton" onclick="toggleLike()">좋아요</button>
    <p id="likeCount">좋아요 수: </p>

    <script>
        const contextPath = "<%= request.getContextPath() %>";
        const boardId = 123;

        // 로그인된 사용자의 memberIdx 세션에서 받아오기 (없으면 -1)
        const memberIdx = <%= session.getAttribute("memberIdx") == null ? -1 : session.getAttribute("memberIdx") %>;

        if (memberIdx === -1) {
            alert("로그인 후 이용 가능한 기능입니다.");
            $("#likeButton").prop("disabled", true);
        }

        function toggleLike() {
            if (memberIdx === -1) {
                alert("로그인 후 이용해주세요.");
                return;
            }

            $.ajax({
                url: contextPath + "/checkLike.do",
                type: "GET",
                data: {
                    boardId: boardId,
                    memberIdx: memberIdx
                },
                success: function(exists) {
                    if (exists === true || exists === "true") {
                        removeLike();
                    } else {
                        addLike();
                    }
                },
                error: function(xhr) {
                    console.error("상태 확인 실패:", xhr.responseText);
                }
            });
        }

        function addLike() {
            $.ajax({
                url: contextPath + "/addLike.do",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({ boardId, memberIdx }),
                success: function() {
                    getLikeCount();
                    updateButton(true);
                },
                error: function(xhr) {
                    console.error("좋아요 등록 실패:", xhr.responseText);
                }
            });
        }

        function removeLike() {
            $.ajax({
                url: contextPath + "/cancelLike.do",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({ boardId, memberIdx }),
                success: function() {
                    getLikeCount();
                    updateButton(false);
                },
                error: function(xhr) {
                    console.error("좋아요 취소 실패:", xhr.responseText);
                }
            });
        }

        function getLikeCount() {
            $.ajax({
                url: contextPath + "/countLike.do",
                type: "GET",
                data: { boardId },
                success: function(count) {
                    $("#likeCount").text("좋아요 수: " + count);
                },
                error: function(xhr) {
                    console.error("좋아요 수 가져오기 실패:", xhr.responseText);
                }
            });
        }

        function updateButton(isLiked) {
            $("#likeButton").text(isLiked ? "좋아요 취소" : "좋아요");
        }

        function checkInitialStatus() {
            if (memberIdx === -1) return;

            $.ajax({
                url: contextPath + "/checkLike.do",
                type: "GET",
                data: {
                    boardId: boardId,
                    memberIdx: memberIdx
                },
                success: function(exists) {
                    updateButton(exists === true || exists === "true");
                }
            });
        }

        $(document).ready(function() {
            getLikeCount();
            checkInitialStatus();
        });
    </script>
</body>
</html>
