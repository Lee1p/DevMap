<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/asset/css/style.css">
    <title>Dev Test</title>
    
    
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

body, html {
    margin: 0;
    padding: 0;
    height: 100%;
    font-family: 'Noto Sans KR', sans-serif;
    background: linear-gradient(135deg, var(--gradient-1), var(--gradient-2));
    overflow: hidden;
}

.app-main {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}

.container {
    text-align: center;
    background-color: white;
    padding: 60px 50px;
    border-radius: var(--radius-xl);
    box-shadow: var(--shadow-lg);
    max-width: 500px;
    width: 90%;
    position: relative;
    z-index: 1;
}

/* 텍스트 스타일 */
#devTest_start_text p {
    margin: 10px 0;
    font-size: 1.2rem;
    color: var(--neutral-700);
}

#devTest_start_text p:first-child {
    font-size: 1.5rem;
    font-weight: 700;
    color: var(--primary-dark);
}

#devTest_start_text p:nth-of-type(2) {
    font-size: 1.3rem;
    color: var(--accent-dark);
}

#devTest_start_text p:last-child {
    font-weight: 500;
    font-size: 1.2rem;
}

/* 버튼 스타일 */
.button {
    position: relative;
    display: inline-block;
    padding: 14px 28px;
    margin-top: 30px;
    background-color: var(--primary);
    border: none;
    border-radius: var(--radius-lg);
    color: white;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    box-shadow: var(--shadow-md);
    transition: all 0.3s ease;
}

.button:hover {
    background-color: var(--primary-dark);
    transform: scale(1.05);
    box-shadow: 0 12px 20px rgba(0, 0, 0, 0.12);
}

/* 불필요한 내부 장식 숨김 */
.button__line,
.button__drow1,
.button__drow2 {
    display: none;
}

.button__text {
    z-index: 2;
    position: relative;
}


    
    </style>

</head>
<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
<div class="bg-element bg-element-1"></div>
<div class="bg-element bg-element-2"></div>
    <div class="app-wrapper">
        <%@ include file="/WEB-INF/views/inc/navbar.jsp" %>
        <main class="app-main">
            <div class="container">
                <div id="devTest_start_text">
                    <p>Dev에 오신 것을 환영합니다.</p>
                    <br>
                    <p>"개발자 로드맵을 찾고 싶다면?"</p>
                    <br>
                    <p>1분이면 충분합니다!</p>
                    <br>
                    <p>지금 바로 개발 유형 테스트를 시작해보세요.</p>
                </div>

                <!-- 진행하기 버튼을 form 요소로 감싸서 POST 방식으로 서블릿에 요청을 보냄 -->
                <form action="<%=request.getContextPath()%>/devtest.do" method="POST">
                    <button type="submit" class="button type--A">
                        <div class="button__line"></div>
                        <div class="button__line"></div>
                        <span class="button__text">진행하기</span>
                        <div class="button__drow1"></div>
                        <div class="button__drow2"></div>
                    </button>
                </form>
            </div>

        </main>
        <%@ include file="/WEB-INF/views/inc/footer.jsp" %>
    </div>
    <%@ include file="/WEB-INF/views/inc/footer_js.jsp" %>

    <script src="<%= request.getContextPath() %>/views/devtest/js/script.js"></script>

</body>
</html>
