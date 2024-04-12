<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>루키증권</title>
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
    // 로컬 스토리지에서 JWT를 가져오기
    const JWTToken = localStorage.getItem('SKJWTToken');
    // 토큰이 있을 경우 자동 로그인을 위한 요청
    if(JWTToken!=null){
        $("#auto-login").prop("checked", true);
        $("#auto_login_popup").show();
        $.ajax({
            type: "POST",
            url: "/login/autologin",
            headers: {
                'Authorization': 'Bearer '+JWTToken,
                // 필요에 따라 추가적인 헤더를 설정할 수 있습니다.
            },
            success: (data) => {
                if(data.state.code == "0000"){
                    window.location.href = '/main';
                }else{
                    alert(data.state.desc);
                }
            }
        });
    }

    $(() => {
        if(JWTToken!=null) {
            $("#auto-login").prop("checked", true);
            $("#auto_login_popup").show();
        }
        $("#signup").click(function(){
            window.location.href = '/signup';
        });
        $("#login").click(function() {
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
                , auto_login_flag: $("#auto-login").is(":checked")
            }
            console.log(document.cookie);
            $("#login_fail").hide();
            $.ajaxPOST("login/login", data, function(result){
                if (result.state.code == "0000") {
                    if(result.body.userData!=null){
                        // 자동로그인 체크 했을 경우 로컬 스토리지로 저장
                        if($("#auto-login").is(":checked")){
                            if(result.body.jwtToken!=null&&result.body.jwtToken!='') {
                                localStorage.setItem('SKJWTToken', result.body.jwtToken);
                                try {
                                    Android.setToken(result.body.jwtToken);
                                } catch (err) {/* 안드로이드를 위한 코드기 때문에 Web에서 에러 발생함.*/}
                            }
                        }
                        window.location.href = '/main';
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
        <div class="checkbox">
            <input type="checkbox" id="auto-login" name="auto-login" />
            <label for="auto-login">자동로그인</label>
        </div>
        <button type="submit" id="login" class="login-btn">로그인</button>
<%--    </form>--%>
    <button class="signup-btn" id="signup">회원가입</button>
</div>
<div id="auto_login_popup" class="auto-login-overlay" style="display: none;">
    <div class="auto-login-message">자동 로그인 중입니다.</div>
</div>
</body>
</html>
