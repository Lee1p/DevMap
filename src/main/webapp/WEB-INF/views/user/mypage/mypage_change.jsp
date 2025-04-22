<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.0/chart.umd.min.js"></script>

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
.profile-user-img {
	transition: transform 0.3s ease-in-out;
}

.profile-user-img:hover {
	transform: scale(1.05);
}

.badge-mp img {
	transition: transform 0.2s;
}

.badge-mp img:hover {
	transform: scale(1.1);
}

main {
	width: 90%;
	margin: 0 auto;
}

.image-frame {
	display: inline-block;
	padding: 5px; /* 프레임과 이미지 간 여백 */
	border: 1.5px solid #ddd; /* 프레임 테두리 */
	border-radius: 5px; /* 모서리 둥글게 */
	background-color: #ffffff; /* 프레임 배경색 */
}

.image-frame img {
	display: block; /* 이미지의 여백 문제 해결 */
	width: 145px; /* 원래 설정 유지 */
	border-radius: 3px; /* 기존 이미지 스타일 유지 */
}

.custom-list {
	list-style: none; /* 기본 리스트 스타일 제거 */
	padding: 5px;
	margin: 0;
}

.custom-list li {
	padding: 10px 0; /* 리스트 간격 조정 */
	font-size: 16px;
	color: #333; /* 글자 색 */
	display: flex;
	align-items: center;
}

.custom-list b {
	margin-right: 5px; /* 제목과 내용 간격 조정 */
}

.custom-list a {
	text-decoration: none;
	color: black;
	font-weight: bold;
}

.custom-list a:hover {
	color: #007bff; /* 링크 호버 시 색 변경 */
}

.badge-container-mp {
	display: flex;
	flex-wrap: wrap;
	gap: 15px;
}

.badge-mp {
	width: 80px;
	height: 80px;
	border-radius: 50%;
	background: linear-gradient(145deg, #ffffff, #f0f0f0);
	box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.1), -4px -4px 10px
		rgba(255, 255, 255, 0.6);
	display: flex;
	justify-content: center;
	align-items: center;
	border: 2px solid #ccc;
	position: relative;
	transition: transform 0.3s ease, box-shadow 0.3s ease;
	cursor: pointer;
}

