<%@ page import="com.bankingsystem.banking.account.repository.domain.Account" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="./deposit" method="POST">
    <%
        List<Account> accounts =  (List<Account>) request.getSession().getAttribute("accounts");
        for (Account account : accounts){
    %>
    <input type="radio" name="accountNum" value = <%=account.getAccountNum()%> onclick="getCheckboxValue(event)">계좌번호 : <%=account.getAccountNum()%> </input>
    <br>
    <%}%>
    <br>
    입금 금액 : <input type="text" name="amount" />
    <button type="submit">입금</button>
</form>

</body>
</html>