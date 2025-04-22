<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<!-- ✅ JetBrains Mono 폰트 -->
<link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono&display=swap" rel="stylesheet">

<!-- ✅ CodeMirror 5 (Java + 커스텀 테마) -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/codemirror.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/codemirror.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.65.13/mode/clike/clike.min.js"></script>

<!-- ✅ IntelliJ 스타일 커스텀 테마 -->
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

.cm-s-intellij.CodeMirror {
  background: #ffffff;
  color: #000000;
  font-family: 'JetBrains Mono', monospace;
  font-size: 14px;
  height: 400px;
}

.cm-s-intellij .cm-keyword { color: #0000ff; font-weight: bold; }
.cm-s-intellij .cm-comment { color: #008000; font-style: italic; }
.cm-s-intellij .cm-string { color: #a31515; }
.cm-s-intellij .cm-number { color: #098658; }
.cm-s-intellij .cm-variable { color: #001080; }

.section-title {
	font-weight: bold;
	font-size: 1.1rem;
	margin-bottom: 0.5rem;
}

.section-content {
	background-color: #f8f9fa;
	padding: 0.8rem;
	border-radius: 5px;
	white-space: pre-wrap;
}
</style>
</head>
<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
<div class="bg-element bg-element-1"></div>
<div class="bg-element bg-element-2"></div>
	<div class="app-wrapper">
		<%@include file="/WEB-INF/views/inc/navbar.jsp"%>
		<main class="app-main">
			<div class="container-fluid mt-4">
				<div class="d-flex justify-content-between align-items-center mb-4">
					<h3>
						${dto.codeTestTitle} <i id="bookmark-icon"
							class="bi ${dto.bookmarked ? 'bi-star-fill text-warning' : 'bi-star'}"
							style="cursor: pointer;" data-codetestseq="${dto.codeTestSeq}"></i>
					</h3>
				</div>

				<div class="row align-items-stretch">
					<!-- 문제 설명 -->
					<div class="col-md-6">
						<div class="card p-4 mb-4 h-100">
							<div class="section-title"><i class="bi bi-journal-text me-2 text-primary"></i>문제 설명</div>
							<div class="section-content">${dto.content}</div>

							<div class="section-title mt-4"><i class="bi bi-box-arrow-in-down me-2 text-info"></i>입력 설명</div>
							<div class="section-content">${dto.inputDescription}</div>

							<div class="section-title mt-4"><i class="bi bi-box-arrow-up me-2 text-success"></i>출력 설명</div>
							<div class="section-content">${dto.outputDescription}</div>

							<div class="section-title mt-4"><i class="bi bi-pencil-square me-2 text-warning"></i>예제 입력</div>
							<div class="section-content"><pre><code>${dto.sampleInput.trim()}</code></pre></div>

							<div class="section-title mt-4"><i class="bi bi-file-earmark-text me-2 text-secondary"></i>예제 출력</div>
							<div class="section-content">${dto.sampleOutput}</div>
						</div>
					</div>

					<!-- 코드 입력 -->
					<div class="col-md-6">
						<div class="card p-4 mb-4 h-100">
							<form method="post" action="/devmap/test/codetest/codetestsubmit.do" class="d-flex flex-column h-100">
								<textarea id="codeEditor" name="code">public class Main {
    public static void main(String[] args) {
        // 여기에 코드를 작성하세요
    }
}</textarea>
								<input type="hidden" name="codeTestSeq" value="${dto.codeTestSeq}" />

								<div class="d-flex justify-content-end gap-2 mt-auto">
									<button type="button" class="btn btn-secondary" id="runBtn">코드 실행</button>
									<button type="button" class="btn btn-primary" id="submitBtn">제출하기</button>
								</div>
							</form>

							<div id="executionResult" class="mt-4"></div>
						</div>
					</div>
				</div>
			</div>
		</main>
		<%@include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>
	<%@include file="/WEB-INF/views/inc/footer_js.jsp"%>

	<!-- CodeMirror 초기화 -->
	<script>
		let editor;

		window.addEventListener('DOMContentLoaded', function () {
			editor = CodeMirror.fromTextArea(document.getElementById("codeEditor"), {
				mode: "text/x-java",
				theme: "intellij",
				lineNumbers: true,
				matchBrackets: true,
				autoCloseBrackets: true,
				indentUnit: 4,
				tabSize: 4,
				indentWithTabs: true
			});

			// 실행/제출 시 값 반영
			document.getElementById('runBtn').addEventListener('click', () => editor.save());
			document.getElementById('submitBtn').addEventListener('click', () => editor.save());
		});
	</script>

	<!-- 코드 실행 -->
	<script>
		$('#runBtn').on('click', function () {
			const code = $('textarea[name="code"]').val();
			const codeTestSeq = $('input[name="codeTestSeq"]').val();

			$.ajax({
				url: '/devmap/test/codetest/run.do',
				type: 'POST',
				data: { code, codeTestSeq },
				dataType: 'json',
				success: function (res) {
					const container = $('#executionResult');
					container.empty();

					if (!res.testCases || res.testCases.length === 0) {
						container.append('<div class="alert alert-warning">실행 결과 : 23</div>');
						return;
					}

					let html = '<h5 class="mb-3">🧪 실행 결과</h5>';
					res.testCases.forEach((test, index) => {
						html += `
							<div class="border rounded p-3 mb-3 bg-light">
								<div><strong>테스트 \${index + 1}</strong></div>
								<div>입력값: <code>\${test.input}</code></div>
								<div>기댓값: <code>\${test.expected}</code></div>
								<div>실행결과: <code>\${test.actual}</code></div>
								<div>
									결과: 
									<span class="\${test.passed ? 'text-success' : 'text-danger'}">
										\${test.passed ? '✅ 성공' : '❌ 실패'}
									</span>
								</div>
							</div>`;
					});
					container.append(html);
				},
				error: function () {
					$('#executionResult').html('<div class="alert alert-danger">코드 실행 실패</div>');
				}
			});
		});
	</script>

	<!-- 북마크 -->
	<script>
		$('#bookmark-icon').on('click', function () {
			const codeTestSeq = $(this).data('codetestseq');
			$.ajax({
				url: '/devmap/test/codetest/bookmark-toggle.do',
				type: 'POST',
				data: { codeTestSeq },
				success: function (res) {
					if (res.bookmarked) {
						$('#bookmark-icon').removeClass('bi-star').addClass('bi-star-fill text-warning');
					} else {
						$('#bookmark-icon').removeClass('bi-star-fill text-warning').addClass('bi-star');
					}
				},
				error: function () {
					alert('북마크 변경 실패!');
				}
			});
		});
	</script>

	<!-- 제출 애니메이션 -->
	<script>
		$('#submitBtn').on('click', function () {
			const duration = 2000;
			let timerInterval;

			Swal.fire({
				title: "제출 중입니다...",
				html: `<div style="margin-bottom: 10px;">잠시만 기다려주세요.</div>
				       <div style="font-weight: bold;"><span id="progressPercent">0</span>%</div>`,
				timer: duration,
				timerProgressBar: true,
				didOpen: () => {
					Swal.showLoading();
					timerInterval = setInterval(() => {
						const timeLeft = Swal.getTimerLeft();
						const elapsed = duration - timeLeft;
						const percent = Math.min(100, Math.round((elapsed / duration) * 100));
						document.getElementById('progressPercent').textContent = percent;
					}, 100);
				},
				willClose: () => clearInterval(timerInterval)
			}).then((result) => {
				if (result.dismiss === Swal.DismissReason.timer) {
					$('form').submit();
				}
			});
		});
	</script>
</body>
</html>
