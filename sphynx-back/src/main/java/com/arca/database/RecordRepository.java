package com.arca.database;

import com.arca.database.model.Country;
import com.arca.database.model.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends CrudRepository<Record, Long> {

  List<Record> findByTimestampBetweenOrderByTimestamp(Long start, Long end);

  @Query(value = "SELECT r FROM record r WHERE r.country.name = ?1 AND r.timestamp between ?2 AND ?3")
  List<Record> findByCountryAndTimestampBetweenOrderByTimestamp(String country, Long start, Long end);
}
