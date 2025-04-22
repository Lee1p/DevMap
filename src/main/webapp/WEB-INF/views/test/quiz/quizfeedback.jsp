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
              <div class="col-sm-6"><h3 class="mb-0">Quiz-Score</h3></div>
				<div class="col-sm-6">
				  <ol class="breadcrumb float-sm-end">
				    <li style="margin-right: 5px">
				      <button type="button" class="btn btn-outline-dark" onclick="location.href='/test/codetest/codetest.do'">CodeTest</button>
				    </li>
				    <li>
				      <button type="button" class="btn btn-outline-dark" onclick="location.href='/test/quiz/quiz.do'">Quiz</button>
				    </li>
				  </ol>
				</div>

            </div>
            <!--end::Row-->
          </div>
          <!--end::Container-->
        </div>
        <!--end::App Content Header-->
        <!--begin::App Content-->
        <div class="app-content">
          <!--begin::Container-->
          <div class="container-fluid">
            <!--begin::Row-->
            <div class="row">
              <div class="col">
                <div class="card mb-4">
                  <div class="card-body">
                    <div class="text-center mb-4">
					    <h3>당신의 점수는?</h3>
					    <p class="score" style="${totalScore < 60 ? 'color: red;' : 'color: #007bff;'}">"${totalScore}점"입니다.</p>
					    <c:choose>
					        <c:when test="${totalScore < 60}">
					            <p>다시 도전하세요!</p>
					        </c:when>
					        <c:otherwise>
					            <p>합격입니다!</p>
					        </c:otherwise>
					    </c:choose>
					    
					    <button class="btn btn-sm btn-primary" onclick="location.href='${pageContext.request.contextPath}/test/quiz/quiz.do'">리스트로 돌아가기</button>
					    <button class="btn btn-sm btn-info" onclick="location.href='${pageContext.request.contextPath}/test/quiz/quizsolv.do?subjectSeq=${subjectSeq}'">다시 풀기</button>
					</div>
                    
                    <h4 class="mt-5">문제별 결과</h4>
                    <table class="table table-bordered">
                        <thead>
						    <tr>
						        <th class="text-center" style="width: 5%;">번호</th>
						        <th>문제</th>
						        <th class="text-center" style="width: 5%;">내 답</th>
						        <th class="text-center" style="width: 5%;">정답</th>
						        <th class="text-center" style="width: 5%;">결과</th>
						        <th class="text-center" style="width: 5%;">배점</th>
						    </tr>
						</thead>
                        <tbody>
                            <c:forEach items="${results}" var="result" varStatus="status">
                                <tr>
                                    <td class="text-center">${status.count}</td>
                                    <td>${result.quizTitle}</td>
                                    <td class="text-center">${result.selectedAnswer}</td>
                                    <td class="text-center">${result.correctAnswer}</td>
                                    <td class="text-center">
                                        <c:if test="${result.isCorrect eq 'Y'}">
                                            <span class="correct">O</span>
                                        </c:if>
                                        <c:if test="${result.isCorrect eq 'N'}">
                                            <span class="incorrect">X</span>
                                        </c:if>
                                    </td>
                                    <td class="text-center" style="white-space: nowrap;">
									    <c:if test="${result.isCorrect eq 'Y'}">
									        ${result.quizScore}점
									    </c:if>
									    <c:if test="${result.isCorrect eq 'N'}">
									        0점
									    </c:if>
									</td>
                                </tr>
                                <tr>
                                    <td colspan="6" class="explanation-box" style ="background-color: #FFF8EB;">
                                        <strong>해설:</strong> ${result.explanation}
                                    </td>
                                </tr>
                                <tr>
                                	<td style="border: none;">
                                	</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                  </div> 
                </div>
                <!-- /.card -->
              </div>  
            </div>
            <!--end::Row-->
          </div>
          <!--end::Container-->
        </div>
        <!--end::App Content-->
      </main>
		<%@include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>
	<%@include file="/WEB-INF/views/inc/footer_js.jsp"%>
</body>

<script>
  document.addEventListener('DOMContentLoaded', () => {
    const totalScore = ${totalScore}; // 서버에서 전달된 점수
    const subjectSeq = ${subjectSeq}; // 서버에서 전달된 과목 번호

    // 로그인 상태라면 세션에 userSeq가 있다고 가정
    const userSeq = ${sessionScope.userSeq};

    if (totalScore >= 60) {
        //System.out.println("✅ [QuizFeedback] totalScore >= 60 → DB 저장 시도");

        UserSubjectDAO dao = new UserSubjectDAO();

        if (dao.checkIfExists(userSeq + "", subjectSeq + "")) {
            //System.out.println("➡ 기존 과목 있음 → updateEndDate 실행");
            dao.updateEndDate(userSeq + "", subjectSeq + "");
        } else {
            //System.out.println("➡ 기존 과목 없음 → insertCompletedSubject 실행");
            dao.insertCompletedSubject(userSeq + "", subjectSeq + "");
        }

        dao.close();
    }
</script>
</html>

