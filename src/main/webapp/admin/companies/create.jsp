<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Crear Empresa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
  <style>.form { max-width: 520px; margin: 30px auto; background:#fff; padding:20px; border-radius:8px; box-shadow:0 0 10px rgba(0,0,0,.1);} .form div{margin-bottom:12px;}</style>
</head>
<body>
  <c:if test="${empty sessionScope.adminId}">
    <c:redirect url="${pageContext.request.contextPath}/admins/login"/>
  </c:if>

  <div class="form">
    <h2>Crear empresa</h2>

    <c:if test="${not empty errors}">
      <div class="errors" style="color:#b00;">
        <ul>
          <c:forEach var="e" items="${errors}"><li>${e}</li></c:forEach>
        </ul>
      </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/companies/create">
      <div>
        <label for="name">Nombre</label><br/>
        <input id="name" name="name" type="text" required maxlength="100" style="width:100%;"/>
      </div>
      <div>
        <label>
          <input type="checkbox" name="assignMe" checked/>
          Asignarme como administrador de la empresa
        </label>
      </div>
      <div>
        <button type="submit" class="btn" style="padding:8px 12px; background:#0a66c2; color:#fff; border-radius:6px; border:none;">Crear</button>
        <a class="btn" href="${pageContext.request.contextPath}/admin/home" style="margin-left:8px;">Cancelar</a>
      </div>
    </form>
  </div>
</body>
</html>

