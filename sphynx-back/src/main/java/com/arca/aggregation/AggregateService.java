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
import java.util.*;
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

    //System.out.println(aggregate.getCountry().getName() + " : " + String.valueOf(oldValue + aggregate.getAggregateValue()));
  }

  // Retourne la Map aggregates sous forme de liste d'Aggregate.
  public List<Aggregate> getAggregates() {
    List<Aggregate> listeAggregate = aggregates.entrySet()
      .stream()
      .map(entry -> new Aggregate(entry.getKey(), entry.getValue()))
      .collect(Collectors.toList());

    /*for(int i = 0 ; i < listeAggregate.size() ; i++) {
      System.out.println("CountryName : " + listeAggregate.get(i).getCountry().getName());
      System.out.println("Value : " + listeAggregate.get(i).getAggregateValue());
    }*/

    return listeAggregate;
  }


  public Map<LocalDate, Long> getTotalByDay(LocalDate startDate, LocalDate endDate) {
    Long startDateMillis = DateConverter.toMillis(startDate);
    Long endDateMillis = DateConverter.toMillis(endDate.plusDays(1));

    //System.out.println("startDateMillis : " + startDateMillis);
    //System.out.println("endDateMillis : " + endDateMillis);

    Map<LocalDate, Long> records = recordRepository.findByTimestampBetweenOrderByTimestamp(startDateMillis, endDateMillis)
      .stream()
      .collect(
        Collectors.groupingBy(record -> {
            LocalDate date = DateConverter.toLocaldate(record.getTimestamp());
            return date;
          }
          , Collectors.summingLong(Record::getValue)
        ));

    // Ceci permet d'ordonner les enregistrements par date croissante.
    records = new TreeMap<LocalDate, Long>(records);

    //System.out.println("records.keySet().toString() = " + records.keySet().toString());
    Iterator<Long> iterator = records.values().iterator();

    /*while(iterator.hasNext()) {
      System.out.println("records.value = " + iterator.next());
    }*/

    // On recherche la liste des enregistrements qui ont une date comprise entre startDate et endDate en les groupant
    // par date.
    return records;
  }
}
