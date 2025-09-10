package com.vtc.service;

import java.util.List;

import com.vtc.model.log.DailyLog;
import com.vtc.persistence.dao.DailyLogDao;
import com.vtc.persistence.jpa.DailyLogDaoJpa;

public class DailyLogService {

    private final DailyLogDao dailyLogDao;

    public DailyLogService() { this.dailyLogDao = new DailyLogDaoJpa(); }

    public DailyLogService(DailyLogDao dailyLogDao) { this.dailyLogDao = dailyLogDao; }

    public void createDailyLog(DailyLog log) { dailyLogDao.create(log); }
    public void updateDailyLog(DailyLog log) { dailyLogDao.update(log); }
    public void createOrUpdateDailyLog(DailyLog log) { dailyLogDao.createOrUpdate(log); }
    public void createOrUpdateDailyLogByDate(DailyLog log) {
        if (log == null || log.getAppendix() == null || log.getAppendix().getId() == null || log.getDate() == null)
            throw new IllegalArgumentException("appendix id and date required");
        DailyLog existing = dailyLogDao.findByAppendixAndDate(log.getAppendix().getId(), log.getDate());
        if (existing == null) {
            dailyLogDao.create(log);
        } else {
            // Update mutable fields only
            existing.setConnection(log.getConnection());
            existing.setPresence(log.getPresence());
            existing.setAuxiliaryTasks(log.getAuxiliaryTasks());
            existing.setBillingAmount(log.getBillingAmount());
            dailyLogDao.update(existing);
        }
    }
    public void deleteDailyLog(Long id) { dailyLogDao.delete(id); }

    public List<DailyLog> listDailyLogs() { return dailyLogDao.findAll(); }
    public DailyLog getDailyLog(Long id) { return dailyLogDao.findById(id); }
    public DailyLog findByAppendixAndDate(Long appendixId, java.time.LocalDate date) {
        return dailyLogDao.findByAppendixAndDate(appendixId, date);
    }
}
