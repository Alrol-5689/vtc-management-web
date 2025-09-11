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
  <title>Seniority — ${annex.name}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
  <style>
    .table { width:100%; border-collapse: collapse; }
    .table th, .table td { border:1px solid #ddd; padding:6px 8px; }
    .table th { background:#f7f7f7; }
    .actions { display:flex; gap:8px; align-items:center; }
    .hint { color:#666; font-size:.85em; margin-top:4px; }
  </style>
  <script>
    function formatMonthsLabel(m) {
      const months = parseInt(m, 10);
      if (isNaN(months) || months < 0) return "";
      const yrs = Math.floor(months / 12);
      const mos = months % 12;
      if (mos === 0 && yrs > 0) return yrs + ' year' + (yrs === 1 ? '' : 's');
      if (yrs === 0) return mos + ' month' + (mos === 1 ? '' : 's');
      return yrs + ' year' + (yrs === 1 ? '' : 's') + ' ' + mos + ' month' + (mos === 1 ? '' : 's');
    }

    function addRow() {
      const tbody = document.getElementById('rows');
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>
          <input type="number" name="months" min="0" step="1" style="width:100%"/>
          <div class="hint"></div>
        </td>
        <td><input type="number" name="percent" step="0.01" style="width:100%"/></td>
        <td><button type="button" onclick=\"this.closest('tr').remove()\">Remove</button></td>
      `;
      tbody.appendChild(tr);
      const input = tr.querySelector('input[name="months"]');
      const hint = tr.querySelector('.hint');
      hint.textContent = formatMonthsLabel(input.value);
    }

    document.addEventListener('input', function(e) {
      if (e.target && e.target.name === 'months') {
        const cell = e.target.closest('td');
        const hint = cell && cell.querySelector('.hint');
        if (hint) hint.textContent = formatMonthsLabel(e.target.value);
      }
    });

    document.addEventListener('DOMContentLoaded', function() {
      document.querySelectorAll('#rows tr').forEach(function(tr){
        const input = tr.querySelector('input[name="months"]');
        const hint = tr.querySelector('.hint');
        if (input && hint) hint.textContent = formatMonthsLabel(input.value);
      });
    });
  </script>
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
      <h1>Annex seniority: ${annex.name}</h1>
      <p>Configure breakpoints (months → %).</p>
    </header>

    <section class="cards">
      <div class="card" style="grid-column: 1 / -1; text-align:left;">
        <c:if test="${not empty errors}">
          <div style="background:#fdecea;color:#b12718;border:1px solid #f5c2c0;padding:8px 12px;border-radius:6px;margin-bottom:10px;">
            <ul style="margin:0 0 0 18px;">
              <c:forEach var="e" items="${errors}"><li>${e}</li></c:forEach>
            </ul>
          </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/admin/annex/seniority">
          <input type="hidden" name="annexId" value="${annex.id}"/>
          <input type="hidden" name="agreementId" value="${agreementId}"/>

          <table class="table">
            <thead>
              <tr>
                <th>Minimum months</th>
                <th>% increase</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody id="rows">
              <c:forEach var="e" items="${annex.seniorityBreakpoints}">
                <tr>
                  <td>
                    <input type="number" name="months" min="0" step="1" value="${e.key}" style="width:100%"/>
                    <c:set var="totalMonths" value="${e.key}"/>
                    <c:set var="yrsInt" value="${(totalMonths - (totalMonths mod 12)) div 12}"/>
                    <c:set var="mosInt" value="${totalMonths mod 12}"/>
                    <div class="hint">
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
                    </div>
                  </td>
                  <td><input type="number" name="percent" step="0.01" value="${e.value}" style="width:100%"/></td>
                  <td><button type="button" onclick="this.closest('tr').remove()">Remove</button></td>
                </tr>
              </c:forEach>
              <!-- initial empty row for convenience -->
              <tr>
                <td>
                  <input type="number" name="months" min="0" step="1" style="width:100%"/>
                  <div class="hint"></div>
                </td>
                <td><input type="number" name="percent" step="0.01" style="width:100%"/></td>
                <td><button type="button" onclick="this.closest('tr').remove()">Remove</button></td>
              </tr>
            </tbody>
          </table>

          <div class="actions" style="margin-top:10px;">
            <button type="button" class="btn" onclick="addRow()">Add row</button>
            <button type="submit" class="btn" style="background:#0a66c2; color:#fff;">Save</button>
            <a class="btn" href="${pageContext.request.contextPath}/admin/agreements/view?id=${agreementId}">Cancel</a>
          </div>
        </form>
      </div>
    </section>
  </div>
</div>
</body>
</html>
