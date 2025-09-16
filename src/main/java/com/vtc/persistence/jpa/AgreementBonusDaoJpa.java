package com.vtc.persistence.jpa;

import com.vtc.model.agreement.AgreementAnnex;
import com.vtc.model.agreement.AgreementBonus;
import com.vtc.persistence.dao.AgreementBonusDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AgreementBonusDaoJpa extends GenericDaoJpa<AgreementBonus, Long> implements AgreementBonusDao {

    public AgreementBonusDaoJpa() { super(AgreementBonus.class); }

    @Override
    public AgreementBonus create(AgreementBonus bonus) {
        if (bonus == null) return null;
        try (EntityManager em = em()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                attachAnnex(em, bonus);
                em.persist(bonus);
                tx.commit();
                return bonus;
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }

    @Override
    public AgreementBonus createOrUpdate(AgreementBonus bonus) {
        if (bonus == null) return null;
        try (EntityManager em = em()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                attachAnnex(em, bonus);
                AgreementBonus managed;
                Object identifier = em.getEntityManagerFactory()
                        .getPersistenceUnitUtil()
                        .getIdentifier(bonus);
                if (identifier == null) {
                    em.persist(bonus);
                    managed = bonus;
                } else {
                    managed = em.merge(bonus);
                }
                tx.commit();
                return managed;
            } catch (RuntimeException e) {
                if (tx.isActive()) tx.rollback();
                throw e;
            }
        }
    }

    private void attachAnnex(EntityManager em, AgreementBonus bonus) {
        AgreementAnnex annex = bonus.getAnnex();
        if (annex != null && annex.getId() != null) {
            bonus.setAnnex(em.getReference(AgreementAnnex.class, annex.getId()));
        }
    }
}
