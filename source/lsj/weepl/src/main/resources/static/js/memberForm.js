
		// 비밀번호 확인 기능
		function pwdConfirm() {
        	var confirmPwd = document.getElementById("confirmPwd");
        	var pwd = document.getElementById("pwd");
        	var confrimMsg = document.getElementById('confirmMsg');		
        	if(pwd.value != confirmPwd.value) {
        		confirmPwd.value = "";
        		alert("비밀번호가 일치하지않습니다.");
        	}
        }
		
		// 아이디 중복체크 기능
		function chkIdOverlapped(){
		    var _id= $("#_id").val();
		    if(_id==''){
				alert("ID를 입력하세요");
				return;
		    }
			$.ajax({
				type: "get",
				url: "/members/exists",
				data : { "id" : _id },
				contentType: "application/json",
				success: function(result){
						if(result.result == false){
							 alert("사용할 수 있는 ID입니다.");
					       	    $('#btnOverlapped').prop("disabled", true);
					       	 	$('#_id').prop("disabled", true);
					       	    $('#id').val(_id);
					       	 	$('#checked').val('checked');
						}
					else{
						alert("사용할 수 없는 ID입니다.");
					}
					},
					error : function(){
						alert("에러발생");
					}
				});
        }
		
		// 이메일 인증코드 기능
		function fMailConfirm() {
			var _email = $("#email").val();
			if(_email==''){
			   	 alert("이메일을 입력하세요");
			   	 return;
			}
			$.ajax({
				type : "get",
				url : "/members/mailConfirm",
				data : {
					"email" : _email
				},
				contentType: "application/json",
				success : function(result){
			         alert("해당 이메일로 인증번호 발송이 완료되었습니다. \n 확인부탁드립니다.")
			         console.log("data : "+result.result);
			         $("#emailchkcomp").val(result.result);
				},
				error : function(){
					alert("에러발생");
				}
			});
		}
		
		function frmSubmit() {
			var tel = $("#tel1").val() + $("#tel2").val() + $("#tel3").val();
			$("#tel").val(tel);
			var birY = $("#birY").val();
			var birM = $("#birM").val();
			var birD = $("#birD").val();
			var bir = birY + birM + birD;
			$("#bir").val(bir);
			
			var mailchk=$("#emailchkcomp").val();
			var memailconfirm=$("#memailconfirm").val();
			if(mailchk != memailconfirm) {
				alert('이메일 확인코드가 잘못되었습니다.');
				return false;
			}
			document.getElementById('frm').submit();
		}
		
		