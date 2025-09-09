package com.vtc.persistence.dao;

import java.time.LocalDate;
import java.util.List;

import com.vtc.model.log.DailyLog;

public interface DailyLogDao {

    void create(DailyLog log);

    List<DailyLog> findAll();

    DailyLog findById(Long id);

    void update(DailyLog log);

    void createOrUpdate(DailyLog log);

    void delete(Long id);

    DailyLog findByAppendixAndDate(Long appendixId, LocalDate date);
}
