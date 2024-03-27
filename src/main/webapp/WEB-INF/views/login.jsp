<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인 화면</title>
<link rel="stylesheet" href="login_styles.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
</head>
<body>
<div class="login-container">
    <h1 class="logo">Rookie 증권</h1>
    <form class="login-form">
        <div class="input-group">
            <label for="username">ID 로그인</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="input-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="login-btn">로그인</button>
    </form>
    <button class="signup-btn">회원가입</button>
</div>
</body>
</html>
