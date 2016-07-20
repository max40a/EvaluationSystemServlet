<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String testName = request.getParameter("testName");
%>
<html>
<head>
    <title>Question 1</title>
    <link rel="stylesheet" href="../Style/tests1Style.css" type="text/css">
</head>
<body>
<h1>Question 1</h1>
<p>As primitive types available in Java?</p>
<form method="post" name="q1" action="/test-service">
    <input type="hidden" name="nextPage" value="JSP/SimplyJavaTest/Test2.jsp?testName=<%=testName%>">
    <input type="radio" name="q" value="1" checked>5<br>
    <input type="radio" name="q" value="2">2<br>
    <input type="radio" name="q" value="3">8<br>
    <input type="radio" name="q" value="4">233<br>
    <input type="radio" name="q" value="5">As much as need<br>
    <input type="submit" value="Send Answer">
</form>
</body>
</html>