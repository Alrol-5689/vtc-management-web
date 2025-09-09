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
  <title>Create Contract</title>
</head>
<body>
  <h1>Create Contract</h1>
  <p><a href="${pageContext.request.contextPath}/driver/home.jsp">← Back to Home</a></p>

  <c:if test="${not empty errors}">
    <ul style="color:#b00;">
      <c:forEach var="e" items="${errors}"><li>${e}</li></c:forEach>
    </ul>
  </c:if>

  <form action="${pageContext.request.contextPath}/contracts/create" method="post">
    <fieldset>
      <legend>Company</legend>
      <div>
        <label for="companyId">Select existing:</label>
        <select name="companyId" id="companyId">
          <option value="">-- Choose --</option>
          <c:forEach var="c" items="${companies}">
            <option value="${c.id}">${c.name}</option>
          </c:forEach>
        </select>
      </div>
      <div>
        <label>Or create new:</label>
        <input type="text" name="companyName" placeholder="New company name">
      </div>
    </fieldset>
    <div>
      <label>Start date:</label>
      <input type="date" name="startDate" required>
    </div>
    <div>
      <label>End date (optional):</label>
      <input type="date" name="endDate">
    </div>
    <button type="submit">Create</button>
  </form>
</body>
</html>

