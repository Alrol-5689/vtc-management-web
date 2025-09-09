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

@WebServlet(name = "AdminCreateServlet", urlPatterns = {"/admins/create"})
public class AdminCreateServlet extends HttpServlet {

    private final AdministratorService adminService = new AdministratorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Policy: allow self-service only if there are zero admins; otherwise require logged admin
        boolean hasAny = !adminService.listAdministrators().isEmpty();
        HttpSession session = req.getSession(false);
        boolean loggedAdmin = session != null && session.getAttribute("adminId") != null;
        if (hasAny && !loggedAdmin) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }
        req.setAttribute("selfService", !hasAny);
        req.getRequestDispatcher("/auth/adminCreate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        boolean hasAny = !adminService.listAdministrators().isEmpty();
        HttpSession session = req.getSession(false);
        boolean loggedAdmin = session != null && session.getAttribute("adminId") != null;
        if (hasAny && !loggedAdmin) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }

        String name = trim(req.getParameter("name"));
        String password = trim(req.getParameter("password"));
        String confirm = trim(req.getParameter("confirm"));

        List<String> errors = new ArrayList<>();
        if (name == null || name.isEmpty()) errors.add("Username is required");
        if (password == null || password.isEmpty()) errors.add("Password is required");
        if (confirm == null || confirm.isEmpty()) errors.add("Confirm password is required");
        if (password != null && confirm != null && !password.equals(confirm)) errors.add("Passwords do not match");

        if (name != null && adminService.findByUsername(name) != null) {
            errors.add("Username already exists");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("selfService", !hasAny);
            req.getRequestDispatcher("/auth/adminCreate.jsp").forward(req, resp);
            return;
        }

        Administrator a = new Administrator();
        a.setName(name);
        // Hash when creating if BCrypt available
        String hash = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
        a.setPassword(hash);
        try {
            adminService.createAdministrator(a);
        } catch (Exception ex) {
            errors.add("Could not create administrator");
            req.setAttribute("errors", errors);
            req.setAttribute("selfService", !hasAny);
            req.getRequestDispatcher("/auth/adminCreate.jsp").forward(req, resp);
            return;
        }

        // If self-service bootstrap, log in automatically
        if (!hasAny) {
            HttpSession s = req.getSession(true);
            s.setAttribute("adminId", a.getId());
            s.setAttribute("adminName", a.getName());
            s.setMaxInactiveInterval(30 * 60);
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
}

