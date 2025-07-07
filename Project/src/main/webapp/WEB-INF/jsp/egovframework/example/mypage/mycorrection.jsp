<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원 정보 수정</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/mycorrection.css" />
    <link rel="stylesheet" href="./css/mycorrection.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</head>
<body>
<form id="addForm" name="addForm" method="post" enctype="multipart/form-data" action="/mypage/update.do">
<div class="container container-box">
    <!-- 좌측 정보/메뉴 -->
    <div class="left-box">
        <!-- 프로필 이미지 & 변경 -->
        <img src="${member.profileImagePath != null ? member.profileImagePath : '/img/profile-default.png'}"
             id="profilePreview" class="profile-img" alt="프로필 이미지" />
        <!-- 프로필 사진 변경 버튼 -->
        <input type="file" id="profileImage" name="profileImage" accept="image/*" style="display:none;"
               onchange="readURL(this)">
        <label for="profileImage" class="change-photo-label">
            <i class="bi bi-camera"></i> 프로필 사진 변경
        </label>

        <div class="username">${member.name}</div>
        <div class="useremail">${member.email}</div>
        <div class="joindate">가입일: ${member.joinDate}</div>
        <ul class="side-menu">
            <li><i class="bi bi-person"></i> <span>내 정보 수정</span></li>
            <li><i class="bi bi-box-arrow-in-right"></i> <span>로그아웃</span></li>
        </ul>
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
                <input type="text" id="nickname" name="nickname" class="form-control" value="${member.name}">
            </div>
            <!-- 기본 submit 사용! onclick 제거 -->
            <button type="submit" class="btn btn-edit mt-3">수정 완료</button>
    </div>
</div>
</form>

<!-- 프로필 사진 미리보기 JS -->
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
<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- 부트스트랩 js -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- 유효성 체크 플러그인 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.21.0/dist/jquery.validate.min.js"></script>
<script src="/js/mycorrection-validation-config.js"></script>
</body>
</html>
