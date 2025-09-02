<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!-- Si no hay sesión de driver, redirige al login correcto -->
<c:if test="${empty sessionScope.driverId}">
  <c:redirect url="${pageContext.request.contextPath}/drivers/login"/>
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Driver Home</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
</head>
<body>
<div class="layout">

  <!-- SIDEBAR -->
  <nav class="sidebar">
    <h2>Menu</h2>
    <ul>
      <li><a href="registroFacturacion.jsp">Daily Log</a></li>
      <li><a href="verComisiones.jsp">Commission Forecast</a></li>
      <li><a href="verNominas.jsp">Payslips</a></li>
      <li><a href="${pageContext.request.contextPath}/index.jsp">Logout</a></li>
      <!-- cuando tengas logout servlet:
      <li><a href="${pageContext.request.contextPath}/drivers/logout">Logout</a></li>
      -->
    </ul>
  </nav>

  <!-- MAIN CONTENT -->
  <div class="main-content">
    <!-- TOP HEADER -->
    <header class="top-header">
      <h1>Welcome, ${sessionScope.driverUsername} 👋</h1>
    </header>

    <!-- DASHBOARD CARDS -->
    <section class="cards">
      <div class="card">
        <a href="registroFacturacion.jsp">Register Daily Billing</a>
      </div>
      <div class="card">
        <a href="verComisiones.jsp">View Commission Forecast</a>
      </div>
      <div class="card">
        <a href="verNominas.jsp">View Payslips</a>
      </div>
      <div class="card">
        <form action="${pageContext.request.contextPath}/SvEliminarConductor" method="post">
          <button type="submit">Delete account</button>
        </form>
      </div>
    </section>

    <!-- WORKED DAYS EXAMPLE -->
    <section class="driver-days">
      <h2>Worked days this month:</h2>
      <c:if test="${not empty driverDays}">
        <ul>
          <c:forEach var="dia" items="${driverDays}">
            <li>${dia.fecha}</li>
          </c:forEach>
        </ul>
      </c:if>
      <c:if test="${empty driverDays}">
        <p>No records yet.</p>
      </c:if>
    </section>

  </div>
</div>
</body>
</html>