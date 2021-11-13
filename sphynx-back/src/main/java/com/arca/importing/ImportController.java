package com.arca.importing;

import com.arca.configuration.SphynxProperties;
import com.arca.messaging.WebsocketMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
public class ImportController {

  @Autowired
  private ImportService importService;

  @Autowired
  private WebsocketMessaging websocketMessaging;

  @GetMapping("/start")
  public void startImport() {
    importService.startImport();
  }

  @GetMapping("/stop")
  public void stopImport() {
    importService.stopImport();
  }

  @DeleteMapping("/database")
  public void deleteDatabase() {
    importService.emptyDatabase();
  }

  @GetMapping("/getNbLines")
  public void getNbLines() {
    importService.getNbLines();
  }
}
