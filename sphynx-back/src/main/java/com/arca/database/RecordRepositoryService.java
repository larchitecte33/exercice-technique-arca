package com.arca.database;


import com.arca.database.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

  public void saveAll(ArrayList<com.arca.importing.model.Record> recs) {
    ArrayList<Record> records = new ArrayList<>();

    for(int i = 0 ; i < recs.size() ; i++) {
      Record record = Record.from(recs.get(i));
      records.add(record);
    }

    recordRepository.saveAll(records);
  }

  public void emptyDatabase() {
    recordRepository.deleteAll();
  }
}
