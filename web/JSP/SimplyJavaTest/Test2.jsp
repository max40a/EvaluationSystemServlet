<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String testName = request.getParameter("testName");
%>
<html>
<head>
    <title>Question 2</title>
    <link rel="stylesheet" href="../Style/tests1Style.css" type="text/css">
</head>
<body>
<h1>Question 2</h1>
<p>What is the maximum size of type <b>int</b></p>
<form method="post" name="q2" action="/test-service">
    <input type="hidden" name="nextPage" value="JSP/SimplyJavaTest/Test3.jsp?testName=<%=testName%>">
    <input type="radio" name="q" value="1" checked>2<sup>16</sup><br>
    <input type="radio" name="q" value="2">2<sup>32</sup><br>
    <input type="radio" name="q" value="3">2<sup>64</sup><br>
    <input type="radio" name="q" value="4">lost of<br>
    <input type="radio" name="q" value="5">As much as need<br>
    <input type="submit" value="Send Answer">
</form>
</body>
</html>