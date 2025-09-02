package com.vtc.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    //===>> FIELDS <<===//

    private static volatile EntityManagerFactory emf;

    //===>> METHODS <<===//

    public static EntityManagerFactory getEntityManagerFactory() {
        EntityManagerFactory result = emf;
        if (result == null) {
            synchronized (JpaUtil.class) {
                result = emf;
                if (result == null) {
                    result = Persistence.createEntityManagerFactory("vtcPU-dev");
                    emf = result;
                }
            }
        }
        return result;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static void close() {
        EntityManagerFactory local = emf;
        if (local != null) {
            synchronized (JpaUtil.class) {
                local = emf; // re-lee dentro del lock
                if (local != null) {
                    try {
                        if (local.isOpen()) local.close();
                    } finally {
                        emf = null; // MUY IMPORTANTE: permitir recreación en siguiente init
                    }
                }
            }
        }
    }
}

