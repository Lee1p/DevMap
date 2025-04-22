<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
    <title>코딩 경험 여부</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/asset/css/question.css">
    
    
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
    
    </style>
    
</head>
<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
<div class="bg-element bg-element-1"></div>
<div class="bg-element bg-element-2"></div>
    <div class="app-wrapper">
        <%@ include file="/WEB-INF/views/inc/navbar.jsp" %>
        <main class="app-main">
            <div class="form-container">
                <div class="form-box">
                    <h1 id="dev">개발 유형 테스트</h1>
                    <!-- EL을 사용하여 [회원이름]을 출력 -->
                    <p class="greeting">안녕하세요. ${user.name}님 진심으로 반갑습니다.</p>

                    <form action="<%= request.getContextPath() %>/devtest/CodingQuestion.do" method="POST">
                        <p class="question">
                            <b>Q. "${user.name}"님은 코딩 경험이 있으십니까?</b>
                        </p>
                       <div class="noexp-button-container" style="display: flex; justify-content: center; gap: 30px;">
    <button type="submit" name="experience" value="yes" class="noexp-button" style="width: 150px;">예</button>
    <button type="submit" name="experience" value="no" class="noexp-button" style="width: 150px;">아니요</button>
</div>

                    </form>

                </div>
            </div>
        </main>
        <%@ include file="/WEB-INF/views/inc/footer.jsp" %>
    </div>
    <%@ include file="/WEB-INF/views/inc/footer_js.jsp" %>
</body>
</html>
