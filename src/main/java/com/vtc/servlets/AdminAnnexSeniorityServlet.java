package com.vtc.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.model.user.Administrator;
import com.vtc.service.AdministratorService;
import com.vtc.service.CollectiveAgreementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminAnnexSeniorityServlet", urlPatterns = {"/admin/annex/seniority"})
public class AdminAnnexSeniorityServlet extends HttpServlet {

    private final CollectiveAgreementService agreementService = new CollectiveAgreementService();
    private final AdministratorService adminService = new AdministratorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }

        Long annexId = parseLong(req.getParameter("annexId"));
        Long agreementId = parseLong(req.getParameter("agreementId"));
        if (annexId == null || agreementId == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/agreements");
            return;
        }

        // We need the current annex to prefill existing policy
        AgreementAnnex annex = agreementService.getAgreementWithDetails(agreementId)
            .getAnnexes().stream().filter(a -> a.getId().equals(annexId)).findFirst().orElse(null);
        if (annex == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/agreements/view?id=" + agreementId);
            return;
        }

        // Ensure the map is ordered ascending for display
        if (annex != null && annex.getSeniorityBreakpoints() != null && !(annex.getSeniorityBreakpoints() instanceof TreeMap)) {
            annex.setSeniorityBreakpoints(new TreeMap<>(annex.getSeniorityBreakpoints()));
        }
        req.setAttribute("annex", annex);
        req.setAttribute("agreementId", agreementId);
        req.getRequestDispatcher("/admin/annex/seniority.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }

        Long adminId = (Long) session.getAttribute("adminId");
        Administrator admin = adminService.getAdministrator(adminId);

        Long annexId = parseLong(req.getParameter("annexId"));
        Long agreementId = parseLong(req.getParameter("agreementId"));
        String[] monthsArr = req.getParameterValues("months");
        String[] percentArr = req.getParameterValues("percent");

        List<String> errors = new ArrayList<>();
        if (annexId == null || agreementId == null) errors.add("Parámetros inválidos");

        Map<Integer, Double> map = new LinkedHashMap<>();
        if (monthsArr != null && percentArr != null) {
            int n = Math.min(monthsArr.length, percentArr.length);
            for (int i = 0; i < n; i++) {
                String mStr = trim(monthsArr[i]);
                String pStr = trim(percentArr[i]);
                if ((mStr == null || mStr.isEmpty()) && (pStr == null || pStr.isEmpty())) continue;
                try {
                    Integer m = Integer.valueOf(mStr);
                    Double p = Double.valueOf(pStr);
                    if (m < 0) throw new NumberFormatException("negative");
                    map.put(m, p);
                } catch (Exception ex) {
                    errors.add("Fila inválida en antigüedad: meses='" + mStr + "', %='" + pStr + "'");
                }
            }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            // reload annex to re-render
            AgreementAnnex annex = agreementService.getAgreementWithDetails(agreementId)
                .getAnnexes().stream().filter(a -> a.getId().equals(annexId)).findFirst().orElse(null);
            if (annex != null && annex.getSeniorityBreakpoints() != null && !(annex.getSeniorityBreakpoints() instanceof java.util.TreeMap)) {
                annex.setSeniorityBreakpoints(new java.util.TreeMap<>(annex.getSeniorityBreakpoints()));
            }
            req.setAttribute("annex", annex);
            req.setAttribute("agreementId", agreementId);
            req.getRequestDispatcher("/admin/annex/seniority.jsp").forward(req, resp);
            return;
        }

        try {
            agreementService.updateAnnexSeniority(admin, annexId, map);
            resp.sendRedirect(req.getContextPath() + "/admin/agreements/view?id=" + agreementId);
        } catch (IllegalArgumentException ex) {
            errors.add(ex.getMessage());
            req.setAttribute("errors", errors);
            AgreementAnnex annex = agreementService.getAgreementWithDetails(agreementId)
                .getAnnexes().stream().filter(a -> a.getId().equals(annexId)).findFirst().orElse(null);
            if (annex != null && annex.getSeniorityBreakpoints() != null && !(annex.getSeniorityBreakpoints() instanceof java.util.TreeMap)) {
                annex.setSeniorityBreakpoints(new java.util.TreeMap<>(annex.getSeniorityBreakpoints()));
            }
            req.setAttribute("annex", annex);
            req.setAttribute("agreementId", agreementId);
            req.getRequestDispatcher("/admin/annex/seniority.jsp").forward(req, resp);
        }
    }

    private static Long parseLong(String s) {
        try { return s == null ? null : Long.valueOf(s); } catch (Exception ex) { return null; }
    }
    private static String trim(String s) { return s == null ? null : s.trim(); }
}
