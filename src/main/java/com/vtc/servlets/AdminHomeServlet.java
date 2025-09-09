package com.vtc.servlets;

import java.io.IOException;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.model.agreement.CollectiveAgreement;
import com.vtc.service.CollectiveAgreementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminHomeServlet", urlPatterns = {"/admin/home"})
public class AdminHomeServlet extends HttpServlet {

    private final CollectiveAgreementService agreementService = new CollectiveAgreementService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }

        CollectiveAgreement current = null;
        AgreementAnnex currentAnnex = null;
        try {
            current = agreementService.findCurrentAgreement();
            if (current != null) {
                currentAnnex = agreementService.findCurrentAnnex(current.getId());
            }
        } catch (Exception ignore) { }

        req.setAttribute("currentAgreement", current);
        req.setAttribute("currentAnnex", currentAnnex);
        req.getRequestDispatcher("/admin/home.jsp").forward(req, resp);
    }
}

