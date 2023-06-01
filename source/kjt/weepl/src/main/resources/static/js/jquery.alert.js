/*
* custom alert jQuery Plugin
* author Yoon  (info@billible.co.kr) [http://billible.co.kr]
*
* version 1.0.2 [JUL. 2022]
*
* @changelog
* v 1.0.1      ->   2020.07 플러그인 완성
* v 1.0.2      ->   버튼 위치 변경 옵션 추가
*/

(function($) {

    $.fn.alert = function(msg,op) {$(this).queue(function(){$.alert(msg,op);});};
    $.fn.confirm = function(msg,op) {$(this).queue(function(){$.alert(msg,op);});};
    $.fn.prompt = function(msg,op) {$(this).queue(function(){$.alert(msg,op);});};

    var con = {
        idx:'IDX'+Math.floor(Math.random()*1000),
        tt :'TT'+Math.floor(Math.random()*1000),
        tb :'TB'+Math.floor(Math.random()*1000),
        em:'EM'+Math.floor(Math.random()*1000),
        btn:'BTN'+Math.floor(Math.random()*1000),
        input:'INPUT'+Math.floor(Math.random()*1000),
        opClass:'popup_open',
        sp:'body',
        op : {
            title:'알림',
            em:'',
            buttonReverse:false,
            callEvent:null,
            cancelEvent:null,
            confirmButton:'확인',
            cancelButton:'취소',
            input:'text',
            value:null
        }
    },frame = function(){
        return $('<div id="'+con.idx+'" class="popup">').append(
                $('<div class="popup-wrap">').append(
                    $('<h2 id="'+con.tt+'" class="popup-title">')
                ).append(
                    $('<div class="popup-content">').append(
                        $('<div class="text-box">').append($('<p id="'+con.tb+'">')).append($('<strong id="'+con.em+'">'))
                    ).append(
                        $('<div class="popup-btn" id="'+con.btn+'">')
                    )
                )
                ).append('<div class="dimmed"></div>')}
    ,input = {
        text:function(){return $('<div class="input-box">').append('<input id="'+con.input+'" type="text">')},
        tel:function(){return $('<div class="input-box">').append('<input id="'+con.input+'" type="tel">')},
        number:function(){return $('<div class="input-box">').append('<input id="'+con.input+'" type="number">')},
        password:function(){return $('<div class="input-box">').append('<input id="'+con.input+'" type="password">')}
    }
    ,button = {
        alert:function(op){return $('<ul>')
                .append($('<li>').append($('<a href="#" class="confirm">'+op.confirmButton+'</a>')))},
        confirm:function(op){return $("<ul "+(op.buttonReverse?"class='button-reverse'":"")+">")
                .append($('<li>').append($('<a href="#" class="close">'+op.cancelButton+'</a>')))
                .append($('<li>').append($('<a href="#" class="confirm">'+op.confirmButton+'</a>')))},
        prompt:function(op){return $("<ul "+(op.buttonReverse?"class='button-reverse'":"")+">")
                .append($('<li>').append($('<a href="#" class="close">'+op.cancelButton+'</a>')))
                .append($('<li>').append($('<a href="#" class="confirm">'+op.confirmButton+'</a>')))},
    },event = {
        creation:function(fn){$(con.sp).append(frame());fn();},
        confirm: function(op){event.close();event.callEvent(op)},
        cancel: function(op){event.close();event.cancelEvent(op)},
        close: function(){$(con.sp).removeClass(con.opClass);$('#'+con.idx).remove();},
        show: function(){$(con.sp).addClass(con.opClass);},
        callEvent:function(op){if (typeof op.callEvent == 'function') op.callEvent(op.value);},
        cancelEvent:function(op){if (typeof op.cancelEvent == 'function') op.cancelEvent();},
    }

    function init(){
        /*reset*/
    }

    $.alert = function(msg,op) {
        if($("body").hasClass(con.opClass))return false;
        if(typeof msg =='function' || typeof msg =='object') msg='['+typeof msg+']';

        op = $.extend({}, con.op,op);
        event.creation(function(){
            $("#"+con.idx).addClass('alert');
            $("#"+con.idx).find('#'+con.btn).append(button.alert(op));
            if(op.title)$("#"+con.idx).find('#'+con.tt).html(op.title); else $("#"+con.idx).find('#'+con.tt).remove();
            $("#"+con.idx).find('#'+con.tb).html(msg);
            $("#"+con.idx).find('#'+con.em).html(op.em);
            $("#"+con.idx).find('#'+con.btn).find('.confirm').unbind('click.confirm').bind('click.confirm',function(e){
                e.preventDefault()
                event.confirm(op);
            })
            event.show();
        })

    };

    $.confirm = function(msg,op) {
        if($("body").hasClass(con.opClass))return false;
        if(typeof msg =='function' || typeof msg =='object') msg='['+typeof msg+']';

        op = $.extend({}, con.op,op);
        event.creation(function() {
            $("#" + con.idx).addClass('confirm');
            $("#" + con.idx).find('#' + con.btn).append(button.confirm(op));
            $("#" + con.idx).find('#' + con.tt).html(op.title);
            $("#" + con.idx).find('#' + con.tb).html(msg);
            $("#" + con.idx).find('#' + con.em).html(op.em);
            $("#" + con.idx).find('#' + con.btn).find('.confirm').unbind('click.confirm').bind('click.confirm', function (e) {
                e.preventDefault()
                event.confirm(op);
            })
            $("#" + con.idx).find('#' + con.btn).find('.close').unbind('click.close').bind('click.close', function (e) {
                e.preventDefault()
                event.cancel(op);
            })
            event.show();
        })
    };

    $.prompt = function(msg,op) {
        if($("body").hasClass(con.opClass))return false;
        if(typeof msg =='function' || typeof msg =='object') msg='['+typeof msg+']';

        op = $.extend({}, con.op,op);
        event.creation(function() {
            console.log(op);
            $("#" + con.idx).addClass('prompt');
            $("#" + con.idx).find('#' + con.btn).before(input[op.input]());
            $("#" + con.idx).find('#' + con.btn).append(button.prompt(op));
            $("#" + con.idx).find('#' + con.tt).html(op.title);
            $("#" + con.idx).find('#' + con.tb).html(msg);
            $("#" + con.idx).find('#' + con.em).html(op.em);
            $("#" + con.idx).find('#' + con.btn).find('.confirm').unbind('click.confirm').bind('click.confirm', function (e) {
                e.preventDefault()
                op.value =$("#" + con.idx).find('#' + con.input).val();
                event.confirm(op);
            })
            $("#" + con.idx).find('#' + con.btn).find('.close').unbind('click.close').bind('click.close', function (e) {
                e.preventDefault()
                event.cancel(op);
            })
            event.show();
        })
    };

    $.close = function(){
        if($("body").hasClass(con.opClass)) event.close()
    }
    //init()
})(jQuery);
