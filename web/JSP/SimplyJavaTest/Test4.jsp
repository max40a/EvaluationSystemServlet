<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question 4</title>
    <link rel="stylesheet" href="/Style/SimplyJavaTest.css" type="text/css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<h1>Question 4</h1>
<p>Is it possible ti modify the array in Java after initialization?</p>
<form method="post" name="q4" action="/test-service">
    <input type="hidden" name="nextPage" value="JSP/SimplyJavaTest/Test5.jsp">
    <input type="hidden" name="firstPage" value="false">
    <input type="radio" name="q" value="1" checked>no<br>
    <input type="radio" name="q" value="2">yes<br>
    <input type="radio" name="q" value="3">maybe<br>
    <input type="radio" name="q" value="4">I do not understand what do you mean<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>