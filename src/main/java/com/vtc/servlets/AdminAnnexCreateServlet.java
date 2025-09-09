package com.vtc.servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet(name = "AdminAnnexCreateServlet", urlPatterns = {"/admin/annex/create"})
public class AdminAnnexCreateServlet extends HttpServlet {

    private final CollectiveAgreementService agreementService = new CollectiveAgreementService();
    private final AdministratorService adminService = new AdministratorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }
        req.getRequestDispatcher("/admin/annex/create.jsp").forward(req, resp);
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

        String agreementIdStr = trim(req.getParameter("agreementId"));
        String name = trim(req.getParameter("name"));
        String start = trim(req.getParameter("start"));
        String salaryStr = trim(req.getParameter("salary"));
        String weeklyHoursStr = trim(req.getParameter("weeklyHours"));
        String auxMinutesStr = trim(req.getParameter("auxMinutes"));

        List<String> errors = new ArrayList<>();
        Long agreementId = null;
        try { agreementId = Long.valueOf(agreementIdStr); } catch (Exception ex) { errors.add("Invalid agreement Id"); }
        if (name == null || name.isEmpty()) errors.add("Name required");

        LocalDate startDate = null;
        try { startDate = LocalDate.parse(start); } catch (Exception ex) { errors.add("Invalid start date"); }

        Double salary = null;
        try { salary = salaryStr == null || salaryStr.isEmpty() ? null : Double.valueOf(salaryStr); }
        catch (NumberFormatException ex) { errors.add("Invalid salary"); }

        Duration weekly = null;
        try { weekly = weeklyHoursStr == null || weeklyHoursStr.isEmpty() ? null : Duration.ofHours(Long.parseLong(weeklyHoursStr)); }
        catch (NumberFormatException ex) { errors.add("Invalid weekly hours"); }

        Duration aux = null;
        try { aux = auxMinutesStr == null || auxMinutesStr.isEmpty() ? null : Duration.ofMinutes(Long.parseLong(auxMinutesStr)); }
        catch (NumberFormatException ex) { errors.add("Invalid auxiliary minutes"); }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/admin/annex/create.jsp").forward(req, resp);
            return;
        }

        AgreementAnnex annex = new AgreementAnnex();
        annex.setName(name);
        annex.setStartDate(startDate);
        if (salary != null) annex.setAnnualSalary(salary);
        if (weekly != null) annex.setFullTimeWeeklyHours(weekly);
        if (aux != null) annex.setAuxiliaryTasks(aux);

        try {
            agreementService.addAnnex(admin, agreementId, annex);
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } catch (IllegalArgumentException ex) {
            errors.add(ex.getMessage());
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/admin/annex/create.jsp").forward(req, resp);
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
}

