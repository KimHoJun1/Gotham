<%@ page language="java" contentType="text/plain; charset=UTF-8"%>
<% String news[] = { "남산1호터널 강남방향 진입로시점→입구부시점 공사작업으로 3차로",
		"율곡로 원남사거리 → 이화사거리방향 공사로 3차로 차단",
		"신촌로 충정로역 →아현교차로 하위차로 부분차단",
  	    "남대문로 (숭례문 → 한국은행사거리)하위차로 공사 작업으로 차단",
		"백제고분로 석촌역 → 방이사거리 하위차로 공사",
		"양화로 합정역 → 서교동사거리 방향 공사로 하위차로 차단",
		" 덕수궁길 차 없는 거리(대한문~원형분수대) ※ 시간(평일 11:00~14:00, 토 10:00~17:00)"}; %>
<% response.setHeader("Cache-Control", "no-cache"); %>
<% int num = (int)(Math.random() * 7);%>
{ 
	"news" : "<%= news[num] %>"
}