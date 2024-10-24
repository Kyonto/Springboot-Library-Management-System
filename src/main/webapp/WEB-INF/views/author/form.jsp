<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Author Form</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <h1>${author.id == null ? 'Create' : 'Edit'} Author</h1>
    <form:form action="${author.id == null ? '/authors' : '/authors/update'}" method="post" modelAttribute="author">
        <form:hidden path="id"/>
        <div>
            <label for="name">Name:</label>
            <form:input path="name" required="true"/>
        </div>
        <div>
            <label for="email">Email:</label>
            <form:input path="email" type="email" required="true"/>
        </div>
        <button type="submit">Save</button>
    </form:form>
</body>
</html>
