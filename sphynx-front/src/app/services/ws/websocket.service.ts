import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {ProgressListener} from "./ProgressListener";
import {ProgressDto} from "../../model/ProgressDto";

@Injectable({
  providedIn: 'root',
})
export class WebsocketService {
  private listeners: ProgressListener[] = [];

  constructor() {
    var socket = new WebSocket(environment.serverWebSocketUrl);

    console.log("test socket");

    socket.onopen = function () {
      console.log('WebSocket connection opened.');
    };

    let that = this;
    socket.onmessage = function (message) {
      let progressJson = JSON.parse(message.data);
      let progressDto = new ProgressDto(progressJson.value, progressJson.total);
      for (let progressListener of that.listeners) {
        progressListener.onProgress(progressDto);
      }
    };
  }

  public addListener (listener : ProgressListener) {
    this.listeners.push(listener);
  }
}
