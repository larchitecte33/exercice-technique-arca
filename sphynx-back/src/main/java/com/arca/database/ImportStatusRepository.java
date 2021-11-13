package com.arca.database;

import com.arca.database.model.ImportStatus;
import org.springframework.data.repository.CrudRepository;

public interface ImportStatusRepository extends CrudRepository<ImportStatus, String> {
}
