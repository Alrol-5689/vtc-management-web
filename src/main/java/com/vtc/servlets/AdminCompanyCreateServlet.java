package com.vtc.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vtc.model.company.Company;
import com.vtc.model.user.Administrator;
import com.vtc.service.AdministratorService;
import com.vtc.service.CompanyService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminCompanyCreateServlet", urlPatterns = {"/admin/companies/create"})
public class AdminCompanyCreateServlet extends HttpServlet {

    private final CompanyService companyService = new CompanyService();
    private final AdministratorService adminService = new AdministratorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }
        req.getRequestDispatcher("/admin/companies/create.jsp").forward(req, resp);
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

        String name = trim(req.getParameter("name"));
        boolean assignMe = "on".equals(req.getParameter("assignMe"));

        List<String> errors = new ArrayList<>();
        if (name == null || name.isEmpty()) errors.add("Company name is required");

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/admin/companies/create.jsp").forward(req, resp);
            return;
        }

        Company c = new Company();
        c.setName(name);
        if (assignMe) {
            Administrator a = adminService.getAdministrator(adminId);
            c.setAdministrator(a);
        }
        try {
            companyService.createCompany(c);
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } catch (IOException ex) {
            errors.add("Could not create company");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/admin/companies/create.jsp").forward(req, resp);
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
}

