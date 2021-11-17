package com.arca.aggregation.model;

import com.arca.database.model.Country;
import com.arca.importing.model.Record;

public class Aggregate {
  // Pays
  private Country country;
  private long aggregateValue;

  public Aggregate(Country country, long aggregateValue) {
    this.country = country;
    this.aggregateValue = aggregateValue;
  }

  // Retourne un objet Aggregate construit à partir du objet Record.
  public static Aggregate from(Record rec) {
    return new Aggregate(Country.from(rec.getCountry()), rec.getValue());
  }

  // Accesseurs
  public Country getCountry() {
    return country;
  }

  public long getAggregateValue() {
    return aggregateValue;
  }

  public void setAggregateValue(long value) {
    this.aggregateValue = value;
  }
}
