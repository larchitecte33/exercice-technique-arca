import {Component, OnInit} from '@angular/core';
import {Chart} from 'chart.js';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {ChartJsService} from "../services/chartjs/chart-js.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.sass']
})
export class ChartComponent implements OnInit {

  chart = [];
  startDate: Date;
  endDate: Date;
  selectedDate: Date;
  selectedGranularite: String = '';
  example : number = 2021;
  choixGranularite: any = [
    'Jour',
    'Semaine',
    'Mois',
    'An'
  ];

  // format date in typescript
  getFormatedDate(date: Date, format: string) {
    const datePipe = new DatePipe('fr-FR');
    return datePipe.transform(date, format);
  }

  // Gestionnaire d'évènement pour le clic sur un radioButton.
  radioChangeHandler(event : any) {
    this.selectedGranularite = event.target.value;
    console.log("this.selectedGranularite = " + this.selectedGranularite);
    this.updateGraph();
  }

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

  yearChangeHandler() {
    console.log(this.example);
    this.startDate = new Date(this.example, 0, 1);
    this.endDate = new Date(this.example, 11, 31);
    console.log(this.startDate);
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
      this.chart = this.chartService.buildChart("canvas", Object.keys(value), Object.values(value), this.selectedGranularite))
  }
}
