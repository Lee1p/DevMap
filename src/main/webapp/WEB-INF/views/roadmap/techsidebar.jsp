<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>



  .tech-card {
    background-color: #27548A;
    color: white;
    padding: 16px;
    border-radius: 12px;
    margin-bottom: 20px;
    position: relative;
  }

  .tech-card h2 {
    font-size: 24px;
    text-align: center;
    margin-bottom: 8px;
  }

  .tech-card .close-btn {
    position: absolute;
    top: 12px;
    right: 16px;
    background: transparent;
    border: none;
    color: white;
    font-size: 22px;
    font-weight: bold;
    cursor: pointer;
  }

  .tab-btn {
    margin: 0 4px;
    padding: 6px 14px;
    border-radius: 20px;
    font-size: 14px;
    background: white;
    border: 1px solid #ccc;
    color: #333;
    cursor: pointer;
    transition: all 0.2s ease;
  }

  .tab-btn.active {
    background-color: #27548A;
    color: white;
    border-color: #27548A;
  }

  .subject-group {
    display: none;
    margin-top: 16px;
    padding: 16px;
    background-color: #F9FAFB;
    border: 1px solid #ddd;
    border-radius: 8px;
  }

  .subject-group.show {
    display: block;
  }

  .progress-group {
    font-size: 14px;
    margin-bottom: 12px;
  }

  .progress-sm {
    height: 6px;
    background-color: #e9ecef;
    border-radius: 4px;
    overflow: hidden;
  }

  .progress-bar {
    height: 100%;
    background-color: #27548A;
  }

  .list-group-item {
    font-size: 14px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 12px;
  }

  .list-group-item .btn {
    font-size: 13px;
    padding: 4px 10px;
  }

  .sidebar {
    position: fixed;
    right: 0;
    top: 0;
    width: 400px;
    height: 100%;
    background: white;
    box-shadow: -2px 0 8px rgba(0, 0, 0, 0.1);
    z-index: 9999;
    display: none;
  }
  
  
  .markdown-body .highlight pre, .markdown-body pre {
	background-color: #fff;
}

  .markdown-body {
  	background-color: #fff;
  }
</style>

<!-- 기술 설명 카드 -->
<div class="tech-card">
  <h2>${tech.techName}</h2>
  <p class="mb-0">${fn:replace(tech.techDesc, '.', '.<br>')}</p>
  <button onclick="closeSidebar()" class="close-btn">×</button>
</div>

<!-- 탭 버튼 -->
<div class="text-center mb-3">
  <button type="button" class="tab-btn active" onclick="showLevel(1)">초급</button>
  <button type="button" class="tab-btn" onclick="showLevel(2)">중급</button>
  <button type="button" class="tab-btn" onclick="showLevel(3)">고급</button>
</div>

<!-- 난이도별 과목 리스트 -->
<c:forEach var="entry" items="${levelMap}">
  <c:set var="level" value="${entry.key}" />
  <c:set var="levelCode" value="${level == '초급' ? 1 : level == '중급' ? 2 : 3}" />
  <div class="subject-group ${level == '초급' ? 'show' : ''}" id="level-${levelCode}">
    <!-- 진행률 계산 -->
    <c:set var="total" value="${fn:length(entry.value)}" />
    <c:set var="completedCount" value="0" />
    <c:forEach var="subject" items="${entry.value}">
      <c:if test="${fn:contains(completedList, subject.subjectSeq)}">
        <c:set var="completedCount" value="${completedCount + 1}" />
      </c:if>
    </c:forEach>
    <c:set var="progress" value="${total > 0 ? (completedCount * 100) / total : 0}" />

    <!-- 진행률 -->
    <div class="progress-group">
      <span><strong>${level}</strong></span>
      <span class="float-end"><fmt:formatNumber value="${progress}" type="number" maxFractionDigits="1" />%</span>
      <div class="progress progress-sm">
        <div class="progress-bar" style="width:${progress}%"></div>
      </div>
    </div>

    <!-- 과목 리스트 -->
    <ul class="list-group mb-2">
      <c:forEach var="subject" items="${entry.value}">
        <li class="list-group-item">
          <div class="d-flex align-items-center">
            <input type="checkbox" class="form-check-input me-2" disabled
              <c:if test="${fn:contains(completedList, subject.subjectSeq)}">checked</c:if> />
            ${subject.subjectName}
          </div>
          <a href="${pageContext.request.contextPath}/roadmap/subject.do?subjectSeq=${subject.subjectSeq}"
             class="btn btn-sm btn-outline-secondary">상세보기</a>
        </li>
      </c:forEach>

      <c:if test="${empty entry.value}">
        <li class="list-group-item text-danger">⚠ ${level} 과목 없음</li>
      </c:if>
    </ul>
  </div>
</c:forEach>

<!-- JS -->
<script>
  function showLevel(level) {
    const buttons = document.querySelectorAll(".tab-btn");
    const groups = document.querySelectorAll(".subject-group");

    buttons.forEach((btn, index) => {
      btn.classList.toggle("active", index === level - 1);
    });

    groups.forEach(group => {
      group.classList.remove("show");
      if (group.id === "level-" + level) {
        group.classList.add("show");
      }
    });
  }

  function openSidebar() {
    const sidebar = document.querySelector('.sidebar');
    if (sidebar) {
      sidebar.style.display = 'block';
    }
  }

  function closeSidebar() {
    const sidebar = document.querySelector('.sidebar');
    if (sidebar) {
      sidebar.style.display = 'none';
    }
    // 풀기
    document.body.style.pointerEvents = 'auto';
  }
</script>
