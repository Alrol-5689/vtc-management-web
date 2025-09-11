<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add bonus</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/driver/home.css">
  <style>.form{max-width:720px;margin:30px auto;background:#fff;padding:20px;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,.1);} .form div{margin-bottom:12px;}</style>
</head>
<body>
  <c:if test="${empty sessionScope.adminId}"><c:redirect url="${pageContext.request.contextPath}/admins/login"/></c:if>

  <div class="form">
    <h2>Add bonus</h2>

    <c:if test="${not empty errors}">
      <div class="errors" style="color:#b00;">
        <ul><c:forEach var="e" items="${errors}"><li>${e}</li></c:forEach></ul>
      </div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/bonus/create">
      <input type="hidden" name="annexId" value="${param.annexId}"/>
      <input type="hidden" name="agreementId" value="${param.agreementId}"/>
      <div>
        <label for="type">Type</label><br/>
        <select id="type" name="type" required>
          <option value="QUALITY">Quality</option>
          <option value="LONGEVITY">Longevity</option>
          <option value="UNIFORM">Uniform</option>
          <option value="OTHER">Other</option>
        </select>
      </div>
      <div>
        <label for="annualAmount">Annual amount (€)</label><br/>
        <input id="annualAmount" name="annualAmount" type="number" step="0.01" min="0" required/>
      </div>
      <div>
        <label for="requiredMonths">Required months (longevity only)</label><br/>
        <input id="requiredMonths" name="requiredMonths" type="number" min="0" placeholder="0"/>
      </div>
      <div>
        <span>Payment months:</span><br/>
        <c:set var="months" value="Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec"/>
        <c:forEach var="i" begin="0" end="11">
          <label style="margin-right:8px;"><input type="checkbox" name="paidMonths" value="${i}" checked/> ${fn:split(months,',')[i]}</label>
        </c:forEach>
      </div>
      <div>
        <label for="notes">Notes</label><br/>
        <textarea id="notes" name="notes" rows="2" style="width:100%;"></textarea>
      </div>
      <div>
        <button type="submit" class="btn" style="padding:8px 12px; background:#0a66c2; color:#fff; border-radius:6px; border:none;">Save</button>
        <a class="btn" href="${pageContext.request.contextPath}/admin/agreements/view?id=${param.agreementId}" style="margin-left:8px;">Cancel</a>
      </div>
    </form>
  </div>
</body>
</html>
