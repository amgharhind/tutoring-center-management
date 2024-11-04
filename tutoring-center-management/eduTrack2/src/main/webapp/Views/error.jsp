<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
    <h2>Error Details</h2>
    <p>
    
    <%
// Check if exception is not null before calling getClass()
if (exception != null) {
    Class<?> exceptionClass = exception.getClass();
    // Do something with exceptionClass

%>
        <strong>Exception Type:</strong> <%= exception.getClass().getName() %><br/>
        <strong>Message:</strong> <%= exception.getMessage() %>
       <%} %>
    </p>
</body>
</html>