.badge-mp:hover {
	transform: scale(1.15);
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.badge-mp::before {
	content: '';
	position: absolute;
	top: -6px;
	left: -6px;
	right: -6px;
	bottom: -6px;
	border-radius: 50%;
	border: 1.5px solid rgba(0, 0, 0, 0.1);
	pointer-events: none;
}

.badge-mp img {
	width: 65%;
	height: 65%;
	object-fit: contain;
	z-index: 1;
}

.card-header, .card-body, .card-footer {
	background-color: #fff;
}

.card-title {
	font-size: 1.2rem;
	font-weight: bold;
	color: #333;
}

/* 테이블 스타일 */
.table th, .table td {
	text-align: center;
	vertical-align: middle;
}

.table th {
	background-color: #f8f9fa;
	font-weight: 600;
}

.table-hover tbody tr:hover {
	background-color: #f1f1f1;
}

/* 링크 스타일 */
a {
	text-decoration: none;
	color: #4a69bd;
}

a:hover {
	color: #1e3799;
	text-decoration: underline;
}

/* 탭 스타일 */
.nav-underline .nav-link.active {
	font-weight: bold;
	color: #1e3799;
	border-bottom: 2px solid #1e3799;
}

/* 버튼 */
.btn-outline-primary {
	font-size: 0.9rem;
	padding: 4px 10px;
	border-radius: 20px;
}

/* 페이지네이션 스타일 */
.pagination .page-link {
	color: #4a69bd;
	border: none;
	background-color: transparent;
}

.pagination .page-item.active .page-link {
	font-weight: bold;
	color: #fff;
	background-color: #4a69bd;
	border-radius: 50px;
}

.pagination .page-link:hover {
	color: #1e3799;
	text-decoration: underline;
}

td a {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	display: inline-block;
	max-width: 200px;
}

.custom-popover {
	--bs-popover-max-width: 250px;
	--bs-popover-border-color: #ccc;
	--bs-popover-header-bg: #f8f9fa;
	--bs-popover-header-color: #333;
	--bs-popover-body-bg: #ffffff;
	--bs-popover-body-color: #555;
	--bs-popover-border-radius: 12px;
	--bs-popover-box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
	border: 2px solid var(--bs-popover-border-color);
	font-family: 'Segoe UI', sans-serif;
	font-size: 0.9rem;
	animation: fadeIn 0.3s ease-in-out;
}

@
keyframes fadeIn {from { opacity:0;
	transform: translateY(5px);
}

to {
	opacity: 1;
	transform: translateY(0);
}
}
</style>



</head>

<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
<div class="bg-element bg-element-1"></div>
<div class="bg-element bg-element-2"></div>
	<div class="app-wrapper">
		<%@include file="/WEB-INF/views/inc/navbar.jsp"%>
		<main class="app-main">
			<div class="app-content-header">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-3">
							<div class="card mb-4 shadow rounded-4 border-0">
								<div class="card-body box-profile"
									style="min-height: 80vh; padding: 2rem;">

									<!-- 프로필 이미지 -->
									<div class="mb-3 text-center">
										<img
											src="${pageContext.request.contextPath}/images/main/user2.png"
											alt="User profile" class="rounded-circle shadow"
											style="width: 140px; height: 140px; object-fit: cover; border: 5px solid #eee;">
									</div>

									<!-- 이름 및 날짜 -->
									<h3 class="fw-bold text-center mb-1">${dto.name}</h3>
									<div class="text-muted text-center mb-3">${dto.join_date.substring(0,10)}</div>

									<!-- 랭크 -->
									<div class="mb-3 text-center">
										<span class="text-secondary"><i
											class="fa-solid fa-trophy me-1"></i>Rank</span> <span
											class="fw-bold text-primary">${userrating.currentRating}</span>
									</div>

									<!-- 버튼 -->
									<div class="d-grid mb-4">
										<button class="btn btn-outline-success">성향테스트 다시하기</button>
									</div>

									<!-- 유저 정보 -->
									<div>
										<ul class="list-group list-group-flush">
											<li class="list-group-item"><i
												class="fa-solid fa-envelope me-2"></i> <strong>이메일:</strong>
												${dto.email}</li>
											<li class="list-group-item"><i
												class="fa-solid fa-phone me-2"></i> <strong>연락처:</strong>
												${dto.phone_number}</li>
											<li class="list-group-item"><i
												class="fa-solid fa-shield-halved me-2"></i> <strong>회원
													레벨:</strong> ${dto.user_level}</li>
											<li class="list-group-item"><i
												class="fa-solid fa-link me-2"></i> <strong>기타 링크:</strong>
												<div class="mt-2">
													<c:forEach var="link" items="${linklist}">
														<c:if test="${link.linkType == 'Github'}">
															<a class="btn btn-sm btn-dark me-2"
																href="${link.linkName}" target="_blank">GitHub <i
																class="fa-brands fa-github"></i></a>
														</c:if>
														<c:if test="${link.linkType eq 'Blog'}">
															<a class="btn btn-sm btn-warning me-2"
																href="${link.linkName}" target="_blank">Blog <i
																class="fa-solid fa-blog"></i></a>
														</c:if>
														<c:if test="${link.linkType eq 'Youtube'}">
															<a class="btn btn-sm btn-danger" href="${link.linkName}"
																target="_blank">YouTube <i
																class="fa-brands fa-youtube"></i></a>
														</c:if>
													</c:forEach>
												</div></li>
											<li class="list-group-item"><i
												class="fa-regular fa-clock me-2"></i> <strong>최근
													로그인 날짜:</strong> ${dto.last_login_date}</li>
										</ul>
									</div>

									<!-- Badge -->
									<hr class="my-4">
									<div class="text-center">
										<h5 class="fw-bold mb-3">🏅 Badge Status</h5>
										<div
											class="d-flex flex-wrap justify-content-center gap-3 badge-container-mp">
											<c:forEach var="badge" items="${badgelist}">
												<c:if
													test="${badge.badgeType == 'Backend' or badge.badgeType == 'Frontend' or badge.badgeType == 'DB'}">
													<div class="badge-mp" tabindex="0" role="button"
														data-bs-toggle="popover" data-bs-placement="bottom"
														data-bs-custom-class="custom-popover"
														data-bs-trigger="focus" data-bs-title="${badge.badgeDesc}"
														data-bs-content="${badge.badgeCondition}">
														<img
															src="/devmap/images/badge/language/${badge.badgeName}.png"
															alt="${badge.badgeName}">
													</div>
												</c:if>
											</c:forEach>
										</div>
									</div>

								</div>
							</div>
						</div>

						<div class="col-md-9">
							<div class="row">
								<div class="col">

									<div class="card mb-4">
										<div class="card-body box-profile" style="height: 30vh">
											<div class="row">
												<div class="col-md-8" style="border-right: 2px solid #ccc">
													<canvas id="myChart" width="300" height="80"></canvas>
												</div>
												<div class="col-md-4">
													<div class="ranking-wrapper">
														<table class="table ranking">
															<thead>
																<tr>
																	<th scope="col">랭킹</th>
																	<th scope="col">이름</th>
																	<th scope="col">점수</th>
																	<!-- 증감 처리 뭐지..? -->
																</tr>
															</thead>
															<tbody>

																<c:forEach var="rating" items="${ratinglist}">
																	<tr class="align-middle">

																		<c:choose>
																			<c:when test="${rating.rn == '1'}">
																				<th scope="row"><img alt=""
																					src="/devmap/images/badge/success/1st.png"
																					style="width: 30px"></th>
																			</c:when>
																			<c:when test="${rating.rn == '2'}">
																				<th scope="row"><img alt=""
																					src="/devmap/images/badge/success/2nd.png"
																					style="width: 30px"></th>
																			</c:when>
																			<c:when test="${rating.rn == '3'}">
																				<th scope="row"><img alt=""
																					src="/devmap/images/badge/success/3rd.png"
																					style="width: 30px"></th>
																			</c:when>
																			<c:otherwise>
																				<th scope="row">${rating.rn}</th>
																				<!-- 3등 이상이면 이미지 없음 -->
																			</c:otherwise>
																		</c:choose>

																		<td>${rating.name}</td>

																		<td>${rating.currentRating}</td>
																	</tr>
																</c:forEach>



															</tbody>
														</table>
													</div>
												</div>
											</div>

										</div>
									</div>

								</div>
							</div>

							<div class="row">
								<div class="col-md-6">
									<div class="card mb-4">

										<!-- /.card-header -->
										<div class="card-body">
											<ul class="nav nav-underline" id="myTab" role="tablist">
												<li class="nav-item" role="presentation">
													<button class="nav-link active" id="wrong-tab"
														data-bs-toggle="tab" data-bs-target="#wrong" type="button"
														role="tab" aria-controls="wrong" aria-selected="true">많이
														틀린 문제</button>
												</li>
												<li class="nav-item" role="presentation">
													<button class="nav-link" id="bookmark-tab"
														data-bs-toggle="tab" data-bs-target="#bookmark"
														type="button" role="tab" aria-controls="bookmark"
														aria-selected="false">북마크</button>
												</li>
											</ul>

											<div class="tab-content" id="myTabContent">
												<div class="tab-pane fade show active" id="wrong"
													role="tabpanel" aria-labelledby="wrong-tab">
													<div class="card-body">
														<table class="table table-bordered" id="table1">
															<thead>
																<tr>
																	<th>문제</th>
																	<th>시도한 횟수</th>
																</tr>
															</thead>
															<tbody>

																<c:forEach var="cote" items="${cotersult}">
																	<tr class="align-middle">
																		<td><a
																			href="/devmap/test/codetest/codetestsolv.do?seq=${cote.codetestSeq}">
																				${cote.codetesttitle} </a></td>
																		<td>${cote.cnt}</td>
																	</tr>
																</c:forEach>

															</tbody>
														</table>
													</div>
												</div>
												<div class="tab-pane fade" id="bookmark" role="tabpanel"
													aria-labelledby="bookmark-tab">
													<div class="card-body">
														<table class="table table-bordered" id="table2">
															<thead>
																<tr>
																	<th>과목명</th>
																	<th>문제</th>
																</tr>
															</thead>
															<tbody>



																<c:forEach var="bookmark" items="${bookmarklist}">
																	<tr class="align-middle">
																		<td>${bookmark.subjectName}</td>
																		<td><a
																			href="/devmap/test/codetest/codetestsolv.do?seq=${bookmark.codeTestSeq}">${bookmark.codetesttitle}</td>
																	</tr>
																</c:forEach>

															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
										<!-- /.card-body -->
										<div class="card-footer clearfix">
										
											<ul class="pagination pagination-sm m-0 float-end" id="pg1">
												<!-- <li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
												<li class="page-item"><a class="page-link" href="#">1</a></li>
												<li class="page-item"><a class="page-link" href="#">2</a></li>
												<li class="page-item"><a class="page-link" href="#">3</a></li>
												<li class="page-item"><a class="page-link" href="#">&raquo;</a></li> -->
											</ul>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="card mb-4">
										<div class="card-header">
											<h3 class="card-title">JAVA - 망각 곡선 복습</h3>
										</div>

										<div class="card-body">
											<table class="table table-bordered" id="table3">
												<thead>
													<tr>
														<th>과목명</th>
														<th>문제</th>
														<th>비고</th>
												</thead>
												<tbody>
													<c:forEach var="review" items="${recommendreview}">
														<tr class="align-middle">
															<!-- subject 링크 연결 할 것 -->
															<td>${review.subjectName}</td>
															<td>${review.codeTestTitle}</td>
															<td><a type="button" class="btn btn-outline-primary"
																href="/devmap/test/codetest/codetestsolv.do?seq=${review.codeTestSeq}">문제풀기</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<div class="card-footer clearfix">
											<ul class="pagination pagination-sm m-0 float-end" id="pg2">
												<li class="page-item"><a class="page-link" href="#">&laquo;</a></li>
												<li class="page-item"><a class="page-link" href="#">1</a></li>
												<li class="page-item"><a class="page-link" href="#">2</a></li>
												<li class="page-item"><a class="page-link" href="#">3</a></li>
												<li class="page-item"><a class="page-link" href="#">&raquo;</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col">
									<div class="card mb-4">
										<div class="card-body box-profile" style="height: 30vh">

											<c:forEach var="link" items="${linklist}">
												<c:if test="${link.linkType == 'Github'}">
													<c:set var="username"
														value="${fn:substringAfter(link.linkName, 'github.com/')}" />

													<img src="https://ghchart.rshah.org/${username}"
														style="width: 1100px" />

													<!-- <img src="https://ghchart.rshah.org/daumi125"
														style="width: 1100px" /> -->
												</c:if>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="app-content">
				<div class="container-fluid"></div>
			</div>
		</main>
		<%@include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>
	<%@include file="/WEB-INF/views/inc/footer_js.jsp"%>


	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	
	<script>
	// 많이 틀린 문제
	let wrongData = [];
	let wrongCurrentPage = 1;
	const wrongItemsPerPage = 5;
	
	
	/* table1_많이 틀린 문제 */
	/* $.ajax({
        url: '/devmap/user/mypage/mypage_cotedata.do',
        type: 'GET',
        dataType: "json",
        success: function(res) {
            const tbody = $('#table1 tbody');
            tbody.empty(); // 혹시 이전 데이터 있을까봐 비움
            
            console.log(res);

            // res가 배열인 경우
            res.forEach(item => {
                const row = `
                    <tr>
                        <td><a href="/devmap/test/codetest/codetestsolv.do?seq=${item.codetestSeq}">${item.codetesttitle}</a></td>
                        <td>${item.cnt} 테스트용</td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function(err) {
            console.error("AJAX 에러 발생:", err);
        }
    }); */
	
	$(document).ready(function () {
	  let bookmarkData = [];
	  const itemsPerPage = 3;
	  let bookmarkCurrentPage = 1;

	  // ⭐ 북마크 탭 클릭 시 AJAX 호출
	  $('#bookmark-tab').on('click', function () {
	    if (bookmarkData.length === 0) {
	      $.ajax({
	        url: '/devmap/user/mypage/mypage_bmarkdata.do',
	        type: 'GET',
	        dataType: "json",
	        success: function(res) {
	          bookmarkData = res;
	          renderBookmarkTable();
	          renderBookmarkPagination();
	        },
	        error: function(err) {
	          console.error("북마크 AJAX 에러:", err);
	        }
	      });
	    }
	  });

	  // ⭐ 북마크 테이블 출력
	  function renderBookmarkTable() {
	    const tbody = $('#table2 tbody');
	    tbody.empty();

	    const startIndex = (bookmarkCurrentPage - 1) * itemsPerPage;
	    const endIndex = startIndex + itemsPerPage;
	    const pageItems = bookmarkData.slice(startIndex, endIndex);

	    pageItems.forEach(item => {
	      const row = `
	        <tr class="align-middle">
	          <td>\${item.subjectName}</td>
	          <td>
	            <a href="/devmap/test/codetest/codetestsolv.do?seq=\${item.codeTestSeq}">
	              \${item.codetesttitle}
	            </a>
	          </td>
	        </tr>
	      `;
	      tbody.append(row);
	    });
	  }

	  // ⭐ 북마크 페이지네이션
	  function renderBookmarkPagination() {
	    const pagination = $('#pg1');
	    pagination.empty();

	    const totalPages = Math.ceil(bookmarkData.length / itemsPerPage);

	    pagination.append(`
	      <li class="page-item \${bookmarkCurrentPage === 1 ? 'disabled' : ''}">
	        <a class="page-link" href="#" onclick="changeBookmarkPage(\${bookmarkCurrentPage - 1})">&laquo;</a>
	      </li>
	    `);

	    for (let i = 1; i <= totalPages; i++) {
	      pagination.append(`
	        <li class="page-item \${bookmarkCurrentPage === i ? 'active' : ''}">
	          <a class="page-link" href="#" onclick="changeBookmarkPage(\${i})">\${i}</a>
	        </li>
	      `);
	    }

	    pagination.append(`
	      <li class="page-item \${bookmarkCurrentPage === totalPages ? 'disabled' : ''}">
	        <a class="page-link" href="#" onclick="changeBookmarkPage(\${bookmarkCurrentPage + 1})">&raquo;</a>
	      </li>
	    `);
	  }

	  // ⭐ 페이지 전환 함수
	  window.changeBookmarkPage = function(page) {
	    const totalPages = Math.ceil(bookmarkData.length / itemsPerPage);
	    if (page >= 1 && page <= totalPages) {
	      bookmarkCurrentPage = page;
	      renderBookmarkTable();
	      renderBookmarkPagination();
	    }
	  };
	});
	
	
	</script>


	<script>
	const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
	const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))
	
	
		const ctx = document.getElementById('myChart');

		new Chart(ctx,
				{
					type : 'line',
					
					data : {
						labels : [ 
							
							<c:forEach var="rate" items="${userratinghistory}" varStatus="loop">
						    	'${rate.changedAt.substring(0, 10)}'
						    <c:if test="${!loop.last}">, </c:if>
						</c:forEach>
							
						],
						datasets: [
						    {
						      label: '[${dto.name}]님의 성장 그래프',
						      
						      data: [ 
						    	    <c:forEach var="rate" items="${userratinghistory}" varStatus="loop">
						    	        ${rate.rating + 1000}<c:if test="${!loop.last}">, </c:if>
						    	    </c:forEach>
						    	],
						      
						      borderColor: '#03f0fc',
						      yAxisID: 'y',
						    },
						    {
						      label: '전체 학생의 성장 그래프',
						      data: [ 
						    	  
						    	  <c:forEach var="rateall" items="${ratinghisotryall}" varStatus="loop">
					    	        ${rateall.rating + 1000}<c:if test="${!loop.last}">, </c:if>
					    	    </c:forEach>
						    	  
						      ],
						      borderColor: '#f39cf7',
						      yAxisID: 'y1',
						    }
						  ]},
					options: {
					    animations: {
					      radius: {
					        duration: 400,
					        easing: 'linear',
					        loop: (context) => context.active
					      }
					    },
					    hoverRadius: 12,
					    hoverBackgroundColor: 'yellow',
					    interaction: {
					      mode: 'nearest',
					      intersect: false,
					      axis: 'x'
					    },
					    plugins: {
					      tooltip: {
					        enabled: false
					      }
					    },
					    scales: {
					        y: {
					          type: 'linear',
					          display: true,
					          position: 'left',
					        },
					        y1: {
					          type: 'linear',
					          display: true,
					          position: 'right',

					          // grid line settings
					          grid: {
					            drawOnChartArea: false, // only want the grid lines for one axis to show up
					          },
					        },
					      }
					  }
				});
		
	</script>

</body>
</html>






