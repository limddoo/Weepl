

$(document).ready(function(){
let hiddenContainer = document.getElementById('hiddenContainer');
	$(".menu").mouseenter(function(){
		hiddenContainer.style.backgroundColor="white";
		hiddenContainer.style.zIndex=-1;
		$(".depth_1").stop().slideDown(400);
	}),
	$(".menu").mouseleave(function(){
		hiddenContainer.style.display="hidden";
		hiddenContainer.style.backgroundColor="transparent";
		hiddenContainer.style.zIndex=-1;
		$(".depth_1").stop().slideUp(300);
	})
	})