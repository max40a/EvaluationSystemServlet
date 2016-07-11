<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question 2</title>
    <link rel="stylesheet" href="../Style/tests1Style.css" type="text/css">
</head>
<body>
<h1>Question 2</h1>
<p>What is the maximum size of type <b>int</b></p>
<form method="post" name="q2" action="/test-service">
    <input type="radio" name="q2" value="1" checked>2<sup>16</sup><br>
    <input type="radio" name="q2" value="2">2<sup>32</sup><br>
    <input type="radio" name="q2" value="3">2<sup>64</sup><br>
    <input type="radio" name="q2" value="4">lost of<br>
    <input type="radio" name="q2" value="5">As much as need<br>
    <input type="submit" value="Send Answer">
</form>
<form action="Test3.jsp">
    <input type="submit" value="Next Question">
</form>
</body>
</html>
