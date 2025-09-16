package com.vtc.persistence.dao;

import java.time.LocalDate;

import com.vtc.model.log.DailyLog;

public interface DailyLogDao extends GenericDao<DailyLog, Long> {

    DailyLog findByAppendixAndDate(Long appendixId, LocalDate date);
}
