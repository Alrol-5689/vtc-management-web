package com.vtc.servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.model.agreement.CollectiveAgreement;
import com.vtc.model.user.Administrator;
import com.vtc.service.AdministratorService;
import com.vtc.service.CollectiveAgreementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminAgreementCreateServlet", urlPatterns = {"/admin/agreements/create"})
public class AdminAgreementCreateServlet extends HttpServlet {

    private final CollectiveAgreementService agreementService = new CollectiveAgreementService();
    private final AdministratorService adminService = new AdministratorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            resp.sendRedirect(req.getContextPath() + "/admins/login");
            return;
        }
        req.getRequestDispatcher("/admin/agreements/create.jsp").forward(req, resp);
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

        String aName = trim(req.getParameter("agreementName"));
        String aNotes = trim(req.getParameter("agreementNotes"));
        String aStart = trim(req.getParameter("agreementStart"));

        String xName = trim(req.getParameter("annexName"));
        String xStart = trim(req.getParameter("annexStart"));
        String xSalary = trim(req.getParameter("annexSalary"));
        String xHours = trim(req.getParameter("annexWeeklyHours"));
        String xAuxMin = trim(req.getParameter("annexAuxMinutes"));

        List<String> errors = new ArrayList<>();

        if (aName == null || aName.isEmpty()) errors.add("Agreement name required");
        LocalDate agreementStart = null;
        try { agreementStart = LocalDate.parse(aStart); } catch (Exception ex) { errors.add("Invalid agreement start date"); }

        if (xName == null || xName.isEmpty()) errors.add("Annex name required");
        LocalDate annexStart = null;
        try { annexStart = LocalDate.parse(xStart); } catch (Exception ex) { errors.add("Invalid annex start date"); }

        Double salary = null;
        try { salary = xSalary == null || xSalary.isEmpty() ? null : Double.valueOf(xSalary); }
        catch (NumberFormatException ex) { errors.add("Invalid salary"); }

        Duration weekly = null;
        try { weekly = xHours == null || xHours.isEmpty() ? null : Duration.ofHours(Long.parseLong(xHours)); }
        catch (NumberFormatException ex) { errors.add("Invalid weekly hours"); }

        Duration aux = null;
        try { aux = xAuxMin == null || xAuxMin.isEmpty() ? null : Duration.ofMinutes(Long.parseLong(xAuxMin)); }
        catch (NumberFormatException ex) { errors.add("Invalid auxiliary minutes"); }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/admin/agreements/create.jsp").forward(req, resp);
            return;
        }

        CollectiveAgreement agreement = new CollectiveAgreement();
        agreement.setName(aName);
        agreement.setNotes(aNotes);
        agreement.setStartDate(agreementStart);

        AgreementAnnex annex = new AgreementAnnex();
        annex.setName(xName);
        annex.setStartDate(annexStart);
        if (salary != null) annex.setAnnualSalary(salary);
        if (weekly != null) annex.setFullTimeWeeklyHours(weekly);
        if (aux != null) annex.setAuxiliaryTasks(aux);

        try {
            agreementService.createWithFirstAnnex(admin, agreement, annex);
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } catch (IllegalArgumentException ex) {
            errors.add(ex.getMessage());
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/admin/agreements/create.jsp").forward(req, resp);
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
}
