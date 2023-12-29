<%@ page import="com.bankingsystem.banking.account.repository.domain.Account" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table border="1">
    <th>계좌번호</th><th>은행</th><th>잔액</th><th>잠금여부</th><th>사용자 이름</th>
<%
    List<Account> accounts =  (List<Account>) request.getSession().getAttribute("accounts");
    for (Account account : accounts){
%>
<tr>
    <td><%=account.getAccountNum()%></td>
    <td><%=account.getBankName()%></td>
    <td><%=account.getBalance()%></td>
    <td><%=account.isLocked()%></td>
    <td><%=account.getMember().getName()%></td>
</tr>
<%
    }
%>
</table>
<br>
<a href="deposit"><button>입금하기</button></a>
<a href="withdraw"><button>출금하기</button></a>
<a href="transfer"><button>송금하기</button></a>
<a href="create"><button>계좌생성</button></a>
<a href="delete"><button>계좌삭제</button></a>

</body>
</html>