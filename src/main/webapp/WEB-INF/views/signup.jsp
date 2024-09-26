<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rookie 증권 회원가입</title>
    <link rel="stylesheet" href="signup_styles.css">
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
        <input type="text" id="phone-number" name="phone-number" placeholder="휴대폰 번호 입력">
    </div>
    
    <div class="input-group">
        <label for="carrier">통신사</label>
        <select id="carrier" name="carrier">
            <option value="">통신사 선택</option>
            <option value="SKT">SKT</option>
            <option value="KT">KT</option>
            <option value="LGU">LG U+</option>
        </select>
    </div>
    
    <div class="input-group">
        <label for="full-name">이름</label>
        <input type="text" id="full-name" name="full-name" placeholder="이름 입력">
    </div>
    
    <div class="input-group">
        <label for="ssn">주민등록번호</label>
        <input type="text" id="ssn" name="ssn" placeholder="주민등록번호 입력">
    </div>
    
    <div class="input-group">
        <label for="id">아이디</label>
        <input type="text" id="id" name="id" placeholder="아이디 입력">
    </div>
    
    <div class="input-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" placeholder="비밀번호 입력">
    </div>
    
    <div class="input-group">
        <label for="password-confirm">비밀번호 확인</label>
        <input type="password" id="password-confirm" name="password-confirm" placeholder="비밀번호 재입력">
    </div>
    
    <div class="input-group">
        <label for="bank">은행사</label>
        <select id="bank" name="bank">
            <option value="">은행사 선택</option>
            <option value="IBK">기업은행</option>
            <option value="KB">KB국민은행</option>
            <option value="Shinhan">신한은행</option>
            <option value="Woori">우리은행</option>
        </select>
    </div>
    
    <div class="input-group">
        <label for="account-number">계좌번호</label>
        <input type="text" id="account-number" name="account-number" placeholder="계좌번호 입력">
    </div>
    
    <button type="submit" class="submit-btn">회원가입</button>
</div>

</body>
</html>
    