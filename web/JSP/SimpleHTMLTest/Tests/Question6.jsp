<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question 6</title>
    <link rel="stylesheet" href="../../../Style/SimplyHTMLTest.css" type="text/css">
</head>
<body>
<jsp:include page="../Header.jsp" flush="true"/>
<p>6. Does validation document having held &lt;img&gt; with no <b>alt</b> attribute set? </p>
<form method="post" name="q5" action="/test-service">
    <input type="hidden" name="firstPage" value="false">
    <input type="radio" name="q" value="1" checked>yes<br>
    <input type="radio" name="q" value="2">maybe<br>
    <input type="radio" name="q" value="3">no<br>
    <input type="radio" name="q" value="4">probably<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>