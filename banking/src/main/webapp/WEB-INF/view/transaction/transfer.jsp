<%@ page import="com.bankingsystem.banking.account.repository.domain.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bankingsystem.banking.account.DTO.AccountResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="./transfer" method="POST">
    <%
        List<AccountResponse> accounts =  (List<AccountResponse>) request.getSession().getAttribute("accounts");
        for (AccountResponse account : accounts){
    %>
    <input type="radio" name="fromAccountNum" value = <%=account.getAccountNum()%>>계좌번호 : <%=account.getAccountNum()%> </input>
    <br>
    <%}%>
    <br>
    송금 금액 : <input type="text" name="amount"/>
    <br>
    상대 계좌번호 : <input type="text" name="toAccountNum"/>
    <button type="submit">송금</button>
</form>

</body>
</html>