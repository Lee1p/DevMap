<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>
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

    .score {
        font-size: 3rem;
        font-weight: bold;
        color: #007bff;
    }
    .correct {
        color: green;
        font-weight: bold;
    }
    .incorrect {
        color: red;
        font-weight: bold;
    }
    .explanation-box {
        /* background-color: #f0f0f0; */
        padding: 15px;
        border-radius: 5px;
        margin-top: 10px;
    }
    .timer {
        font-size: 1.5rem;
        font-weight: bold;
        text-align: center;
        margin-bottom: 20px;
        color: #dc3545;
    }
    .option {
        margin-bottom: 15px;
        padding: 10px;
        border: 1px solid #eee;
        border-radius: 5px;
        cursor: pointer;
    }
    .option:hover {
        background-color: #f0f0f0;
    }
    .answer-number {
        display: inline-block;
        width: 30px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        border: 1px solid #ddd;
        border-radius: 50%;
        margin: 5px;
        cursor: pointer;
    }
    .answer-selected {
        background-color: #007bff;
        color: white;
    }
    .user-selected {
        background-color: #28a745;
        color: white;
    }
    .current-question {
        background-color: #ffc107 !important;
        border: 2px solid #dc3545;
    }
</style>
</head>
<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
<div class="bg-element bg-element-1"></div>
<div class="bg-element bg-element-2"></div>
    <div class="app-wrapper">
        <%@include file="/WEB-INF/views/inc/navbar.jsp"%>
        <main class="app-main">
            <!--begin::App Content Header-->
            <div class="app-content-header">
                <!--begin::Container-->
                <div class="container-fluid">
                    <!--begin::Row-->
                    <div class="row mb-5">
                        <div class="col-sm-6">
                            <h3 class="mb-0">Quiz-Solv</h3>
                        </div>
                       <!--  <div class="col-sm-6">
                            <div class="timer" id="timer">남은 시간: 30:00</div>
                        </div> -->
                    </div>
                    <!--end::Row-->
                </div>
                <!--end::Container-->
            </div>
            <!--end::App Content Header-->
            <div class="app-content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-9">
                            <div class="card mb-4">
                                <div class="card-body">
                                    <h4>${quiz.quizTitle}</h4>
                                    <hr>
                                    <form id="quizForm" method="POST" action="quizsolv.do">
                                        <input type="hidden" name="quizSeq" value="${quiz.quizSeq}">
                                        <input type="hidden" name="subjectSeq" value="${quiz.subjectSeq}">
                                        <input type="hidden" id="selectedAnswer" name="selectedAnswer" value="">
                                        <!-- <input type="hidden" id="timeLeft" name="timeLeft" value=""> -->
                                        
                                        <div class="options">
                                            <c:set var="key" value="selectedAnswer_${quiz.quizSeq}" />
                                            <div class="option" onclick="selectOption('A')">
                                                <input type="radio" name="answer" id="optionA" value="A" ${sessionScope[key] == 'A' ? 'checked' : ''}>
                                                <label for="optionA">A. ${quiz.optionA}</label>
                                            </div>
                                            <div class="option" onclick="selectOption('B')">
                                                <input type="radio" name="answer" id="optionB" value="B" ${sessionScope[key] == 'B' ? 'checked' : ''}>
                                                <label for="optionB">B. ${quiz.optionB}</label>
                                            </div>
                                            <c:if test="${not empty quiz.optionC}">
                                                <div class="option" onclick="selectOption('C')">
                                                    <input type="radio" name="answer" id="optionC" value="C" ${sessionScope[key] == 'C' ? 'checked' : ''}>
                                                    <label for="optionC">C. ${quiz.optionC}</label>
                                                </div>
                                            </c:if>
                                            <c:if test="${not empty quiz.optionD}">
                                                <div class="option" onclick="selectOption('D')">
                                                    <input type="radio" name="answer" id="optionD" value="D" ${sessionScope[key] == 'D' ? 'checked' : ''}>
                                                    <label for="optionD">D. ${quiz.optionD}</label>
                                                </div>
                                            </c:if>
                                        </div>
                                        
                                        <div class="text-center mt-4">
                                            <c:if test="${prevQuizSeq > 0}">
                                                <a href="quizsolv.do?quizSeq=${prevQuizSeq}&subjectSeq=${quiz.subjectSeq}" class="btn btn-secondary">이전 문제</a>
                                            </c:if>
                                            <button type="button" onclick="customSubmit()" class="btn btn-primary">다음 문제</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card mb-4">
                                <div class="card-header">
                                    <h5 class="card-title">답안지</h5>
                                </div>
                                <div class="card-body">
                                    <div class="answer-numbers">
                                        <c:forEach items="${quizList}" var="q" varStatus="status">
                                            <c:set var="answerKey" value="selectedAnswer_${q.quizSeq}" />
                                            <span class="answer-number ${answeredQuizzes.contains(q.quizSeq) ? 'answer-selected' : ''} ${not empty sessionScope[answerKey] ? 'user-selected' : ''}" 
                                                  id="answer-${q.quizSeq}" 
                                                  onclick="location.href='quizsolv.do?quizSeq=${q.quizSeq}&subjectSeq=${quiz.subjectSeq}'">
                                                ${status.count}
                                            </span>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="/WEB-INF/views/inc/footer.jsp"%>
    </div>
    <%@include file="/WEB-INF/views/inc/footer_js.jsp"%>
<script>
/* 		//타이머 설정 (서버에서 받은 남은 시간)
		let timeLeft = ${timeLeft != null ? timeLeft : 1800}; // 서버에서 전달받은 남은 시간 (초)
		const timerElement = document.getElementById('timer');
		
		// 페이지 로드 즉시 타이머 표시
		const minutes = Math.floor(timeLeft / 60);
		let seconds = timeLeft % 60;
		seconds = seconds < 10 ? '0' + seconds : seconds;
		timerElement.innerHTML = `남은 시간: ${minutes}:${seconds}`;
		
		const timer = setInterval(function() {
		    timeLeft--;
		    
		    const minutes = Math.floor(timeLeft / 60);
		    let seconds = timeLeft % 60;
		    seconds = seconds < 10 ? '0' + seconds : seconds;
		    
		    timerElement.innerHTML = `남은 시간: ${minutes}:${seconds}`;
		    
		    if (timeLeft <= 0) {
		        clearInterval(timer);
		        alert('시험 시간이 종료되었습니다.');
		        document.getElementById('quizForm').submit();
		    }
		}, 1000); */
    function customSubmit() {
        if (document.getElementById('selectedAnswer').value === '') {
            alert('답변을 선택해주세요.');
            return false;
        }
        
        /* document.getElementById('timeLeft').value = timeLeft; */
        document.getElementById('quizForm').submit();
    }
    
    function selectOption(answer) {
        document.getElementById('option' + answer).checked = true;
        document.getElementById('selectedAnswer').value = answer;
        
        const quizSeq = ${quiz.quizSeq};
        const answerElement = document.getElementById('answer-' + quizSeq);
        if (answerElement) {
            answerElement.classList.add('user-selected');
        }
    }
    
    window.onload = function() {
        const quizSeq = ${quiz.quizSeq};
        const answerElement = document.getElementById('answer-' + quizSeq);
        if (answerElement) {
            answerElement.style.backgroundColor = '#ffc107';
        }
        
        // 이전에 선택한 답변 복원
        <c:set var="key" value="selectedAnswer_${quiz.quizSeq}" />
        const selectedAnswer = '${sessionScope[key]}';
        if (selectedAnswer) {
            selectOption(selectedAnswer);
        }
    };
</script>
</body>
</html>
