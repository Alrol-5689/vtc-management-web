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
    public void deleteDailyLog(Long id) { dailyLogDao.delete(id); }

    public List<DailyLog> listDailyLogs() { return dailyLogDao.findAll(); }
    public DailyLog getDailyLog(Long id) { return dailyLogDao.findById(id); }
}

