package com.arca.database.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "import_status")
public class ImportStatus {

  @Id
  private String propName;

  private long lineNumber;

  public ImportStatus() {
  }

  public ImportStatus(String propName, long lineNumber) {
    this.propName = propName;
    this.lineNumber = lineNumber;
  }

  public long getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(long lineNumber) {
    this.lineNumber = lineNumber;
  }

  public String getPropName() {
    return propName;
  }

  public void setPropName(String propName) {
    this.propName = propName;
  }
}
