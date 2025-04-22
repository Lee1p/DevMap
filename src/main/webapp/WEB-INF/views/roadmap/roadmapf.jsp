<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:set var="completedTechSet" value="${userCompletedTechSet}" />

<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>개발자 맞춤 로드맵</title>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&family=Noto+Sans+KR:wght@300;400;500;600&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jsPlumb/2.15.6/js/jsplumb.min.js"></script>

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

* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

body {
	font-family: 'Noto Sans KR', 'Poppins', sans-serif;
	background: linear-gradient(135deg, var(--gradient-1), var(--gradient-2));
	background-attachment: fixed;
	color: var(--neutral-800);
	line-height: 1.6;
	padding: 0;
	min-height: 100vh;
}

.container {
	max-width: 1280px;
	margin: 0 auto;
	padding: 40px 20px;
}

header {
	text-align: center;
	margin-bottom: 80px;
	padding-top: 40px;
}

header h1 {
	font-size: 3.2rem;
	font-weight: 600;
	margin-bottom: 16px;
	background: linear-gradient(to right, var(--primary-dark), var(--accent));
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
	background-clip: text;
	letter-spacing: -0.5px;
}

header p {
	font-size: 1.2rem;
	color: var(--neutral-600);
	max-width: 680px;
	margin: 0 auto;
	line-height: 1.7;
	font-weight: 300;
}

#roadmap-container {
	position: relative;
	min-height: 2000px;
	margin: 0 auto;
	padding: 40px;
	background: rgba(255, 255, 255, 0.7);
	backdrop-filter: blur(10px);
	border-radius: var(--radius-xl);
	box-shadow: var(--shadow-lg);
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

/* 카테고리 노드 (tblTechCategory) */
.category-node {
	width: 280px;
	padding: 24px;
	background: white;
	border-radius: var(--radius-md);
	box-shadow: var(--shadow-md);
	font-size: 18px;
	font-weight: 500;
	color: var(--neutral-800);
	position: absolute;
	text-align: center;
	transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
	cursor: pointer;
	z-index: 10;
}

.category-node:hover {
	transform: translateY(-5px);
	box-shadow: var(--shadow-lg);
}

.category-node::before {
	content: '';
	position: absolute;
	left: 0;
	top: 0;
	width: 8px;
	height: 100%;
	background: var(--primary);
	border-top-left-radius: var(--radius-md);
	border-bottom-left-radius: var(--radius-md);
	transition: all 0.3s ease;
}

.category-node:hover::before {
	width: 12px;
	background: var(--primary-dark);
}

/* 스킬/기술 노드 (tblTech) */
.tech-node {
	width: 200px;
	padding: 16px;
	background: white;
	border-radius: var(--radius-sm);
	box-shadow: var(--shadow-sm);
	font-size: 15px;
	position: absolute;
	transition: all 0.3s ease;
	cursor: pointer;
	z-index: 10;
}

.tech-node:hover {
	transform: translateY(-3px);
	box-shadow: var(--shadow-md);
}

.tech-node-title {
	font-weight: 500;
	margin-bottom: 8px;
	color: var(--neutral-800);
}

.tech-node-desc {
	font-size: 13px;
	color: var(--neutral-600);
	line-height: 1.5;
}

.tech-left::before {
	content: '';
	position: absolute;
	right: 0;
	top: 0;
	width: 5px;
	height: 100%;
	background: var(--primary);
	border-top-right-radius: var(--radius-sm);
	border-bottom-right-radius: var(--radius-sm);
}

.tech-right::before {
	content: '';
	position: absolute;
	left: 0;
	top: 0;
	width: 5px;
	height: 100%;
	background: var(--accent);
	border-top-left-radius: var(--radius-sm);
	border-bottom-left-radius: var(--radius-sm);
}

/* 추천 기능 스타일 */
.tech-node.recommended {
	box-shadow: 0 0 12px rgba(255, 126, 103, 0.5);
	border: 2px solid var(--accent);
}

.recommend-badge {
	position: absolute;
	top: -12px;
	right: -12px;
	background: var(--accent);
	color: white;
	border-radius: 50%;
	width: 32px;
	height: 32px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 12px;
	font-weight: 600;
	box-shadow: var(--shadow-sm);
	z-index: 15;
}

