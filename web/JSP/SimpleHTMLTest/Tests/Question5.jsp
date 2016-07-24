<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="dbBean" scope="page" class="servlets.testsService.testBean.TestBean"/>
<html>
<head>
    <title>Question 5</title>
    <link rel="stylesheet" href="../../../Style/SimplyHTMLTest.css" type="text/css">
</head>
<body>
<jsp:include page="../Header.jsp" flush="true"/>
<p>5. Which element is used to define a logical separation of content on the page or elements </p>
<form method="post" name="q5" action="/test-service">
    <input type="hidden" name="firstPage" value="false">
    <input type="radio" name="q" value="1" checked>div<br>
    <input type="radio" name="q" value="2">span<br>
    <input type="radio" name="q" value="3">hgroup<br>
    <input type="radio" name="q" value="4">pre<br>
    <input type="radio" name="q" value="5">aside<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>