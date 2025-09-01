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
}

