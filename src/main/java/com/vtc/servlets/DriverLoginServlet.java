package com.vtc.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vtc.model.user.Driver;
import com.vtc.service.DriverService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "DriverLoginServlet", urlPatterns = {"/drivers/login"})
public class DriverLoginServlet extends HttpServlet {

    private final DriverService service = new DriverService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Si ya hay sesión de driver, redirige a su home
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("driverId") != null) {
            resp.sendRedirect(req.getContextPath() + "/driver/home.jsp");
            return;
        }
        // Muestra el formulario
        req.getRequestDispatcher("/auth/driverLogin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");

        String username = trim(req.getParameter("username"));
        String password = trim(req.getParameter("password"));

        List<String> errors = new ArrayList<>();
        if (isBlank(username) || isBlank(password)) {
            errors.add("Username and password are required.");
        }

        Driver driver = null;
        
        if (errors.isEmpty()) {
            // Nota: ahora mismo password va en claro; más adelante lo cambiaremos por hash (BCrypt).
            driver = service.authenticate(username, password);
            if (driver == null) {
                errors.add("Invalid credentials.");
            }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            // Mantén el username en el form con ${param.username}, no repoblamos password
            req.getRequestDispatcher("/auth/driverLogin.jsp").forward(req, resp);
            return;
        }

        if (driver == null) {
            // Esto nunca debería ocurrir si el control de errores está bien,
            // pero es una buena práctica defensiva.
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error inesperado de autenticación.");
            return;
        }

        // Login OK → crea sesión y guarda solo datos mínimos
        HttpSession session = req.getSession(true);
        session.setAttribute("driverId", driver.getId());
        session.setAttribute("driverUsername", driver.getUsername());

        // (Opcional) tiempo de inactividad antes de expirar (en segundos)
        session.setMaxInactiveInterval(30 * 60); // 30 minutos

        // Redirige al home del driver
        resp.sendRedirect(req.getContextPath() + "/driver/home.jsp");
    }

    // ===== Helpers =====
    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    
}
