<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty sessionScope.adminId}">
  <c:redirect url="${pageContext.request.contextPath}/admins/login"/>
</c:if>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Convenios</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
</head>
<body>
<div class="layout">
  <nav class="sidebar">
    <h2>Admin</h2>
    <ul>
      <li><a href="${pageContext.request.contextPath}/admin/home">Dashboard</a></li>
      <li><strong>Convenios</strong></li>
      <li><a href="${pageContext.request.contextPath}/admin/agreements/create">Crear convenio</a></li>
      <li><a href="${pageContext.request.contextPath}/admins/logout">Logout</a></li>
    </ul>
  </nav>

  <div class="main-content">
    <header class="top-header"><h1>Convenios</h1></header>

    <c:if test="${empty agreements}">
      <p>No hay convenios aún.</p>
      <p><a class="btn" href="${pageContext.request.contextPath}/admin/agreements/create">Crear convenio</a></p>
    </c:if>

    <c:if test="${not empty agreements}">
      <ul>
        <c:forEach var="a" items="${agreements}">
          <li style="margin-bottom:10px;">
            <a href="${pageContext.request.contextPath}/admin/agreements/view?id=${a.id}">
              <strong>${a.name}</strong>
            </a>
            — desde ${a.startDate}
            <c:if test="${not empty a.endDate}">hasta ${a.endDate}</c:if>
          </li>
        </c:forEach>
      </ul>
    </c:if>
  </div>
</div>
</body>
</html>

