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

  buildChart(canvasId: String, labels: String[], data: String[], granularite: String) {
    console.log("labels : " + labels);
    console.log("data : " + data);

    let arrayLabels = new Array();
    let arrayData = new Array();

    // Si la granularité correspond au mois
    if(granularite == "Mois") {
      console.log("Granularité Mois");
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
    else if(granularite == "Semaine") {
      console.log("Granularité Semaine");
      console.log("DateTime.local(2017, 5, 25).weekNumber : " + DateTime.local(2017, 5, 25).weekNumber);
      let weekNumber = -1;
      let date = null;

      // On parcourt toutes les dates.
      labels.forEach((element, index) => {
        console.log("element : " + element);
        date = DateTime.fromFormat(element, 'yyyy-MM-dd');
        console.log("date : " + date);
        weekNumber = DateTime.local(date.year, date.month, date.day).weekNumber;
        console.log("weeknumber of " + element + " : " + weekNumber);

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
      console.log("Granularité An");
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
    else if(granularite == "Jour") {
      console.log("Granularité jour");
      console.log("labels : " + labels);
      console.log("data : " + data);
    }

    console.log("arrayLabel : " + arrayLabels);
    console.log("arrayData : " + arrayData);

    return new Chart("canvas", {
      type: 'line',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Total value',
            data: data,
            backgroundColor: 'rgba(240, 50, 50, 0.5)'
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
