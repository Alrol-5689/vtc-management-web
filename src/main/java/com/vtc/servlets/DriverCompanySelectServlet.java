package com.vtc.servlets;

import java.io.IOException;

import com.vtc.model.company.Company;
import com.vtc.service.CompanyService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "DriverCompanySelectServlet", urlPatterns = {"/driver/company/select"})
public class DriverCompanySelectServlet extends HttpServlet {
    private final CompanyService companyService = new CompanyService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("driverId") == null) {
            resp.sendRedirect(req.getContextPath() + "/drivers/login");
            return;
        }
        String companyIdStr = req.getParameter("companyId");
        if (companyIdStr != null) {
            try {
                Long cid = Long.valueOf(companyIdStr);
                Company c = companyService.getCompany(cid);
                if (c != null) {
                    session.setAttribute("activeCompanyId", c.getId());
                    session.setAttribute("activeCompanyName", c.getName());
                }
            } catch (NumberFormatException ignore) {}
        }
        resp.sendRedirect(req.getContextPath() + "/driver/home.jsp");
    }
}

