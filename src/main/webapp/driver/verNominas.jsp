<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty sessionScope.driverId}">
  <c:redirect url="${pageContext.request.contextPath}/drivers/login"/>
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Payslips</title>
</head>
<body>
  <h1>Payslips for ${sessionScope.activeCompanyName}</h1>
  <p><a href="${pageContext.request.contextPath}/driver/home.jsp">← Back to Home</a></p>

  <c:if test="${empty requestScope.payslips}">
    <p>No payslips found.</p>
  </c:if>

  <c:if test="${not empty requestScope.payslips}">
    <table border="1" cellspacing="0" cellpadding="6">
      <tr>
        <th>Month</th>
        <th>Base Salary</th>
        <th>Commission</th>
        <th>Quality bonus</th>
        <th>Longevity bonus</th>
        <th>Other bonuses</th>
      </tr>
      <c:forEach var="p" items="${payslips}">
        <tr>
          <td>${p.month}</td>
          <td>${p.salarioBase}</td>
          <td>${p.comision}</td>
          <td>${p.plusCalidad}</td>
          <td>${p.plusPermanencia}</td>
          <td>${p.otrosPluses}</td>
        </tr>
      </c:forEach>
    </table>
  </c:if>
</body>
</html>
