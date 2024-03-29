<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rookie 증권 회원가입</title>
    <link rel="stylesheet" href="/css/signup_styles.css">

    <!-- Scripts -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/browser.min.js"></script>
    <script src="/js/breakpoints.min.js"></script>
    <script src="/js/util.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/common.js"></script>

    <script src="/js/rsa/jsbn.js"></script>
    <script src="/js/rsa/prng4.js"></script>
    <script src="/js/rsa/rng.js"></script>
    <script src="/js/rsa/rsa.js"></script>
    <input type="hidden" id="RSAModulus" name="RSAModulus" value="${RSAModulus}">
    <input type="hidden" id="RSAExponent" name="RSAExponent" value="${RSAExponent}">

<script>
function sendData() {

    let modulus = document.getElementById("RSAModulus").value;
    let exponent = document.getElementById("RSAExponent").value;
    const rsa = new RSAKey()
    rsa.setPublic(modulus,exponent);

  let data = {
	userId: rsa.encrypt($("#USER_ID").val()),
    userPw: rsa.encrypt($("#USER_PW").val()),
    userTelno: $("#USER_TELNO").val(),
    userName: $("#USER_NM").val(),
    userBirth: $("#USER_BIRTH").val(),
    userBank: $("#USER_BANK").val(),
    accountNumber: $("#USER_ACCOUNT_NUM").val(),
    userAgency: $("#USER_AGENCY").val(),
    accessLevel: $("#ACCESS_LEVEL").val(),
    accountBalance: $("#ACCOUNT_BALANCE").val()
  };

    console.log(data.RSA_Pw);
    console.log(data.RSA_Id);

    $.ajaxPOST("test_signup/signup_confirm", data, function(result){
        if (result.state.code == "0000") {
            console.log(result.body);
        }

        if(result.body != null){
            alert("회원 가입을 축하드립니다.")
            window.location.href = "test_login";
        }else{
            alert("가입실패")
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
    