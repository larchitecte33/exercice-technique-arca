package com.arca.database;

import com.arca.database.model.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends CrudRepository<Record, Long> {

  List<Record> findByTimestampBetweenOrderByTimestamp(Long start, Long end);
}
