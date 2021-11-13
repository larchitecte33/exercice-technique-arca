package com.arca.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateConverter {

  /**
   * Transforms a LocalDate to a number of milliseconds
   * @param date The date
   * @return The number of milliseconds from epoch at the start of day
   */
  public static long toMillis (LocalDate date){
    return date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  /**
   * Transforms a number of milliseconds from epoch to a LocalDate
   * @param millis
   * @return The date the milliseonds belong to
   */
  public static LocalDate toLocaldate (long millis){
    return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
