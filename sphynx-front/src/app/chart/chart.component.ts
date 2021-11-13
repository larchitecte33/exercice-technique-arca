import {Component, OnInit} from '@angular/core';
import {Chart} from 'chart.js';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {ChartJsService} from "../services/chartjs/chart-js.service";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.sass']
})
export class ChartComponent implements OnInit {

  chart = [];
  startDate: Date;
  endDate: Date;

  constructor(private http: HttpClient, private chartService : ChartJsService) {
  }

  ngOnInit() {

  }

  startDateChange($event) {
    this.startDate = $event.value;
    this.updateGraph();
  }

  endDateChange($event) {
    this.endDate = $event.value;
    this.updateGraph();
  }

  private updateGraph() {

    if((!this.startDate) || (!this.endDate)){
      return
    }

    this.http.get(environment.serverUrl + "/dailyTotal",
      {
        params: {
          from: this.startDate.toISOString(),
          to: this.endDate.toISOString()
        }
      }).subscribe(value =>
      this.chart = this.chartService.buildChart("canvas", Object.keys(value), Object.values(value)))
  }
}
