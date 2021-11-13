import {Component, OnInit} from '@angular/core';
import {ProgressListener} from "../services/ws/ProgressListener";
import {ProgressDto} from "../model/ProgressDto";
import {WebsocketService} from "../services/ws/websocket.service";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.sass']
})
export class ImportComponent implements OnInit, ProgressListener {

  progressPercentage: number = 0;

  constructor(private wsService: WebsocketService, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.wsService.addListener(this);
  }

  onProgress(progress: ProgressDto) {
    this.progressPercentage = progress.getPercentage();
    console.log(this.progressPercentage);
  }

  startImport() {
    this.http.get(environment.serverUrl + "/import/start").subscribe();
  }

  stopImport() {
    this.http.get(environment.serverUrl + "/import/stop").subscribe();
  }

  emptyDatabase() {
    this.http.delete(environment.serverUrl + "/import/database").subscribe();
  }
}
