<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty sessionScope.driverId}">
  <c:redirect url="${pageContext.request.contextPath}/drivers/login"/>
  <c:remove var="errors" scope="request"/>
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Create Daily Log</title>
</head>
<body>
  <h1>Create Daily Log</h1>
  <p>Active company: <strong>${sessionScope.activeCompanyName}</strong></p>

  <c:if test="${not empty errors}">
    <ul style="color:#b00;">
      <c:forEach var="e" items="${errors}"><li>${e}</li></c:forEach>
    </ul>
  </c:if>

  <form action="${pageContext.request.contextPath}/daily-logs/create" method="post">
    <div>
      <label>Appendix ID:</label>
      <input type="number" name="appendixId" required>
    </div>
    <div>
      <label>Date:</label>
      <input type="date" name="date" required>
    </div>
    <div>
      <label>Connection (HH:mm or minutes):</label>
      <input type="text" name="connection" placeholder="e.g., 07:30 or 450">
    </div>
    <div>
      <label>Presence (HH:mm or minutes):</label>
      <input type="text" name="presence" placeholder="e.g., 04:00 or 240">
    </div>
    <div>
      <label>Auxiliary Tasks (HH:mm or minutes):</label>
      <input type="text" name="auxiliaryTasks" placeholder="e.g., 01:00 or 60">
    </div>
    <div>
      <label>Billing Amount (€):</label>
      <input type="number" step="0.01" name="billingAmount">
    </div>
    <button type="submit">Save</button>
    <a href="${pageContext.request.contextPath}/driver/home.jsp">Cancel</a>
  </form>
</body>
</html>

