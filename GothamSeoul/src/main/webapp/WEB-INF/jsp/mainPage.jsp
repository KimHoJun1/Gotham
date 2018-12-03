<%@page import="java.util.Base64"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
	<title>서울 재난정보 플랫폼 - main</title>
	<script src="http://code.jquery.com/jquery-2.1.3.min.js"></script>
	
	<!-- CSS link -->
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<!-- Bootstrap theme CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<!-- Leaflet CSS -->
	<link rel="stylesheet" href="<c:url value='/leaflet/leaflet.css' />" />
	<!-- Leaflet GPS control CSS -->
	<link href='https://api.mapbox.com/mapbox.js/plugins/leaflet-locatecontrol/v0.43.0/L.Control.Locate.mapbox.css' rel='stylesheet' />
	<link href='https://api.mapbox.com/mapbox.js/plugins/leaflet-locatecontrol/v0.43.0/css/font-awesome.min.css' rel='stylesheet' />
	<!-- FontAwesome CSS -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
	<!-- Google webfont CSS -->
	<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR" rel="stylesheet">
	<!-- Main CSS -->
	<link rel="stylesheet" href="<c:url value='/css/gotham_style.css' />" />
	
	<!-- JavaScript -->
	<!-- Leaflet -->
	<script src="<c:url value='/leaflet/leaflet.js' />"></script>
	<!-- Leaflet GPS control -->
	<script src='https://api.mapbox.com/mapbox.js/plugins/leaflet-locatecontrol/v0.43.0/L.Control.Locate.min.js'></script>
	<!-- Naver Geocode -->
	<script src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=t76XFOO4KOQe79v0K_L0&submodules=geocoder"></script>	
	<!-- proj4 Geocode -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/proj4js/2.5.0/proj4-src.js"></script>
	<!-- jQeury -->
	<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
	<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	<!-- jQuery User Interface -->
	<script src="https://code.jquery.com/ui/jquery-ui-git.js"></script>
	<!-- Bootstrap -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<!-- Main Javascript -->
	<!-- placed at the end of HTML body -->
	
</head>

<body>
	<div class="container-fluid" id="header">
		<div class="row" id="main_container">
			<div class="col-sm-2" id="title_container">
				<a href="main.do"><strong>GOTHAM SEOUL</strong></a>
			</div>
			<div class="col-sm-10" id="subtitle_container">
				<div id="search_container">
					현재 주소 - 
					<select id="search_input_district">
				    	<option>강남구</option>
				    	<option>강동구</option>
				 	  	<option>강북구</option>
				 	    <option>강서구</option>
		 			 	<option>관악구</option>
		 			 	<option>광진구</option>
		 			 	<option>구로구</option>
		 			 	<option>금천구</option>
		 			 	<option>노원구</option>
		 			 	<option>도봉구</option>
		 			 	<option>동대문구</option>
		 			 	<option>동작구</option>
		 			 	<option>마포구</option>
		 			 	<option>서대문구</option>
		 			 	<option>서초구</option>
		 			 	<option>성동구</option>
		 			 	<option>성북구</option>
		 			 	<option>송파구</option>
		 			 	<option>양천구</option>
		 			 	<option>영등포구</option>
		 			 	<option>용산구</option>
		 			 	<option>은평구</option>
		 			 	<option>종로구</option>
		 			 	<option>중구</option>
		 			 	<option>중랑구</option>
					</select>
					<input id="search_input_detail" type="text" placeholder="검색할 주소를 입력하세요." />
					<i class="fas fa-search btn" id="search_button"></i>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid" id="main">
		<div class="row" id="main_container">
			<div class="col-sm-2" id="sidebar_container">
				<div id="sidebar_1">
					<div class="sidebar_title" id="sidebar_1_title">
						화면 모드
					</div>
					<div class="content_title" id="content_title_1">
						<i class="fas fa-map"></i> 지도
					</div>
					<div class="content_title" id="content_title_2">
						<i class="fas fa-file-alt"></i> 보고서
					</div>
				</div>
				<div id="sidebar_2">
					<div class="sidebar_title" id="sidebar_2_title">
						분류
					</div>
					<div class="item_title" id="item_title_1">
						<i class="fas fa-sun"></i> 날씨/생활
					</div>
					<div class="item_title" id="item_title_2">
						<i class="fas fa-car-crash"></i> 화재/사고
					</div>
					<div class="item_title" id="item_title_3">
						<i class="fas fa-shield-alt"></i> 보건/안전
					</div>
				</div>
			</div>
			<div class="col-sm-10" id="submain">
				<div class="row" id="index_container">
					<div class="col-sm-2" id="index_icon">
						<i class="fas fa-chart-line fa-2x"></i>
						<p>오늘의 보건지수</p>
					</div>
					<div class="col-sm-2 index_content" id="index_1">
						<p>감기가능지수</p>
						<span><i class="fas fa-spinner fa-spin"></i></span>
					</div>
					<div class="col-sm-2 index_content" id="index_2">
						<p>천식폐질환가능지수</p>
						<span><i class="fas fa-spinner fa-spin"></i></span>
					</div>
					<div class="col-sm-2 index_content" id="index_3">
						<p>뇌졸중가능지수</p>
						<span><i class="fas fa-spinner fa-spin"></i></span>
					</div>
					<div class="col-sm-2 index_content" id="index_4">
						<p>피부질환가능지수</p>
						<span><i class="fas fa-spinner fa-spin"></i></span>
					</div>
					<div class="col-sm-2 index_content" id="index_5">
						<p>꽃가루농도위험지수</p>
						<span><i class="fas fa-spinner fa-spin"></i></span>
					</div>
				</div>
				<div class="row" id="alarm_container">
					<div class="col-sm-2" id="alarm_icon">
						<i class="fas fa-volume-up fa-2x"></i>
						<p>긴급알림</p>
					</div>
					<div class="col-sm-10 alarm_content" id="alarm_1">
						<p id="news">................</p>
					</div>
				</div>
				<div class="embed-responsive embed-responsive-16by9" id="map_container">
					<iframe class="embed-responsive-item" src="" allowfullscreen></iframe>
				</div>
				<div id="report_container_1">
					<p class="weather-title">현재 위치 날씨</p>
				    <hr>
				    <div>상태 이미지</div>
				    <br><br><br><br>
				    <div>현재 상태 : <div id="comm"></div></div>
				    <div>현재 온도 : <div id="temp"></div></div>
					<img id="w_graph1" src= ${w_graph1} width="400" height="300">
					<img id="w_graph2" src= ${w_graph2} width="400" height="300">
				</div>
				<div id="report_container_2">
				화재/사고 보고서
				</div>
				<div id="report_container_3">
				보건/안전 보고서
					<img id="result_graphy" src="" width="400" height="300">				
				</div>
				<div id="site_info">
					GOTHAM.SEOUL<br>
					version : 0.10 (last update - 2018/12/04)<br>
					Multicampus Java Programming Course Final Project<br>
					(c) 2018 Kim Hojun - Kim Sooji - Lim Dongjoo - Choi Miji - Ji Joonho - Lee Jaechan
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
	</div>

<!-- Main JavaScript -->
<script src="<c:url value='/js/gotham_script.js' />"></script>

</body>

</html>