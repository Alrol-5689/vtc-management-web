<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session.getAttribute("usuario_logueado") == null) {
        response.sendRedirect("loginConductor.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Inicio - Conductor VTC</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">

</head>
<body>
<div class="layout">

    <!-- SIDEBAR -->
    <nav class="sidebar">
        <h2>Menú</h2>
        <ul>
            <li><a href="registroFacturacion.jsp">Registro Diario</a></li>
            <li><a href="verComisiones.jsp">Pronóstico Comisiones</a></li>
            <li><a href="verNominas.jsp">Nóminas</a></li>
            <li><a href="${pageContext.request.contextPath}/index.jsp">Cerrar Sesión</a></li>
        </ul>
    </nav>

    <!-- MAIN CONTENT -->
    <div class="main-content">
        <!-- TOP HEADER -->
        <header class="top-header">
            <h1>Bienvenido, ${usuario_logueado.nombre} 👋</h1>
        </header>

        <!-- DASHBOARD CARDS -->
        <section class="cards">
            <div class="card">
                <a href="registroFacturacion.jsp">Registrar Facturación Diaria</a>
            </div>
            <div class="card">
                <a href="verComisiones.jsp">Ver Pronóstico de Comisiones</a>
            </div>
            <div class="card">
                <a href="verNominas.jsp">Ver Nóminas</a>
            </div>
            <div class="card">
                <form action="${pageContext.request.contextPath}/SvEliminarConductor" method="post">
                    <button type="submit">Eliminar cuenta</button>
                </form>
            </div>
        </section>

        <!-- DÍAS TRABAJADOS DEL MES -->
        <section class="driver-days">
            <h2>Días trabajados este mes:</h2>
            <c:if test="${not empty driverDays}">
                <ul>
                    <c:forEach var="dia" items="${driverDays}">
                        <li>${dia.fecha}</li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty driverDays}">
                <p>No hay registros aún.</p>
            </c:if>
        </section>

    </div>

</div>
</body>
</html>