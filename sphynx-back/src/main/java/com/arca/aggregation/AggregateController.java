package com.arca.aggregation;

import com.arca.aggregation.model.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class AggregateController {

  @Autowired
  private AggregateService aggregateService;

  /**
   * Fonction qui va permettre d'aller chercher la somme des données par pays.
   * @return une liste d'Aggregate correspondant à la somme des données par pays.
   */
  @GetMapping("/aggregate")
  public List<Aggregate> getAggregates() {
    return aggregateService.getAggregates();
  }

  /**
   * Fonction qui va permettre d'aller chercher la somme des données par pays entre deux dates.
   * @param startDate : date de début.
   * @param endDate : date de fin.
   * @return une liste d'Aggregate correspondant à la somme des données par pays entre startDate et endDate.
   */
  @GetMapping("/aggregateBetweenDates")
  public List<Aggregate> getAggregatesBetweenDates(@RequestParam("from")
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       LocalDateTime startDate,
                                                   @RequestParam("to")
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                     LocalDateTime endDate) {
    return aggregateService.getAggregatesBetweenDates(startDate.toLocalDate(), endDate.toLocalDate());
  }

  /**
   * Va chercher la somme des données par jour, toutes sources confondues.
   * @param startDate : date de début.
   * @param endDate : date de fin.
   * @param autrePays : autre pays pour lequel chercher la somme des données par jour.
   * @return
   */
  @GetMapping("/dailyTotal")
  public List<Map<LocalDate, Long>> getTotalByDay(@RequestParam("from")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                LocalDateTime startDate,
                                            @RequestParam("to")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                              LocalDateTime endDate,
                                            @RequestParam String autrePays) {
    return aggregateService.getTotalByDay(startDate.toLocalDate(), endDate.toLocalDate(), autrePays);
  }

}
