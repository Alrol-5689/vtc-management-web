package com.vtc.persistence.dao;

import com.vtc.model.log.DailyLog;
import java.util.List;

public interface DailyLogDao {

    void create(DailyLog log);

    List<DailyLog> findAll();

    DailyLog findById(Long id);

    void update(DailyLog log);

    void createOrUpdate(DailyLog log);

    void delete(Long id);
}

