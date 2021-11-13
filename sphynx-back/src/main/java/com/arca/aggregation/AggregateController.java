package com.arca.aggregation;

import com.arca.aggregation.model.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class AggregateController {

  @Autowired
  private AggregateService aggregateService;

  /**
   * Fonction qui va chercher la map aggregates contenue dans AggregateService sous forme de liste d'Aggregate.
   * @return la map aggregates contenue dans AggregateService sous forme de liste d'Aggregate.
   */
  @GetMapping("/aggregate")
  public List<Aggregate> getAggregates() {
    return aggregateService.getAggregates();
  }

  @GetMapping("/dailyTotal")
  public Map<LocalDate, Long> getTotalByDay(@RequestParam("from")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                LocalDateTime startDate,
                                            @RequestParam("to")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                              LocalDateTime endDate) {
    return aggregateService.getTotalByDay(startDate.toLocalDate(), endDate.toLocalDate());
  }

}
