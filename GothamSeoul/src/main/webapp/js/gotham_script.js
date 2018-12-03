// Global variables
var lng = 37.5367416;
var lat = 127.1472435;
var address = "서울시 강동구 천호대로 1231 길동현대아파트";
var mymap;
var districts;
var crimeData;
var lifeIndex_callback;

// Functions description

// 0. Others

// Leaflet
// 1. Leaflet
// 1-1. Draw leaflet
function drawLeaflet(lat, lng) {
	$.ajax({
		url: "/GothamSeoul/locationData.do?lat=" + lat + "&lng=" + lng,
		type: "get",
		success: function(data) {
			$('#map_container iframe').attr("src", "leafletchart/index.html");
			$('#comm').html(data.comm);
			$('#temp').html(data.temp+'°C');
			$('#w_graph1').attr("src","/test1.jpg");
			$('#w_graph2').attr("src","/test2.jpg");
		}
	});
};

function drawGraph(){
	$.ajax({
		url: "/GothamSeoul/graphy.do?",
		type: "get",
		success: function(data) {
//			alert("data:image/jpg;base64, " + data);
			$('#result_graphy').attr("src", "data:image/jpg;base64, "+ data);
		}
	});
}

function newsAlarm() {
	setInterval(function() {
		$.getJSON('newsjson.jsp', function(data) {
			$.each(data, function(key, value) {
				$('#news').text(value);
			});
		});
	}, 2000)
};

// 2. Address search
// 2-1. Dropdown setting
$('.dropdown ul li a').click(function() {
	$(this).parents('.dropdown').children('button').text($(this).text());
//	$(this).parents('.dropdown').children('.btn btn-default dropdown-toggle').text("modified");
});

// 2-2. Show district searched
function showDistricts() {
//	$.getJSON("geoJSON/seoul_municipalities_geo.json", function(jsonFeature) {
//		districts = jsonFeature;
		var jsonFeature_ = (districts.features).filter(function(data) {
			// to use .filter() function, selected a key: '.features' which is enclosed by '[,]' -> Array type
			return data.properties.SIG_KOR_NM == $('#search_input_district button').text();
		});
		L.geoJSON(jsonFeature_).addTo(mymap);
//	});
};

// 3. Geocode
// 3-0. Get current location
function getCurrentLocation() {
	function getLocation() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(showPosition,
					showError);
		} else {
			alert(" 이 브라우저는 geolocation을 지원하지 않습니다.");
		}
	};
	
	function showPosition(position) {
		lat = position.coords.latitude;
		lng = position.coords.longitude;
		setTimeout(function() {
			drawLeaflet(lat, lng);
		}, 200);
	};
	
	function showError(error) {
		alert("error");
	};
	
	getLocation();
//	setTimeout( function(){
//		   naver.maps.Service.reverseGeocode({
//		       location: new naver.maps.LatLng(lat, lng),
//		   }, function(status, response) {
//		       if (status !== naver.maps.Service.Status.OK) {
//		           return alert('Something wrong!');
//		       }
//		       
//		       var result = response.result, // 검색 결과의 컨테이너
//		       items = result.items; // 검색 결과의 배열
//		       guName = items[2].address.split(' ')[1];
//	//		       console.log(guName);
//	//		       alert(guName);
//		   }); 
//		}, 20);
};

// 3-1. Get a location of given address
// 3-1-0. Get a string of given address
function getAddress() {
	$('#search_button').click(function() {
		address = $('#search_input_district button').text() + $('#search_input_detail')[0].value;
		getAddressLocation(address);
	});
};

// 3-1-1. Get a location of given address
function getAddressLocation(address) {
	naver.maps.Service.geocode({
	    address: address
		}, function(status, response) {
	    if (status !== naver.maps.Service.Status.OK) {
	        return alert('Something wrong!');
	    }

	    var result = response.result, // container for search result
	        point = result.items[0].point; // result needed
	    
	    lat = JSON.stringify(point.x);
	    lng = JSON.stringify(point.y);

	    drawLeaflet(lng, lat); // callback
	});
};

// 4. Tab Click Event
// 4-1. Item container event(1)
function tabClick1() {
	$('.content_title').click(function() {
		$(this).css({color: "white"});
		$(this).siblings('.content_title').css({color: "#808D9A"});
		$('#map_container').show();
		$('#report_container_1').hide();
	});
	$('#content_title_2').click(function() {
		$('#map_container').hide();
		$('#report_container_1').show();
	});
	$('#content_title_1').trigger("click");
};

// 4-2. Item container event(2)
function tabClick2() {
	$('#item_title_1').click(function() {
		$(this).css({color: "white"});
		$(this).siblings('.item_title').css({color: "#808D9A"});
		$('.index_content span').each(function() {
			$(this).html('<i class="fas fa-spinner fa-spin"></i>').css({color: "black"});
		});
		lifeIndex();
		$('#report_container_1').show();
		$('#report_container_2').hide();
		$('#report_container_3').hide();
	});
	$('#item_title_2').click(function() {
		$(this).css({color: "white"});
		$(this).siblings('.item_title').css({color: "#808D9A"});
		$('#report_container_1').hide();
		$('#report_container_2').show();
		$('#report_container_3').hide();
	});
	$('#item_title_3').click(function() {
		clearInterval(lifeIndex_callback);
		$(this).css({color: "white"});
		$(this).siblings('.item_title').css({color: "#808D9A"});
		$('.index_content span').each(function() {
			$(this).html('<i class="fas fa-spinner fa-spin"></i>').css({color: "black"});
		});
		healthIndex();
		$('#report_container_1').hide();
		$('#report_container_2').hide();
		$('#report_container_3').show();
		drawGraph();
	});
	$('#item_title_1').trigger("click");
}

