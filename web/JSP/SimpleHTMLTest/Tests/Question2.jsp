<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String testName = request.getParameter("testName");
%>
<html>
<head>
    <title>Question 2</title>
    <link rel="stylesheet" href="../../../Style/SimplyHTMLTest.css" type="text/css">
</head>
<body>
<jsp:include page="../Header.jsp" flush="true"/>
<p>2. How many heading levels exist in HTML ?</p>
<form method="post" name="q2" action="/test-service">
    <input type="hidden" name="nextPage" value="JSP/SimpleHTMLTest/Tests/Question3.jsp?testName=<%=testName%>">
    <input type="radio" name="q" value="1" checked>1<br>
    <input type="radio" name="q" value="2">as much as needs<br>
    <input type="radio" name="q" value="3">5<br>
    <input type="radio" name="q" value="4">7<br>
    <input type="radio" name="q" value="5">6<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>