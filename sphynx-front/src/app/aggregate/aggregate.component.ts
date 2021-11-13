import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../services/ws/websocket.service";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AggregateDto} from "../model/AggregateDto";

@Component({
  selector: 'app-aggregate',
  templateUrl: './aggregate.component.html',
  styleUrls: ['./aggregate.component.sass']
})
export class AggregateComponent implements OnInit {

  aggregates: AggregateDto[] = [];
  displayedColumns: string[] = ['country', 'value'];

  constructor(private wsService: WebsocketService, private http: HttpClient) {
  }

  ngOnInit() {
    this.loadData();
  }

  reload () {
    this.loadData();
  }

  private loadData() {
    this.http.get(environment.serverUrl + "/aggregate")
      .subscribe((data: AggregateDto[]) => {
        this.aggregates = data;
      });
  }

}
