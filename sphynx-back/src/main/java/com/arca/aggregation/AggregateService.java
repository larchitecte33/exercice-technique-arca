package com.arca.aggregation;

import com.arca.aggregation.model.Aggregate;
import com.arca.database.RecordRepository;
import com.arca.database.model.Country;
import com.arca.database.model.Record;
import com.arca.date.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AggregateService {

  private final RecordRepository recordRepository;
  private Map<Country, Long> aggregates = new HashMap<>();

  // Constructeur
  @Autowired
  public AggregateService(RecordRepository recordRepository) {
    this.recordRepository = recordRepository;
  }

  // Ajoute un Aggregate à la Map aggregates.
  public void add(Aggregate aggregate) {
    if (!aggregates.containsKey(aggregate.getCountry())) {
      aggregates.put(aggregate.getCountry(), 0L);
    }

    Long oldValue = aggregates.get(aggregate.getCountry());
    aggregates.put(aggregate.getCountry(), oldValue + aggregate.getAggregateValue());
  }

  // Retourne la Map aggregates sous forme de liste d'Aggregate.
  public List<Aggregate> getAggregates() {
    List<Aggregate> listeAggregate = aggregates.entrySet()
      .stream()
      .map(entry -> new Aggregate(entry.getKey(), entry.getValue()))
      .collect(Collectors.toList());

    return listeAggregate;
  }


  public Map<LocalDate, Long> getTotalByDay(LocalDate startDate, LocalDate endDate) {
    Long startDateMillis = DateConverter.toMillis(startDate);
    Long endDateMillis = DateConverter.toMillis(endDate.plusDays(1));

    // On recherche la liste des enregistrements qui ont une date comprise entre startDate et endDate en les groupant
    // par date.
    return recordRepository.findByTimestampBetween(startDateMillis, endDateMillis)
      .stream()
      .collect(
        Collectors.groupingBy(record -> {
            LocalDate date = DateConverter.toLocaldate(record.getTimestamp());
            return date;
          }
          , Collectors.summingLong(Record::getTimestamp)
        ));
  }
}
