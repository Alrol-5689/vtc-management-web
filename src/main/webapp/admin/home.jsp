<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<c:if test="${empty sessionScope.adminId}">
  <c:redirect url="${pageContext.request.contextPath}/admins/login"/>
  </c:if>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin Home</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
</head>
<body>
<div class="layout">

  <nav class="sidebar">
    <h2>Admin</h2>
    <ul>
      <li><a href="${pageContext.request.contextPath}/admin/home">Dashboard</a></li>
      <li><a href="${pageContext.request.contextPath}/admin/agreements">Convenios</a></li>
      <li><a href="${pageContext.request.contextPath}/admin/agreements/create">Crear convenio</a></li>
      <li><a href="${pageContext.request.contextPath}/admin/companies/create">Crear empresa</a></li>
      <li><a href="${pageContext.request.contextPath}/admins/logout">Logout</a></li>
    </ul>
  </nav>

  <div class="main-content">
    <header class="top-header" style="display:flex;align-items:center;justify-content:space-between;gap:16px;">
      <div style="display:flex;align-items:center;gap:12px;">
        <h1 style="margin:0;">Hola, ${sessionScope.adminName}</h1>
      </div>
      <div class="avatar" style="width:36px;height:36px;border-radius:50%;background:#444;color:#fff;display:flex;align-items:center;justify-content:center;font-weight:600;cursor:pointer;" title="Account">
        <c:set var="initials" value="${fn:toUpperCase(fn:substring(sessionScope.adminName,0,1))}" />
        <span>${initials}</span>
      </div>
    </header>

    <section class="cards">
      <div class="card" style="grid-column: 1 / -1; text-align:left;">
        <h2 style="margin-top:0;">Convenio vigente</h2>
        <c:choose>
          <c:when test="${not empty currentAgreement}">
            <p>
              <a href="${pageContext.request.contextPath}/admin/agreements/view?id=${currentAgreement.id}" style="text-decoration:none; color:inherit;">
                <strong>${currentAgreement.name}</strong>
              </a><br/>
              Desde ${currentAgreement.startDate}
              <c:if test="${not empty currentAgreement.endDate}">hasta ${currentAgreement.endDate}</c:if>
            </p>
            <c:if test="${not empty currentAnnex}">
              <p>Anejo vigente: <strong>${currentAnnex.name}</strong> (salario anual: ${currentAnnex.annualSalary})</p>
            </c:if>
            <div style="display:flex; gap:12px; flex-wrap:wrap;">
              <a class="btn" style="padding:8px 12px; background:#0a66c2; color:#fff; border-radius:6px; text-decoration:none;" href="${pageContext.request.contextPath}/admin/annex/create?agreementId=${currentAgreement.id}">Añadir anejo</a>
              <a class="btn" style="padding:8px 12px; background:#444; color:#fff; border-radius:6px; text-decoration:none;" href="#" onclick="alert('Implementar cierre de convenio en un siguiente paso'); return false;">Cerrar convenio</a>
            </div>
          </c:when>
          <c:otherwise>
            <p>No hay convenio activo.</p>
            <a class="btn" style="padding:8px 12px; background:#0a66c2; color:#fff; border-radius:6px; text-decoration:none;" href="${pageContext.request.contextPath}/admin/agreements/create">Crear convenio</a>
          </c:otherwise>
        </c:choose>
      </div>

      <div class="card">
        <a href="${pageContext.request.contextPath}/admin/agreements/create">Crear convenio</a>
      </div>
      <div class="card">
        <a href="${pageContext.request.contextPath}/admin/companies/create">Crear empresa</a>
      </div>
    </section>
  </div>
</div>
</body>
</html>
