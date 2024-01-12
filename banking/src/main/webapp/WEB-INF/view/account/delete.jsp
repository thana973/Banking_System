<%@ page import="com.bankingsystem.banking.account.DTO.AccountResponse" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE>
<html lang="ko">
<head>
    <title>Title</title>
</head>
<body>


<form action="./delete" method="POST">
    <%
        ArrayList<AccountResponse> accounts =  (ArrayList<AccountResponse>) request.getSession().getAttribute("accounts");
        for (AccountResponse account : accounts){
    %>
        <input type="checkbox" name="accountNum" value = <%=account.getAccountNum()%>>계좌번호 : <%=account.getAccountNum()%> </input>
    <br>
    <%}%>
    <br>

    <button type="submit">삭제</button>
</form>


</body>
</html>