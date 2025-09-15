package com.vtc.validation.impl;

import com.vtc.model.user.Driver;
import com.vtc.persistence.util.JpaUtil;
import com.vtc.validation.constraints.UniqueUsername;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // La obligatoriedad la gestiona @NotBlank
            return true;
        }

        EntityManager em = null;
        try {
            em = JpaUtil.getEntityManager();
            Long count = em.createQuery(
                            "SELECT COUNT(d) FROM " + Driver.class.getName() + " d WHERE d.username = :u",
                            Long.class)
                    .setParameter("u", value)
                    .getSingleResult();
            return count == 0L;
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}

