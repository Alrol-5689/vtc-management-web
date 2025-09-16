package com.vtc.persistence.jpa;

import java.time.LocalDate;
import java.util.List;

import com.vtc.model.log.DailyLog;
import com.vtc.persistence.dao.DailyLogDao;

import jakarta.persistence.EntityManager;

public class DailyLogDaoJpa extends GenericDaoJpa<DailyLog, Long> implements DailyLogDao {

    public DailyLogDaoJpa() { super(DailyLog.class); }

    @Override
    public DailyLog findByAppendixAndDate(Long appendixId, LocalDate date) {
        try (EntityManager em = em()) {
            List<DailyLog> result = em.createQuery(
                            "SELECT l FROM DailyLog l WHERE l.appendix.id = :aid AND l.date = :d",
                            DailyLog.class)
                    .setParameter("aid", appendixId)
                    .setParameter("d", date)
                    .setMaxResults(1)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        }
    }
}
