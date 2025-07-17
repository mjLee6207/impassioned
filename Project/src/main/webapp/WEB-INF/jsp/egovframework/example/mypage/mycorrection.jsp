<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>회원 정보 수정</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/mycorrection.css" />
</head>
<body>
<jsp:include page="/common/header.jsp" />


<form id="addForm" name="addForm" method="post" enctype="multipart/form-data" action="/mypage/update.do">
<!-- ★ 기존 프로필 이미지 값 hidden 필드 (프로필 유지용) ★ -->
    <input type="hidden" name="originProfileImage" value="${member.profile}" />
    <div class="main-wrap">
        <!-- 좌측 프로필카드 -->
        <div class="profile-card">
            <img src="${member.profile != null ? member.profile : '/images/default_profile.png'}"
                 id="profilePreview" class="profile-img" alt="프로필 이미지" />
            <input type="file" id="profileImage" name="profileImage" accept="image/*" style="display:none;"
                   onchange="readURL(this)">
            <label for="profileImage" class="change-photo-label">
                <i class="bi bi-camera"></i> 프로필 사진 변경
            </label>
            <div class="username">${member.nickname}</div>
            <div class="useremail">${member.email}</div>
            <div class="joindate">
                가입일: <fmt:formatDate value="${member.joinDate}" pattern="yyyy-MM-dd" />
            </div>

        </div>
        <!-- 우측 정보 수정 폼 -->
        <div class="content-area">
            <!-- 임시 비밀번호 안내 메시지 -->
			<c:if test="${member.tempPasswordYn eq 'Y'}">
			    <div class="alert alert-warning" role="alert">
			        <strong>🔐 현재 임시 비밀번호로 로그인 중입니다.</strong><br>
			        보안을 위해 새 비밀번호로 꼭 변경해 주세요.
			    </div>
			</c:if>
		    
            <div class="form-title">회원 정보 수정</div>
            <div class="form-group">
                <label for="email" class="form-label">이메일</label>
                <input type="email" id="email" name="email" class="form-control" value="${member.email}" readonly>
            </div>
            <div class="form-group">
                <label for="password" class="form-label">비밀번호</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="새 비밀번호" minlength="6">
            </div>
            <div class="form-group">
                <label for="repassword" class="form-label">비밀번호 확인</label>
                <input type="password" id="repassword" name="repassword" class="form-control" placeholder="비밀번호 재입력" minlength="6">
            </div>
            <div class="form-group">
                <label for="nickname" class="form-label">닉네임</label>
                <input type="text" id="nickname" name="nickname" class="form-control" value="${member.nickname}">
                <span id="nicknameStatus" style="font-size: 0.9em;"></span>
            </div>
            <button type="button" id="deleteBtn" class="btn btn-danger">회원 탈퇴</button>
            <button type="submit" class="btn btn-edit mt-3">수정 완료</button>
        </div>
    </div>
</form>

<c:if test="${updateSuccess}">
<script>
    alert("회원 정보가 성공적으로 수정되었습니다.");
</script>
</c:if>

<!-- JS 스크립트는 동일하게 유지 -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.21.0/dist/jquery.validate.min.js"></script>

<script>
function readURL(input) {
    if(input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('profilePreview').src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
    }
}
</script>
<script>
let nicknameChecked = true;
$(document).ready(function () {
    $("#nickname").on("blur", function () {
        const nickname = $(this).val().trim();
        if (nickname === "") {
            $("#nicknameStatus").text("닉네임을 입력하세요.").css("color", "red");
            nicknameChecked = false;
            return;
        }
        fetch("/member/nicknameCheck.do?nickname=" + encodeURIComponent(nickname))
            .then(res => res.json())
            .then(result => {
                if (result.available) {
                    $("#nicknameStatus").text("사용 가능한 닉네임입니다.").css("color", "green");
                    nicknameChecked = true;
                } else {
                    $("#nicknameStatus").text("이미 사용 중인 닉네임입니다.").css("color", "red");
                    nicknameChecked = false;
                }
            });
    });
    
    $("#deleteBtn").on("click", function () {
        if (!confirm("정말 회원 탈퇴하시겠습니까?")) return;

        $.post("/member/delete.do", {
            memberIdx: ${member.memberIdx}  // 서버로 보낼 데이터
        })
        .done(function () {
            alert("탈퇴 완료되었습니다.");
            location.href = "/";  // 홈으로 이동
        })
        .fail(function () {
            alert("탈퇴 중 오류가 발생했습니다.");
        });
    });
    	
    $("#addForm").on("submit", function () {
        const pw = $("#password").val();
        const repw = $("#repassword").val();
        if (pw !== "" || repw !== "") {
            if (pw.length < 6) {
                alert("비밀번호는 최소 6자 이상이어야 합니다.");
                $("#password").focus();
                return false;
            }
            if (pw !== repw) {
                alert("비밀번호가 일치하지 않습니다.");
                $("#repassword").focus();
                return false;
            }
        }
        if (!nicknameChecked) {
            alert("닉네임 중복 확인을 해주세요.");
            return false;
        }
        return confirm("정말 수정하시겠습니까?");
    });
});
</script>
<!-- 꼬리말 jsp include-->
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>
