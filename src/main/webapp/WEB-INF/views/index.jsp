<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<style>

:root {
	/* 소프트한 그라데이션 컬러 */
	--gradient-1: #f5f7fa;
	--gradient-2: #c3cfe2;
	/* 메인 컬러: 부드러운 블루 계열 */
	--primary: #6d9feb;
	--primary-light: #b4d0ff;
	--primary-dark: #4c7bd9;
	/* 포인트 컬러: 친근한 코럴 계열 */
	--accent: #ff7e67;
	--accent-light: #ffbfb3;
	--accent-dark: #e05e4b;
	/* 중립 톤 */
	--neutral-50: #f9fafb;
	--neutral-100: #f3f4f6;
	--neutral-200: #e5e7eb;
	--neutral-300: #d2d5db;
	--neutral-600: #6b7280;
	--neutral-700: #374151;
	--neutral-800: #1f2937;
	/* 그림자 */
	--shadow-sm: 0 2px 8px rgba(0, 0, 0, 0.04);
	--shadow-md: 0 8px 16px rgba(0, 0, 0, 0.06);
	--shadow-lg: 0 16px 24px rgba(0, 0, 0, 0.08);
	/* 라운드 처리 */
	--radius-sm: 10px;
	--radius-md: 16px;
	--radius-lg: 24px;
	--radius-xl: 32px;
}

/* 배경 요소 */
.bg-element {
	position: absolute;
	border-radius: 50%;
	filter: blur(80px);
	opacity: 0.4;
	z-index: -1;
}

.bg-element-1 {
	width: 400px;
	height: 400px;
	background: var(--primary-light);
	top: -100px;
	left: -150px;
}

.bg-element-2 {
	width: 350px;
	height: 350px;
	background: var(--accent-light);
	bottom: 100px;
	right: -100px;
}


