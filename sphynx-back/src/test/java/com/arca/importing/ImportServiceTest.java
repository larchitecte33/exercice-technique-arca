package com.arca.importing;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImportServiceTest {

    @Mock
    private DataFileFactory dataFileFactory;

    @InjectMocks
    private ImportService importService;

    private FileSystem fs = Jimfs.newFileSystem(Configuration.unix());

    @Test
    public void getNbLinesOnExistingFile() throws IOException {
        DataFile dataFile = createDatafileWithNbLines(2);
        when(dataFileFactory.createFromProperties()).thenReturn(dataFile);

        long nbLines = importService.getNbLines();

        Assertions.assertThat(nbLines).isEqualTo(2);
    }
    @Test
    public void getNbLinesOnLargeExistingFile() throws IOException {
        DataFile dataFile = createDatafileWithNbLines(100000);
        when(dataFileFactory.createFromProperties()).thenReturn(dataFile);

        long nbLines = importService.getNbLines();

        Assertions.assertThat(nbLines).isEqualTo(100000L);
    }

    private DataFile createDatafileWithNbLines(long nbLines) throws IOException {
        Path foo = fs.getPath("/foo");
        List<String> lines =  new ArrayList<>();
        for (int i = 0; i < nbLines; i++) {
            lines.add("toto");
        }
        Files.write(foo,lines);
        return new DataFile(foo);
    }

    @Test(expected = ImportException.class)
    public void getNbLinesOnMissingFilePath() throws IOException {
        DataFile dataFile = new DataFile((Path) null);
        when(dataFileFactory.createFromProperties()).thenReturn(dataFile);

        importService.getNbLines();
    }

    @Test(expected = ImportException.class)
    public void getNbLinesOnMissingFilePathAsString() throws IOException {
        DataFile dataFile = new DataFile((String) null);
        when(dataFileFactory.createFromProperties()).thenReturn(dataFile);

        importService.getNbLines();
    }
}