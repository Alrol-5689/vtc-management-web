package com.vtc.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vtc.model.user.Administrator;
import com.vtc.service.AdministratorService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminLoginServlet", urlPatterns = {"/admins/login"})
public class AdminLoginServlet extends HttpServlet {

    private final AdministratorService adminService = new AdministratorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("adminId") != null) {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
            return;
        }
        req.getRequestDispatcher("/auth/adminLogin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = trim(req.getParameter("username"));
        String password = trim(req.getParameter("password"));

        List<String> errors = new ArrayList<>();
        if (isBlank(username) || isBlank(password)) {
            errors.add("Username and password are required.");
        }

        Administrator admin = null;
        if (errors.isEmpty()) {
            admin = adminService.authenticate(username, password);
            if (admin == null) errors.add("Invalid credentials.");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/auth/adminLogin.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("adminId", admin.getId());
        session.setAttribute("adminName", admin.getName());
        session.setMaxInactiveInterval(30 * 60);

        resp.sendRedirect(req.getContextPath() + "/admin/home");
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}

