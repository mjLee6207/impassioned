<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>비밀번호 찾기</title>
  <link rel="stylesheet" href="/css/login.css" />
  <link rel="stylesheet" href="/css/findpasswordform.css" />
  
</head>
<body>
<div class="wrapper">
  <div class="container">
    <div class="right-login">
      <form class="form-box" method="post" action="${pageContext.request.contextPath}/member/findPassword.do" onsubmit="return validateEmailCode();">
        <h1>비밀번호 찾기</h1>

        <!-- 사용자 ID -->
        <input type="text" name="id" placeholder="아이디 (이메일 형식)" required />

        <!-- 이메일 인증 요청 -->
        <div class="form-group">
          <input type="email" id="email" name="email" placeholder="가입한 이메일 주소" required />
          <button type="button" onclick="sendEmailCode()">인증 요청</button>
        </div>

        <!-- 인증번호 입력 -->
        <div class="form-group">
          <input type="text" id="emailCode" placeholder="인증번호 입력" />
          <button type="button" onclick="verifyEmailCode()">인증 확인</button>
        </div>

        <!-- 인증 상태 메시지 -->
        <span id="emailStatus" style="display:block; margin:10px 0; color:green;"></span>

        <!-- 결과 메시지 출력 -->
        <c:if test="${not empty msg}">
          <p style="margin-top: 16px; color: blue;">${msg}</p>
        </c:if>

        <button class="submit-btn" type="submit">임시 비밀번호 발급</button>

        <!-- 하단 경로 링크 수정됨 -->
        <div class="find" style="margin-top: 10px; text-align: center;">
          <a href="${pageContext.request.contextPath}/member/findidform.do" style="margin-right: 10px;">아이디 찾기</a>
          <a href="${pageContext.request.contextPath}/member/findpasswordform.do">비밀번호 찾기</a>
        </div>

        <div class="find" style="margin-top: 16px; text-align: center;">
          <a href="${pageContext.request.contextPath}/member/login.do">로그인 화면으로</a>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
  let emailVerified = false;

  function sendEmailCode() {
    const email = document.getElementById('email').value;

    if (!email || !email.includes("@")) {
      alert("올바른 이메일 형식을 입력해주세요.");
      return;
    }

    fetch('${pageContext.request.contextPath}/member/sendEmailCode.do', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({email})
    }).then(res => res.text()).then(msg => {
      document.getElementById('emailStatus').textContent = msg;
    });
  }

  function verifyEmailCode() {
    const emailCode = document.getElementById('emailCode').value;

    fetch('${pageContext.request.contextPath}/member/verifyCode.do', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({code: emailCode})
    }).then(res => res.json()).then(result => {
      if (result.success) {
        emailVerified = true;
        document.getElementById('emailStatus').textContent = "✅ 인증 완료되었습니다.";
        document.getElementById('email').readOnly = true;
        document.getElementById('emailCode').readOnly = true;
      } else {
        document.getElementById('emailStatus').textContent = "❌ 인증번호가 일치하지 않습니다.";
      }
    });
  }

  function validateEmailCode() {
    if (!emailVerified) {
      alert("이메일 인증을 완료해주세요.");
      return false;
    }
    return true;
  }
</script>
</body>
</html>