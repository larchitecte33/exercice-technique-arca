package com.arca.database.model;

import com.arca.database.RepositoryException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RecordTest {

  @Test
  public void testFrom() {
    com.arca.importing.model.Record record = com.arca.importing.model.Record.from("123,4,France");
    Record result = Record.from(record);
    assertThat(result.getCountry().getName()).isEqualTo("France");
    assertThat(result.getTimestamp()).isEqualTo(123);
    assertThat(result.getValue()).isEqualTo(4);
  }

  @Test (expected = RepositoryException.class)
  public void testFromNull() {
    Record result = Record.from(null);
    assertThat(result.getCountry().getName()).isEqualTo("France");
    assertThat(result.getTimestamp()).isEqualTo(123);
    assertThat(result.getValue()).isEqualTo(4);
  }
}
