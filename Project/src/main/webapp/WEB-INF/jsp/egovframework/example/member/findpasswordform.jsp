<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>비밀번호 찾기</title>
  <link rel="stylesheet" href="/css/login.css" />  
</head>
<body>
<div class="wrapper">
  <div class="container">
    <div class="right-login">
      <form class="form-box" method="post" action="${pageContext.request.contextPath}/member/findPassword.do" onsubmit="return validateEmailCode();">
        <h1>비밀번호 찾기</h1>

        <!-- 사용자 ID -->
        <input type="text" name="id" placeholder="아이디" required />

        <!-- 이메일 인증 요청 -->
        <div class="form-group">
          <input type="email" id="email" name="email" placeholder="가입한 인증 이메일 주소" required />
          <button type="button" onclick="sendEmailCode()">인증 요청</button>
        </div>

        <!-- 인증번호 입력 -->
        <div class="form-group">
          <input type="text" id="emailCode" placeholder="인증번호 입력" />
          <button type="button" onclick="verifyEmailCode()">인증 확인</button>
        </div>

        <!-- 인증 상태 메시지 -->
        <span id="emailStatus" style="display:block; margin:10px 0; color:green;"></span>
        <span id="countdown" style="display:block; margin:10px 0; color:green;"></span>

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
let timerInterval;
let emailVerified = false;

function startCountdown(duration) {
	  let timeLeft = Number(duration.toString().trim());
	  console.log("시작된 타이머 시간:", timeLeft, typeof timeLeft);

	  let localTimeLeft = timeLeft;

	  const update = () => {
	    const countdownDisplay = document.getElementById("countdown"); 
	    if (!countdownDisplay) {
	      console.error("countdown 요소를 찾을 수 없습니다.");
	      return;
	    }

	    const safeTime = Number.isFinite(localTimeLeft) ? localTimeLeft : 0;
	    const minNum = Math.floor(safeTime / 60);
	    const secNum = safeTime % 60;

	    const minutes = String(minNum).padStart(2, '0');
	    const seconds = String(secNum).padStart(2, '0');
	    const text = "남은 인증 유효시간" + " " + minutes + ":" + seconds;

	    console.log("minutes =", minutes, "| seconds =", seconds, "| text =", text);

	    countdownDisplay.textContent = text;

	    if (safeTime <= 0) {
	      clearInterval(timerInterval);
	      countdownDisplay.textContent = "인증 유효시간 만료됨";
	      alert("인증 유효시간이 만료되었습니다. 다시 요청해주세요.");
	    } else {
	      localTimeLeft--;
	    }
	  };

	  update();
	  timerInterval = setInterval(update, 1000);
	}


	function sendEmailCode() {
	  const email = document.getElementById('email').value.trim();
	  const statusEl = document.getElementById('emailStatus');
	  const emailInput = document.getElementById('email');          
	  const emailCodeInput = document.getElementById('emailCode');           
	  const verifyBtn = document.querySelector('button[onclick="verifyEmailCode()"]');

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
	    headers: { 'Content-Type': 'application/json' },
	  	body: JSON.stringify({
		  	  email: email,
		  	  mode: "findPw"
		  	})
	  })
	  .then(res => res.json())
	  .then(result => {
	    statusEl.textContent = result.message;

	    if (result.success) {
	      alert("인증번호가 이메일로 전송되었습니다.");
	      startCountdown(300); // 5분 = 300초
	      
	      // ✅ 인증 상태 초기화
	      emailVerified = false;

	      // ✅ 입력창/버튼 다시 활성화 (닫혔던 경우 대비)
	      emailInput.readOnly = false;
	      emailInput.style.backgroundColor = "";
	      emailInput.style.color = "";

	      emailCodeInput.value = "";
	      emailCodeInput.readOnly = false;
	      emailCodeInput.style.backgroundColor = "";
	      emailCodeInput.style.color = "";

	      verifyBtn.disabled = false;
	      verifyBtn.style.backgroundColor = "";
	      verifyBtn.style.cursor = "pointer";
	    } else {
	      alert("❌ " + result.message);
	    }
	  })
	  .catch(() => {
	    alert("서버 오류가 발생했습니다.");
	  });
	}

	function verifyEmailCode() {
	  const emailCode = document.getElementById('emailCode').value.trim();
	  const statusEl = document.getElementById('emailStatus');
	  const countdownEl = document.getElementById('countdown');
	  const emailInput = document.getElementById('email');          
	  const emailCodeInput = document.getElementById('emailCode');           
	  const verifyBtn = document.querySelector('button[onclick="verifyEmailCode()"]');

	  if (emailCode === "") {
	    alert("인증번호를 입력해주세요.");
	    return;
	  }

	  fetch('${pageContext.request.contextPath}/member/verifyCode.do', {
	    method: 'POST',
	    headers: { 'Content-Type': 'application/json' },
	    body: JSON.stringify({ code: emailCode })
	  })
	  .then(res => res.json())
	  .then(result => {
	    statusEl.textContent = result.message;

	    if (result.success) {
	      emailVerified = true;
	      
	      // 입력창 및 버튼 잠금 처리
	      emailInput.readOnly = true;
	      emailInput.style.backgroundColor = "#eee";
	      emailInput.style.color = "#555";

	      emailCodeInput.readOnly = true;
	      emailCodeInput.style.backgroundColor = "#eee";
	      emailCodeInput.style.color = "#555";

	      verifyBtn.disabled = true;
	      verifyBtn.style.backgroundColor = "#aaa";
	      verifyBtn.style.cursor = "not-allowed";

	      // 타이머 종료
	      if (timerInterval) {
	        clearInterval(timerInterval);
	      }

	      // 남은 시간 숨김
	      if (countdownEl) {
	        countdownEl.textContent = "";
	        countdownEl.style.display = "none";
	      }
	    }
	  })
	  .catch(() => {
	    alert("서버 오류가 발생했습니다.");
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