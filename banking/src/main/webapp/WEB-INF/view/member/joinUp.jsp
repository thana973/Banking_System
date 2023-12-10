<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f4f4f4;
			margin: 20px;
		}

		form {
			max-width: 400px;
			margin: 0 auto;
			background-color: #fff;
			padding: 20px;
			border-radius: 8px;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
		}

		label {
			display: block;
			margin-bottom: 8px;
		}

		input {
			width: 100%;
			padding: 10px;
			margin-bottom: 15px;
			box-sizing: border-box;
			border: 1px solid #ccc;
			border-radius: 4px;
		}

		input[type="button"] {
			background-color: #4caf50;
			color: #fff;
			cursor: pointer;
		}

		input[type="button"]:hover {
			background-color: #45a049;
		}
	</style>
</head>
<body>
	<form id="joinUp" action="/joinUp" method="post">
		<!-- id -->
		<label for="memberId">id:</label>
		<input type="text" id="memberId" name="memberId"><br>
		<button type="button" id="isIdDuplicateBtn" name="isIdDuplicateBtn" onclick="isIdDuplicate()">중복검사</button>
		<span id="errorMessage" style="color: red;"></span>

		<%--email--%>
		<label for="mail">mail:</label>
		<input type="text" id="mail" name="mail"><br>
		<button type="button" id="sendNumberBtn" name="sendNumberBtn" onclick="sendNumber()">인증번호</button>

		<div id="mail_number" name="mail_number" style="display: none">
			<input type="text" name="mailConfirmNumber" id="mailConfirmNumber" style="width:250px; margin-top: -10px" placeholder="인증번호 입력">
			<button type="button" name="confirmBtn" id="confirmBtn" onclick="confirmNumber()">이메일 인증</button>
		</div>

		<!-- Name -->
		<label for="name">Name:</label>
		<input type="text" id="name" name="name"><br>

		<!-- Password -->
		<label for="password">Password:</label>
		<input type="password" id="password" name="password"><br>
		<span id="pwdErrorMessage" style="color: red;"></span>

		<!-- Confirm Password -->
		<label for="password2">Confirm Password:</label>
		<input type="password" id="password2" name="password2"><br>
		<span id="pwdConfirmerrorMessage" style="color: red;"></span>

		<!-- Identification (주민번호) -->
		<label for="identification">Identification:</label>
		<input type="password" id="identification" name="identification" inputmode="numeric" pattern="[0-9]*" maxlength="13"><br>

		<!-- Phone Number -->
		<label for="phoneNum">Phone Number:</label>
		<input type="text" id="phoneNum" name="phoneNum"><br>

		<!-- Home Number -->
		<label for="homeNum">Home Number:</label>
		<input type="text" id="homeNum" name="homeNum"><br>

		<!-- Address -->
		<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
		<input type="text" id="sample6_postcode" placeholder="우편번호">
		<input type="text" id="sample6_address" placeholder="주소" name="address"><br>
		<input type="text" id="sample6_detailAddress" placeholder="상세주소">
		<input type="text" id="sample6_extraAddress" placeholder="참고항목">

		<input type="button" value="Register" id="registerButton">
	</form>
</body>
</html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
	// id 중복검사
	var idCheck = true;
	function isIdDuplicate(){
		const idInput = document.forms['joinUp'].elements['memberId'];
		const errorMessageSpan = $("#errorMessage");
		if(idInput.value){
			$.ajax({
				url: "/isIdDuplicate",
				type: "post",
				data: { "memberId": idInput.value },
				success: function(data) {
					if(data){
						errorMessageSpan.text("존재하는 아이디입니다.");
					} else {
						if(/^[a-zA-Z0-9]{1,10}$/.test(idInput.value)){
							errorMessageSpan.text("사용가능 합니다.");
							idCheck = data;
						}else {
							errorMessageSpan.text("아이디는 10자리 이하의 숫자와 영문자로만 이뤄져야 합니다.");
						}
					}
				},
				error: function(e) {
					console.error("AJAX request failed.",e);
					alert("AJAX 요청 중 오류가 발생했습니다.");
				}
			});
		} else {
			alert("아이디를 입력하세요");
		}
	}

	var confirmationCode;
	function sendNumber(){
		const emailInput = document.forms['joinUp'].elements['mail'];
		if(idCheck){
			return alert("아이디 중복 검사를 진행해주세요")
		}
		if(emailInput.value){
			$("#mail_number").show();
			$("#sendNumberBtn").hide();
			$.ajax({
				url: "/mail",
				type: "post",
				data: { "mail": emailInput.value },
				success: function(data) {
					console.log("인증번호 발송");
					confirmationCode = data;
				},
				error: function(request, status, error) {
					console.error("AJAX request failed. Status:", status, "Error:", error);
					alert("AJAX 요청 중 오류가 발생했습니다.");
				}
			});
		} else {
			alert("이메일을 입력하세요")
		}
	}

	function confirmNumber(){
		const userEnteredCode = $("#mailConfirmNumber").val();

		if(userEnteredCode === confirmationCode){
			alert("인증되었습니다.");
			$("#mail_number").hide();
			$("#registerButton").prop("disabled", false);
		}else{
			alert("번호가 다릅니다.");
			$("#registerButton").prop("disabled", true);
		}
	}
	$(document).ready(function(){
		// 주민번호 숫자만 입력 받기
		$("#identification").on("input",function(){
			$(this).val($(this).val().replace(/[^0-9]/g, ''));
		});
		$("#registerButton").click(function(){
			const form = document.forms['joinUp'];
			const pwd= form.elements['password'];
			const pwdConfirm = form.elements['password2'];
			const identification = form.elements['identification'];
			const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,12}$/;
			const residentNumberRegex = /^[0-9]{13}$/;
			if(pwd.value.match(passwordRegex) == null){
				return alert("비밀번호는 문자와 숫자, 특수 기호를 포함한 8-12자리 입니다.");
			}
			if(pwd.value !== pwdConfirm.value){
				return alert("비밀번호와 비밀번호 확인이 맞지 않습니다.");
			}

			$.ajax({
				type: "POST",
				url: "/joinUp",
				data: $("#joinUp").serialize(),
				success: function(response) {
					if (response === "joinUpFailed") {
						alert("회원가입에 실패하였습니다. 다시 시도해주세요.");
					} else if (response === "emailVerificationFailed") {
						alert("이메일 인증에 실패했습니다. 다시 시도해주세요.");
					} else {
						window.location.href = "/completed";
					}
				},
				error: function(error) {
					// Handle errors during the AJAX request
					console.error("Error:", error);
				}
			});
		})
	});

	function sample6_execDaumPostcode() {
		new daum.Postcode({
			oncomplete: function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

				// 각 주소의 노출 규칙에 따라 주소를 조합한다.
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수

				//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}

				// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				if(data.userSelectedType === 'R'){
					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if(data.buildingName !== '' && data.apartment === 'Y'){
						extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
					}
					// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if(extraAddr !== ''){
						extraAddr = ' (' + extraAddr + ')';
					}
					// 조합된 참고항목을 해당 필드에 넣는다.
					document.getElementById("sample6_extraAddress").value = extraAddr;

				} else {
					document.getElementById("sample6_extraAddress").value = '';
				}

				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('sample6_postcode').value = data.zonecode;
				document.getElementById("sample6_address").value = addr;
				// 커서를 상세주소 필드로 이동한다.
				document.getElementById("sample6_detailAddress").focus();
			}
		}).open();
	}
</script>