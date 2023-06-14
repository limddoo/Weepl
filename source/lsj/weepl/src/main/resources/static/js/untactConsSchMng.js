
$(document).ready(function() {
	var now_utc = Date.now() // 지금 날짜를 밀리초로
	// getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환
	var timeOff = new Date().getTimezoneOffset()*60000; // 분단위를 밀리초로 변환
	// new Date(now_utc-timeOff).toISOString()은 '2022-05-11T18:09:38.134Z'를 반환
	var today = new Date(now_utc-timeOff).toISOString().split("T")[0];
	document.getElementById("startDate").setAttribute("min", today);
	
	$("#schArea").on("click", ".sch", function() {
		this.remove();
	});
});


// 시작일만 처음 설정할시 종료일에도 시작일을 넣어줌
function setEndDate() {
	var startDt = document.getElementById("startDate");
	var endDt = document.getElementById("endDate");
	endDt.setAttribute("min",startDt.value);
	endDt.value=startDt.value;
	// span 태그 생성해줌
	getSchList();
}

// 시작일, 종료일에 맞춰 span 태그(각 날짜) 생성해줌
function getSchList() {
	// 시작일, 종료일 변경될때 기존에 있던 span태그 삭제
	$("#schArea *").remove();
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	var startDt = new Date(startDate);
	var endDt = new Date(endDate);
	var curDt = startDt;
	var str = "";
	while(curDt <= endDt) {
		// 주말은 자동으로 날짜목록에서 사라짐
		if(curDt.getDay()!=0 && curDt.getDay() !=6) {
			str += "<span class='sch'>";
			str += toStringByFormatting(curDt);
			str += "</span>";
		}
		curDt.setDate(curDt.getDate() + 1);
	}
	
	$("#schArea").append(str);
}

// span태그에 표시될 날짜 포맷 설정
function leftPad(value) {
	if (value >= 10) {
		return value;
	}

	return `0${value}`;
}
// span태그에 표시될 날짜 포맷 설정
function toStringByFormatting(source, delimiter = '-') {
	const year = source.getFullYear();
	const month = leftPad(source.getMonth() + 1);
	const day = leftPad(source.getDate());

	return [year, month, day].join(delimiter);
}

// 일정을 등록(ajax)
function setSch() {
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	 const schList = document.getElementsByClassName('sch');
	 var schs = "";
	 for(let i=0; i < schList.length; i++) {
		 schs += schList[i].innerText+","
	 }
	 if(schs == "") {
		 alert("날짜를 선택해주세요!");
		 return;
	 }
	 var am = document.getElementById('am');
	 var pm = document.getElementById('pm');
	 var checkedAM = "";
	 if (am.checked) {
		 checkedAM = am.value;
	 }
	 var checkedPM = "";
	 if (pm.checked) {
		 checkedPM = pm.value;
	 }
	 if(checkedAM == "" && checkedPM == "") {
		 alert("시간을 선택해주세요!");
		 return;
	 }
	 $.ajax({
		 url:"/admin/ucSchCreate",
		 method:"post",
		 data :{
			 "schDate":schs,
			 "am":checkedAM,
			 "pm":checkedPM
		 },beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
		 },
		 success: function(result) {
			 alert(result.result);
			 location.reload();
		 }, error: function() {
			 alert("에러발생!");
		 }
	 });
}

// full calendar
document.addEventListener('DOMContentLoaded', function() {

	var calendarEl = document.getElementById('calendar');
	
	
	  var request = $.ajax({
		url : "/reservation/selectDate",
		method : "GET",
		async : true,
		headers : { 
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "GET"
		},
		dataType : 'json',
		success : function(result) { 
			console.log(result)
		},
		error : function(request, status, error) { // 결과 에러 콜백함수
			console.log(error)
		}
	});

	request.done(function(result) {
		var calendar = new FullCalendar.Calendar(calendarEl, {
			initialView : 'dayGridMonth',
			showNonCurrentDates : true,
			headerToolbar : {
				left : 'prev',
				center : 'title',
				right : 'next'
			},				
			navLinks : true, // can click day/week names to navigate views
			businessHours : true, // display business hours
			editable : true,
			selectable : true,
			locale : "ko",
			eventClick : function(info) {
				var data = JSON.parse(JSON.stringify(info.event));
				if(data.title != '예약가능') {
					alert('예약된 일정은 삭제가 불가능합니다.');
					return false;
				}
				if(confirm("이 일정을 삭제하시겠습니까?")) {
					selectedId = data.id;
					$.ajax({
						url: "/admin/ucSchDelete",
						method: "GET",
						data: {selectedId: selectedId},
						success: function(result) {
							alert(result.result);
							location.reload();
						},
						error: function(result) {
							alert("일정 삭제중 에러가 발생했습니다.")
						}
					});
				}
			},
			eventDisplay:'block'	 
		});
		calendar.render();	
			for(var i =0; i<result.length;i++){
				calendar.addEvent(result[i])
			}	
	});
	
});