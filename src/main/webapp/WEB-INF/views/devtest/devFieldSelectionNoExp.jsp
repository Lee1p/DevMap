<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<%@ include file="/WEB-INF/views/inc/header.jsp"%>
<title>코딩 경험 여부</title>

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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/asset/css/question.css">
<script>
	let selectedField = null; // 현재 선택된 버튼 저장

	function toggleSelection(button) {
		
		document.getElementById("selectedField").value = button.value;
		
		// 기존에 선택된 버튼이 있으면 해제
		if (selectedField && selectedField !== button) {
			selectedField.classList.remove('selected');
		}

		// 버튼의 선택 상태를 토글
		button.classList.toggle('selected');

		// 선택 여부를 추적
		selectedField = button.classList.contains('selected') ? button : null;

		// Next 버튼 활성화/비활성화 처리
		const nextButton = document.getElementById('next');

		if (selectedField) {
			nextButton.classList.add('active'); // 활성화 클래스 추가
			nextButton.classList.remove('disabled'); // 비활성화 클래스 제거
			nextButton.removeAttribute('disabled'); // 버튼의 `disabled` 속성 제거
		} else {
			nextButton.classList.remove('active'); // 활성화 클래스 제거
			nextButton.classList.add('disabled'); // 비활성화 클래스 추가
			nextButton.setAttribute('disabled', 'true'); // 버튼을 다시 비활성화
		}
	}
</script>
</head>

<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
<div class="bg-element bg-element-1"></div>
<div class="bg-element bg-element-2"></div>
	<div class="app-wrapper">
		<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
		<main class="app-main">
			<div class="form-container">
				<div class="form-box">
					<h1 id="dev">개발 분야 선택</h1>
					
					
					<form
						action="<%=request.getContextPath()%>/devtest/DevFieldSelectionNoExp.do"
						method="POST" id="devFieldForm">
						<!-- 선택된 개발 분야 값을 저장하는 hidden input, value 값을 전송할 field -->
						<input type="hidden" name="field" id="selectedField">
						
						<p class="question">
							<b class="highlight" style="font-size: 15px;">"아니오."를
								선택하였습니다.</b>
						</p>
						<p class="question">
							<b class="highlight">Q. ${user.name}님 "어떤 개발 분야에 도전해 보고
								싶으신가요?"</b>
						</p>


						<div class="noexp-button-container">
							<button type="button" name="field" value="1" class="noexp-button"
								onclick="toggleSelection(this)">디자인관련 직업에 관심이 많고, 웹이나
								앱의 사용자 경험을 중요하게 생각한다.</button>
							<button type="button" name="field" value="2" class="noexp-button"
								onclick="toggleSelection(this)">웹사이트나 앱이 빠르게 데이터를 처리하고
								안정적으로 동작하는 이유가 궁금하다.</button>
							<button type="button" name="field" value="3" class="noexp-button"
								onclick="toggleSelection(this)">사람처럼 똑똑한 인공지능이나 머신러닝에
								흥미가 있다.</button>
						</div>

						<!-- Back과 Next 버튼을 form-box 내에서 위치를 지정할 div로 감싸기 -->
						<div class="form-buttons">
							<button type="submit" name="checkNoExp" value="no" id="back"
								class="button-back">Back</button>
							<button type="submit" name="checkNoExp" value="yes" id="next"
								class="button-no-next">Next</button>
						</div>
					</form>
				</div>
			</div>
		</main>

		<%@ include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>
	<%@ include file="/WEB-INF/views/inc/footer_js.jsp"%>
</body>

</html>
