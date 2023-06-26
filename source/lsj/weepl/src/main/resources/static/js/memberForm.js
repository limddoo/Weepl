$(document).ready(function () {
    header = $("meta[name='_csrf_header']").attr('content');
    token = $("meta[name='_csrf']").attr('content');

});
var chkEmail = false;
// 주소입력
function inputPostcode() {
    new daum
        .Postcode({
            oncomplete: function (data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분. 각 주소의 노출 규칙에 따라 주소를 조합한다. 내려오는 변수가 값이 없는
                // 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if (data.userSelectedType === 'R') {
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외) 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if (data.buildingName !== '' && data.apartment === 'Y') {
                        extraAddr += (
                            extraAddr !== ''
                                ? ', ' + data.buildingName
                                : data.buildingName
                        );
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    addr += extraAddr;

                } else {
                    document
                        .getElementById("addrDtl")
                        .value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document
                    .getElementById('postNum')
                    .value = data.zonecode;
                document
                    .getElementById("addr")
                    .value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document
                    .getElementById("addrDtl")
                    .focus();
            }
        })
        .open();
}

// 비밀번호 확인 기능
function pwdConfirm() {
    var confirmPwd = document.getElementById("confirmPwd");
    var pwd = document.getElementById("pwd");
    var confrimMsg = document.getElementById('confirmMsg');
    if (pwd.value != confirmPwd.value) {
        confirmPwd.value = "";
        alert("비밀번호가 일치하지않습니다.");
    }
}

// 아이디 중복체크 기능
function chkIdOverlapped() {
    var _id = $("#_id").val();
    if (_id == '') {
        alert("ID를 입력하세요");
        return;
    }
    $.ajax({
        type: "post",
        url: "/members/exists",
        data: {
            "id": _id
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {
            if (result.result == false) {
                alert("사용할 수 있는 ID입니다.");
                $('#btnOverlapped').prop("disabled", true);
                $('#_id').prop("disabled", true);
                $('#id').val(_id);
                $('#checked').val('checked');
            } else {
                alert("사용할 수 없는 ID입니다.");
            }
        },
        error: function () {
            alert("에러발생");
        }
    });
}

// 이메일 인증코드 기능
function fMailConfirm() {
    var _email = $("#email").val();
    if (_email == '') {
        alert("이메일을 입력하세요");
        return;
    }
    $.ajax({
        type: "post",
        url: "/members/mailConfirm",
        data: {
            "email": _email
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {
            alert("해당 이메일로 인증번호 발송이 완료되었습니다. \n 확인부탁드립니다.")
            console.log("data : " + result.result);
            $("#emailchkcomp").val(result.result);
        },
        error: function () {
            alert("에러발생");
        }
    });
}

function confirmEmail() {
    var mailchk = $("#emailchkcomp").val();
    var memailconfirm = $("#memailconfirm").val();
    if (mailchk != memailconfirm) {
        alert('이메일 인증번호가 잘못되었습니다.');
        return false;
    } else {
        alert('이메일 인증이 완료되었습니다.');
        chkEmail = true;
    }
}

function frmSubmit() {
    var tel = $("#tel1").val() + $("#tel2").val() + $("#tel3").val();
    $("#tel").val(tel);
    var birY = $("#birY").val();
    var birM = $("#birM").val();
    var birD = $("#birD").val();
    var bir = birY + birM + birD;
    $("#bir").val(bir);
    if (!chkEmail) {
        alert('이메일 인증은 필수입니다.');
        document
            .getElementById("memailconfirm")
            .focus();
        return false;
    }

    document
        .getElementById('frm')
        .submit();
}

$(document).ready(function () {
    header = $("meta[name='_csrf_header']").attr('content');
    token = $("meta[name='_csrf']").attr('content');

});
var chkEmail = false;
// 주소입력
function inputPostcode() {
    new daum
        .Postcode({
            oncomplete: function (data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분. 각 주소의 노출 규칙에 따라 주소를 조합한다. 내려오는 변수가 값이 없는
                // 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if (data.userSelectedType === 'R') {
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외) 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if (data.buildingName !== '' && data.apartment === 'Y') {
                        extraAddr += (
                            extraAddr !== ''
                                ? ', ' + data.buildingName
                                : data.buildingName
                        );
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    addr += extraAddr;

                } else {
                    document
                        .getElementById("addrDtl")
                        .value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document
                    .getElementById('postNum')
                    .value = data.zonecode;
                document
                    .getElementById("addr")
                    .value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document
                    .getElementById("addrDtl")
                    .focus();
            }
        })
        .open();
}

// 비밀번호 확인 기능
function pwdConfirm() {
    var confirmPwd = document.getElementById("confirmPwd");
    var pwd = document.getElementById("pwd");
    var confrimMsg = document.getElementById('confirmMsg');
    if (pwd.value != confirmPwd.value) {
        confirmPwd.value = "";
        alert("비밀번호가 일치하지않습니다.");
    }
}

// 아이디 중복체크 기능
function chkIdOverlapped() {
    var _id = $("#_id").val();
    if (_id == '') {
        alert("ID를 입력하세요");
        return;
    }
    $.ajax({
        type: "post",
        url: "/members/exists",
        data: {
            "id": _id
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {
            if (result.result == false) {
                alert("사용할 수 있는 ID입니다.");
                $('#btnOverlapped').prop("disabled", true);
                $('#_id').prop("disabled", true);
                $('#id').val(_id);
                $('#checked').val('checked');
            } else {
                alert("사용할 수 없는 ID입니다.");
            }
        },
        error: function () {
            alert("에러발생");
        }
    });
}

// 이메일 인증코드 기능
function fMailConfirm() {
    var _email = $("#email").val();
    if (_email == '') {
        alert("이메일을 입력하세요");
        return;
    }
    $.ajax({
        type: "post",
        url: "/members/mailConfirm",
        data: {
            "email": _email
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {
            alert("해당 이메일로 인증번호 발송이 완료되었습니다. \n 확인부탁드립니다.")
            console.log("data : " + result.result);
            $("#emailchkcomp").val(result.result);
        },
        error: function () {
            alert("에러발생");
        }
    });
}

function confirmEmail() {
    var mailchk = $("#emailchkcomp").val();
    var memailconfirm = $("#memailconfirm").val();
    if (mailchk != memailconfirm) {
        alert('이메일 인증번호가 잘못되었습니다.');
        return false;
    } else {
        alert('이메일 인증이 완료되었습니다.');
        chkEmail = true;
    }
}

function changedEmail() {
	chkEmail = false;
}

function frmSubmit() {
    var tel = $("#tel1").val() + $("#tel2").val() + $("#tel3").val();
    $("#tel").val(tel);
    var birY = $("#birY").val();
    var birM = $("#birM").val();
    var birD = $("#birD").val();
    var bir = birY + birM + birD;
    $("#bir").val(bir);
    if (!chkEmail) {
        alert('이메일 인증은 필수입니다.');
        document
            .getElementById("memailconfirm")
            .focus();
        return false;
    }

    document
        .getElementById('frm')
        .submit();
}
