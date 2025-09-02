package com.vtc.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vtc.persistence.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        log.info("Initializing EntityManagerFactory...");
        // Crear el EMF al iniciar la app
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

        // (Opcional) Forzar toque temprano para que Hibernate dispare la generación de esquema ya.
        // Algunos proveedores ya generan en createEntityManagerFactory(); por si acaso:
        try (EntityManager em = emf.createEntityManager()) {
            em.getMetamodel(); // “toca” el metamodelo
            log.info("JPA Metamodel loaded. Schema generation should have run.");
        } catch (Exception ignored) {
            // Si hay error aquí, mejor dejar que el flujo normal lo revele con logs
            log.error("Error initializing JPA", ignored);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.close();
    }
}
