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

		input[type="submit"] {
			background-color: #4caf50;
			color: #fff;
			cursor: pointer;
		}

		input[type="submit"]:hover {
			background-color: #45a049;
		}
	</style>
</head>
<body>
	<form action="/joinUp" method="post">
		<!-- Name -->
		<label for="name">Name:</label>
		<input type="text" id="name" name="name"><br>

		<%--email--%>
		<label for="mail">mail:</label>
		<input type="text" id="mail" name="main"><br>
		<button type="button" id="sendBtn" name="sendBtn" onclick="sendNumber()">인증번호</button>

		<div id="mail_number" name="mail_number" style="display: none">
			<input type="text" name="mailConfirmNumber" id="mailConfirmNumber" style="width:250px; margin-top: -10px" placeholder="인증번호 입력">
			<button type="button" name="confirmBtn" id="confirmBtn" onclick="confirmNumber()">이메일 인증</button>
		</div>

		<!-- Password -->
		<label for="password">Password:</label>
		<input type="password" id="password" name="password"><br>

		<!-- Confirm Password -->
		<label for="password2">Confirm Password:</label>
		<input type="password" id="password2" name="password2"><br>

		<!-- Identification (주민번호) -->
		<label for="identification">Identification:</label>
		<input type="text" id="identification" name="identification"><br>

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

		<input type="submit" value="Register" id="registerButton" disabled>
	</form>
</body>
</html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
	// 세션에 저장된 메시지를 확인하고 alert 창에 표시
	var emailVerificationMessage = "<%= session.getAttribute("emailVerificationMessage") %>";
	if (emailVerificationMessage && emailVerificationMessage !== "null") {
		alert(emailVerificationMessage);
	}

	var confirmationCode;
	function sendNumber(){
		$("#mail_number").show();
		$("#sendBtn").hide();
		$.ajax({
			url: "/mail",
			type: "post",
			data: { "mail": $("#mail").val() },
			success: function(data) {
				console.log("인증번호 발송");
				confirmationCode = data;
			},
			error: function(request, status, error) {
				console.error("AJAX request failed. Status:", status, "Error:", error);
				alert("AJAX 요청 중 오류가 발생했습니다.");
			}
		});
	}

	function confirmNumber(){
		var userEnteredCode = $("#mailConfirmNumber").val();

		if(userEnteredCode === confirmationCode){
			alert("인증되었습니다.");
			$("#registerButton").prop("disabled", false);
			$("#mail_number").hide();
		}else{
			alert("번호가 다릅니다.");
			$("#registerButton").prop("disabled", true);
		}
	}

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