// 5. Animation
// 5-?. Subindex viewer
function subindexViewer() {
	
};

// 5-?. Index color differing
function itemIndexColor() {
	$('.index_content span').each(function() {
		var key = $(this).text();
		if (key == "위험") {
			$(this).css({color: "#6400af"});
		} else if (key == "매우높음" || key == "경고") {
			$(this).css({color: "#c12424"});
		} else if (key == "높음" || key == "주의") {
			$(this).css({color: "#ff6612"});
		} else if (key == "보통" || key == "관심") {
			$(this).css({color: "#ffce00"});
		} else if (key == "낮음") {
			$(this).css({color: "#bdff00"});
		} else {
			$(this).css({color: "#e3f018"});
		}
	});
};

// 5-?. Sidebar height control
function sidebarHeight() {
	$('#sidebar_container').css({height: $('#submain').height()});
};

// 6. Get alarm data
// 6-1. Accident Alarm
function accidentAlarm() {
	$.ajax({
		url: "/GothamSeoul/accidentAlarm.do",
		type: "get",
		success: function(data) {
			alert(JSON.stringify(data));
		}
	});
};

// 6-2. Fire Alarm
function fireAlarm() {
	$.ajax({
		url: "/GothamSeoul/fireAlarm.do",
		type: "get",
		success: function(data) {
			alert(JSON.stringify(data));
		}
	});
};

// 6-3. Life Index
function lifeIndex() {
	$('#index_icon p').text("오늘의 생활지수")
	$('#index_1 p').text("식중독지수");
	$('#index_2 p').text("불쾌지수");
	$('#index_3 p').text("체감온도");
	$('#index_4 p').text("동파가능지수");
	$('#index_5 p').text("자외선지수");
	$.ajax({
		url: "/GothamSeoul/lifeIndex.do",
		type: "get",
		success: function(data) {
			lifeIndex_callback = setInterval(function() {
				$('#index_1 span').fadeOut("slow", function() {
					$(this).text(data.result.식중독지수.stage).fadeIn("slow");
				});
				$('#index_2 span').fadeOut("slow", function() {
					$(this).text(data.result.불쾌지수.stage).fadeIn("slow");
				});
				$('#index_3 span').fadeOut("slow", function() {
					$(this).text(data.result.체감온도.stage).fadeIn("slow");
				});
				$('#index_4 span').fadeOut("slow", function() {
					$(this).text(data.result.동파가능지수.stage).fadeIn("slow");
				});
				$('#index_5 span').fadeOut("slow", function() {
					$(this).text(data.result.자외선지수.stage).fadeIn("slow");
					itemIndexColor();
				});
				setTimeout(function() {
					$('#index_1 span').fadeOut("slow", function() {
						$(this).text(data.result.식중독지수.point).fadeIn("slow");
					});
					$('#index_2 span').fadeOut("slow", function() {
						$(this).text(data.result.불쾌지수.point).fadeIn("slow");
					});
					$('#index_3 span').fadeOut("slow", function() {
						$(this).text(data.result.체감온도.point + "℃").fadeIn("slow");
					});
					$('#index_4 span').fadeOut("slow", function() {
						$(this).text(data.result.동파가능지수.point).fadeIn("slow");
					});
					$('#index_5 span').fadeOut("slow", function() {
						$(this).text(data.result.자외선지수.point).fadeIn("slow");
					});
				}, 4000);
			}, 8000);
		},
		error: function(data) {
			alert("오류 발생. 다시 시도해주세요. ㅠㅠ");
		}
	});
};

// 6-4. Health Index
function healthIndex() {
	$('#index_icon p').text("오늘의 보건지수")
	$('#index_1 p').text("감기가능지수");
	$('#index_2 p').text("천식폐질환가능지수");
	$('#index_3 p').text("뇌졸중가능지수");
	$('#index_4 p').text("피부질환가능지수");
	$('#index_5 p').text("꽃가루농도위험지수");
	$.ajax({
		url: "/GothamSeoul/healthIndex.do",
		type: "get",
		success: function(data) {
			$('#index_1 span').text(data.result.감기가능지수);
			$('#index_2 span').text(data.result.천식폐질환가능지수);
			$('#index_3 span').text(data.result.뇌졸중가능지수);
			$('#index_4 span').text(data.result.피부질환가능지수);
			$('#index_5 span').text(data.result.꽃가루농도위험지수);
			itemIndexColor();
		},
		error: function(data) {
			alert("오류 발생. 다시 시도해주세요. ㅠㅠ");
		}
	});
};

//Page script starter
$(document).ready(function() {
	getCurrentLocation();
	tabClick1();
	tabClick2();
	getAddress();
//	fireAlarm();
//	accidentAlarm();
	sidebarHeight();
	newsAlarm();
});
