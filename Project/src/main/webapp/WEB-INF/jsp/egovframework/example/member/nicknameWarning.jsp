<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h2>닉네임 중복으로 자동 변경되었습니다</h2>
    <p>원래 닉네임: <strong>${nicknameBefore}</strong></p>
    <p>변경된 닉네임: <strong>${nicknameAfter}</strong></p>

    <form method="get" action="/mypage/mycorrection.do">
        <button type="submit">닉네임 수정하러 가기</button>
    </form>
    
    <form method="get" action="/">
        <button type="submit">지금 닉네임 그대로 사용</button>
    </form>
</body>
</html>