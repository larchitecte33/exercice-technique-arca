package com.arca.database;


import com.arca.database.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordRepositoryService {

  private RecordRepository recordRepository;

  @Autowired
  public RecordRepositoryService(RecordRepository recordRepository) {
    this.recordRepository = recordRepository;
  }

  public void save(com.arca.importing.model.Record rec) {
    Record record = Record.from(rec);
    recordRepository.save(record);
  }

  public void emptyDatabase() {
    recordRepository.deleteAll();
  }
}
