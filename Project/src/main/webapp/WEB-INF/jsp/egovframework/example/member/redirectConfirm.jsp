<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>임시 비밀번호 경고</title>
    <script>
        window.onload = function () {
            const result = confirm("🔐 임시 비밀번호로 로그인하셨습니다.\n지금 비밀번호를 변경하시겠습니까?");
            
            if (result) {
                // [예] → 내 정보 수정 페이지로 이동
                location.href = "${pageContext.request.contextPath}/mypage/mycorrection.do";
            } else {
                // [아니오] → 그냥 메인으로 이동
                location.href = "${pageContext.request.contextPath}/";
            }
        };
    </script>
</head>
<body> 
</body>
</html>