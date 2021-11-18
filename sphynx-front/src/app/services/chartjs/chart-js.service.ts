import {Injectable} from '@angular/core';
import {Chart} from 'chart.js';
import {DateTime} from "luxon";

@Injectable({
  providedIn: 'root'
})
export class ChartJsService {

  constructor() {
  }

  extractYearAndMonth(date: String) {
    if(date.length < 7) {
      return date;
    }
    else {
      return date.substring(0, 7);
    }
  }

  extractYear(date: String) {
    if(date.length < 4) {
      return date;
    }
    else {
      return date.substring(0, 4);
    }
  }

  buildChart(canvasId: String, labels: String[], data: String[], data2: String[], granularite: String) {
    let arrayLabels = new Array();
    let arrayData = new Array();

    // Si la granularité correspond au mois
    if(granularite == "Mois") {
      // On parcourt toutes les dates.
      labels.forEach((element, index) => {
        // S'il existe une chaine qui correspond à la date sans le jour (mm/aaaa), alors on ajoute la valeur à la valeur 
        // correspondant à cette date.
        if(arrayLabels.indexOf(this.extractYearAndMonth(element)) != -1) {
          let indexElem = arrayLabels.indexOf(this.extractYearAndMonth(element));
          arrayData[indexElem] = arrayData[indexElem] + data[index];
        }
        // Sinon, on ajoute la date dans arrayLabels et la valeur dans arrayData.
        else {
          arrayLabels.push(this.extractYearAndMonth(element));
          arrayData.push(data[index]);
        }
      });

      labels.length = 0;
      data.length = 0;

      // On transfère les valeurs de arrayLabels dans labels et celles de arrayData dans data.
      arrayLabels.forEach(element => {
        labels.push(element);
      });

      arrayData.forEach(element => {
        data.push(element);
      });
    }
    // Si la granularité correspond à la semaine
    else if(granularite == "Semaine") {let weekNumber = -1;
      let date = null;

      // On parcourt toutes les dates.
      labels.forEach((element, index) => {
        date = DateTime.fromFormat(element, 'yyyy-MM-dd');
        weekNumber = DateTime.local(date.year, date.month, date.day).weekNumber;

        if(arrayLabels.indexOf(weekNumber) != -1) {
          let indexElem = arrayLabels.indexOf(weekNumber);
          arrayData[indexElem] = arrayData[indexElem] + data[index];
        }
        else {
          arrayLabels.push(weekNumber);
          arrayData.push(data[index]);
        }
      });

      labels.length = 0;
      data.length = 0;

      // On transfère les valeurs de arrayLabels dans labels et celles de arrayData dans data.
      arrayLabels.forEach(element => {
        labels.push(element);
      });

      arrayData.forEach(element => {
        data.push(element);
      });
    }
    // Si la granularité correspond à l'année
    else if(granularite == "An") {
      // On parcourt toutes les dates.
      labels.forEach((element, index) => {
        // S'il existe une chaine qui correspond à l'année, alors on ajoute la valeur à la valeur 
        // correspondant à cette date.
        if(arrayLabels.indexOf(this.extractYear(element)) != -1) {
          let indexElem = arrayLabels.indexOf(this.extractYear(element));
          arrayData[indexElem] = arrayData[indexElem] + data[index];
        }
        // Sinon, on ajoute la date dans arrayLabels et la valeur dans arrayData.
        else {
          arrayLabels.push(this.extractYear(element));
          arrayData.push(data[index]);
        }
      });

      labels.length = 0;
      data.length = 0;

      // On transfère les valeurs de arrayLabels dans labels et celles de arrayData dans data.
      arrayLabels.forEach(element => {
        labels.push(element);
      });

      arrayData.forEach(element => {
        data.push(element);
      });
    }

    return new Chart("canvas", {
      type: 'line',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Total value',
            data: data,
            backgroundColor: 'rgba(240, 50, 50, 0.5)'
          },
          {
            label: 'Value data2',
            data: data2,
            backgroundColor: 'rgba(40, 250, 50, 0.5)'
          }
        ]
      },
      options: {
        legend: {
          display: false
        },
        scales: {
          xAxes: [{
            display: true
          }],
          yAxes: [{
            display: true
          }],
        }
      }
    });
  }
}
