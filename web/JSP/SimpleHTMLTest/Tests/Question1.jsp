<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String testName =  "2";//request.getParameter("testName");
%>
<html>
<head>
    <title>Question 1</title>
    <link rel="stylesheet" href="../../../Style/SimplyHTMLTest.css" type="text/css">
</head>
<body>
<jsp:include page="../Header.jsp" flush="true"/>
<p>1. What is indicated by the tag paragraph in HTML ?</p>
<form method="post" name="q1" action="/test-service">
    <input type="hidden" name="nextPage" value="JSP/SimpleHTMLTest/Tests/Question2.jsp?testName=<%=testName%>">
    <input type="radio" name="q" value="1" checked>&lt;em&gt;<br>
    <input type="radio" name="q" value="2">&lt;div&gt;<br>
    <input type="radio" name="q" value="3">&lt;b&gt;<br>
    <input type="radio" name="q" value="4">&lt;p&gt;<br>
    <input type="radio" name="q" value="5">&lt;paragraph&gt;<br>
    <input class="button" type="submit" value="Send Answer">
</form>
</body>
</html>