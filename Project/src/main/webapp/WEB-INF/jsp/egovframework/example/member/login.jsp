<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>로그인 &amp; 회원가입</title>
      <link rel="stylesheet" href="/css/login.css" />
</head>
<body>
<div class="wrapper">
  <div class="container">
    <div class="left-slide">
      <img src="${pageContext.request.contextPath}/images/슬라이드/깍두기.jpg"  alt="깍두기" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/부찌.jpg"  alt="부찌" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/파베초콜릿.png"  alt="파베초콜릿" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/오니기리.jpg"  alt="오니기리" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/브런치.jpg"  alt="브런치" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/부르기뇽.jpg"  alt="부르기뇽" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/미네.jpg"  alt="미네" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/위안샤오.jpg"  alt="위안샤오" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/동파육.jpg"  alt="동파육" class="slide-image"/>
	  <img src="${pageContext.request.contextPath}/images/슬라이드/마라.jpg"  alt="마라" class="slide-image"/>
    </div>

    <div class="right-login">
      <!-- 로그인 폼 -->
      <form class="form-box" id="loginForm" method="post" action="${pageContext.request.contextPath}/member/login.do">
        <input type="hidden" name="redirect" value="${param.redirect}" />
        <div class="home-icon-wrapper">
		  <a href="${pageContext.request.contextPath}/home.do">
		    <img class="gohome" src="${pageContext.request.contextPath}/images/home.png" alt="홈으로" />
		  </a>
		</div>
		
        <h1>LOGIN</h1>
        <input type="text" name="id" placeholder="아이디" required />
        <input type="password" name="password" placeholder="비밀번호" required />
		<c:if test="${not empty errorMsg}">
		  <script>
		    alert('${fn:escapeXml(errorMsg)}');
		  </script>
		</c:if>
        <button class="submit-btn" type="submit"><h3>시작하기</h3></button>
        <span class="toggle-link" onclick="toggleForm('signup')"><h3>회원가입</h3></span>
        <div class="find">
          <a href="${pageContext.request.contextPath}/member/findidform.do">아이디 찾기</a> &nbsp;
          <a href="${pageContext.request.contextPath}/member/findpasswordform.do">비밀번호 찾기</a>
        </div>
		<div id="kakaoLoginContainer" style="margin-top: 10px;">
		  <a href="${kakaoLink}">
		    <img src="https://developers.kakao.com/assets/img/about/logos/kakaologin/kr/kakao_account_login_btn_medium_narrow.png"
		         alt="카카오 로그인"
		         style="width: 100%; max-width: 240px; display: block; margin: 0 auto;" />
		  </a>
		</div>
      </form>

      <!-- 회원가입 폼 -->
      <form class="form-box" id="signupForm" method="post" action="${pageContext.request.contextPath}/member/register.do" style="display: none;" onsubmit="return validateForm()">
        <div class="home-icon-wrapper">
		  <a href="${pageContext.request.contextPath}/home.do">
		    <img class="gohome" src="${pageContext.request.contextPath}/images/home.png" alt="홈으로" />
		  </a>
		</div>
        <h1>WELCOME!</h1>

        <div class="form-group">
		  <input type="text" id="id" name="id" placeholder="아이디 (6~20자)" 
		         required minlength="6" maxlength="20"
		         pattern="^[a-zA-Z0-9]{6,20}$" 
		         title="아이디는 영문 또는 숫자 6~20자로 입력하세요.">
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
        <input type="password" id="passwordConfirm" placeholder="비밀번호 확인" required onkeyup="checkPasswordMatch()" />
        <span id="pwStatus"></span>

        <div class="form-group">
          <input type="text" id="nickname" name="nickname" placeholder="닉네임" required />
          <button type="button" onclick="checkNickname()">중복확인</button>
        </div>
        <span id="nicknameStatus"></span>

        <button class="submit-btn" type="submit"><h3>가입하기</h3></button>
        <span class="toggle-link" onclick="toggleForm('login')"><h3>로그인</h3></span>
      </form>
    </div>
  </div>
</div>

