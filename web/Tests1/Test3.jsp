<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question 3</title>
    <link rel="stylesheet" href="../Style/tests1Style.css" type="text/css">
</head>
<body>
<h1>Question 3</h1>
<p>What class is the superclass for all classes</p>
<form method="post" name="q3" action="/test-service">
    <input type="radio" name="q3" value="1" checked>Class<br>
    <input type="radio" name="q3" value="2">SuperClass<br>
    <input type="radio" name="q3" value="3">GrandFather<br>
    <input type="radio" name="q3" value="4">Object<br>
    <input type="radio" name="q3" value="5">This class does not exist<br>
    <input type="submit" value="Send Answer">
</form>
</body>
</html>
