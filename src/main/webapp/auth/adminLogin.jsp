<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin Login</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/driverLogin.css">
  <style>
    h1 { margin-top: 24px; }
  </style>
  </head>
<body>
  <h1>Administrators</h1>

  <c:if test="${not empty errors}">
    <div class="errors" role="alert" aria-live="polite">
      <ul>
        <c:forEach var="e" items="${errors}">
          <li><c:out value="${e}"/></li>
        </c:forEach>
      </ul>
    </div>
  </c:if>

  <div class="login-container">
    <form method="post" action="${pageContext.request.contextPath}/admins/login" accept-charset="UTF-8" autocomplete="on" novalidate>
      <div class="form-group">
        <label for="username">Username</label>
        <input id="username" name="username" type="text" required maxlength="50" autocomplete="username" autofocus value="${param.username}"/>
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <div class="password-wrapper">
          <input id="password" name="password" type="password" required maxlength="255" autocomplete="current-password"/>
          <button type="button" class="toggle" aria-label="Show password" title="Show/Hide">👁️</button>
        </div>
      </div>

      <div class="form-actions">
        <button type="submit" class="btn primary">Sign in</button>
        <a class="btn link" href="${pageContext.request.contextPath}/index.jsp">← Back</a>
        <a class="btn link" href="${pageContext.request.contextPath}/admins/create" style="margin-left:8px;">Crear nuevo admin</a>
      </div>
    </form>
  </div>

  <script>
    (function () {
      const toggle = document.querySelector(".toggle");
      const pwd = document.getElementById("password");
      if (toggle && pwd) {
        toggle.addEventListener("click", function () {
          const isPwd = pwd.getAttribute("type") === "password";
          pwd.setAttribute("type", isPwd ? "text" : "password");
          toggle.setAttribute("aria-label", isPwd ? "Hide password" : "Show password");
        });
      }
    })();
  </script>
</body>
</html>