<script>
/* 로그인 이미지 슬라이드 */
	document.addEventListener('DOMContentLoaded', function () {
	    const slides = document.querySelectorAll('.slide-image');
	    let currentSlide = 0;
	    
	    // 처음 이미지 활성화
	    slides[currentSlide].classList.add('active');
	    
	    // 슬라이드 자동 전환
	    setInterval(() => {
	      slides[currentSlide].classList.remove('active'); // 현재 활성화된 이미지 숨기기
	      currentSlide = (currentSlide + 1) % slides.length; // 다음 이미지로 이동
	      slides[currentSlide].classList.add('active'); // 새 이미지를 활성화
	    }, 5000); // 5초마다 이미지 변경
	  });
	  
  let emailVerified = false;
  let nicknameChecked = false;
  let idChecked = false;

  function toggleForm(mode) {
    const loginForm = document.getElementById('loginForm');
    const signupForm = document.getElementById('signupForm');
    if (mode === 'signup') {
      loginForm.style.display = 'none';
      signupForm.style.display = 'block';
      resetSignupForm(); // 회원가입폼 초기화
    } else {
      loginForm.style.display = 'block';
      signupForm.style.display = 'none';
    }
  }
  
  // 입력 양식 초기화 함수
  function resetSignupForm() {
	  document.getElementById('id').value = "";
	  document.getElementById('email').value = "";
	  document.getElementById('emailCode').value = "";
	  document.getElementById('password').value = "";
	  document.getElementById('passwordConfirm').value = "";
	  document.getElementById('nickname').value = "";

	  document.getElementById('idStatus').textContent = "";
	  document.getElementById('emailStatus').textContent = "";
	  document.getElementById('pwStatus').textContent = "";
	  document.getElementById('nicknameStatus').textContent = "";

	  // 인증 상태 초기화
	  emailVerified = false;
	  nicknameChecked = false;
	  idChecked = false;
	}
  
  function validateForm() {
    const pw = document.getElementById('password').value;
    const pwc = document.getElementById('passwordConfirm').value;
    const pwStatus = document.getElementById('pwStatus');

    if (pw !== pwc) {
      pwStatus.textContent = "비밀번호가 일치하지 않습니다.";
      pwStatus.style.color = "red";
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
      alert("아이디 중복 확인을 완료해주세요.");
      return false;
    }
    return true;
  }

  function checkId() {
	  const id = document.getElementById('id').value.trim();

	  if (id === "") {
	    alert("아이디를 입력해주세요.");
	    return;
	  }

	  const idRegex = /^[a-zA-Z0-9]{6,20}$/;  // 영문/숫자 조합, 6~20자
	  if (!idRegex.test(id)) {
	    alert("아이디는 영문 또는 숫자 조합 6~20자로 입력해주세요.");
	    return;
	  }

	  fetch('${pageContext.request.contextPath}/member/idCheck.do?id=' + encodeURIComponent(id))
	    .then(res => res.json())
	    .then(result => {
	      if (result.available) {
	        idChecked = true;
	        document.getElementById('idStatus').textContent = "✅ 사용 가능한 아이디입니다.";
	        document.getElementById('idStatus').style.color = "green";
	      } else {
	        idChecked = false;
	        document.getElementById('idStatus').textContent = "❌ 이미 사용 중인 아이디입니다.";
	        document.getElementById('idStatus').style.color = "red";
	      }
	    })
	    .catch(() => {
	      alert("서버와 통신 중 오류가 발생했습니다.");
	    });
	}

  function checkNickname() {
    const nickname = document.getElementById('nickname').value.trim();
    if (nickname === "") {
      alert("닉네임을 입력해주세요.");
      return;
    }
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

  function sendEmailCode() {
    const email = document.getElementById('email').value.trim();
    if (email === "") {
      alert("이메일을 입력해주세요.");
      return;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      alert("올바른 이메일 형식이 아닙니다.");
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
    const emailCode = document.getElementById('emailCode').value.trim();
    if (emailCode === "") {
      alert("인증번호를 입력해주세요.");
      return;
    }
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

  function checkPasswordMatch() {
    const pw = document.getElementById('password').value;
    const pwc = document.getElementById('passwordConfirm').value;
    const status = document.getElementById('pwStatus');
    if (pw === "" || pwc === "") {
      status.textContent = "";
      return;
    }
    if (pw.length < 6) {
        status.textContent = "비밀번호는 최소 6자 이상이어야 합니다.";
        status.style.color = "red";
        return;
      }
    if (pw === pwc) {
      status.textContent = "비밀번호가 일치합니다.";
      status.style.color = "green";
    } else {
      status.textContent = "비밀번호가 일치하지 않습니다.";
      status.style.color = "red";
    }
  }

  window.onload = function () {
    const params = new URLSearchParams(window.location.search);
    const mode = params.get("mode");
    if (mode === "signup") {
      toggleForm('signup');
    } else {
      toggleForm('login');
    }
  };

</script>
</body>
</html>