.sidebar {
	position: fixed;
	top: 0;
	right: -400px;
	width: 400px;
	height: 100%;
	background: #fff;
	box-shadow: -2px 0 8px rgba(0, 0, 0, 0.1);
	z-index: 999;
	overflow-y: auto;
	transition: right 0.3s ease;
	padding: 20px;
}

.sidebar.open {
	right: 0;
}
</style>
</head>
<body data-completed-tech="${fn:join(completedTechSet, ',')}">
	<div class="container">
		<header>
			<h1>개발자 맞춤 로드맵</h1>
			<p>어떤 기술을 배워야 할지 고민이신가요? 나에게 맞는 기술 로드맵을 확인하고 체계적으로 성장하세요.</p>
		</header>

		<div id="roadmap-container">
			<!-- 배경 효과 요소 -->
			<div class="bg-element bg-element-1"></div>
			<div class="bg-element bg-element-2"></div>

			<!-- 카테고리 노드 (HTML로 정적 생성) -->
			<c:forEach var="category" items="${roadmapcategory}">
				<div id="category-${category.categorySeq}" class="category-node">
					${category.categoryName}</div>
			</c:forEach>

			<!-- 기술 노드 (HTML로 정적 생성) -->
			<c:forEach var="tech" items="${roadmaplist}">
				<div id="tech-${tech.techSeq}" class="tech-node">
					<div class="tech-node-title">${tech.techName}</div>
					<div class="tech-node-desc">${tech.techDesc}</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<script>
        // 카테고리와 기술 데이터 (정적 배열로 정의)
        
        document.addEventListener('DOMContentLoaded', () => {

            const categories = [
                <c:forEach var="category" items="${roadmapcategory}" varStatus="status">
                {
                    categorySeq: ${category.categorySeq},
                    categoryName: "${category.categoryName}"
                }<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            
            const techs = [
                <c:forEach var="tech" items="${roadmaplist}" varStatus="status">
                {
                    techSeq: "${tech.techSeq}",
                    techName: "${tech.techName}",
                    techDesc: `${tech.techDesc}`,
                    categorySeq: "${tech.categorySeq}"
                }<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];

            const userTechScores = [
                <c:forEach var="score" items="${userTechScores}" varStatus="status">
                {
                    techSeq: ${score.techSeq},
                    isRecommended: "${score.isRecommended}"
                }<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
			
            
            console.log("____----------------------여기 위치ㅋ	");
            console.log('techs: ', techs);
            
            
            techs.forEach(tech => {
               const found = userTechScores.find(score =>
                    score.techSeq === tech.techSeq && score.isRecommended === "Y"
                );
                tech.isRecommended = found ? true : false;
            });
            
            console.log("최종 categories", categories);
            console.log("최종 techs", techs);
        
        
        
        // jsPlumb 인스턴스
        let jsPlumbInstance;
        
        // 요소 위치 계산 및 배치 함수
        function positionElements() {
            // 1. 카테고리 위치 계산
            const containerWidth = $('#roadmap-container').width();
            const startY = 100;
            const categoryGap = 250; // 카테고리 간 세로 간격
            const centerX = containerWidth / 2; // 중앙 X 좌표
            
            // 카테고리 노드 배치
            categories.forEach((category, index) => {
                $('#category-' + category.categorySeq).css({
                    left: (centerX - 140) + 'px', // 너비의 절반(280/2)을 빼서 중앙 정렬
                    top: (startY + (index * categoryGap)) + 'px'
                });
                
                // 위치 정보 저장 (연결선 계산용)
                category.x = centerX;
                category.y = startY + (index * categoryGap) + 40; // 노드 중앙 Y 좌표
            });
            
            // 2. 기술 노드 배치
            const techGap = 120; // 기술 노드 간 수직 간격
            const sideOffset = 350; // 카테고리로부터 좌우 거리
            
            // 카테고리별 기술 그룹화
            const techsByCategorySeq = {};
            techs.forEach(tech => {
                if (!techsByCategorySeq[tech.categorySeq]) {
                    techsByCategorySeq[tech.categorySeq] = [];
                }
                techsByCategorySeq[tech.categorySeq].push(tech);
            });
            
            // 각 카테고리에 속한 기술 배치
            Object.keys(techsByCategorySeq).forEach(categorySeq => {
                const categoryTechs = techsByCategorySeq[categorySeq];
                const category = categories.find(c => c.categorySeq === parseInt(categorySeq));
                
                if (!category) return;
                
                // 왼쪽/오른쪽으로 기술 분배
                const leftTechs = categoryTechs.slice(0, Math.ceil(categoryTechs.length / 2));
                const rightTechs = categoryTechs.slice(Math.ceil(categoryTechs.length / 2));
                
                // 왼쪽 기술 배치
                leftTechs.forEach((tech, idx) => {
                    const techNode = $('#tech-' + tech.techSeq);
                    const topPosition = category.y - ((leftTechs.length - 1) * techGap / 2) + (idx * techGap) - 30;
                    
                    techNode.css({
                        left: (centerX - sideOffset) + 'px',
                        top: topPosition + 'px'
                    }).addClass('tech-left');
                    
                    // 추천 표시 추가
                    if (tech.isRecommended) {
                        techNode.addClass('recommended');
                        techNode.append('<div class="recommend-badge">추천</div>');
                    }
                    
                    // 위치 정보 저장
                    tech.x = centerX - sideOffset + 100; // tech 노드 중앙 X 좌표 (노드 너비 고려)
                    tech.y = topPosition + 30; // tech 노드 중앙 Y 좌표
                    tech.side = 'left';
                });
                
                // 오른쪽 기술 배치
                rightTechs.forEach((tech, idx) => {
                    const techNode = $('#tech-' + tech.techSeq);
                    const topPosition = category.y - ((rightTechs.length - 1) * techGap / 2) + (idx * techGap) - 30;
                    
                    techNode.css({
                        left: (centerX + sideOffset - 200) + 'px', // 200은 tech 노드 너비
                        top: topPosition + 'px'
                    }).addClass('tech-right');
                    
                    // 추천 표시 추가
                    if (tech.isRecommended) {
                        techNode.addClass('recommended');
                        techNode.append('<div class="recommend-badge">추천</div>');
                    }
                    
                    // 위치 정보 저장
                    tech.x = centerX + sideOffset - 100; // tech 노드 중앙 X 좌표
                    tech.y = topPosition + 30; // tech 노드 중앙 Y 좌표
                    tech.side = 'right';
                });
            });
        }
        
        // jsPlumb 연결선 생성 함수
        function createConnections() {
            // 기존 연결 제거
            jsPlumbInstance.deleteEveryConnection();
            
            // 카테고리 간 연결선 (위에서 아래로)
            for (let i = 0; i < categories.length - 1; i++) {
                jsPlumbInstance.connect({
                    source: 'category-' + categories[i].categorySeq,
                    target: 'category-' + categories[i+1].categorySeq,
                    anchors: ["Bottom", "Top"],
                    connector: ["Bezier", { curviness: 50 }],
                    paintStyle: { 
                        stroke: "var(--primary-dark)", 
                        strokeWidth: 3,
                        dashstyle: "4 2"
                    },
                    endpoint: "Blank",
                    overlays: [
                        ["Arrow", { width: 8, length: 8, location: 1 }]
                    ]
                });
            }
            
            // 기술과 카테고리 간 연결선
            techs.forEach(tech => {
                const categoryId = 'category-' + tech.categorySeq;
                const techId = 'tech-' + tech.techSeq;
                
                jsPlumbInstance.connect({
                    source: techId,
                    target: categoryId,
                    anchors: [
                        tech.side === 'left' ? "Right" : "Left", 
                        tech.side === 'left' ? "Left" : "Right"
                    ],
                    connector: ["Bezier", { curviness: 40 }],
                    paintStyle: { 
                        stroke: tech.side === 'left' ? "var(--primary)" : "var(--accent)", 
                        strokeWidth: 2 
                    },
                    endpoint: "Blank"
                });
            });
        }
        
        // 초기화 함수
        function initRoadmap() {
            // jsPlumb 인스턴스 초기화
            jsPlumbInstance = jsPlumb.getInstance({
                Connector: ["Bezier", { curviness: 40 }],
                DragOptions: { cursor: "pointer", zIndex: 2000 },
                Container: "roadmap-container"
            });
            
            // 요소 위치 계산 및 배치
            positionElements();
            
            // 모든 DOM 요소가 완전히 로드된 후에 연결선 생성
            setTimeout(() => {
                createConnections();
                
                // 모든 노드를 draggable하게 설정
                jsPlumbInstance.draggable($(".category-node, .tech-node"), {
                    containment: "parent",
                    stop: function() {
                        jsPlumbInstance.repaintEverything();
                    }
                });
            }, 500);
            
            // 창 크기 변경 시 요소 재배치
            $(window).on('resize', function() {
                positionElements();
                setTimeout(() => {
                    createConnections();
                }, 100);
            });
        }
        
        // 페이지 로드 완료 시 초기화
        $(document).ready(function() {
            // DOM이 완전히 로드된 후에 초기화
            initRoadmap();
        });
        
        });
        //지워우어웅
        
        // 기술 노드 클릭 시 사이드바 AJAX 호출
          $('.tech-node').on('click', function () {
       const techName = $(this).find('.tech-node-title').text().trim();
       
       //디버깅
       console.log("👉 techName:", techName);


       $.ajax({
           url: '/devmap/roadmap/techsidebar.do',
           method: 'GET',
           data: { techName },
           success: function (html) {
               if ($('#sidebar').length === 0) {
                   $('body').append('<div id="sidebar" class="sidebar"><div id="sidebar-content"></div></div><div id="sidebar-overlay"></div>');
               }

               $('#sidebar-content').html(html);
               $('#sidebar').addClass('open').show();
               $('#sidebar-overlay').show().addClass('active');
               $('body').addClass('scroll-lock');
           },
           error: function (xhr) {
               console.error('❌ AJAX 실패:', xhr);
               alert('사이드바 불러오기 실패\n' + xhr.responseText);
           }
       });
   });

           // 사이드바 닫기
           $('#sidebar-overlay').on('click', function () {
               $('#sidebar').removeClass('open');
               setTimeout(() => {
                   $('#sidebar').hide();
                   $('#sidebar-overlay').hide();
                   $('body').css('overflow', '');
               }, 300);
           });
           /* $('.tech-node').on('click', function () {
               const techName = $(this).data('name');

               $.ajax({
               	  url: '/devmap/roadmap/techsidebar.do', // ✅ 정확히 일치
                   method: 'GET',
                   data: { techName },
                   success: function (html) {
                       if ($('#sidebar').length === 0) {
                           $('body').append('<div id="sidebar" class="sidebar"><div id="sidebar-content"></div></div><div id="sidebar-overlay"></div>');
                       }

                       $('#sidebar-content').html(html);
                       $('#sidebar').addClass('open');
                       $('#sidebar-overlay').addClass('active');
                       $('body').addClass('scroll-lock');
                   },
                   error: function (xhr) {
                       console.error('❌ AJAX 실패:', xhr);
                       alert('사이드바 불러오기 실패\n' + xhr.responseText);
                   }
               });
           }); */

           $(document).on('click', '#sidebar-overlay', function () {
               $('#sidebar').removeClass('open');
               $('#sidebar-overlay').removeClass('active');
               $('body').removeClass('scroll-lock');
           });
           
        // 완료된 기술 표시 (배경색 회색으로)
           /* const completedTech = document.body.dataset.completedTech?.split(',').map(Number) || [];

           techs.forEach(tech => {
               if (completedTech.includes(tech.techSeq)) {
                   const node = document.getElementById('tech-' + tech.techSeq);
                   if (node) {
                       node.style.backgroundColor = '#e0e0e0'; // 회색 배경
                   }
               }
           });
           console.log("👉 techName:", techName); */


       </script>


	<!-- 사이드바 및 오버레이 -->
	<div id="sidebar" class="sidebar" style="display: none;">
		<div id="sidebar-content"></div>
	</div>
	<div id="sidebar-overlay"
		style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.4); z-index: 998;"></div>

</body>
</html>
