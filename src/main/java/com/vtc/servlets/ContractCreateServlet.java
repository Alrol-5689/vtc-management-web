package com.vtc.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.vtc.model.company.Company;
import com.vtc.model.contract.Contract;
import com.vtc.model.user.Driver;
import com.vtc.service.CompanyService;
import com.vtc.service.ContractService;
import com.vtc.service.DriverService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ContractCreateServlet", urlPatterns = {"/contracts/create", "/contracts/create/"})
public class ContractCreateServlet extends HttpServlet {

    private final CompanyService companyService = new CompanyService();
    private final ContractService contractService = new ContractService();
    private final DriverService driverService = new DriverService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("driverId") == null) {
            resp.sendRedirect(req.getContextPath() + "/drivers/login");
            return;
        }
        // List existing companies for convenience
        req.setAttribute("companies", companyService.listCompanies());
        req.getRequestDispatcher("/contract/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("driverId") == null) {
            resp.sendRedirect(req.getContextPath() + "/drivers/login");
            return;
        }
        Long driverId = (Long) session.getAttribute("driverId");
        Driver driver = driverService.getDriver(driverId);

        List<String> errors = new ArrayList<>();

        String companyIdStr = trim(req.getParameter("companyId"));
        String companyName  = trim(req.getParameter("companyName"));
        String startDateStr = trim(req.getParameter("startDate"));
        String endDateStr   = trim(req.getParameter("endDate"));

        Company company = null;
        if (companyIdStr != null && !companyIdStr.isEmpty()) {
            try { company = companyService.getCompany(Long.valueOf(companyIdStr)); }
            catch (NumberFormatException ex) { errors.add("Invalid company selection."); }
        } else if (companyName != null && !companyName.isEmpty()) {
            company = new Company();
            company.setName(companyName);
            try { companyService.createCompany(company); }
            catch (Exception e) { errors.add("Could not create company. It may already exist."); }
        } else {
            errors.add("Select a company or enter a new one.");
        }

        LocalDate startDate = null, endDate = null;
        if (startDateStr == null) errors.add("Start date is required.");
        else {
            try { startDate = LocalDate.parse(startDateStr); }
            catch (DateTimeParseException ex) { errors.add("Invalid start date."); }
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            try { endDate = LocalDate.parse(endDateStr); }
            catch (DateTimeParseException ex) { errors.add("Invalid end date."); }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("companies", companyService.listCompanies());
            req.getRequestDispatcher("/contract/create.jsp").forward(req, resp);
            return;
        }

        Contract contract = new Contract();
        contract.setDriver(driver);
        contract.setCompany(company);
        contract.setStartDate(startDate);
        contract.setEndDate(endDate);

        try {
            contractService.createContract(contract);
        } catch (Exception ex) {
            errors.add("Could not create contract.");
            req.setAttribute("errors", errors);
            req.setAttribute("companies", companyService.listCompanies());
            req.getRequestDispatcher("/contract/create.jsp").forward(req, resp);
            return;
        }

        // Refresh companies in session and set active to the contract's company
        List<Company> companies = companyService.findByDriverId(driverId);
        session.setAttribute("companies", companies);
        session.setAttribute("activeCompanyId", company.getId());
        session.setAttribute("activeCompanyName", company.getName());

        resp.sendRedirect(req.getContextPath() + "/driver/home.jsp");
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
}

