<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>로그인 & 회원가입</title>
      <link rel="stylesheet" href="/css/login.css" />
</head>
<body>
<div class="wrapper">
  <div class="container">
    <div class="left-slide">
    <img src="${pageContext.request.contextPath}/images/3조사진/마크사진.png"  alt="마크" />
    </div>

    <div class="right-login">
      <!-- 로그인 폼 -->
      <form class="form-box" id="loginForm" method="post" action="${pageContext.request.contextPath}/member/login.do">
        <h1>로그인</h1>
        <input type="email" name="id" placeholder="이메일" required />
        <input type="password" name="password" placeholder="비밀번호" required />
        <button class="submit-btn" type="submit">시작하기</button>
        <span class="toggle-link" onclick="toggleForm('signup')">회원가입</span>
      </form>

      <!-- 회원가입 폼 -->
      <form class="form-box" id="signupForm" method="post" action="${pageContext.request.contextPath}/member/register.do" style="display: none;" onsubmit="return validateForm()">
        <h1>회원가입</h1>

        <div class="form-group">
          <input type="email" id="id" name="id" placeholder="이메일형식의 아이디" required />
          <button type="button" onclick="checkId()">중복확인</button>
        </div>
        <span id="idStatus"></span>

        <div class="form-group">
         <input type="email" id="email" name="email" placeholder="이메일" required />
         <button type="button" onclick="sendEmailCode()">인증요청</button>
        </div>
        <div class="form-group">
          <input type="text" id="emailCode" placeholder="인증번호 입력" />
          <button type="button" onclick="verifyEmailCode()">인증확인</button>
        </div>
        <span id="emailStatus"></span>

        <input type="password" id="password" name="password" placeholder="비밀번호" required />
        <input type="password" id="passwordConfirm" placeholder="비밀번호 확인" required />
        <span id="pwStatus"></span>

        <div class="form-group">
          <input type="text" id="nickname" name="nickname" placeholder="닉네임" required />
          <button type="button" onclick="checkNickname()">중복확인</button>
        </div>
        <span id="nicknameStatus"></span>

        <button class="submit-btn" type="submit">가입하기</button>
        <span class="toggle-link" onclick="toggleForm('login')">로그인</span>
      </form>
    </div>
  </div>
</div>

<script>
  const slides = document.querySelectorAll('.slide-image');
  let currentSlide = 0;
  setInterval(() => {
    slides[currentSlide].classList.remove('active');
    currentSlide = (currentSlide + 1) % slides.length;
    slides[currentSlide].classList.add('active');
  }, 3000);

  function toggleForm(formType) {
    document.getElementById("loginForm").style.display = formType === 'login' ? 'block' : 'none';
    document.getElementById("signupForm").style.display = formType === 'signup' ? 'block' : 'none';
  }

  let emailVerified = false;
  let nicknameChecked = false;
  let idChecked = false;

  function validateForm() {
    const pw = document.getElementById('password').value;
    const pwc = document.getElementById('passwordConfirm').value;
    if (pw !== pwc) {
      document.getElementById('pwStatus').textContent = "비밀번호가 일치하지 않습니다.";
      return false;
    }
    if (!emailVerified) {
      alert("이메일 인증을 완료해주세요.");
      return false;
    }
    if (!nicknameChecked) {
      alert("닉네임 중복 확인을 완료해주세요.");
      return false;
    }
    if (!idChecked) {
      alert("아이디(이메일) 중복 확인을 완료해주세요.");
      return false;
    }
    return true;
  }

  function checkNickname() {
    const nickname = document.getElementById('nickname').value;
    fetch('${pageContext.request.contextPath}/member/nicknameCheck.do?nickname=' + encodeURIComponent(nickname))
      .then(res => res.json()).then(result => {
        if (result.available) {
          nicknameChecked = true;
          document.getElementById('nicknameStatus').textContent = "사용 가능한 닉네임입니다.";
        } else {
          document.getElementById('nicknameStatus').textContent = "이미 사용 중인 닉네임입니다.";
        }
      });
  }

  function checkId() {
    const id = document.getElementById('id').value;
    fetch('${pageContext.request.contextPath}/member/idCheck.do?id=' + encodeURIComponent(id))
      .then(res => res.json()).then(result => {
        if (result.available) {
          idChecked = true;
          document.getElementById('idStatus').textContent = "사용 가능한 아이디입니다.";
        } else {
          document.getElementById('idStatus').textContent = "이미 사용 중인 아이디입니다.";
        }
      });
  }

  function sendEmailCode() {
    const email = document.getElementById('email').value;
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
        document.getElementById('emailStatus').textContent = "인증 완료되었습니다.";
      } else {
        document.getElementById('emailStatus').textContent = "인증번호가 일치하지 않습니다.";
      }
    });
  }
</script>
</body>
</html>
