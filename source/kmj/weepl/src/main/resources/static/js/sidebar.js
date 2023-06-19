// 매핑에 따라 사이드바 활성화/비활성화함
// 가장 뒤에 나오는 매핑과 사이드바 id를 일치시켜야함.
$(document).ready(function() {

    var current = location.pathname.split("/").slice(-1)[0].replace(/^\/|\/$/g, '');
    console.log(current);
    var selected = document.getElementById(current);
    if (selected != null) {
        $('.active').removeClass('active');
        selected.classList.add('active');
    } else {
        current = location.pathname.split("/").slice(-2)[0].replace(/^\/|\/$/g, '');
        if (current.includes('board') || current.includes('Board')) {
            $('.active').removeClass('active');
            var consList = document.getElementById('consList');
            if (consList != null) {
                consList.classList.add('active');
            }
        } else if (current.includes('myCons')) {
            $('.active').removeClass('active');
            var myConsList = document.getElementById('myConsList');
            if (myConsList != null) {
                myConsList.classList.add('active');
            }
        } else if (current.includes('modMy')) {
            $('.active').removeClass('active');
            var myConsList = document.getElementById('myConsList');
            if (myConsList != null) {
                myConsList.classList.add('active');
            }
        }
    }
});
       