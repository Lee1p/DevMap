<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>

<!-- ✅ jQuery 추가 (가장 먼저!) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- GoJS -->
<script src="https://cdn.jsdelivr.net/npm/gojs/release/go-debug.js"></script>

<style>
.d-none {
	display: none !important;
}

div.d-none2 {
	display: block !important;
}

.sidebar-overlay {
	position: fixed;
	top: 60px;
	left: 0;
	width: 100vw;
	height: calc(100vh - 60px);
	background: rgba(0, 0, 0, 0.4);
	z-index: 999;
	display: none;
}

.sidebar-slide {
	position: fixed;
	top: 60px;
	right: -620px;
	width: 600px;
	height: calc(100% - 60px);
	background-color: #fff;
	box-shadow: -2px 0 10px rgba(0, 0, 0, 0.2);
	transition: right 0.3s ease;
	z-index: 1000;
	overflow-y: auto;
	padding: 20px;
}

.sidebar-slide.open {
	right: 0;
}

.sidebar-overlay.active {
	display: block;
}

body.scroll-lock {
	overflow: hidden;
}
</style>
</head>

<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
	<div class="app-wrapper">
		<%@include file="/WEB-INF/views/inc/navbar.jsp"%>

		<main class="app-main">
			<div id="myDiagramDiv"
				style="width: 100vh; height: 100vh; background-color: #DAE4E4;"></div>

			<!-- 사이드바 -->
			<div id="sidebar-overlay" class="sidebar-overlay"></div>
			<div id="sidebar" class="sidebar-slide">
				<button id="sidebar-close"
					class="btn btn-sm btn-outline-secondary float-end">×</button>
				<div id="sidebar-content"></div>
			</div>
		</main>

		<%@include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>

	<%@include file="/WEB-INF/views/inc/footer_js.jsp"%>

	<script>
    const contextPath = '<%= request.getContextPath() %>';

    const myDiagram = new go.Diagram("myDiagramDiv", {
      "undoManager.isEnabled": true,
      layout: new go.TreeLayout({ angle: 90, layerSpacing: 35 })
    });

    myDiagram.nodeTemplate = new go.Node("Auto", { desiredSize: new go.Size(100, 50) }).add(
      new go.Shape().bind("figure", "fig").bind("fill", "color"),
      new go.TextBlock({ margin: 5 }).bind("text")
    );

    myDiagram.linkTemplate = new go.Link({
      routing: go.Routing.AvoidsNodes,
      corner: 10,
      toShortLength: 8
    }).add(
      new go.Shape({ strokeWidth: 3 }),
      new go.Shape({ toArrow: "Standard", scale: 1.5 })
    );

    myDiagram.model = new go.GraphLinksModel([
      { key: 1, text: "Start", color: "#0daf46", fig: "Ellipse" },
      { key: 2, text: "Java", color: "#8b43f9", fig: "RoundedRectangle" },
      { key: 3, text: "Spring", color: "#8b43f9", fig: "RoundedRectangle" },
      { key: 4, text: "JPA", color: "#8b43f9", fig: "RoundedRectangle" },
      { key: 5, text: "End", color: "#f94343", fig: "Ellipse" }
    ], [
      { from: 1, to: 2 },
      { from: 2, to: 3 },
      { from: 3, to: 4 },
      { from: 4, to: 5 }
    ]);

    // 🔍 노드 클릭 시 사이드바 AJAX 호출
    let techName;
    myDiagram.addDiagramListener("ObjectSingleClicked", function (e) {
      const part = e.subject.part;
      if (part instanceof go.Node) {
        techName = part.data.text;
        loadTechSidebar(techName);
      }
    });

    function loadTechSidebar() {
      $.ajax({
        url: contextPath + '/roadmap/techsidebar.do',
        method: 'GET',
        data: { techName },
        success: function (html) {
          console.log('[DEBUG] sidebar html:', html);
          $('#sidebar-content').html(html);
          $('#sidebar').addClass('open');
          $('#sidebar-overlay').addClass('active');
          $('body').addClass('scroll-lock');
          bindSidebarTabs();
        },
        error: function (xhr) {
          console.error('❌ AJAX error:', xhr);
          alert('❌ 사이드바 로딩 실패\n\n' + xhr.responseText);
        }
      });
    }

    function bindSidebarTabs() {
      $(document).off('click', '.tab-btn').on('click', '.tab-btn', function () {
        const level = $(this).data('level');
        $('.tab-btn').removeClass('active');
        $(this).addClass('active');
        $('.subject-group').addClass('d-none');
        $(`.subject-group[data-level="${level}"]`).removeClass('d-none');
      });
    }

    $('#sidebar-close, #sidebar-overlay').on('click', function () {
      $('#sidebar').removeClass('open');
      $('#sidebar-overlay').removeClass('active');
      $('body').removeClass('scroll-lock');
    });
  </script>
</body>
</html>
