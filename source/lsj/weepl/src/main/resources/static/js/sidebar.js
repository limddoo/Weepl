// 매핑에 따라 사이드바 활성화/비활성화함
// 가장 뒤에 나오는 매핑과 사이드바 id를 일치시켜야함.
$(document).ready(function() {
		var current = location.pathname.split("/").slice(-1)[0].replace(/^\/|\/$/g, '');
		$('.active').removeClass('active');
		document.getElementById(current).classList.add('active');
});