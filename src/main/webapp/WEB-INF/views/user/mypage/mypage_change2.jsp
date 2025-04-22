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
	justify-content: flex-start;
	align-items: center;
	gap: 15px; /* 배지 간격 조절 */
}

.badge-mp {
	width: 70px;
	height: 70px;
	border-radius: 50%; /* 원형으로 자르기 */
	background: #fff;
	display: flex;
	justify-content: center;
	align-items: center;
	border: 2px solid #eee; /* 테두리 색상 */
	overflow: hidden;
}

.badge-mp img {
	width: 100%;
	height: 100%;
	object-fit: cover;
}
</style>



</head>

<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
	<div class="app-wrapper">
		<%@include file="/WEB-INF/views/inc/navbar.jsp"%>
		<main class="app-main">
			<div class="app-content-header">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-3">
							<div class="card mb-4">
								<div class="card-body box-profile" style="height: 80vh">
									<div class="row">
										<div class="col-md-5">
											<div class="image-frame">
											
												<img class="profile-user-img"
												
												
													src="${pageContext.request.contextPath}/images/main/user1.jpeg"
													alt="User profile picture">
											</div>
										</div>

										<div class="col-md-7 mb-3" style="padding: 0 25px">
											<h3 class="mb-1"><b>${dto.name}</b></h3>
											<div>${dto.join_date.substring(0, 10)}</div>
											<div>
												
												<h4 class="mb-0">
													Rank <span style="text-weigth: border">${ userrating.currentRating }</span>
												</h4>

												<div>
												Hi:) I'm gildong~! <br>
												Nice to meet u~<br>
												I think we would be good friends<br>
												hmm.. that's it~ okay bye:) <br>
												</div>
											</div>

										</div>

									</div>
									
									
									
									
									<div class="mb-3">
										<button type="button" class="btn btn-success btn-lg"
											style="width: 100%">성향테스트 다시하기</button>
									</div>

									<div class="mb-3">


										<ul class="custom-list">
											<li><b><i class="fa-solid fa-envelope"></i> 이메일: </b>
												${dto.email}</li>
											<li><b><i class="fa-solid fa-phone"></i> 연락처: </b>
												${dto.phone_number}</li>
											<li><b><i class="fa-solid fa-shield-halved"></i> 회원
													레벨: </b> ${dto.user_level}</li>
											<li><b>기타 링크:</b> <c:forEach var="link"
													items="${linklist}">
													<c:if test="${link.linkType == 'Github'}">
														<a href="${link.linkName}">Git <i
															class="fa-brands fa-github"></i></a>
														<span style="margin: 0 5px">|</span>
													</c:if>
													<c:if test="${link.linkType eq 'Blog'}">
														<a href="${link.linkName}">Blog <i
															class="fa-solid fa-blog"></i></a>
														<span style="margin: 0 5px">|</span>
													</c:if>
													<c:if test="${link.linkType eq 'Youtube'}">
														<a href="${link.linkName}">Youtube <i
															class="fa-brands fa-youtube" style="margin: 0px 5px"></i></a>
													</c:if>
												</c:forEach></li>
											<li><b>최근 로그인 날짜: </b> ${dto.last_login_date}</li>
										</ul>
									</div>
									<hr>

									<div class="row">
										<h3 class="mb-0 text-center">Badge Status</h3>


										<div class="badge-container-mp">
										
											<c:forEach var="badge-mp" items="${badgelist}">
												<c:if
													test="${badge.badgeType == 'Backend' or badge.badgeType == 'Frontend' or badge.badgeType == 'DB'}">
													<div class="badge-mp">
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

									<!-- <div class="row">
										<div class="col">
											<div class="card mb-4">
												<div class="card-body box-profile" style="height: 30vh">
													<h3 class="mb-0 text-center">rating</h3>
												</div>
											</div>
										</div>
										<div class="col">
											<div class="card mb-4">
												<div class="card-body box-profile" style="height: 30vh">
													<h3 class="mb-0 text-center">rating</h3>
												</div>
											</div>
										</div>
									</div> -->




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
														<table class="table table-bordered">
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
														<table class="table table-bordered">
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
								<div class="col-md-6">
									<div class="card mb-4">
										<div class="card-header">
											<h3 class="card-title">JAVA - 망각 곡선 복습</h3>
										</div>
										<!-- /.card-header -->



										<div class="card-body">
											<table class="table table-bordered">
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






