<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>회원 정보 수정</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- CSS -->
    <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/mycorrection.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</head>
<body>

<form id="addForm" name="addForm" method="post" enctype="multipart/form-data" action="/mypage/update.do">
<div class="container container-box">
    <!-- 좌측 정보/메뉴 -->
    <div class="left-box">
        <img src="${member.profile != null ? member.profile : '/img/profile-default.png'}"
             id="profilePreview" class="profile-img" alt="프로필 이미지" />
        <input type="file" id="profileImage" name="profileImage" accept="image/*" style="display:none;"
               onchange="readURL(this)">
        <label for="profileImage" class="change-photo-label">
            <i class="bi bi-camera"></i> 프로필 사진 변경
        </label>

        <div class="username">${member.nickname}</div>
        <div class="useremail">${member.email}</div>
       <div class="joindate">
                가입일:
                <fmt:formatDate value="${member.joinDate}" pattern="yyyy-MM-dd" />
            </div>
            </div>

    <!-- 우측 정보 수정 영역 -->
    <div class="right-box">
        <div class="form-title">회원 정보 수정</div>

        <div class="form-group">
            <label for="email" class="form-label">이메일</label>
            <input type="email" id="email" name="email" class="form-control" value="${member.email}" readonly>
        </div>

        <div class="form-group">
            <label for="password" class="form-label">비밀번호</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="새 비밀번호">
        </div>

        <div class="form-group">
            <label for="repassword" class="form-label">비밀번호 확인</label>
            <input type="password" id="repassword" name="repassword" class="form-control" placeholder="비밀번호 재입력">
        </div>

        <div class="form-group">
            <label for="nickname" class="form-label">닉네임</label>
            <input type="text" id="nickname" name="nickname" class="form-control" value="${member.nickname}">
            <span id="nicknameStatus" style="font-size: 0.9em;"></span>
        </div>

        <button type="submit" class="btn btn-edit mt-3">수정 완료</button>
    </div>
</div>
</form>

<!-- jQuery + 유효성 검사 + 부트스트랩 JS -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.21.0/dist/jquery.validate.min.js"></script>

<!-- 프로필 이미지 미리보기 -->
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

<!-- 닉네임 중복 & 비밀번호 확인 JS -->
<script>
let nicknameChecked = true;

$(document).ready(function () {
    // 닉네임 중복 검사
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

    // 폼 제출 전 검사
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

</body>
</html>