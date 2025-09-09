<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<c:if test="${empty sessionScope.adminId}">
  <c:redirect url="${pageContext.request.contextPath}/admins/login"/>
</c:if>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Convenio: ${agreement.name}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
</head>
<body>
<div class="layout">
  <nav class="sidebar">
    <h2>Admin</h2>
    <ul>
      <li><a href="${pageContext.request.contextPath}/admin/home">Dashboard</a></li>
      <li><a href="${pageContext.request.contextPath}/admin/agreements">Convenios</a></li>
      <li><a href="${pageContext.request.contextPath}/admins/logout">Logout</a></li>
    </ul>
  </nav>

  <div class="main-content">
    <header class="top-header">
      <h1>Convenio: ${agreement.name}</h1>
      <p>Desde ${agreement.startDate} <c:if test="${not empty agreement.endDate}">hasta ${agreement.endDate}</c:if></p>
    </header>

    <section class="cards">
      <div class="card" style="grid-column: 1 / -1; text-align:left;">
        <h3 style="margin:0 0 8px;">Anexos</h3>
        <c:if test="${empty annexes}">
          <p>Este convenio no tiene anexos.</p>
        </c:if>
        <c:forEach var="x" items="${annexes}">
          <div style="border:1px solid #ddd; border-radius:8px; padding:12px; margin-bottom:10px; background:#fff;">
            <div style="display:flex;justify-content:space-between;align-items:center;">
              <div>
                <strong>${x.name}</strong>
                <span style="color:#666;"> — ${x.startDate}<c:if test="${not empty x.endDate}"> a ${x.endDate}</c:if></span>
                <c:if test="${currentAnnex != null && currentAnnex.id == x.id}">
                  <span style="margin-left:8px; padding:2px 6px; background:#e6f4ea; color:#176b3a; border-radius:6px; font-size:.85em;">Vigente</span>
                </c:if>
              </div>
              <a class="btn" href="${pageContext.request.contextPath}/admin/annex/create?agreementId=${agreement.id}">Nuevo anejo</a>
            </div>

            <!-- Bonuses dentro del anejo -->
            <c:choose>
              <c:when test="${empty x.bonuses}">
                <p style="margin:10px 0; color:#666;">Sin bonuses.</p>
              </c:when>
              <c:otherwise>
                <ul style="margin:10px 0 0 18px;">
                  <c:forEach var="b" items="${x.bonuses}">
                    <li>
                      <strong>${b.type}</strong> — anual: ${b.annualAmount}
                      <c:if test="${b.type == 'LONGEVITY'}"> (meses requeridos: ${b.requiredMonths})</c:if>
                    </li>
                  </c:forEach>
                </ul>
              </c:otherwise>
            </c:choose>

            <div style="margin-top:10px;">
              <a class="btn" href="${pageContext.request.contextPath}/admin/bonus/create?annexId=${x.id}&agreementId=${agreement.id}">Añadir bonus</a>
            </div>
          </div>
        </c:forEach>
      </div>
    </section>
  </div>
</div>
</body>
</html>
