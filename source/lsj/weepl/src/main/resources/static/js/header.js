

$(document).ready(function(){
	$(".menu").mouseenter(function(){
		$("#hiddenContainer").attr("style","display:block; background-color:white; z-index:-1");
		$(".depth_1").stop().slideDown(400);
	}),
	$(".menu").mouseleave(function(){
		$("#hiddenContainer").attr("style","display:none; backgroundColor:transparent; z-index:-1");
		$(".depth_1").stop().slideUp(300);
	});
})