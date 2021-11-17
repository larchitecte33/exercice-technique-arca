import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../services/ws/websocket.service";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AggregateDto} from "../model/AggregateDto";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-aggregate',
  templateUrl: './aggregate.component.html',
  styleUrls: ['./aggregate.component.sass']
})
export class AggregateComponent implements OnInit {

  aggregates: AggregateDto[] = [];
  displayedColumns: string[] = ['country', 'value'];
  startDate: Date;
  endDate: Date;

  constructor(private wsService: WebsocketService, private http: HttpClient, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.startDate = params['dateDebut'];
      this.endDate = params['dateFin'];
    });
  }

  ngOnInit() {
    this.loadData();
  }

  reload () {
    this.loadData();
  }

  private loadData() {
    if((this.startDate != undefined) && (this.endDate != undefined)) {
      let startDateIso = new Date(this.startDate);
      let endDateIso = new Date(this.endDate);

      this.http.get(environment.serverUrl + "/aggregateBetweenDates",
        {
          params: {
            from: startDateIso.toISOString(),
            to: endDateIso.toISOString()
          }
        }) 
        .subscribe((data: AggregateDto[]) => {
          this.aggregates = data;
        });
    }
    else {
      this.http.get(environment.serverUrl + "/aggregate")
        .subscribe((data: AggregateDto[]) => {
          this.aggregates = data;
        });
    }
  }

}
