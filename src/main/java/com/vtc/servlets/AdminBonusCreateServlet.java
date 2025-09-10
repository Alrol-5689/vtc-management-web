package com.vtc.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.model.agreement.AgreementBonus;
import com.vtc.model.agreement.AgreementBonus.BonusType;
import com.vtc.service.AgreementBonusService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminBonusCreateServlet", urlPatterns = {"/admin/bonus/create"})
public class AdminBonusCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AgreementBonusService bonusService = new AgreementBonusService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }
        req.getRequestDispatcher("/admin/bonus/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }

        String annexIdStr = req.getParameter("annexId");
        String typeStr = req.getParameter("type");
        String amountStr = req.getParameter("annualAmount");
        String notes = req.getParameter("notes");
        String requiredMonthsStr = req.getParameter("requiredMonths");
        String[] paidMonths = req.getParameterValues("paidMonths");

        List<String> errors = new ArrayList<>();
        Long annexId = null;
        try {
            annexId = Long.valueOf(annexIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            errors.add("annexId inválido");
        }
        BonusType type = null;
        try {
            type = BonusType.valueOf(typeStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            errors.add("tipo inválido");
        }
        Double annualAmount = null;
        try {
            annualAmount = Double.valueOf(amountStr);
        } catch (NumberFormatException | NullPointerException e) {
            errors.add("importe inválido");
        }

        int requiredMonths = 0;
        if (requiredMonthsStr != null && !requiredMonthsStr.isEmpty()) {
            try {
                requiredMonths = Integer.parseInt(requiredMonthsStr);
            } catch (NumberFormatException e) {
                errors.add("meses requeridos inválido");
            }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/admin/bonus/create.jsp").forward(req, resp);
            return;
        }

        AgreementAnnex annex = new AgreementAnnex();
        annex.setId(annexId);

        AgreementBonus b = new AgreementBonus();
        b.setAnnex(annex);
        b.setType(type);
        b.setAnnualAmount(annualAmount);
        b.setNotes(notes);
        if (type == BonusType.LONGEVITY) b.setRequiredMonths(requiredMonths);
        boolean[] months = new boolean[12];
        if (paidMonths != null) {
            for (String m : paidMonths) {
                try {
                    int idx = Integer.parseInt(m); // 0..11
                    if (idx >= 0 && idx < 12) months[idx] = true;
                } catch (NumberFormatException ignore) {}
            }
        }
        b.setPaidInMonth(months);

        try {
            bonusService.createBonus(b);
        } catch (RuntimeException ex) {
            errors.add("No se pudo crear el bonus");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/admin/bonus/create.jsp").forward(req, resp);
            return;
        }

        // Redirect back to agreement detail; for that, we need the agreement id; we can accept it as param
        String agreementId = req.getParameter("agreementId");
        if (agreementId != null) {
            resp.sendRedirect(req.getContextPath() + "/admin/agreements/view?id=" + agreementId);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        }
    }
}
