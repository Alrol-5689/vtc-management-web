package com.vtc.servlets;

import java.io.IOException;
import java.util.List;

import com.vtc.model.agreement.CollectiveAgreement;
import com.vtc.service.CollectiveAgreementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminAgreementsListServlet", urlPatterns = {"/admin/agreements"})
public class AdminAgreementsListServlet extends HttpServlet {

    private final CollectiveAgreementService agreementService = new CollectiveAgreementService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }

        List<CollectiveAgreement> list = agreementService.listAgreements();
        req.setAttribute("agreements", list);
        req.getRequestDispatcher("/admin/agreements/list.jsp").forward(req, resp);
    }
}

