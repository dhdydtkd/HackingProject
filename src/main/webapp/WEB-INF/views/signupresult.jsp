<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>인증번호 입력</title>
<link rel="stylesheet" href="signupresult_styles.css">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
<script>
function validateCode() {
  var input = document.getElementById('code-input');
  var confirmBtn = document.getElementById('confirm-btn');
  var message = document.getElementById('confirmation-message');
  
  if(input.value === '') {
    message.textContent = '인증번호를 입력해주세요.';
    message.style.color = 'red';
  } else {
    message.textContent = '인증이 완료되었습니다.';
    message.style.color = 'orange';
    confirmBtn.style.display = 'none';
  }
  message.style.display = 'block';
}
</script>
</head>
<body>
<div class="container">
    <h1 class="logo">Rookie 증권</h1>
    <p class="instruction">회원가입 완료를 위해 OTP 인증번호를 입력하세요.</p>
    <input type="text" id="code-input" class="code-input" pattern="\d*" maxlength="6" placeholder="인증번호">
    <p id="confirmation-message" class="confirmation-message"></p>
    <button id="confirm-btn" class="confirm-btn" onclick="validateCode()">확인</button>
    <button class="login-btn">로그인 바로가기</button>
</div>
</body>
</html>
