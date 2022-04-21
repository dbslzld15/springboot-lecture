<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Home</title>
</head>
<body class="container-fluid">
<h1>KDT Spring App</h1>
<img src="<c:url value="/resources/img.png"/> " class="img-fluid">
<p> The time on the server ${serverTime}</p>
<h2>Customer Table</h2>
<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Name</th>
        <th scope="col">Email</th>
        <th scope="col">CreatedAt</th>
        <th scope="col">LastLoignAt</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="customer" items="${customers}">
    <tr>
        <th scope="row">${customer.customerId}</th>
        <td>${customer.name}</td>
        <td>${customer.email}</td>
        <td>${customer.createdAt}</td>
        <td>${customer.lastLoginAt}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>