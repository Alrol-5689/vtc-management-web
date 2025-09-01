<%@ page contentType="text/html; charset=UTF-8" %>
<%-- <%@ taglib uri="https://jakarta.ee/jstl/core" prefix="c" %> --%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Drivers</title>
    <meta charset="UTF-8"/>
</head>
<body>
<h1>Drivers</h1>

<p><a href="${pageContext.request.contextPath}/drivers/create">+ New Driver</a></p>

<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>National ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Second Last Name</th>
        <th>Phone</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="d" items="${drivers}">
        <tr>
            <td><c:out value="${d.id}"/></td>
            <td><c:out value="${d.username}"/></td>
            <td><c:out value="${d.nationalId}"/></td>
            <td><c:out value="${d.firstName}"/></td>
            <td><c:out value="${d.lastName}"/></td>
            <td><c:out value="${d.secondLastName}"/></td>
            <td><c:out value="${d.phone}"/></td>
            <td><c:out value="${d.email}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty drivers}">
    <p>No drivers yet.</p>
</c:if>
</body>
</html>