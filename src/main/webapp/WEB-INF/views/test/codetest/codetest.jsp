<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
                  	<a href="/devmap/test/quiz/quiz.do">Quiz</a> </button>
                  </li>
                  
                  <li>
                  	<button type="button" class="btn btn-outline-dark">
                  	<a href="/devmap/test/codetest/codetest.do">CodeTest</a></button>
                  </li>
                </ol>
              </div>
            </div>
            <!--end::Row-->
          </div>
          <!--end::Container-->
        </div>
        <!--end::App Content Header-->
        
		 <div class="container-fluid mb-4" style="margin-left : 0.5rem">
		  <form method="get" action="/devmap/test/codetest/codetest.do" class="row g-3 align-items-center">
		    <div class="col-md-3">
		      <select name="status" id="filter-status" class="form-select">
		        <option value="">상태</option>
		        <option value="solved">푼 문제</option>
		        <option value="unsolved">안 푼 문제</option>
		      </select>
		    </div>
		
		    <div class="col-md-3">
		      <select name="difficulty" id="filter-difficulty" class="form-select">
		        <option value="">난이도</option>
		        <option value="1">초급</option>
		        <option value="2">중급</option>
		        <option value="3">고급</option>
		      </select>
		    </div>
		
		    <div class="col-md-3">
		      <select name="tech" id="filter-tech" class="form-select">
		        <option value="">언어</option>
		        <c:forEach var="tech" items="${techList}">
		          <option value="${tech.techSeq}">${tech.techName}</option>
		        </c:forEach>
		      </select>
		    </div>
		  </form>
		</div>


        
        
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
					<table class="table table-bordered" id="codeTestTable">
					  <thead>
					    <tr>
					      <th class="text-center">상태</th>
					      <th class="text-center">제목</th>
					      <th class="text-center" style="width: 300px;">과목</th>
					      <th class="text-center">정답률</th>
					    </tr>
					  </thead>
					  <tbody></tbody>
					</table>
                  </div>
                  <!-- /.card-body -->
                  <div class="card-footer clearfix">
                    <ul class="pagination pagination-sm m-0 float-end">
                      <li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
                      <li class="page-item"><a class="page-link" href="#">1</a></li>
                      <li class="page-item"><a class="page-link" href="#">2</a></li>
                      <li class="page-item"><a class="page-link" href="#">3</a></li>
                      <li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
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
	
	
	
	<script>
		$(document).ready(function () {
		  let allData = [];
		  const itemsPerPage = 10;
		  let currentPage = 1;
		
		  // 최초 로딩
		  $.ajax({
		    url: '/devmap/test/codetest/list.do',
		    method: 'GET',
		    dataType: 'json',
		    success: function (data) {
		      allData = data;
		      renderPage(currentPage);
		      renderPagination();
		    },
		    error: function (err) {
		      console.log("AJAX 실패", err);
		    }
		  });
		
		  function renderPage(page) {
			  let tbody = $('#codeTestTable tbody');
			  tbody.empty();

			  let start = (page - 1) * itemsPerPage;
			  let end = start + itemsPerPage;
			  let pageData = allData.slice(start, end);

			  pageData.forEach(function(item, index) {
			    let rate = item.accuracy || Math.floor(Math.random() * 100) + 1;
			    let rateClass = getRateClass(rate);

			    // 북마크 아이콘 추가
			    let bookmarkIcon = item.bookmarked
			      ? '<i class="bi bi-star-fill text-warning me-1"></i>'
			      : '';

			    let row = `
			      <tr class="align-middle">
			        <td class="text-center">
			          \${item.isPassed === 'Y' ? '<i class="bi bi-check2-circle text-primary"></i>' : ''}
			        </td>
			        <td>
			          \${bookmarkIcon}
			          <a href="/devmap/test/codetest/codetestsolv.do?seq=\${item.codeTestSeq}">
			            \${item.title}
			          </a>
			        </td>
			        <td>\${item.subjectName}</td>
			        <td>
			          <div class="progress progress-xs">
			            <div class="progress-bar \${rateClass}" style="width: \${rate}%"></div>
			          </div>
			          <span class="badge \${rateClass}">\${rate}%</span>
			        </td>
			      </tr>
			    `;

			    tbody.append(row);
			  });
			}

		
		  function renderPagination() {
		    let pageCount = Math.ceil(allData.length / itemsPerPage);
		    let pagination = $('.pagination');
		    pagination.empty();
		
		    pagination.append(`<li class="page-item"><a class="page-link" href="#" data-page="first">&laquo;</a></li>`);
		
		    for (let i = 1; i <= pageCount; i++) {
		      pagination.append(`<li class="page-item"><a class="page-link" href="#" data-page="\${i}">\${i}</a></li>`);
		    }
		
		    pagination.append(`<li class="page-item"><a class="page-link" href="#" data-page="last">&raquo;</a></li>`);
		
		    pagination.off('click').on('click', 'a', function (e) {
		      e.preventDefault();
		      let selected = $(this).data('page');
		
		      if (selected === 'first') currentPage = 1;
		      else if (selected === 'last') currentPage = pageCount;
		      else currentPage = parseInt(selected);
		
		      renderPage(currentPage);
		    });
		  }
		
		  function loadFilteredList() {
		    $.ajax({
		      url: '/devmap/test/codetest/list.do',
		      method: 'GET',
		      data: {
		        status: $('#filter-status').val(),
		        difficulty: $('#filter-difficulty').val(),
		        tech: $('#filter-tech').val()
		      },
		      dataType: 'json',
		      success: function (data) {
		        allData = data;
		        renderPage(1);
		        renderPagination();
		      },
		      error: function (err) {
		        console.error("필터 적용 실패", err);
		      }
		    });
		  }
		
		  $('#filter-status, #filter-difficulty, #filter-tech').on('change', loadFilteredList);
		
		  function getRateClass(rate) {
		    if (rate >= 80) return "text-bg-success";
		    if (rate >= 60) return "text-bg-primary";
		    if (rate >= 40) return "text-bg-warning";
		    return "text-bg-danger";
		  }
		});
		</script>		


		          /* 랜덤 정답률 */
				  <!-- <td>
		            <div class="progress progress-xs">
		              <div class="progress-bar \${rateClass}" style="width: \${rate}%"></div>
		            </div>
		            <span class="badge \${rateClass}">\${rate}%</span>
		          </td> -->


</body>


</html>






