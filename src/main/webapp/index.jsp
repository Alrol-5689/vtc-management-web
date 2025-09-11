<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Welcome</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
  <!--<link rel="stylesheet" href="css/estilos.css">-->
</head>
<body>
    <h1>Mi VTC controller</h1>
    <br>
    <div class="login-container">
        <div class="button-group">
        <a href="${pageContext.request.contextPath}/auth/driverLogin.jsp" class="btn">Sign in</a>
        <a href="${pageContext.request.contextPath}/drivers/create" class="btn">New driver</a>
        </div>
    </div>
    <footer>
        <a href="${pageContext.request.contextPath}/auth/adminLogin.jsp">Admin access</a>
    </footer>
</body>
</html>
