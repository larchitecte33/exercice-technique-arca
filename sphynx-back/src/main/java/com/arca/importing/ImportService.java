package com.arca.importing;

import com.arca.aggregation.AggregateService;
import com.arca.aggregation.model.Aggregate;
import com.arca.database.RecordRepositoryService;
import com.arca.importing.model.Record;
import com.arca.messaging.WebsocketMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ImportService {

  private DataFileFactory dataFileFactory;
  private RecordRepositoryService recordRepositoryService;
  private WebsocketMessaging websocketMessaging;
  private ImportStatusService importStatusService;
  private AggregateService aggregateService;

  private boolean isImporting = false;
  private Long totalNbLines = null;

  @Autowired
  public ImportService(DataFileFactory dataFileFactory,
                       RecordRepositoryService recordRepositoryService,
                       WebsocketMessaging websocketMessaging,
                       ImportStatusService importStatusService,
                       AggregateService aggregateService) {
    this.dataFileFactory = dataFileFactory;
    this.recordRepositoryService = recordRepositoryService;
    this.websocketMessaging = websocketMessaging;
    this.importStatusService = importStatusService;
    this.aggregateService = aggregateService;
  }

  public long getNbLines() {
    if (totalNbLines != null) { // cache the value if known, as reading the number of lines can take some time
      return totalNbLines;
    }

    DataFile dataFile = dataFileFactory.createFromProperties();
    checkThatDataFileExists(dataFile);

    try {
      totalNbLines = Files.lines(dataFile.getPath()).count();
      return totalNbLines;
    } catch (IOException e) {
      throw new ImportException("The data file exists, but we could not read its contents at the following path: " + dataFile.getPath());
    }
  }

  private void checkThatDataFileExists(DataFile dataFile) {
    if (!dataFile.exist()) {
      throw new ImportException("The data file does not exists at the specified path: " + dataFile.getPath());
    }
  }

  public void startImport() {
    isImporting = true;

    // On crée un DataFile représentant un fichier à partir du chemin du fichier lu dans application.properties.
    DataFile dataFile = dataFileFactory.createFromProperties();
    checkThatDataFileExists(dataFile);

    // On va chercher le nombre de lignes déjà importées.
    long lineNumber = importStatusService.getImportLineNumber();
    // On va chercher les nombre de lignes total du fichier.
    long totalNbLines = getNbLines();

    try (BufferedReader bufr = Files.newBufferedReader(dataFile.getPath())) {

      // On se place sur la première ligne non importée du fichier.
      for (int i = 0; i < lineNumber && (bufr.readLine() != null) && isImporting; i++) {
      }

      String line;
      ArrayList<Record> records = new ArrayList<>();

      // Tant qu'il reste des lignes à lire
      while ((line = bufr.readLine()) != null && isImporting) {
        // On construit un Record à partir de la ligne lue
        Record rec = Record.from(line);

        if (lineNumber % (totalNbLines / (totalNbLines / 100)) == 0) { // 1000000
          websocketMessaging.sendProgress(totalNbLines, lineNumber + 1);
        }

        // On ajoute un Aggregate à la Map aggregates contenue dans aggregation.model.AggrgateService.
        aggregateService.add(Aggregate.from(rec));
        records.add(rec);

        if(lineNumber % (totalNbLines / 100) == 0) { // 1000000
          recordRepositoryService.saveAll(records);
          importStatusService.saveImportLineNumber(lineNumber);
          records.clear();
        }

        lineNumber++;
      }
    } catch (IOException e) {
      throw new ImportException("We could not read the datafile contents at the following path: " + dataFile.getPath());
    }

    if(lineNumber >= totalNbLines) {
      websocketMessaging.sendProgress(totalNbLines, totalNbLines);
    }

    System.out.println("L'import est terminé.");
  }

  public void stopImport() {
    isImporting = false;
  }

  public void emptyDatabase() {
    recordRepositoryService.emptyDatabase();
    importStatusService.saveImportLineNumber(0);
    websocketMessaging.sendProgress(0, 0);
  }
}
