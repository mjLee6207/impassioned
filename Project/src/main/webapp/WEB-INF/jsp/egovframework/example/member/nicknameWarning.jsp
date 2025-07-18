<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.nicknameAutoRenamedYn}">
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>닉네임 자동 변경 안내</title>
    <script>
        window.onload = function () {
            const before = "${sessionScope.nicknameBefore}";
            const after = "${sessionScope.nicknameAfter}";

            const result = confirm(
                "🙂 닉네임이 자동으로 변경되었습니다.\n" +
                "기존 닉네임: " + before + "\n" +
                "변경된 닉네임: " + after + "\n\n" +
                "지금 내 정보를 수정하시겠습니까?"
            );

            if (result) {
                location.href = "${pageContext.request.contextPath}/mypage/mycorrection.do";
            } else {
                location.href = "${pageContext.request.contextPath}/";
            }
        };
    </script>
</head>
<body>
</body>
</html>

<c:remove var="nicknameAutoRenamedYn" scope="session"/>
<c:remove var="nicknameBefore" scope="session"/>
<c:remove var="nicknameAfter" scope="session"/>
</c:if>