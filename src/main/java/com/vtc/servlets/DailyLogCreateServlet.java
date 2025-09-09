package com.vtc.servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.vtc.model.contract.ContractAppendix;
import com.vtc.model.log.DailyLog;
import com.vtc.service.ContractAppendixService;
import com.vtc.service.DailyLogService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DailyLogCreateServlet", urlPatterns = {"/daily-logs/create", "/daily-logs/create/"})
public class DailyLogCreateServlet extends HttpServlet {

    private final DailyLogService dailyLogService = new DailyLogService();
    private final ContractAppendixService appendixService = new ContractAppendixService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/dailylog/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");

        List<String> errors = new ArrayList<>();

        String appendixIdStr = trim(req.getParameter("appendixId"));
        String dateStr       = trim(req.getParameter("date"));
        String connectionStr = trim(req.getParameter("connection"));
        String presenceStr   = trim(req.getParameter("presence"));
        String auxStr        = trim(req.getParameter("auxiliaryTasks"));
        String billingStr    = trim(req.getParameter("billingAmount"));

        Long appendixId = null;
        if (appendixIdStr == null) {
            errors.add("appendixId is required.");
        } else {
            try { appendixId = Long.valueOf(appendixIdStr); }
            catch (NumberFormatException ex) { errors.add("Invalid appendixId."); }
        }

        LocalDate date = null;
        if (dateStr == null) {
            errors.add("date is required.");
        } else {
            try { date = LocalDate.parse(dateStr); }
            catch (DateTimeParseException ex) { errors.add("Invalid date format. Use yyyy-MM-dd."); }
        }

        Duration connection = parseDuration(connectionStr, errors, "connection");
        Duration presence   = parseDuration(presenceStr, errors, "presence");
        Duration auxiliary  = parseDuration(auxStr, errors, "auxiliaryTasks");

        double billing = 0.0;
        if (billingStr != null && !billingStr.isEmpty()) {
            try { billing = Double.parseDouble(billingStr); }
            catch (NumberFormatException ex) { errors.add("Invalid billingAmount."); }
        }

        ContractAppendix appendix = null;
        if (appendixId != null) {
            appendix = appendixService.getAppendix(appendixId);
            if (appendix == null) errors.add("Appendix not found.");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/dailylog/create.jsp").forward(req, resp);
            return;
        }

        DailyLog log = new DailyLog();
        log.setAppendix(appendix);
        log.setDate(date);
        log.setConnection(connection);
        log.setPresence(presence);
        log.setAuxiliaryTasks(auxiliary);
        log.setBillingAmount(billing);

        try {
            dailyLogService.createOrUpdateDailyLogByDate(log);
        } catch (Exception ex) {
            errors.add("Could not save daily log. Check constraints and try again.");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/dailylog/create.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/daily-logs");
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }

    private static Duration parseDuration(String input, List<String> errors, String field) {
        if (input == null || input.isEmpty()) return null;
        try {
            if (input.contains(":")) {
                String[] parts = input.split(":");
                if (parts.length != 2) throw new IllegalArgumentException();
                long hours = Long.parseLong(parts[0]);
                long minutes = Long.parseLong(parts[1]);
                if (minutes < 0 || minutes > 59) throw new IllegalArgumentException();
                return Duration.ofMinutes(hours * 60 + minutes);
            } else {
                long minutes = Long.parseLong(input);
                return Duration.ofMinutes(minutes);
            }
        } catch (IllegalArgumentException e) {
            errors.add("Invalid duration for " + field + ". Use HH:mm or total minutes.");
            return null;
        }
    }
}

