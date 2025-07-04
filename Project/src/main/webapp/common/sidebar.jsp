<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="profile-card">
    <img src="${member.profileImagePath != null ? member.profileImagePath : '/img/profile-default.png'}" class="profile-img" alt="프로필 사진">
    <div class="username">이름: <b>${member.name}</b></div>
    <div class="useremail">이메일: ${member.email}</div>
    <div class="joindate">가입일: ${member.joinDate}</div>
    <div class="card-menu">
          <button class="card-menu-btn" type="button"><i class="bi bi-person"></i>내 정보 수정</button> 
        <button class="card-menu-btn logout-btn" type="button"><i class="bi bi-box-arrow-right"></i>로그아웃</button>
    </div>
</div>
</body>
</html>