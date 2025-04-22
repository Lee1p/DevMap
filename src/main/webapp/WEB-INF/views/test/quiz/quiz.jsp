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
              <div class="col-sm-6"><h3 class="mb-0">BackEnd - JAVA</h3></div>
              <div class="col-sm-6">
                <ol class="breadcrumb float-sm-end">
                  <li style="margin-right: 5px">
                  	<button type="button" class="btn btn-outline-dark">
                  	<a href="/devmap/test/codetest/codetest.do">CodeTest</a> </button>
                  </li>
                  <li>
                  	<button type="button" class="btn btn-outline-dark">
                  	<a href="/devmap/test/quiz/quiz.do">Quiz</a></button>
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
                  <div class="card-header"><h3 class="card-title">JAVA</h3></div>
                  <!-- /.card-header -->
                  <div class="card-body">
                    <table class="table table-bordered">
					  <thead>
					    <tr>
					      <th class="text-center" style="width: 5%;">#</th>
					      <th class="text-center">과목명</th>
					      <!-- <th class="text-center">문제</th> -->
					      <th class="text-center" style="width: 10%;">점수</th>
					      <th class="text-center" style="width: 13%;">진행상태</th>  <!-- 새로운 열 추가 -->
					    </tr>
					  </thead>
					  <tbody>
					    <c:forEach items="${subjectList}" var="subject" varStatus="status">
					      <tr class="align-middle">
					        <td class="text-center">${status.count}</td>
					        <td>${subject.subjectName}</td>
					    <%--    <td>
							  <div class="progress progress-xs">
							    <div class="progress-bar ${subject.progressBarColor}" style="width: ${subject.progressPercentage}%"></div>
							  </div>
							 	문제
							  <div class="progress progress-xs">
								  <div class="progress-bar ${subject.progressBarColor}" style="width: ${subject.correctRate}%"></div>
								</div>
							</td> --%>
							
					        <%-- <td class="text-center"><span class="badge ${subject.badgeColor}" style="font-size: 0.9rem; padding: 0.5rem 0.75rem;">${subject.correctRate} 점</span></td> --%>
								<td class="text-center">
								  <c:choose>
								    <c:when test="${subject.progressPercentage > 0}">
								      <c:choose>
								        <c:when test="${subject.correctRate < 60}">
								          <span class="badge bg-danger" style="font-size: 0.9rem; padding: 0.5rem 0.75rem; min-width: 70px; display: inline-block;">
								            ${subject.correctRate} 점
								          </span>
								        </c:when>
								        <c:otherwise>
								          <span class="badge bg-primary" style="font-size: 0.9rem; padding: 0.5rem 0.75rem; min-width: 70px; display: inline-block;">
								            ${subject.correctRate} 점
								          </span>
								        </c:otherwise>
								      </c:choose>
								    </c:when>
								    <c:otherwise>
								      <span class="badge bg-secondary" style="font-size: 0.9rem; padding: 0.5rem 0.75rem; min-width: 70px; display: inline-block;">
								        미응시
								      </span>
								    </c:otherwise>
								  </c:choose>
								</td>
					        <%-- <td>
					          <!-- 문제풀기 버튼 -->
					          <a href="/devmap/test/quiz/quizsolv.do?subjectSeq=${subject.subjectSeq}" class="btn btn-sm btn-primary">문제풀기</a>
					          
					          <!-- 결과보기 버튼 (진행률이 0보다 큰 경우에만 표시) -->
					          <c:if test="${subject.progressPercentage > 0}">
					            <a href="/devmap/test/quiz/quizfeedback.do?subjectSeq=${subject.subjectSeq}" class="btn btn-sm btn-info">결과보기</a>
					          </c:if>
					        </td> --%>
					        <%-- <td class="text-center">
							  <!-- 진행률이 0인 경우(문제를 풀지 않은 경우)에만 문제풀기 버튼 표시 -->
							  <c:if test="${subject.progressPercentage == 0}">
							    <a href="/devmap/test/quiz/quizsolv.do?subjectSeq=${subject.subjectSeq}" class="btn btn-sm btn-success">문제풀기</a>
							  </c:if>
							  
							  <!-- 진행률이 0보다 큰 경우(문제를 푼 경우)에는 결과보기 버튼만 표시 -->
							  <c:if test="${subject.progressPercentage > 0}">
							    <a href="/devmap/test/quiz/quizfeedback.do?subjectSeq=${subject.subjectSeq}" class="btn btn-sm btn-warning">결과보기</a>
							  </c:if>
							</td> --%>
							<td class="text-center">
							  <c:choose>
							    <c:when test="${subject.progressPercentage == 0}">
							      <a href="/devmap/test/quiz/quizsolv.do?subjectSeq=${subject.subjectSeq}" class="btn btn-sm btn-success">문제풀기</a>
							    </c:when>
							    <c:otherwise>
							      <a href="/devmap/test/quiz/quizfeedback.do?subjectSeq=${subject.subjectSeq}" class="btn btn-sm btn-warning">결과보기</a>
							    </c:otherwise>
							  </c:choose>
							</td>
					      </tr>
					    </c:forEach>
					  </tbody>
					</table>
           		</div>
                  <!-- /.card-body -->
					<div class="card-footer clearfix">
					    <ul class="pagination pagination-sm m-0 float-end">

					        <c:if test="${currentPage > 1}">
					            <li class="page-item"><a class="page-link" href="?page=${currentPage - 1}">&laquo;</a></li>
					        </c:if>
					        <c:forEach begin="1" end="${totalPages}" var="i">
					            <li class="page-item ${currentPage == i ? 'active' : ''}">
					                <a class="page-link" href="?page=${i}">${i}</a>
					            </li>
					        </c:forEach>
					        <c:if test="${currentPage < totalPages}">
					            <li class="page-item"><a class="page-link" href="?page=${currentPage + 1}">&raquo;</a></li>
					        </c:if>
					    </ul>
					</div>
                </div>          
              </div>
              <!-- /.col -->
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
</html>
