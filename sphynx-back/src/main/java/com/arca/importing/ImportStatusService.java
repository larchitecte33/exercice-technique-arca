package com.arca.importing;

import com.arca.database.ImportStatusRepository;
import com.arca.database.RepositoryException;
import com.arca.database.model.ImportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportStatusService {

  public static final String CURRENT_LINE = "current line";
  private ImportStatusRepository importStatusRepository;

  @Autowired
  public ImportStatusService(ImportStatusRepository importStatusRepository) {
    this.importStatusRepository = importStatusRepository;
  }

  public long getImportLineNumber() {
    return importStatusRepository.findById(CURRENT_LINE)
      .map(importStatus -> importStatus.getLineNumber())
      .orElse(0L);
      //.orElseThrow(() -> new RepositoryException("Impossible to retrieve the current import line number."));
  }

  public void saveImportLineNumber(long lineNumber) {
    ImportStatus importStatus = new ImportStatus(CURRENT_LINE, lineNumber);
    importStatusRepository.save(importStatus);
  }
}
