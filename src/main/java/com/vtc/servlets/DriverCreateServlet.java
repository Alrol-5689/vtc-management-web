package com.vtc.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vtc.model.user.Driver;
import com.vtc.service.DriverService;
import com.vtc.validation.BeanValidation;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DriverCreateServlet", urlPatterns = {"/drivers/create", "/drivers/create/"})
public class DriverCreateServlet extends HttpServlet {

    private final DriverService service = new DriverService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Muestra el formulario
        req.getRequestDispatcher("/auth/driverCreate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");

        String username       = trim(req.getParameter("username"));
        String password       = trim(req.getParameter("password"));
        String confirm        = trim(req.getParameter("confirm_password"));
        String nationalId     = trim(req.getParameter("nationalId"));
        String firstName      = trim(req.getParameter("firstName"));
        String lastName       = trim(req.getParameter("lastName"));
        String secondLastName = trim(req.getParameter("secondLastName"));
        String phone          = trim(req.getParameter("phone"));
        String email          = trim(req.getParameter("email"));

        List<String> errors = new ArrayList<>();

        if (password == null || confirm == null || !password.equals(confirm))
            errors.add("Passwords do not match.");

        Driver driver = new Driver();
        driver.setUsername(username);
        // Hash password with BCrypt (use a reasonable work factor)
        String hashed = (password != null) ? BCrypt.hashpw(password, BCrypt.gensalt(12)) : null;
        driver.setPassword(hashed);
        driver.setNationalId(nationalId);
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setSecondLastName(secondLastName);
        driver.setPhone(phone);
        driver.setEmail(email);

        errors.addAll(BeanValidation.validate(driver));

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/auth/driverCreate.jsp").forward(req, resp);
            return;
        }

        try {
            service.createDriver(driver);
        } catch (Exception ex) {
            // p.ej., violación de UNIQUE (username/nationalId/phone/email)
            errors.add("Could not create driver. Check duplicates for username, national ID, phone, or email.");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/auth/driverCreate.jsp").forward(req, resp);
            return;
        }

        // Redirige a la lista
        resp.sendRedirect(req.getContextPath() + "/drivers");
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }
    
}
