<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rookie 증권 회원가입</title>
    <link rel="stylesheet" href="/css/signup_styles.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
function sendData() {
  var formData = {
    userPw: $("#USER_PW").val(),
    userTelno: $("#USER_TELNO").val(),
    userName: $("#USER_NM").val(),
    userBirth: $("#USER_BIRTH").val(),
    userBank: $("#USER_BANK").val(),
    accountNumber: $("#USER_ACCOUNT_NUM").val(),
    userAgency: $("#USER_AGENCY").val(),
    accessLevel: $("#ACCESS_LEVEL").val(),
    accountBalance: $("#ACCOUNT_BALANCE").val()
  };
  
  $.ajax({
    type: "POST",
    url: "/signup_confirm", // 여기에 실제 컨트롤러의 경로를 입력하세요.
    data: JSON.stringify(formData),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function(response) {
      console.log("Data Sent Successfully: ", response);
      // 성공적으로 데이터를 전송했을 때의 로직을 여기에 작성하세요.
    },
    error: function(xhr, status, error) {
      console.error("Error: ", error);
      // 에러가 발생했을 때의 로직을 여기에 작성하세요.
    }
  });
}
</script>


</head>
<body>

<div class="signup-container">
    <div class="logo">Rookie 증권</div>
    <div class="info-text">
        • Rookie증권에 오신 것을 환영합니다<br>
        • 30분동안 이용하지 않을 경우 안전한 금융거래를 위해 자동 로그아웃돼요.<br>
    </div>
    
    <div class="input-group">
        <label for="phone-number">휴대폰 번호</label>
        <input type="text" id="USER_TELNO" name="phone-number" placeholder="휴대폰 번호 입력">
    </div>
    
    <div class="input-group">
        <label for="carrier">통신사</label>
        <select id="USER_AGENCY" name="carrier">
            <option value="">통신사 선택</option>
            <option value="SKT">SKT</option>
            <option value="KT">KT</option>
            <option value="LGU">LG U+</option>
        </select>
    </div>
    
    <div class="input-group">
        <label for="full-name">이름</label>
        <input type="text" id="USER_NM" name="full-name" placeholder="이름 입력">
    </div>
    
    <div class="input-group">
        <label for="ssn">주민등록번호</label>
        <input type="text" id="USER_BIRTH" name="ssn" placeholder="주민등록번호 입력">
    </div>
    
    <div class="input-group">
        <label for="id">아이디</label>
        <input type="text" id="USER_ID" name="id" placeholder="아이디 입력">
    </div>
    
    <div class="input-group">
        <label for="password">비밀번호</label>
        <input type="password" id="USER_PW" name="password" placeholder="비밀번호 입력">
    </div>
    
    <div class="input-group">
        <label for="password-confirm">비밀번호 확인</label>
        <input type="password" id="USER_PW_confirm" name="password-confirm" placeholder="비밀번호 재입력">
    </div>
    
    <div class="input-group">
        <label for="bank">은행사</label>
        <select id="USER_BANK" name="bank">
            <option value="">은행사 선택</option>
            <option value="IBK">기업은행</option>
            <option value="KB">KB국민은행</option>
            <option value="Shinhan">신한은행</option>
            <option value="Woori">우리은행</option>
        </select>
    </div>
    
    <div class="input-group">
        <label for="account-number">계좌번호</label>
        <input type="text" id="USER_ACCOUNT_NUM" name="account-number" placeholder="계좌번호 입력">
    </div>
    
    <button type="button" onclick="sendData()">회원가입</button>
</div>

</body>
</html>
    