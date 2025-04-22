<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>로드맵 마인드맵</title>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jsPlumb/2.15.6/js/jsplumb.min.js"></script>
<style>
body {
	font-family: sans-serif;
	background: #fefefe;
	padding: 40px;
}

.node {
	width: 220px;
	padding: 14px;
	background-color: #d6d700;
	border: 2px solid #facc15;
	border-radius: 10px;
	box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
	position: absolute;
	text-align: center;
	cursor: pointer;
}
.toggle-circle {
	width: 12px;
	height: 12px;
	background-color: #e11d48;
	border-radius: 50%;
	position: absolute;
	right: -16px;
	top: 50%;
	transform: translateY(-50%);
	cursor: pointer;
}
.sub-node {
	width: 180px;
	padding: 10px;
	background-color: #f1f5f9;
	border: 2px solid #cbd5e1;
	border-radius: 8px;
	position: absolute;
	text-align: center;
	cursor: pointer;
}
/* 클릭된 기술 노드의 스타일 */
.selected-tech {
	background-color: #ffe559 !important;
	border-color: #0284c7 !important;
	color: #1e3a8a !important;
	font-weight: bold;
}
/* 클릭된 기술 노드의 원형 색 */
.toggled {
	background-color: #3b82f6 !important;
}

</style>
</head>
<body>

	<h2 style="margin-bottom: 60px;">${user.name}님
		${devfield.fieldName} 로드맵</h2>

	<!-- Main Steps (로드맵 카테고리 동적 생성) -->
	<c:forEach var="category" items="${roadmapcategory}">
		<!-- 카테고리 노드의 위치를 수동으로 지정 -->
		<c:choose>
			<c:when test="${category.categorySeq == 1}">
				<c:set var="catTop" value="50" />
				<c:set var="catLeft" value="800" />
			</c:when>
			<c:when test="${category.categorySeq == 2}">
				<c:set var="catTop" value="200" />
				<c:set var="catLeft" value="600" />
			</c:when>
			<c:when test="${category.categorySeq == 3}">
				<c:set var="catTop" value="350" />
				<c:set var="catLeft" value="800" />
			</c:when>
			<c:when test="${category.categorySeq == 4}">
				<c:set var="catTop" value="550" />
				<c:set var="catLeft" value="400" />
			</c:when>
			<c:when test="${category.categorySeq == 5}">
				<c:set var="catTop" value="750" />
				<c:set var="catLeft" value="500" />
			</c:when>
			<c:when test="${category.categorySeq == 6}">
				<c:set var="catTop" value="1030" />
				<c:set var="catLeft" value="600" />
			</c:when>
			<c:when test="${category.categorySeq == 7}">
				<c:set var="catTop" value="1130" />
				<c:set var="catLeft" value="600" />
			</c:when>
			<c:otherwise>
				<c:set var="catTop" value="${(category.categorySeq-1) * 300 + 100}" />
				<c:set var="catLeft" value="400" />
			</c:otherwise>
		</c:choose>

		<!-- 카테고리 노드 -->
		<div id="step${category.categorySeq}" class="node"
			style="top:${catTop}px; left:${catLeft}px;">카테고리
			${category.categorySeq}: ${category.categoryName}</div>

		<c:set var="index" value="0" />

		<c:forEach var="tech" items="${roadmaplist}">
		<c:if test="${tech.categorySeq == category.categorySeq}">
		<!-- categorySeq 번호로 기술 노드 높이 지정 -->
		<!-- 기술 노드 위치 좌우 설정 default -->
			<c:choose>
			<c:when test="${category.categorySeq == 4}">	
				<c:set var="techLeft" value="${catLeft + (category.categorySeq % 2 == 0 ? -250 : 300)}" />
				<c:set var="techTop"  value="${catTop + index * 60- 50}" />
			</c:when>
			<c:when test="${category.categorySeq == 5}">	
				<c:set var="techLeft" value="${catLeft + (category.categorySeq % 2 == 0 ? -250 : 300)}" />
				<c:set var="techTop"  value="${catTop + index * 60- 50}" />
			</c:when>
			
			<c:otherwise>	
				<c:set var="techLeft" value="${catLeft + (category.categorySeq % 2 == 0 ? -250 : 300)}" />
				<c:set var="techTop"  value="${catTop + index * 60}" />
			</c:otherwise>
			</c:choose>
				
				
				<div id="tech${tech.techSeq}" class="sub-node"
					style="top:${techTop}px; left:${techLeft}px;">
					${tech.techName}
					<div class="toggle-circle" onclick="toggleCircleColor(this)"></div>
				</div>
				<c:set var="index" value="${index + 1}" />
		</c:if>	
	
		</c:forEach>
	</c:forEach>

	<script>
	jsPlumb.ready(function () {
	      const instance = jsPlumb.getInstance({
	      Connector: ["Straight"],
	      PaintStyle: { stroke: "#3b82f6", strokeWidth: 3 },
	      EndpointStyle: { fill: "#3b82f6", radius: 4 },
	      HoverPaintStyle: { stroke: "#0ea5e9", strokeWidth: 3 },
	      Endpoint: "Blank"
	    });

    document.querySelectorAll(".node, .sub-node").forEach(el => {
        el.setAttribute("draggable", false);
        el.ondragstart = () => false;
        instance.setDraggable(el, false);
    });
    
	// tech 노드 좌우 번갈아 연결선 구분
    <c:forEach var="tech" items="${roadmaplist}">
    
	    instance.connect({
	        source: "tech${tech.techSeq}",
	        target: "step${tech.categorySeq}",
	        anchors: 
	        <c:choose>
	            <c:when test="${tech.categorySeq % 2 == 0}">
	                ["Right", "Left"]
	            </c:when>
	            <c:otherwise>
	            	["Left", "Right"]
	            </c:otherwise>
	        </c:choose>
	    });
    </c:forEach>
    
	// step 노드 상하좌우 연결지점 선택
	<c:forEach var="category" items="${roadmapcategory}" varStatus="status">
		<c:if test="${not status.last}">
			instance.connect({
				source: "step${category.categorySeq}",
			    target: "step${category.categorySeq+1}",
			    
			    
			    //연결지점 : Left/Right/Bottom/Top
			    <c:choose>
			    	<c:when test="${category.categorySeq == 1}">
			    		anchors: ["Left", "Top"]
			    	</c:when>
			    	<c:when test="${category.categorySeq == 2}">
		    			anchors: ["Bottom", "Left"]
		    		</c:when>
			    	<c:when test="${category.categorySeq == 3}">
	    				anchors: ["Left", "Top"]
	    			</c:when>
			    	<c:otherwise>
			    		anchors: ["Bottom", "Top"]
	            	</c:otherwise>
			    </c:choose>
			    ,
			    
			//connector : 직선/곡선    
			<c:choose>
			    <c:when test="${category.categorySeq == 3}">
            		connector: ["Bezier", { curviness: 100 }]
        		</c:when>
	            <c:when test="${category.categorySeq == 6}">
	            	connector: ["Straight"] 
	            </c:when>
	            
	            <c:otherwise>
	            	connector: ["Bezier", { curviness: 50 }]
	            </c:otherwise>
	        </c:choose>
			    
			});
	   </c:if> 
	</c:forEach>
	
    document.querySelectorAll(".sub-node").forEach(el => {
        el.addEventListener("click", () => {
            el.classList.toggle("selected-tech");
        });
    });
  });
	
	
	function toggleCircleColor(element) {
	      element.classList.toggle("toggled");
	  }
</script>
</body>
</html>
