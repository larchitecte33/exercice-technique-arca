package com.arca.database;

import com.arca.importing.model.Record;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RecordRepositoryServiceTest {

  @Mock
  private RecordRepository recordRepository;

  private RecordRepositoryService service;

  @Captor
  private ArgumentCaptor<com.arca.database.model.Record> recordCaptor;

  @Before
  public void before(){
   service = new RecordRepositoryService(recordRepository);
  }

  @Test
  public void shouldCallRepositoryWhenSaving () {
    Record rec = new Record();

    service.save(rec);

    verify(recordRepository).save(recordCaptor.capture());
    assertThat(recordCaptor.getValue()).isNotNull();
  }

  @Test
  public void shouldCallRepositoryWhenEmptyingDatabase () {

    service.emptyDatabase();

    verify(recordRepository).deleteAll();
  }
}
