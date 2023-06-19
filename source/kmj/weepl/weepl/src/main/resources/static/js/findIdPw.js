$(document).ready(function(){
	var header = $("meta[name='_csrf_header']").attr('content');
	var token = $("meta[name='_csrf']").attr('content');
	$("#find_id_btn").click(function() {
		var name = $('#name').val();
		if(name=='') {
			alert("이름을 입력하세요");
			$('#name').focus();
			return;
		}
		var tel1 = $('#tel1').val();
		var tel2 = $('#tel2').val();
		var tel3 = $('#tel3').val();
		var tel = tel1 + tel2 + tel3;
		if(tel.length<10) {
			alert("전화번호를 확인해주세요");
			if(tel1.length == 0) {
				$('#tel1').focus();
				return;
			}
			if(tel2.length == 0) {
				$('#tel2').focus();
				return;
			}
			if(tel3.length == 0) {
				$('#tel3').focus();
				return;
			}
			$('#tel1').focus();
			return;
		}
		$.ajax({
			type: "post",
			url: "/members/findIdByNameAndTel",
			data : { "name" : name,
					 "tel1" : tel1,
					 "tel2" : tel2,
					 "tel3" : tel3
			},
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success: function(result){
				console.log("result :" +result.result)
				var id = result.result;
				if(id != null) {
					alert('회원님의 아이디는 '+id+' 입니다.');
				} else {
					alert("일치하는 회원 정보가 없습니다.");
				}
			},
			error : function(){
				alert("에러발생");
			}
		});
	});
	$("#find_pwd_btn").click(function() {
		var _id = $('#id').val();
			if(_id=='') {
				alert("아이디를 입력하세요");
				$('#id').focus();
				return;
			}
			var name = $('#name').val();
			if(name=='') {
				alert("이름을 입력하세요");
				$('#name').focus();
				return;
			}
			var email = $('#email').val();
			if(email=='') {
				alert("이메일을 입력하세요");
				$('#email').focus();
				return;
			}
			$.ajax({
				type: "post",
				url: "/members/findPwdByEmail",
				data : { "id" : _id,
						 "name" : name,
						 "email" : email
				},
				beforeSend: function(xhr){
					xhr.setRequestHeader(header, token);
				},
				success: function(result){
					var result = result.result;
					console.log("result :" +result)
					if(result == "true"){
						alert('이메일을 확인해주세요.');
					}
					else{
						alert("일치하는 회원 정보가 없습니다.");
					}
				},
				error : function(){
						alert("에러발생");
				}
			});
	});
});