package com.vtc.servlets;

import com.vtc.model.user.Driver;
import com.vtc.service.DriverService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DriverListServlet", urlPatterns = {"/drivers"})
public class DriverListServlet extends HttpServlet {

    private final DriverService service = new DriverService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Driver> drivers = service.listDrivers();
        req.setAttribute("drivers", drivers);
        req.getRequestDispatcher("/driver/list.jsp").forward(req, resp);
    }
}
