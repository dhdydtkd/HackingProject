<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>로그인 화면</title>
<link rel="stylesheet" href="/css/login_styles.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
</head>

<!-- Scripts -->
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

<script>
    $(() => {
        $("#login").click(function() {
            console.log("login")
            let id = $('#userid');
            let pass = $('#password');
            if(id.val() == ""){
                alert("아이디를 입력 해주세요.");
                id.focus();
                return false;
            }
            if(pass.val() == ""){
                alert("비밀번호를 입력 해주세요.");
                pass.focus();
                return false;
            }
            const rsa = new RSAKey();
            rsa.setPublic($('#RSAModulus').val(),$('#RSAExponent').val());
            let data = {
                user_id: rsa.encrypt(id.val())
                , user_pw: rsa.encrypt(pass.val())
            }
            $('#RSAid').val(data.id);
            $('#RSApass').val(data.pass);

            $.ajaxPOST("login/login", data, function(result){
                if (result.state.code == "0000") {
                    if(result.body.jwtToken!=null&&result.body.jwtToken!=''){
                        document.cookie = "SKJWTToken=" + result.body.jwtToken + "; path=/"; // 쿠키에 JWT 토큰 저장
                        window.location.href = '/main';
                        $("#login_fail").hide();
                    }else{
                        $("#login_fail").show();
                    }
                }
            });
        });
    });
</script>

<body>
<div class="login-container">
    <h1 class="logo">Rookie 증권</h1>
    <div style="display: none;" class="col-12 col-12-xsmall">
        공개키 : RSAModulus<input type="text" id="RSAModulus" value="${RSAModulus}" readonly/>
    </div>
    <div style="display: none;" class="col-12 col-12-xsmall">
        공개키 : RSAExponent<input type="text" id="RSAExponent" value="${RSAExponent}" readonly/>
    </div>
<%--    <form class="login-form">--%>
        <div class="input-group">
            <label for="userid">ID 로그인</label>
            <input type="text" id="userid" name="username" required>
        </div>
        <div class="input-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required>
        </div>
        <p id="login_fail" style="display:none; color:red;">로그인 실패!</p>
        <button type="submit" id="login" class="login-btn">로그인</button>
<%--    </form>--%>
    <button class="signup-btn">회원가입</button>
</div>
</body>
</html>
