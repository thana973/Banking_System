<%@ page import="com.bankingsystem.banking.account.repository.domain.Account" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<form action="./delete" method="POST">
    <%
        List<Account> accounts =  (List<Account>) request.getSession().getAttribute("accounts");
        for (Account account : accounts){
    %>
        <input type="checkbox" name="accountNum" value = <%=account.getAccountNum()%> onclick="getCheckboxValue(event)">계좌번호 : <%=account.getAccountNum()%> </input>
    <br>
    <%}%>
    <br>

    <button type="submit">삭제</button>
</form>


</body>
</html>