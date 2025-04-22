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
let selectedField = null;
let currentPage = 1;
const questionsPerPage = 5;
let totalPages;

function toggleSelection(button) {
    if (selectedField && selectedField !== button) {
        selectedField.classList.remove('selected');
    }
    button.classList.toggle('selected');
    selectedField = button.classList.contains('selected') ? button : null;
    updateNextButton();
}

function updateNextButton() {
    const nextButton = document.getElementById('next');
    if (currentPage < totalPages) {
        nextButton.classList.add('active');
        nextButton.classList.remove('disabled');
        nextButton.removeAttribute('disabled');
        nextButton.innerHTML = "다음";
        nextButton.onclick = function() { showPage(currentPage + 1); };
    } else {
        nextButton.innerHTML = "제출";
        const allQuestionsAnswered = checkAllQuestionsAnswered();
        console.log("🔹 모든 질문 체크 완료 여부: " + allQuestionsAnswered); // ✅ 디버깅 로그

        if (allQuestionsAnswered) {
            nextButton.classList.add('active');
            nextButton.classList.remove('disabled');
            nextButton.removeAttribute('disabled');

            nextButton.onclick = function() { 
                console.log("🔹 제출 버튼 클릭됨: 폼 제출 시작"); // ✅ 제출 버튼 클릭 로그 추가
                document.getElementById('questionForm').submit(); 
            };
        } else {
            nextButton.classList.remove('active');
            nextButton.classList.add('disabled');
            nextButton.setAttribute('disabled', 'true');
        }
    }
}


function checkAllQuestionsAnswered() {
    const questionContainers = document.querySelectorAll('.question-container');
    let allAnswered = true;
    
    for (let i = 0; i < questionContainers.length; i++) {
        const radios = questionContainers[i].querySelectorAll('input[type="radio"]:checked');
        if (radios.length === 0) {
            allAnswered = false;
            break;
        }
    }
    
    return allAnswered;
}
function showPage(pageNum) {
    const allQuestions = document.querySelectorAll('.question-container');
    allQuestions.forEach(q => {
        q.style.display = 'none';
    });

    const startIdx = (pageNum - 1) * questionsPerPage;
    const endIdx = Math.min(startIdx + questionsPerPage, allQuestions.length);
    
    for (let i = startIdx; i < endIdx; i++) {
        allQuestions[i].style.display = 'block';
    }

    currentPage = pageNum;
    updatePagination();
    updateNavButtons();
}

function updatePagination() {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';
    
    for (let i = 1; i <= totalPages; i++) {
        const pageLink = document.createElement('span');
        pageLink.textContent = i;
        pageLink.className = 'page-number';
        if (i === currentPage) {
            pageLink.classList.add('current');
        }
        pageLink.addEventListener('click', function() {
            showPage(i);
        });
        pagination.appendChild(pageLink);
    }
}

function updateNavButtons() {
    const backButton = document.getElementById('back');
    const nextButton = document.getElementById('next');
    if (currentPage === 1) {
        backButton.onclick = function() { history.back(); };
    } else {
        backButton.onclick = function() { showPage(currentPage - 1); };
    }
    updateNextButton();
}

//기존 updateProgressBar 함수에 버튼 체크 상태 업데이트 추가
function updateProgressBar() {
    const questionContainers = document.querySelectorAll('.question-container');
    const totalQuestions = questionContainers.length;
    
    let answeredQuestions = 0;
    questionContainers.forEach(container => {
        const checkedRadio = container.querySelector('input[type="radio"]:checked');
        if (checkedRadio) {
            answeredQuestions++;
        }
    });
    
    const progressPercentage = (answeredQuestions / totalQuestions) * 100;
    const progressFill = document.getElementById('progress-fill');
    progressFill.style.width = `${progressPercentage}%`;

    document.getElementById('completed-questions').textContent = answeredQuestions;
    document.getElementById('total-questions').textContent = totalQuestions;

    // 현재 페이지가 마지막 페이지인지 확인하고 버튼 상태 업데이트
    if (currentPage === totalPages) {
        updateNextButton();
    }
}

window.onload = function() {
    const questionContainers = document.querySelectorAll('.question-container');
    totalPages = Math.ceil(questionContainers.length / questionsPerPage);
    
    document.getElementById('total-questions').textContent = questionContainers.length;
    
    const allRadioButtons = document.querySelectorAll('input[type="radio"]');
    allRadioButtons.forEach(radio => {
        radio.addEventListener('change', updateProgressBar);
    });
    
    const paginationDiv = document.createElement('div');
    paginationDiv.id = 'pagination';
    paginationDiv.className = 'pagination';
    document.querySelector('.form-buttons').before(paginationDiv);
    
    showPage(1);
    updateProgressBar();
};
</script>
<style>
.pagination {
    display: flex;
    justify-content: center;
    margin: 20px 0;
}

.page-number {
    display: inline-block;
    padding: 5px 10px;
    margin: 0 5px;
    border: 1px solid #ddd;
    cursor: pointer;
    border-radius: 3px;
}

.page-number.current {
    background-color: #007bff;
    color: white;
    border-color: #007bff;
}

.question-container {
    margin-bottom: 20px;
}

.options {
    display: block;
    width: 100%;
}

.question-container .question {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    text-align: left;
}

.question-container input[type="radio"] {
    margin-right: 10px;
}

.question-container label {
    display: inline-block;
    text-align: left;
    margin: 0;
}

.progress-container {
  margin: 20px 0;
}


.progress-fill {
  height: 100%;
  background-color: #007bff;
  width: 0%;
  transition: width 0.3s ease;
}

.progress-text {
  text-align: center;
  margin-top: 5px;
  font-size: 14px;
  color: #555;
}
</style>
</head>

<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">


<div class="app-wrapper">
<%@ include file="/WEB-INF/views/inc/navbar.jsp"%>
<main class="app-main">
<div class="form-container">
<div class="form-box">
<h1 id="dev">개발 유형 테스트</h1>

<form id="questionForm"
action="<%=request.getContextPath()%>/devtest/DevTestSelection.do"
method="POST">
<p class="question">
<b class="highlight" style="font-size: 25px;">"${selectedFieldName}"를 선택하였습니다.</b>
</p>
<p class="question" style="font-size: 20px;">
<b>로드맵을 제공하기 위한 성향 테스트를 진행하겠습니다.</b>
</p>
<div class="progress-container">
  <div class="progress-bar">
    <div id="progress-fill" class="progress-fill"></div>
  </div>
  <div class="progress-text" style="font-size: 18px;">
    <span id="completed-questions">0</span>/<span id="total-questions">0</span> 
  </div>
</div>

<c:forEach var="question" items="${questionList}">
<div class="question-container">
<p class="question">
<b>Q.${question.questionText}</b>
</p>
<div class="options">
<c:forEach var="option" items="${question.options}">
<div class="question">
<input type="radio" name="question_${question.questionSeq}"
id="option_${option.optionSeq}" value="${option.optionCode}">
<label for="option_${option.optionSeq}">${option.optionText}</label>
</div>
</c:forEach>
</div>
</div>
</c:forEach>

<div class="form-buttons">
<button type="button" id="back" class="button-back">뒤로가기</button>
<button type="button" id="next" class="button-next" disabled>다음</button>
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
