<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<title>성향 테스트 결과</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/asset/css/question.css">
<style>
.recommended {
	font-weight: bold;
	color: blue;
} /* 추천 기술 스타일 */
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

.form-container {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	padding: 40px 20px;
	/* background: linear-gradient(135deg, var(--gradient-1), var(--gradient-2)); */
}

.form-box {
	background-color: white;
	padding: 50px 40px;
	border-radius: var(--radius-xl);
	box-shadow: var(--shadow-lg);
	max-width: 700px;
	width: 100%;
	text-align: center;
	position: relative;
	z-index: 1;
}

#dev {
	font-size: 1.8rem;
	font-weight: 700;
	color: var(--primary-dark);
	margin-bottom: 20px;
}

.question {
	font-size: 1.1rem;
	color: var(--neutral-700);
	margin: 10px 0;
}

table {
	width: 100%;
	margin-top: 30px;
	border-collapse: collapse;
	font-size: 0.95rem;
	box-shadow: var(--shadow-sm);
}

th, td {
	border: 1px solid var(--neutral-200);
	padding: 12px;
	text-align: center;
}

th {
	background-color: var(--primary-light);
	color: white;
	font-weight: 600;
}

td {
	background-color: var(--neutral-100);
}

td.recommended {
	background-color: var(--primary);
	color: white;
	font-weight: 600;
	border-radius: var(--radius-sm);
}

p {
	margin-top: 20px;
	color: var(--neutral-600);
	font-size: 0.95rem;
}

/* 버튼 스타일 */
.noexp-button {
	background-color: var(--accent);
	border: none;
	color: white;
	font-size: 1rem;
	padding: 12px 24px;
	border-radius: var(--radius-lg);
	cursor: pointer;
	box-shadow: var(--shadow-md);
	transition: all 0.3s ease;
}

.noexp-button:hover {
	background-color: var(--accent-dark);
	transform: scale(1.05);
}
</style>
</head>
<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
	<div class="bg-element bg-element-1"></div>
	<div class="bg-element bg-element-2"></div>
	<div class="app-wrapper">
		<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
		<main class="app-main">
			<div class="form-container">
				<div class="form-box">



					<h1 id="dev">개발 유형 테스트 결과</h1>
					<p class="question">
						<b>${user.name}</b>님, 성향 테스트가 완료되었습니다.
					</p>
					<p class="question">
						<b>${user.name}</b>님 선택하신 <b>${selectedFieldName}</b> 로드맵을 제공받겠습니까?
					</p>

					<!-- 성향 테스트 결과 -->
					<h3>개발 로드맵</h3>
					<table border="1">
						<tr>
							<th>카테고리</th>
							<th>기술</th>
							<th>총 점수</th>
						</tr>
						<c:forEach var="techScore" items="${userTechScores}">
							<tr>
								<td>${techScore.categorySeq}</td>
								<td
									class="${techScore.isRecommended eq 'Y' ? 'recommended' : ''}">

									${techScore.techName}</td>
								<td>${techScore.totalScore}</td>
							</tr>
						</c:forEach>
					</table>
					<p>추천된 기술은 **파란색**으로 강조되었습니다.</p>

					<div class="noexp-button-container"
						style="display: flex; justify-content: center; gap: 30px;">
						<form action="<%=request.getContextPath()%>/devtest/ResultTest.do"
							method="POST">
							<button type="submit" class="noexp-button">다시 풀기</button>
						</form>

						<form action="<%=request.getContextPath()%>/roadmap/roadmap.do"
							method="GET">
							<button type="submit" class="noexp-button">로드맵 제공받기</button>
						</form>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer_js.jsp"%>
</body>
</html>
