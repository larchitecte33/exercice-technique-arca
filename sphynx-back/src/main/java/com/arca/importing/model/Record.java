package com.arca.importing.model;


import com.arca.importing.ImportException;

import java.util.Arrays;

// Classe qui représente un enregistrement du ficher des pays.
public class Record {

    private long timestamp; // Timestamp de l'enregistrement.
    private int value; // Valeur
    private Country country; // Pays

    public static Record from(String str) {
        // On crée un nouveau Record.
        Record record = new Record();
        String[] split = str.split(",");

        // On vérifie si l'enregistrement split contient au moins trois valeurs. Si ce n'est pas le cas, une exception est levée.
        checkInputStringIsOk(split);

        // On remplit l'objet Record avec les données extraites de str.
        record.setTimestamp(Long.valueOf(split[0]));
        record.setValue(Integer.valueOf(split[1]));
        record.setCountry(new Country(split[2]));

        return record;
    }

    // Vérifie si l'enregistrement split contient au moins trois valeurs.
    private static void checkInputStringIsOk(String[] split) {
        if (split == null || split.length < 3) {
            throw new ImportException("The data to create this record is incorrect: " + Arrays.toString(split));
        }
    }

    // Accesseurs
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
