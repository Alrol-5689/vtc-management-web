<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Crear administrador</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/driverLogin.css">
  <style>
    .form { max-width: 420px; margin: 24px auto; background:#fff; padding:20px; border-radius:8px; box-shadow:0 0 10px rgba(0,0,0,.08);}
    .form .form-group{margin-bottom:12px;}
  </style>
</head>
<body>
  <h1><c:out value="${selfService ? 'Inicializar administrador' : 'Crear administrador'}"/></h1>

  <c:if test="${not empty errors}">
    <div class="errors" role="alert" aria-live="polite">
      <ul>
        <c:forEach var="e" items="${errors}"><li><c:out value="${e}"/></li></c:forEach>
      </ul>
    </div>
  </c:if>

  <div class="form">
    <form method="post" action="${pageContext.request.contextPath}/admins/create" accept-charset="UTF-8" autocomplete="on">
      <div class="form-group">
        <label for="name">Username</label>
        <input id="name" name="name" type="text" required maxlength="50" autofocus/>
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input id="password" name="password" type="password" required maxlength="255"/>
      </div>
      <div class="form-group">
        <label for="confirm">Confirm password</label>
        <input id="confirm" name="confirm" type="password" required maxlength="255"/>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn primary">Crear</button>
        <a class="btn link" href="${pageContext.request.contextPath}/admins/login">← Back</a>
      </div>
    </form>
  </div>
</body>
</html>

