<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Bienvenido</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
  <!--<link rel="stylesheet" href="css/estilos.css">-->
</head>
<body>
    <h1>Login</h1>
    <br>
    <div class="login-container">
        <div class="button-group">
            <form method="post" action="${pageContext.request.contextPath}/drivers/login" accept-charset="UTF-8">
                <div class="form-group">
                    <label for="username">Username</label><br/>
                    <input id="username" name="username" required maxlength="50" value="${param.username != null ? param.username : ''}"/>
                </div>
                <div class="form-group">
                    <label for="password">Password</label><br/>
                    <input id="password" name="password" type="password" required maxlength="255"/>
                </div>
            </form>
        </div>
    </div>
    <footer>
        <a href="${pageContext.request.contextPath}/index.jsp"><- Volver</a>
    </footer>
</body>
</html>