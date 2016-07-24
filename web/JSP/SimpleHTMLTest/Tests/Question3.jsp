<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question 3</title>
    <link rel="stylesheet" href="../../../Style/SimplyHTMLTest.css" type="text/css">
</head>
<body>
<jsp:include page="../Header.jsp" flush="true"/>
<p>3. Which tag is declared a ordered list ? </p>
<form method="post" name="q3" action="/test-service">
    <input type="hidden" name="nextPage" value="JSP/SimpleHTMLTest/Tests/Question4.jsp?">
    <input type="hidden" name="firstPage" value="false">
    <input type="radio" name="q" value="1" checked>&lt;dd&gt;<br>
    <input type="radio" name="q" value="2">&lt;ol&gt;<br>
    <input type="radio" name="q" value="3">&lt;ul&gt;<br>
    <input type="radio" name="q" value="4">&lt;li&gt;<br>
    <input type="radio" name="q" value="5">&lt;dt&gt;6<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>