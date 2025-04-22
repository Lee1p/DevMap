<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>
<!-- 마크다운 파서 -->
	<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>

<!-- GitHub 스타일 마크다운 CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.2.0/github-markdown.min.css">

<!-- 코드 하이라이팅 highlight.js -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>

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


/* 줄번호만 가볍게 표시 */
.hljs-ln {
	border-collapse: collapse !important;
}

.hljs-ln tr {
	border: none !important;
	background: none !important;
}

.hljs-ln td {
	padding: 0 !important;
	border: none !important;
}

.hljs-ln-numbers {
	text-align: right;
	color: #bbb;
	padding: 0 12px 0 8px;
	user-select: none;
	vertical-align: top;
	background: none !important;
	border-right: 1px solid #eee;
	width: 40px;
}

.hljs-ln-code {
	padding-left: 10px;
}

/* 코드 전체 박스 꾸밈 */
.custom-code {
	border: 1px solid #ddd;
	border-radius: 6px;
	overflow: hidden;
	margin: 1.5rem 0;
	background: #fefefe;
}

.code-header {
	background: #f5f5f5;
	padding: 0.4rem 0.8rem;
	display: flex;
	justify-content: flex-end;
	gap: 6px;
	border-bottom: 1px solid #ddd;
}

.code-header .dot {
	width: 10px;
	height: 10px;
	border-radius: 50%;
}

.dot.red {
	background: #ff5f56;
}

.dot.yellow {
	background: #ffbd2e;
}

.dot.green {
	background: #27c93f;
}

body .bg-primary {
	background-color: #3E3F5B !important;
}

body .badge bg-light text-dark {
	background: red;
}
.markdown-body .highlight pre, .markdown-body pre {
	background-color: #fff;
}


  
.markdown-body {
    background-color: #fff;
    padding: 20px 40px 10px 40px;
    color: black;
}
</style>






</head>

<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
<div class="bg-element bg-element-1"></div>
<div class="bg-element bg-element-2"></div>s
	<div class="app-wrapper">
		<%@include file="/WEB-INF/views/inc/navbar.jsp"%>
		<main class="app-main">
			<div class="card shadow-sm mb-4 border-0">

				<div
					class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
					<div style="width: 100%; text-align: center;">
						<h4 class="mb-0">${subject.subjectName}</h4>
					</div>

					<span class="badge bg-light text-dark"> <c:choose>
							<c:when test="${subject.subjectDifficulty == '1'}">초급</c:when>
							<c:when test="${subject.subjectDifficulty == '2'}">중급</c:when>
							<c:when test="${subject.subjectDifficulty == '3'}">고급</c:when>
						</c:choose>
					</span>
				</div>


				<!-- 마크다운 문자열을 담아둘 곳 (숨겨둠) -->
				<textarea id="md-content" style="display: none;">${subject.subjectDesc}</textarea>

				<!-- 실제 마크다운이 렌더링될 영역 -->
				<div class="markdown-body" id="md-rendered"></div>

				<!-- 📌 마크다운 영역
				<div class="card-body">
					<div class="markdown-body">
						<c:out value="${subject.subjectDesc}" escapeXml="false" />
					</div>

				</div> -->


			</div>

			<!-- 📌 자료 리스트 테이블 -->
			<div class="card shadow-sm border-0">
				<div class="card-header bg-secondary text-white">
					<h5 class="mb-0">관련 학습 자료</h5>
				</div>
				<div class="card-body p-0">
					<table class="table table-hover mb-0">
						<thead class="table-light">
							<tr>
								<th style="width: 30%;">자료 제목</th>
								<th>링크</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="res" items="${subject.resources}">
								<tr>
									<td class="fw-semibold">${res.resourceTitle}</td>
									<td><a href="${res.resourceUrl}" target="_blank"
										class="text-decoration-none text-primary">
											${res.resourceUrl} </a></td>
								</tr>
							</c:forEach>
							<c:if test="${empty subject.resources}">
								<tr>
									<td colspan="2" class="text-center text-muted">등록된 자료가
										없습니다.</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>

		</main>
		<%@include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>
	<%@include file="/WEB-INF/views/inc/footer_js.jsp"%>
</body>

<script
	src="https://cdn.jsdelivr.net/npm/highlight.js@11.9.0/lib/common.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/gh/wcoder/highlightjs-line-numbers.js@2.8.0/dist/highlightjs-line-numbers.min.js"></script>
<script>

document.addEventListener("DOMContentLoaded", function () {
  // 1. 마크다운 → HTML 변환
  const rawMarkdown = document.getElementById("md-content").value;
  const html = marked.parse(rawMarkdown);
  document.getElementById("md-rendered").innerHTML = html;

  // 2. 렌더링된 코드 블럭에 하이라이팅 + wrapping(Mac 스타일)
  document.querySelectorAll('#md-rendered pre code').forEach((codeBlock) => {
    // ✅ Mac 스타일 코드박스 wrapper
    const wrapper = document.createElement('div');
    wrapper.classList.add('custom-code');

    const header = document.createElement('div');
    header.classList.add('code-header');
    header.innerHTML = `
      <span class="dot red"></span>
      <span class="dot yellow"></span>
      <span class="dot green"></span>
    `;

    const pre = codeBlock.parentElement;
    pre.parentElement.insertBefore(wrapper, pre);
    wrapper.appendChild(header);
    wrapper.appendChild(pre);

    // ✅ highlight.js 하이라이팅
    hljs.highlightElement(codeBlock);
  });

  // 3. 줄번호 적용
  hljs.initLineNumbersOnLoad();
});
</script>





</html>






