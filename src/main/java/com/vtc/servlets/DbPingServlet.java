package com.vtc.servlets;

import java.io.IOException;

import com.vtc.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DbPingServlet", urlPatterns = "/db-ping")
public class DbPingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        try (EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager()) {
            em.createNativeQuery("SELECT 1").getSingleResult();
            resp.getWriter().println("✅ JPA/MariaDB OK");
        } catch (Exception e) {
            resp.getWriter().println("❌ Error JPA/MariaDB: " + e.getMessage());
        }
    }
}
