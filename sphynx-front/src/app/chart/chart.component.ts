import {Component, OnInit} from '@angular/core';
import {Chart} from 'chart.js';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {ChartJsService} from "../services/chartjs/chart-js.service";
import {DatePipe} from "@angular/common";
import { element } from 'protractor';

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
  donnees2 = [];
  autrePays : String = '';
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

  autrePaysChangeEvent($event){
    if($event != undefined) {
      this.autrePays = $event.target.value;
      this.updateGraph();
    }
  }

  yearChangeHandler() {
    this.startDate = new Date(this.example, 0, 1);
    this.endDate = new Date(this.example, 11, 31);
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
          to: this.endDate.toISOString(),
          autrePays: "" + this.autrePays
        }
      }).subscribe(value => {
        // value[0] : clés-valeurs pour le diagramme de tous les pays.
        // value[1] : clés-valeurs pour le diagramme du pays supplémentaire.
        if(this.autrePays != "") {
          let i = 0;
          this.donnees2 = [];

          // On parcourt toutes les clés (dates) du diagramme de tous les pays.
          Object.keys(value[0]).forEach((element) => {
            // Si la clé (date) du diagramme du pays supplémentaire est égale à la clé (date) en cours de parcourt du 
            // diagramme de tous les pays, on ajoute la valeur aux valeurs qui vont être utilisées pour le pays supplémentaire.
            if(Object.keys(value[1])[i] === element) {
              this.donnees2.push(Object.values(value[1])[i]);
              i++;
            }
            // Sinon, la valeur à cette date pour le pays supplémentaire n'existe pas, donc on ajoute 0 aux valeurs qui vont 
            // être utilisées pour ce pays.
            else {
              this.donnees2.push(0);
            }
          });  

          this.chart = this.chartService.buildChart("canvas", Object.keys(value[0]), Object.values(value[0]), 
            this.donnees2, this.selectedGranularite)
        }
        else {
          this.chart = this.chartService.buildChart("canvas", Object.keys(value[0]), Object.values(value[0]), 
            null, this.selectedGranularite)
        }
      })
  }
}
