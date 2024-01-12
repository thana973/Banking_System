<%@ page import="com.bankingsystem.banking.account.DTO.AccountResponse" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form method="POST">
    <%
        ArrayList<AccountResponse> accounts =  (ArrayList<AccountResponse>) request.getSession().getAttribute("accounts");
        for (AccountResponse account : accounts){
    %>
    <input type="radio" name="accountNum" value = <%=account.getAccountNum()%>>계좌번호 : <%=account.getAccountNum()%> </input>
    <br>
    <%}%>
    <br>
    출금 금액 : <input type="text" name="amount" />
    <button type="button" onclick="submitFrom(this.form)">출금</button>
</form>

<script>
    var doubleSummitFlag = false // 중복 POST 요청 방지 플래그

    function submitFrom(form){
        if (form.amount.value == ""){
            alert("금액을 입력해주세요.")
            return false;
        }

        if(doubleSummitFlag){
            alert("요청 중 입니다.")
            return false;
        }else {
            doubleSummitFlag = true;
            form.action = "./withdraw"
            form.submit();
        }
    }
</script>

</body>
</html>