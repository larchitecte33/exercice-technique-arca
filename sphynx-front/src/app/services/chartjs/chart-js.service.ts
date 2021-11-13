import {Injectable} from '@angular/core';
import {Chart} from 'chart.js';

@Injectable({
  providedIn: 'root'
})
export class ChartJsService {

  constructor() {
  }

  buildChart(canvasId: String, labels: String[], data: String[]) {
    return new Chart("canvas", {
      type: 'bar',
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
