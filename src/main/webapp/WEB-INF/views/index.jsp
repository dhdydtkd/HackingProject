<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>

<html>
	<head>
		<title>Reve Spac Site</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="/css/main.css" />
	</head>
	<script>
		console.log("index change test");
	</script>
	<body class="is-preload">

		<!-- Wrapper -->
			<div id="wrapper">
				<!-- Main -->
				<div id="main">
					<div class="inner">
						<!-- Header -->
						<header id="header">
							<strong>인사말</strong>
						</header>

						<!-- Banner -->
						<section id="banner">
							<div class="content">
								<header>
									<h1>안녕하세요.</h1>
									<p>기술 스펙을 정리하고 실제 구현을 위한 홈페이지 입니다.</p>
								</header>
								<p>
									This is a website for organizing technical hackingprojects and actual implementation.
								</p>
							</div>
						</section>
					</div>
				</div>
				<!-- Sidebar -->
				<jsp:include page="sidebar.jsp" flush="false"/>
			</div>
		<!-- Scripts -->
		<script src="/js/jquery.min.js"></script>
		<script src="/js/browser.min.js"></script>
		<script src="/js/breakpoints.min.js"></script>
		<script src="/js/util.js"></script>
		<script src="/js/main.js"></script>
	</body>
</html>