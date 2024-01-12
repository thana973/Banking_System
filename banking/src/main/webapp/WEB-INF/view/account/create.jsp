<%@ page import="com.bankingsystem.banking.bankname.DTO.BankNameResponse" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE>
<html lang="ko">
<head>
    <title>Title</title>
</head>

<body>

<form action="./create" method="POST">
    <label for="bankId">은행명:</label>>
    <select name="bankId" id="bankId">
        <%
            ArrayList<BankNameResponse> bankNameList = (ArrayList<BankNameResponse>) request.getAttribute("bankNameList");
            for(int i = 0; i < bankNameList.size(); i++){
                BankNameResponse bankNameResponse = bankNameList.get(i);
                String bankName = bankNameResponse.getName();
                String bankNameId = bankNameResponse.getId();
        %>
            <option value=<%=bankNameId%>><%=bankName%></option>
        <%}%>
    </select>
    <br>
    상품명(1/2): <input type="text" name="productId"/>
    <br>
    <button type="submit">전송</button>
</form>



</body>
</html>