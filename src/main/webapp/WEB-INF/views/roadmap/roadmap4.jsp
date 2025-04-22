<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/inc/header.jsp"%>
<link rel="stylesheet" href="/devmap/asset/css/CollRoadmap.min.css">
<link rel="stylesheet" href="CoolRoadmap.min.css">
        <style>
            body {
                background-color: #15182d;
                color: #FFF;
                font-family: -apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Oxygen,Ubuntu,Cantarell,Fira Sans,Droid Sans,Helvetica Neue,sans-serif;
                margin: 0;
            }
        </style>
</head>

<body class="layout-fixed sidebar-expand-lg bg-body-tertiary">
	<div class="app-wrapper">
		<%@include file="/WEB-INF/views/inc/navbar.jsp"%>
		<main class="app-main">
			<h1>Cool Roadmap Demo - 1 Column</h1>

        <div id='CoolRoadmapWrapper'></div>
		</main>
		<%@include file="/WEB-INF/views/inc/footer.jsp"%>
	</div>

	<%@include file="/WEB-INF/views/inc/footer_js.jsp"%>
	
	
	<script src='CoolRoadmap.min.js'></script>
 	<script src='/devmap/asset/css/CollRoadmap.min.js'></script>
	<script>
            var myroadmap = new roadmap('CoolRoadmapWrapper');

            myroadmap.columns([
                "Component 1",
            ])

            myroadmap.style(["#6a4bcc"])

            myroadmap.milestones([
                {
                    title: "Rank 0 - col 1",
                    descriptionHTML: "Discriptive text",
                    belongsToColumn: 1,
                    status: "complete",
                    forwardConnect: [1, 2],
                    difficulty: 10,
                    rank: 0
                },
                {
                    title: "Rank 2 - col 1",
                    descriptionHTML: "Discriptive text",
                    belongsToColumn: 1,
                    forwardConnect: [1, 3],
                    status: "complete",
                    difficulty: 40,
                    rank: 2
                },
                {
                    title: "Rank 3 - col 1",
                    descriptionHTML: "Discriptive text",
                    belongsToColumn: 1,
                    status: "complete",
                    difficulty: 40,
                    rank: 3
                },
                {
                    title: "Rank 4 - col 1",
                    descriptionHTML: "Discriptive text",
                    belongsToColumn: 1,
                    moreInfoURL: 'http://www.google.com',
                    rank: 4
                },
            ])

            myroadmap.markComplete([
                [0]
            ])
        </script>


</body>
</html>






