package com.vtc.servlets;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.model.agreement.CollectiveAgreement;
import com.vtc.service.CollectiveAgreementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminAgreementDetailServlet", urlPatterns = {"/admin/agreements/view"})
public class AdminAgreementDetailServlet extends HttpServlet {

    private final CollectiveAgreementService agreementService = new CollectiveAgreementService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }

        String idStr = req.getParameter("id");
        Long id = null;
        try { id = Long.valueOf(idStr); } catch (Exception ex) {}
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/agreements");
            return;
        }

        CollectiveAgreement agreement = agreementService.getAgreementWithDetails(id);
        if (agreement == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/agreements");
            return;
        }

        AgreementAnnex currentAnnex = agreementService.findCurrentAnnex(id);
        List<AgreementAnnex> annexes = agreement.getAnnexes();
        if (annexes != null) annexes.sort(Comparator.comparing(AgreementAnnex::getStartDate));

        req.setAttribute("agreement", agreement);
        req.setAttribute("currentAnnex", currentAnnex);
        req.setAttribute("annexes", annexes);
        req.getRequestDispatcher("/admin/agreements/detail.jsp").forward(req, resp);
    }
}
