<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Driver</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/create.css">
</head>
<body>
<c:if test="${not empty errors}">
  <div style="color:#b00020;">
    <ul>
      <c:forEach var="e" items="${errors}">
        <li><c:out value="${e}"/></li>
      </c:forEach>
    </ul>
  </div>
</c:if>
<div class="form-container">
    <div style="display: flex; justify-content: space-between; align-items: center;">
            <a href="${pageContext.request.contextPath}/index.jsp" class="back-link">← Back</a>
            <a href="${pageContext.request.contextPath}/auth/driverLogin.jsp" class="btn-link">Sign in →</a>
    </div>
    <h2>Create Driver</h2>
    <form method="post" action="${pageContext.request.contextPath}/drivers/create" accept-charset="UTF-8">
        <div class="form-group">
            <label for="username">Username*</label><br/>
            <input id="username" name="username" required maxlength="50" value="${param.username != null ? param.username : ''}"/>
        </div>
        <div class="form-group">
            <label for="password">Password*</label><br/>
            <input id="password" name="password" type="password" required maxlength="255"/>
        </div>
        <div class="form-group">
            <label for="confirm_password">Repeat Password*</label><br/>
            <input id="confirm_password" name="confirm_password" type="password" required maxlength="255"/>
        </div>
        <div class="form-group">
            <label for="nationalId">National ID (DNI/NIE)*</label><br/>
            <input id="nationalId" name="nationalId" required maxlength="20" value="${param.nationalId != null ? param.nationalId : ''}"/>
        </div>
        <div class="form-group">
            <label for="firstName">First Name*</label><br/>
            <input id="firstName" name="firstName" required maxlength="80" value="${param.firstName != null ? param.firstName : ''}"/>
        </div>
        <div class="form-group">
            <label for="lastName">Last Name</label><br/>
            <input id="lastName" name="lastName" maxlength="80" value="${param.lastName != null ? param.lastName : ''}"/>
        </div>
        <div class="form-group">
            <label for="secondLastName">Second Last Name</label><br/>
            <input id="secondLastName" name="secondLastName" maxlength="80" value="${param.secondLastName != null ? param.secondLastName : ''}"/>
        </div>
        <div class="form-group">
            <label for="phone">Phone*</label><br/>
            <input id="phone" name="phone" required maxlength="20" value="${param.phone != null ? param.phone : ''}"/>
        </div>
        <div class="form-group">
            <label for="email">Email*</label><br/>
            <input id="email" name="email" required type="email" maxlength="120" value="${param.email != null ? param.email : ''}"/>
        </div>
        <div class="form-group">
            <button type="submit">Create</button>
        </div>
    </form>
</div>
<script>
    (function () {
        const form = document.querySelector("form");
        const pwd = document.getElementById("password");
        const confirm = document.getElementById("confirm_password");

        form.addEventListener("submit", function(e) {
            if (pwd.value !== confirm.value) {
                alert("Passwords do not match.");
                e.preventDefault();
            }
        });
    })();
</script>
</body>
</html>