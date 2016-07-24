<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question 4</title>
    <link rel="stylesheet" href="../../../Style/SimplyHTMLTest.css" type="text/css">
</head>
<body>
<jsp:include page="../Header.jsp" flush="true"/>
<p>4. What character is given a link to the current document ? </p>
<form method="post" name="q4" action="/test-service">
    <input type="hidden" name="nextPage" value="JSP/SimpleHTMLTest/Tests/Question5.jsp">
    <input type="hidden" name="firstPage" value="false">
    <input type="radio" name="q" value="1" checked>#<br>
    <input type="radio" name="q" value="2">&amp;<br>
    <input type="radio" name="q" value="3">&bull;<br>
    <input type="radio" name="q" value="4">?<br>
    <input type="radio" name="q" value="5">+<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>
