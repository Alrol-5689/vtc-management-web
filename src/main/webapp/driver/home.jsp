<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

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
      <li><a href="${pageContext.request.contextPath}/drivers/logout">Logout</a></li>
    </ul>
  </nav>

  <!-- MAIN CONTENT -->
  <div class="main-content">
    <!-- TOP HEADER -->
    <header class="top-header" style="display:flex;align-items:center;justify-content:space-between;gap:16px;">
      <div style="display:flex;align-items:center;gap:12px;">
        <h1 style="margin:0;">Welcome, ${sessionScope.driverUsername} 👋</h1>
        <c:choose>
          <c:when test="${empty sessionScope.companies}">
            <span style="color:#b00; font-weight:600;">No contracts yet</span>
            <a href="${pageContext.request.contextPath}/contracts/create" class="btn" style="padding:6px 10px; background:#0a66c2; color:#fff; border-radius:6px; text-decoration:none;">Create contract</a>
          </c:when>
          <c:otherwise>
            <form action="${pageContext.request.contextPath}/driver/company/select" method="post" style="margin:0;">
              <label for="companyId">Company:</label>
              <select name="companyId" id="companyId" onchange="this.form.submit()">
                <c:forEach var="c" items="${sessionScope.companies}">
                  <option value="${c.id}" ${c.id == sessionScope.activeCompanyId ? 'selected' : ''}>${c.name}</option>
                </c:forEach>
              </select>
            </form>
          </c:otherwise>
        </c:choose>
      </div>
      <div class="avatar" style="width:36px;height:36px;border-radius:50%;background:#444;color:#fff;display:flex;align-items:center;justify-content:center;font-weight:600;cursor:pointer;" title="Account">
        <c:set var="initials" value="${fn:toUpperCase(fn:substring(sessionScope.driverUsername,0,1))}" />
        <span>${initials}</span>
      </div>
    </header>

    <!-- DASHBOARD CARDS -->
    <section class="cards">
      <div class="card">
        <a href="${pageContext.request.contextPath}/daily-logs/create">Register Daily Billing</a>
      </div>
      <div class="card">
        <a href="verComisiones.jsp">View Commission Forecast</a>
      </div>
      <div class="card">
        <a href="${pageContext.request.contextPath}/driver/payslips">View Payslips</a>
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
