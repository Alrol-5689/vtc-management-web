<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Crear convenio + 1er anejo</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
  <style>.form { max-width: 720px; margin: 30px auto; background:#fff; padding:20px; border-radius:8px; box-shadow:0 0 10px rgba(0,0,0,.1);} .form fieldset{margin-bottom:16px;border:1px solid #ddd;padding:16px;border-radius:8px;} .form div{margin-bottom:12px;}</style>
</head>
<body>
  <c:if test="${empty sessionScope.adminId}">
    <c:redirect url="${pageContext.request.contextPath}/admins/login"/>
  </c:if>

  <div class="form">
    <h2>Crear convenio con primer anejo</h2>

    <c:if test="${not empty errors}">
      <div class="errors" style="color:#b00;">
        <ul>
          <c:forEach var="e" items="${errors}"><li>${e}</li></c:forEach>
        </ul>
      </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/agreements/create">
      <fieldset>
        <legend>Convenio</legend>
        <div>
          <label for="agreementName">Nombre</label><br/>
          <input id="agreementName" name="agreementName" type="text" required style="width:100%;"/>
        </div>
        <div>
          <label for="agreementStart">Fecha inicio</label><br/>
          <input id="agreementStart" name="agreementStart" type="date" required/>
        </div>
        <div>
          <label for="agreementNotes">Notas</label><br/>
          <textarea id="agreementNotes" name="agreementNotes" rows="3" style="width:100%;"></textarea>
        </div>
      </fieldset>

      <fieldset>
        <legend>Primer anejo</legend>
        <div>
          <label for="annexName">Nombre</label><br/>
          <input id="annexName" name="annexName" type="text" required style="width:100%;"/>
        </div>
        <div>
          <label for="annexStart">Fecha inicio</label><br/>
          <input id="annexStart" name="annexStart" type="date" required/>
        </div>
        <div>
          <label for="annexSalary">Salario anual (€)</label><br/>
          <input id="annexSalary" name="annexSalary" type="number" step="0.01" min="0"/>
        </div>
        <div>
          <label for="annexWeeklyHours">Horas semanales (jornada completa)</label><br/>
          <input id="annexWeeklyHours" name="annexWeeklyHours" type="number" min="0" placeholder="ej. 40"/>
        </div>
        <div>
          <label for="annexAuxMinutes">Minutos tareas auxiliares</label><br/>
          <input id="annexAuxMinutes" name="annexAuxMinutes" type="number" min="0" placeholder="ej. 30"/>
        </div>
      </fieldset>

      <div>
        <button type="submit" class="btn" style="padding:8px 12px; background:#0a66c2; color:#fff; border-radius:6px; border:none;">Crear</button>
        <a class="btn" href="${pageContext.request.contextPath}/admin/home" style="margin-left:8px;">Cancelar</a>
      </div>
    </form>
  </div>
</body>
</html>

