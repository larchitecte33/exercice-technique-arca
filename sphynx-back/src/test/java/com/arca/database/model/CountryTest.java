package com.arca.database.model;

import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {

  @Test
  public void testFrom() throws Exception {
    com.arca.importing.model.Country country = new com.arca.importing.model.Country("name");
    Country result = Country.from(country);
    assertThat(result).isEqualToComparingFieldByField(country);
  }

  @Test
  public void testFromNull() throws Exception {
    Country result = Country.from(null);
    assertThat(result).isNull();
  }
}
