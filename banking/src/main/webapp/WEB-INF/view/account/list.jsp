<%@ page import="com.bankingsystem.banking.account.DTO.AccountResponse" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html lang="ko">
<head>
    <title>Title</title>
</head>
<body>

<table border="1">
    <caption>계좌 정보</caption>>
    <th>계좌번호</th><th>은행</th><th>잔액</th><th>잠금여부</th><th>상품이름</th>
<%
    ArrayList<AccountResponse> accounts =  (ArrayList<AccountResponse>) request.getSession().getAttribute("accounts");
    for (AccountResponse account : accounts){
%>
<tr>
    <td><%=account.getAccountNum()%></td>
    <td><%=account.getBankName()%></td>
    <td><%=account.getBalance()%></td>
    <td><%=account.isLocked()%></td>
    <td><%=account.getProductName()%></td>
</tr>
<%
    }
%>
</table>
<br>
<a href="./deposit"><button>입금하기</button></a>
<a href="./withdraw"><button>출금하기</button></a>
<a href="../transaction/transfer"><button>송금하기</button></a>
<a href="./create"><button>계좌생성</button></a>
<a href="./delete"><button>계좌삭제</button></a>

</body>
</html>