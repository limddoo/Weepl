

$(document).ready(function(){
	$("#menu").mouseenter(function(){
		 $("#hiddenContainer").attr("style","display:block; background-color:white; z-index:-1");
		menu.style.borderBottom = "2px solid rgba(76,76,78,0.3)";
		$(".depth_1").stop().slideDown(400);
	}),
	$("#menu").mouseleave(function(){
		 $("#hiddenContainer").attr("style","display:none; backgroundColor:transparent; z-index:-1");
		menu.style.borderBottom = "";
		$(".depth_1").stop().slideUp(100);
	})
	})