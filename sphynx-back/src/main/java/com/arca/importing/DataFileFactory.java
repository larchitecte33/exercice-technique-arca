package com.arca.importing;

import com.arca.configuration.SphynxProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataFileFactory {

    @Autowired
    private SphynxProperties props;

    // On crée un DataFile représentant un fichier à partir du chemin du fichier lu dans application.properties.
    public DataFile createFromProperties () {
        return new DataFile(props.getData().getFilePath());
    }

}
