<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>header</title>
      <link rel="icon" type="image/png" href="/images/favicon.png">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- ✅ 외부 스타일 -->
    <link rel="stylesheet" href="/css/header2.css">
    <link rel="stylesheet" href="/css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

</head>
<body>

    <!-- ✅ 상단 바 -->
    <div class="top-nav-wrapper">
        <c:choose>
            <c:when test="${not empty sessionScope.loginUser}">
                <div class="top-text-links">
                    <%-- <span>${sessionScope.loginUser.nickname}님 환영합니다!</span> --%>
                    <a href="/mypage/mypage.do">마이페이지</a>
                    <a href="/member/logout.do">로그아웃</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="top-text-links">
                    <a href="/member/login.do">로그인</a>
                    <a href="/member/login.do?mode=signup">회원가입</a>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- ✅ 햄버거 메뉴 -->
        <div class="app-menu-container">
            <button class="app-menu-toggle" onclick="toggleAppMenu()">
                <i class="bi bi-grid-3x3-gap-fill"></i>
            </button>
            <div class="app-menu-dropdown" id="appMenuDropdown">
                <a href="/recipe/recipe.do">레시피</a>
                <!-- 7/14 hyj 전체 게시판으로 이동하도록 코드 수정 -->
                <a href="${pageContext.request.contextPath}/board/board.do">게시판</a>
                <a href="http://localhost:8080/event/test.do">이벤트</a>
            </div>
        </div>
    </div>

    <!-- ✅ 스크립트 -->
    <script>
        function toggleAppMenu() {
            const menu = document.getElementById("appMenuDropdown");
            menu.style.display = menu.style.display === "block" ? "none" : "block";
        }

        document.addEventListener("click", function(e) {
            if (!e.target.closest(".app-menu-container")) {
                document.getElementById("appMenuDropdown").style.display = "none";
            }
        });
    </script>
</body>
</html>
