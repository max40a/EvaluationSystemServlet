<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question 1</title>
    <link rel="stylesheet" href="../Style/tests1Style.css" type="text/css">
</head>
<body>
<h1>Question 1</h1>
<p>As primitive types available in Java?</p>
<form method="post" name="q1" action="/test-service">
    <input type="radio" name="q1" value="1" checked>5<br>
    <input type="radio" name="q1" value="2">2<br>
    <input type="radio" name="q1" value="3">8<br>
    <input type="radio" name="q1" value="4">233<br>
    <input type="radio" name="q1" value="5">As much as need<br>
    <input type="submit" value="Send Answer">
</form>
<form method action="Test2.jsp">
    <input type="submit" value="Next Question">
</form>
</body>
</html>
