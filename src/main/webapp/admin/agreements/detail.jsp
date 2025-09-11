<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<c:if test="${empty sessionScope.adminId}">
  <c:redirect url="${pageContext.request.contextPath}/admins/login"/>
</c:if>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Agreement: ${agreement.name}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
</head>
<body>
<div class="layout">
  <nav class="sidebar">
    <h2>Admin</h2>
    <ul>
      <li><a href="${pageContext.request.contextPath}/admin/home">Dashboard</a></li>
      <li><a href="${pageContext.request.contextPath}/admin/agreements">Agreements</a></li>
      <li><a href="${pageContext.request.contextPath}/admins/logout">Logout</a></li>
    </ul>
  </nav>

  <div class="main-content">
    <header class="top-header">
      <h1>Agreement: ${agreement.name}</h1>
      <p>Since ${agreement.startDate} <c:if test="${not empty agreement.endDate}">until ${agreement.endDate}</c:if></p>
    </header>

    <section class="cards">
      <div class="card" style="grid-column: 1 / -1; text-align:left;">
        <div style="display:flex;align-items:center;justify-content:space-between;gap:12px;margin:0 0 8px;">
          <h3 style="margin:0;">Annexes</h3>
          <a class="btn" href="${pageContext.request.contextPath}/admin/annex/create?agreementId=${agreement.id}">New annex</a>
        </div>
        <c:if test="${empty annexes}">
          <p>This agreement has no annexes.</p>
        </c:if>
        <c:forEach var="x" items="${annexes}">
          <div style="border:1px solid #ddd; border-radius:8px; padding:12px; margin-bottom:10px; background:#fff;">
            <div style="display:flex;justify-content:space-between;align-items:center;">
              <div>
                <strong>${x.name}</strong>
                <span style="color:#666;"> — ${x.startDate}<c:if test="${not empty x.endDate}"> to ${x.endDate}</c:if></span>
                <c:if test="${currentAnnex != null && currentAnnex.id == x.id}">
                  <span style="margin-left:8px; padding:2px 6px; background:#e6f4ea; color:#176b3a; border-radius:6px; font-size:.85em;">Active</span>
                </c:if>
              </div>
            </div>

            <!-- Annex info -->
            <div style="margin-top:10px; display:grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap:8px;">
              <div>
                <div style="color:#666; font-size:.85em;">Annual salary</div>
                <div><fmt:formatNumber value="${x.annualSalary}" type="currency"/></div>
              </div>
              <c:if test="${not empty x.fullTimeWeeklyHours}">
                <div>
                  <div style="color:#666; font-size:.85em;">Full-time weekly hours</div>
                  <c:set var="ftMin" value="${x.fullTimeWeeklyHours.toMinutes()}"/>
                  <c:set var="ftH" value="${ftMin div 60}"/>
                  <c:set var="ftM" value="${ftMin mod 60}"/>
                  <div>
                    <c:choose>
                      <c:when test="${ftM == 0}">
                        <fmt:formatNumber value="${ftH}" maxFractionDigits="0"/>h
                      </c:when>
                      <c:otherwise>
                        <fmt:formatNumber value="${ftH}" maxFractionDigits="0"/>h ${ftM}m
                      </c:otherwise>
                    </c:choose>
                  </div>
                </div>
              </c:if>
              <c:if test="${not empty x.auxiliaryTasks}">
                <div>
                  <div style="color:#666; font-size:.85em;">Auxiliary tasks</div>
                  <c:set var="auxMin" value="${x.auxiliaryTasks.toMinutes()}"/>
                  <c:set var="auxH" value="${auxMin div 60}"/>
                  <c:set var="auxM" value="${auxMin mod 60}"/>
                  <div>
                    <c:choose>
                      <c:when test="${auxH == 0}">
                        ${auxM}m
                      </c:when>
                      <c:when test="${auxM == 0}">
                        <fmt:formatNumber value="${auxH}" maxFractionDigits="0"/>h
                      </c:when>
                      <c:otherwise>
                        <fmt:formatNumber value="${auxH}" maxFractionDigits="0"/>h ${auxM}m
                      </c:otherwise>
                    </c:choose>
                  </div>
                </div>
              </c:if>
              <c:if test="${not empty x.notes}">
                <div style="grid-column: 1 / -1;">
                  <div style="color:#666; font-size:.85em;">Notes</div>
                  <div>${x.notes}</div>
                </div>
              </c:if>
              <c:choose>
                <c:when test="${not empty x.seniorityBreakpoints}">
                  <div style="grid-column: 1 / -1;">
                    <div style="display:flex; align-items:center; gap:8px;">
                      <div style="color:#666; font-size:.85em;">Seniority (years/months → %)</div>
                      <a class="btn" href="${pageContext.request.contextPath}/admin/annex/seniority?annexId=${x.id}&agreementId=${agreement.id}">Edit seniority</a>
                    </div>
                    <ul style="margin:6px 0 0 18px;">
                        <c:forEach var="e" items="${x.seniorityBreakpoints}">
                          <c:set var="totalMonths" value="${e.key}"/>
                          <c:set var="yrsInt" value="${(totalMonths - (totalMonths mod 12)) div 12}"/>
                          <c:set var="mosInt" value="${totalMonths mod 12}"/>
                          <li>
                            <c:choose>
                              <c:when test="${mosInt == 0 && yrsInt > 0}">
                                <fmt:formatNumber value="${yrsInt}" maxFractionDigits="0"/> year${yrsInt == 1 ? '' : 's'}
                              </c:when>
                              <c:when test="${yrsInt == 0}">
                                <fmt:formatNumber value="${mosInt}" maxFractionDigits="0"/> month${mosInt == 1 ? '' : 's'}
                              </c:when>
                              <c:otherwise>
                                <fmt:formatNumber value="${yrsInt}" maxFractionDigits="0"/> year${yrsInt == 1 ? '' : 's'} <fmt:formatNumber value="${mosInt}" maxFractionDigits="0"/> month${mosInt == 1 ? '' : 's'}
                              </c:otherwise>
                            </c:choose>
                            → <fmt:formatNumber value="${e.value}" minFractionDigits="0" maxFractionDigits="2"/>%
                            → <fmt:formatNumber value="${x.annualSalary * (e.value / 100.0)}" type="currency"/>
                          </li>
                        </c:forEach>
                    </ul>
                  </div>
                </c:when>
                <c:otherwise>
                  <div style="grid-column: 1 / -1;">
                    <div style="display:flex; align-items:center; gap:8px;">
                      <div style="color:#666; font-size:.85em;">Seniority (years/months → %)</div>
                      <a class="btn" href="${pageContext.request.contextPath}/admin/annex/seniority?annexId=${x.id}&agreementId=${agreement.id}">Configure seniority</a>
                    </div>
                    <div style="color:#666; margin-top:6px;">No seniority policy configured.</div>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>

            <!-- Bonuses within the annex -->
            <c:choose>
              <c:when test="${empty x.bonuses}">
                <p style="margin:10px 0; color:#666;">No bonuses.</p>
              </c:when>
              <c:otherwise>
                <ul style="margin:10px 0 0 18px;">
                  <c:forEach var="b" items="${x.bonuses}">
                    <li>
                      <strong>${b.type}</strong> — annual: ${b.annualAmount} 
                      <c:if test="${b.type == 'LONGEVITY'}"> (required months: ${b.requiredMonths})</c:if>
                    </li>
                  </c:forEach>
                </ul>
              </c:otherwise>
            </c:choose>

            <div style="margin-top:10px;">
              <a class="btn" href="${pageContext.request.contextPath}/admin/bonus/create?annexId=${x.id}&agreementId=${agreement.id}">Add bonus</a>
            </div>
          </div>
        </c:forEach>
      </div>
    </section>
  </div>
</div>
</body>
</html>
