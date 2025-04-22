<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>
</head>

<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
	<div class="app-wrapper">
		<%@include file="/WEB-INF/views/inc/navbar.jsp"%>
		<main class="app-main">
		
		
				<!-- 사이드바 콘텐츠 -->
<div class="card mb-3">
  <div class="card-body">
    <h4 class="card-title">${tech.techName}</h4>
    <p class="card-text">${tech.techDesc}</p>
  </div>
</div>

<div class="list-group">
  <c:forEach var="subject" items="${subjectList}">
    <a href="/subject/detail.do?subjectSeq=${subject.subjectSeq}" class="list-group-item list-group-item-action">
      ${subject.subjectName}
      <span class="badge bg-secondary float-end">
        <c:choose>
          <c:when test="${subject.subjectDifficulty == '1'}">초급</c:when>
          <c:when test="${subject.subjectDifficulty == '2'}">중급</c:when>
          <c:otherwise>상급</c:otherwise>
        </c:choose>
      </span>
    </a>
  </c:forEach>
</div>
				
				
			</div>
		</main>
		<%@include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>
	<%@include file="/WEB-INF/views/inc/footer_js.jsp"%>
</body>
</html>






