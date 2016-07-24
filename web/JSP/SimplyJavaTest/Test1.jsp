<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="dbBean" scope="page" class="servlets.testsService.testBean.TestBean"/>
<%
    String testName = request.getParameter("testName");
    int testId = dbBean.getTestId(testName);
%>
<html>
<head>
    <title>Question 1</title>
    <link rel="stylesheet" href="/Style/SimplyJavaTest.css" type="text/css">
</head>
<body>
<jsp:include page="Header.jsp"/>
<h1>Question 1</h1>
<p>As primitive types available in Java?</p>
<form method="post" name="q1" action="/test-service">
    <input type="hidden" name="nextPage" value="JSP/SimplyJavaTest/Test2.jsp">
    <input type="hidden" name="testId" value="<%=testId%>">
    <input type="hidden" name="firstPage" value="true">
    <input type="radio" name="q" value="1" checked>5<br>
    <input type="radio" name="q" value="2">2<br>
    <input type="radio" name="q" value="3">8<br>
    <input type="radio" name="q" value="4">233<br>
    <input type="radio" name="q" value="5">As much as need<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>