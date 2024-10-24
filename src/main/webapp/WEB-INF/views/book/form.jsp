<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Form</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <h1>${book.id == null ? 'Create' : 'Edit'} Book</h1>
    <form:form action="${book.id == null ? '/books' : '/books/update'}" method="post" modelAttribute="book">
        <form:hidden path="id"/>
        <div>
            <label for="title">Title:</label>
            <form:input path="title" required="true"/>
        </div>
        <div>
            <label for="isbn">ISBN:</label>
            <form:input path="isbn" required="true"/>
        </div>
        <div>
            <label for="author">Author:</label>
            <form:select path="author.id" required="true">
                <form:options items="${authors}" itemValue="id" itemLabel="name"/>
            </form:select>
        </div>
        <button type="submit">Save</button>
    </form:form>
</body>
</html>
