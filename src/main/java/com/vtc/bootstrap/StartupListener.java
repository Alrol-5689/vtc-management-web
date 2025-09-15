package com.vtc.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vtc.persistence.util.JpaUtil;

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
        try {
            log.info("Initializing EntityManagerFactory...");
            EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
            try (EntityManager em = emf.createEntityManager()) {
                em.getMetamodel(); // fuerza carga del metamodelo
            }
            log.info("JPA ready. Schema generation should have run.");
        } catch (Exception e) {
            // Importante: NO relanzar para no tumbar el deploy
            log.error("Failed to initialize JPA on startup. Will initialize lazily on first use.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaUtil.close();
    }
}
