package com.vtc.servlets;

import java.io.IOException;
import java.util.List;

import com.vtc.model.payslip.Payslip;
import com.vtc.service.PayslipService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "DriverPayslipsServlet", urlPatterns = {"/driver/payslips"})
public class DriverPayslipsServlet extends HttpServlet {
    private final PayslipService payslipService = new PayslipService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("driverId") == null) {
            resp.sendRedirect(req.getContextPath() + "/drivers/login");
            return;
        }
        Long driverId = (Long) session.getAttribute("driverId");
        Long companyId = (Long) session.getAttribute("activeCompanyId");

        List<Payslip> payslips = java.util.Collections.emptyList();
        if (companyId != null) {
            payslips = payslipService.findByDriverAndCompany(driverId, companyId);
        }
        req.setAttribute("payslips", payslips);
        req.getRequestDispatcher("/driver/verNominas.jsp").forward(req, resp);
    }
}

