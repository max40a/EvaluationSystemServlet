<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question 3</title>
    <link rel="stylesheet" href="/Style/SimplyJavaTest.css" type="text/css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<h1>Question 3</h1>
<p>What class is the superclass for all classes</p>
<form method="post" name="q3" action="/test-service">
    <input type="hidden" name="nextPage" value="/JSP/SimplyJavaTest/Test4.jsp">
    <input type="hidden" name="firstPage" value="false">
    <input type="radio" name="q" value="1" checked>Class<br>
    <input type="radio" name="q" value="2">SuperClass<br>
    <input type="radio" name="q" value="3">GrandFather<br>
    <input type="radio" name="q" value="4">Object<br>
    <input type="radio" name="q" value="5">This class does not exist<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>