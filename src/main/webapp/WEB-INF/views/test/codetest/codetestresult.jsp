<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="ko">

<!-- highlight.js (코드 예쁘게 보이게 하기) -->
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/11.7.0/styles/default.min.css">
<script
	src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/11.7.0/highlight.min.js"></script>
<script>
	hljs.highlightAll();
</script>
<link
	href="https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/11.7.0/styles/github.min.css">



<style>
.code-box {
	background-color: #f8f9fa;
	border-radius: 10px;
	padding: 1rem;
	border: 1px solid #ddd;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
	max-height: 60vh;
	overflow-y: auto;
	white-space: pre-wrap;
	word-break: break-word;
}

pre code {
	font-family: 'JetBrains Mono', monospace;
	font-size: 14px;
	line-height: 1.6;
}

.code-box pre,
.code-box code {
  white-space: pre-wrap;     /* ✅ 줄바꿈 허용 */
  word-break: break-word;    /* ✅ 단어 단위로 잘라줌 */
  overflow-x: hidden;        /* ✅ 가로 스크롤 제거 */
}


/* 스타일 추가 */
.code-wrapper {
	margin: 0;
	padding: 0;
	background: none !important;
	overflow: unset; /* ✅ 중복 제거 */
}

.code-wrapper code {
	display: block;
	overflow-y: auto; /* ✅ 세로만 스크롤 */
	overflow-x: hidden; /* ❌ 가로 스크롤 제거 */
	padding: 1rem;
	background: #ffffff;
	border-radius: 6px;
	font-family: 'JetBrains Mono', monospace;
	font-size: 14px;
	line-height: 1.6;
	white-space: pre-wrap; /* ✅ 자동 줄바꿈 */
	word-break: break-word; /* ✅ 단어 기준 줄바꿈 */
}
</style>

<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>

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
							<h3 class="mb-0">${result.codeTestTitle}</h3>
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
						<!-- 왼쪽: 유저 코드 -->
						<div class="col-md-6">
							<div class="card h-100">
								<div
									class="card-header d-flex justify-content-between align-items-center">
									<strong>내 코드</strong>

								</div>
								<div id="myCodeBox" class="card-body bg-light"
									style="height: 70vh; overflow: auto;">

									<div class="card-body code-box">
										<pre>
											<code class="language-java">${result.submittedCode}</code>
										</pre>
									</div>

								</div>
							</div>
						</div>

						<!-- 오른쪽: AI 코드 -->
						<div class="col-md-6">
							<div class="card h-100">
								<div class="card-header position-relative">
									<strong>AI 추천 코드</strong> <span
										class="badge ${result.isPassed == 'Y' ? 'bg-success' : 'bg-danger'} position-absolute top-50 end-0 translate-middle-y me-3">
										${result.isPassed == 'Y' ? '통과' : '실패'} </span>

								</div>
								<div class="card-body bg-light" style="height: auto;">
									<div class="code-box">
										<pre>
											<code class="language-java">${result.aiFeedback}</code>
										</pre>
									</div>
								</div>




								<div class="card-footer">
									<p>
										<strong>추천 학습 과목:</strong> ${result.weakConcepts}
									</p>
									<div class="d-flex justify-content-end gap-2">
										<c:if test="${result.isPassed == 'N'}">
											<a
												href="${pageContext.request.contextPath}/test/codetest/codetestsolv.do?seq=${param.codeTestSeq}"
												class="btn btn-secondary">다시 풀기</a>
										</c:if>
										<c:if test="${result.isPassed == 'Y'}">
											<a
												href="${pageContext.request.contextPath}/test/codetest/codetest.do"
												class="btn btn-primary">문제 리스트로</a>
										</c:if>
									</div>

								</div>
							</div>
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
</html>












