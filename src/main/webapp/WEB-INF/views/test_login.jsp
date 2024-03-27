<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>

<html>
<head>
	<title>Reve Spac Site</title>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	<link rel="stylesheet" href="/css/main.css" />
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

<body class="is-preload">
<script>
	$(() => {
		$("#login").click(function() {
			let id = $('#id');
			let pass = $('#pass');
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
				id: rsa.encrypt(id.val())
				, pass: rsa.encrypt(pass.val())
			}
			$('#RSAid').val(data.id);
			$('#RSApass').val(data.pass);

			$.ajaxPOST("login/login", data, function(result){
				if (result.state.code == "0000") {
					console.log(result.body);
					if(result.body.jwtToken!=null&&result.body.jwtToken==''){
						document.cookie = "SKJWTToken=" + result.body.jwtToken + "; path=/"; // 쿠키에 JWT 토큰 저장
					}

					if(result.body.userData != null){
						$('#login_success').show();
						$('#login_fail').css("display","none");
					}else{
						$('#login_success').css("display","none");
						$('#login_fail').show();
					}
					//복호화된 데이터 표출 (따로 분기해야함)
					$('#private_id').val(result.body.decryptionData.id);
					$('#private_pass').val(result.body.decryptionData.pass);
					$('#sha256_pass').val(result.body.sha256pass);


				}
			});
		});
	});
</script>
<!-- Wrapper -->
<div id="wrapper">
	<!-- Main -->
	<div id="main">
		<div class="inner">
			<!-- Header -->
			<header id="header">
				<strong>로그인</strong>
			</header>

				<div style="margin-top: 30px" class="content">
					<div class="col-12 col-12-xsmall">
						공개키 : RSAModulus<input type="text" id="RSAModulus" value="${RSAModulus}" readonly/>
					</div>
					<div class="col-12 col-12-xsmall">
						공개키 : RSAExponent<input type="text" id="RSAExponent" value="${RSAExponent}" readonly/>
					</div>

					<div style="margin-top: 30px" class="col-12 col-12-xsmall">
						ID<input style="width: 50%" type="text" id="id" value="" placeholder="예) root" />
					</div>
					<div class="col-12	 col-12-xsmall">
						PASS<input style="width:50%" type="password" id="pass" value="" placeholder="예) root1234" />
					</div>
					<!-- Break -->
					<div style="margin-top: 20px" class="col-12">
						<ul class="actions">
							<li><input id="login" type="button" value="Send Message" class="primary" /></li>
							<li><input type="reset" value="Reset" /></li>
						</ul>
					</div>

					<div style="display: none"  id="login_success" class="col-12 col-12-xsmall">
						<h2>DB 비교 후 로그인 성공</h2>
					</div>
					<div style="display: none"  id="login_fail" class="col-12 col-12-xsmall">
						<h2>DB 비교 후 로그인 실패</h2>
					</div>


					<div style="margin-top: 30px" class="col-12 col-12-xsmall">
						공개키로 암호화된 ID<input type="text" id="RSAid" readonly/>
					</div>
					<div class="col-12 col-12-xsmall">
						공개키로 암호화된 PASS<input type="text" id="RSApass" readonly/>
					</div>

					<div style="margin-top: 30px" class="col-12 col-12-xsmall">
						비밀키로 복호화된 ID<input type="text" id="private_id" readonly/>
					</div>
					<div class="col-12 col-12-xsmall">
						비밀키로 복호화된 PASS<input type="text" id="private_pass" readonly/>
					</div>

					<div style="margin-top: 30px" class="col-12 col-12-xsmall">
						SHA-256으로 암호화된 PASS<input type="text" id="sha256_pass" readonly/>
					</div>
				</div>
		</div>
	</div>
	<!-- Sidebar -->
	<jsp:include page="test_sidebar.jsp" flush="false"/>
</div>

</body>
</html>