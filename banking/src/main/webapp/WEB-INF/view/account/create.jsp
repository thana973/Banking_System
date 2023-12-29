<%@ page import="com.bankingsystem.banking.account.repository.domain.Account" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<body>

<form action="./create" method="POST">
    은행명(A은행/B은행): <input type="text" name="bankName" />
    <br>
    상품명(1/2): <input type="text" name="productId"/>
    <br>
    <button type="submit">전송</button>
</form>
</body>
</html>