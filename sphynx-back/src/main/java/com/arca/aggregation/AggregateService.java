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
  }

  // Retourne la Map aggregates sous forme de liste d'Aggregate.
  public List<Aggregate> getAggregates() {
    List<Aggregate> listeAggregate = aggregates.entrySet()
      .stream()
      .map(entry -> new Aggregate(entry.getKey(), entry.getValue()))
      .collect(Collectors.toList());

    return listeAggregate;
  }

  public List<Aggregate> getAggregatesBetweenDates(LocalDate dateDebut, LocalDate dateFin) {
    Long startDateMillis = DateConverter.toMillis(dateDebut);
    Long endDateMillis = DateConverter.toMillis(dateFin.plusDays(1));

    List<Record> records = recordRepository.findByTimestampBetweenOrderByTimestamp(startDateMillis, endDateMillis);

    List<Aggregate> listeAggregate = new ArrayList<>();
    boolean isAggregateTrouve = false;
    int j = 0;

    for(int i = 0 ; i < records.size() ; i++) {
      j = 0;
      isAggregateTrouve = false;

      while(j < listeAggregate.size() && (!isAggregateTrouve)) {
        if (listeAggregate.get(j).getCountry().getName().equals(records.get(i).getCountry().getName())) {
          listeAggregate.get(j).setAggregateValue(listeAggregate.get(j).getAggregateValue() + records.get(i).getValue());
          isAggregateTrouve = true;
        }

        j++;
      }

      if(!isAggregateTrouve) {
        Aggregate aggregate = new Aggregate(records.get(i).getCountry(), records.get(i).getValue());
        listeAggregate.add(aggregate);
      }
    }

    return listeAggregate;
  }

  public List<Map<LocalDate, Long>> getTotalByDay(LocalDate startDate, LocalDate endDate, String autrePays) {
    Long startDateMillis = DateConverter.toMillis(startDate);
    Long endDateMillis = DateConverter.toMillis(endDate.plusDays(1));

    ArrayList<Map<LocalDate, Long>> listeRecords;
    Map<LocalDate, Long> recordsAutrePays = null;
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

    if(!autrePays.equals("")) {
      Country country = new Country();
      country.setName(autrePays);

      recordsAutrePays =
        recordRepository.findByCountryAndTimestampBetweenOrderByTimestamp(country.getName(), startDateMillis, endDateMillis)
        .stream()
        .collect(
          Collectors.groupingBy(record -> {
              LocalDate date = DateConverter.toLocaldate(record.getTimestamp());
              return date;
            }
            , Collectors.summingLong(Record::getValue)
          ));

      // Ceci permet d'ordonner les enregistrements par date croissante.
      recordsAutrePays = new TreeMap<LocalDate, Long>(recordsAutrePays);
    }

    listeRecords = new ArrayList<>();
    listeRecords.add(records);
    listeRecords.add(recordsAutrePays);

    // On recherche la liste des enregistrements qui ont une date comprise entre startDate et endDate en les groupant
    // par date.
    return listeRecords;
  }
}