.header-container {
	text-align: center;
	padding: 80px 20px;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.header-title {
	font-size: 43px;
	font-weight: bold;
	margin-bottom: 20px;
}

.header-subtitle {
	font-size: 22px;
	color: #555;
	margin-bottom: 30px;
	line-height: 1.5;
}

.header-buttons a {
	text-decoration: none;
	padding: 12px 24px;
	margin: 5px;
	border-radius: 6px;
	font-size: 18px;
	display: inline-block;
}

.btn-primary-custom {
	background-color: #4A90E2;
	color: #fff;
}

.btn-secondary-custom {
	border: 1px solid #4A90E2;
	color: #4A90E2;
	background-color: transparent;
}

.header-note {
	font-size: 16px;
	color: #777;
	margin-top: 20px;
}

app-header navbar navbar-expand bg-body {
	
}

</style>


<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css"> 
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script> 


<title>Landing Page - Start Bootstrap Theme</title>

<!-- Bootstrap Core CSS -->
<!-- 기존 Bootstrap CSS 유지 -->
<link href="<%= request.getContextPath() %>/asset/css/bootstrap.min.css" rel="stylesheet">
<!-- 기존 Custom CSS 유지 -->
<link href="<%= request.getContextPath() %>/asset/css/landing-page.css" rel="stylesheet">
<!-- Font Awesome CSS 유지 -->
<link href="<%= request.getContextPath() %>/asset/font-awesome/css/font-awesome.min.css"
      rel="stylesheet" type="text/css">

<!-- 추가된 AOS CSS -->
<!-- <link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" /> -->
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>






<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">

	
	<jsp:include page="/WEB-INF/views/inc/navbar.jsp" />
	
	

	<!-- Header -->
	<div class="header-container">
		<div class="header-title">Developer Map</div>
		<div class="header-subtitle">
			맞춤형 로드맵과 과학적인 복습 시스템으로<br>개발자의 길을 더 쉽고 빠르게
		</div>
		<div class="header-buttons">
			<a href="${pageContext.request.contextPath}/user/login" class="btn-primary-custom">로드맵 생성하기</a> <a href="#"
				class="btn-secondary-custom">기능 알아보기 →</a>
		</div>
		<div class="header-note">
			지금 바로 나만의 맞춤형 로드맵을 <strong>무료로</strong> 생성해 보세요!
		</div>
	</div>

	<!-- Page Content -->

	<a name="services"></a>
	<div class="content-section-a">
		<div class="container">
			<div class="row">
				<div class="col-lg-5 col-sm-6" data-aos="fade-right">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">개인 맞춤형 로드맵 제공</h2>
					<p class="lead">꾸준한 코드테스트로 실력을 점검하고, 망각 곡선에 기반한 체계적인 복습 시스템으로
						학습 내용을 장기 기억으로 전환하세요.</p>
				</div>
				<div class="col-lg-5 col-lg-offset-2 col-sm-6" data-aos="fade-left">
					<img class="img-responsive" src="<%= request.getContextPath() %>/images/ipad.jpg" alt="">
				</div>
			</div>
		</div>
	</div>

	<div class="content-section-b">
		<div class="container">
			<div class="row">
				<div class="col-lg-5 col-lg-offset-1 col-sm-push-6 col-sm-6"
					data-aos="fade-left">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">코드테스트와 과학적 복습 시스템</h2>
					<p class="lead">꾸준한 코드테스트로 실력을 점검하고, 망각 곡선에 기반한 체계적인 복습 시스템으로
						학습 내용을 장기 기억으로 전환하세요.</p>
				</div>
				<div class="col-lg-5 col-sm-pull-6 col-sm-6" data-aos="fade-right">
					<img class="img-responsive" src="<%= request.getContextPath() %>/images/dog.jpg" alt="">
				</div>
			</div>
		</div>
	</div>

	<div class="content-section-a">
		<div class="container">
			<div class="row">
				<div class="col-lg-5 col-sm-6" data-aos="fade-right">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">퀴즈로 명확한 학습 점검</h2>
					<p class="lead">각 학습 파트가 끝난 후 퀴즈를 풀고 일정 점수를 넘기면 다음 단계로 넘어갑니다.
						자신의 학습 수준을 바로바로 확인할 수 있습니다.</p>
				</div>
				<div class="col-lg-5 col-lg-offset-2 col-sm-6" data-aos="fade-left">
					<img class="img-responsive" src="<%= request.getContextPath() %>/images/phones.jpg" alt="">
				</div>
			</div>
		</div>
	</div>

	<div class="content-section-b">
		<div class="container">
			<div class="row">
				<div class="col-lg-5 col-lg-offset-1 col-sm-push-6 col-sm-6"
					data-aos="fade-left">
					<hr class="section-heading-spacer">
					<div class="clearfix"></div>
					<h2 class="section-heading">마이페이지</h2>
					<p class="lead">북마크한 코드테스트, 자주 틀린 문제, 복습해야 할 문제와 함께 성취를 나타내는
						뱃지까지! 개인 맞춤 콘텐츠로 효율적인 학습을 경험해보세요.</p>
				</div>
				<div class="col-lg-5 col-sm-pull-6 col-sm-6" data-aos="fade-right">
					<img class="img-responsive" src="<%= request.getContextPath() %>/images/ipad.jpg" alt="">
				</div>
			</div>
		</div>
	</div>
	<!-- /.container -->




	<a name="contact"></a>
	<div class="banner">

		<div class="container">

			<div class="row">
				<div class="col-lg-6">
					<h2>Connect to Start Bootstrap:</h2>
				</div>
				<div class="col-lg-6">
					<ul class="list-inline banner-social-buttons">
						<li><a href="https://twitter.com/SBootstrap"
							class="btn btn-default btn-lg"><i class="fa fa-twitter fa-fw"></i>
								<span class="network-name">Twitter</span></a></li>
						<li><a
							href="https://github.com/IronSummitMedia/startbootstrap"
							class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i>
								<span class="network-name">Github</span></a></li>
						<li><a href="#" class="btn btn-default btn-lg"><i
								class="fa fa-linkedin fa-fw"></i> <span class="network-name">Linkedin</span></a>
						</li>
					</ul>
				</div>
			</div>

		</div>
		<!-- /.container -->

	</div>
	<!-- /.banner -->

	<!-- Footer -->
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<ul class="list-inline">
						<li><a href="#">Home</a></li>
						<li class="footer-menu-divider">&sdot;</li>
						<li><a href="#about">About</a></li>
						<li class="footer-menu-divider">&sdot;</li>
						<li><a href="#services">Services</a></li>
						<li class="footer-menu-divider">&sdot;</li>
						<li><a href="#contact">Contact</a></li>
					</ul>
					<p class="copyright text-muted small">Copyright &copy; Your
						Company 2014. All Rights Reserved</p>
				</div>
			</div>
		</div>
	</footer>

<!-- 기존 jQuery 및 Bootstrap JS 유지 (이미 추가되어 있을 경우 생략 가능) -->
<!-- <script src="../../asset/js/jquery.js"></script>
<script src="../../asset/js/bootstrap.min.js"></script> -->



<!-- 추가된 AOS JS -->
	
	

	<script>
	
	//console.log(AOS);
	
	
		AOS.init({
			duration : 800, // 부드러운 애니메이션 지속 시간 (ms)
			once : false
		// 스크롤 시 애니메이션이 한번만 적용됨
		});
	</script>

</body>
</html>






