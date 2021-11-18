package com.arca.database.model;

import com.arca.database.RepositoryException;

import javax.persistence.*;

// Classe métier représentant un enregistrement.
@Entity (name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id; // L'identifiant de l'enregistrement.

    @Column(name = "timestamp")
    private long timestamp; // Le timestamp de l'enregistrement.

    @Column(name = "value")
    private int value; // La valeur entière dans l'enregistrement.

    @Embedded
    @Column(name = "country")
    private Country country; // Le Pays dans l'enregistrement.

    // Fonction qui permet de créer un objet database.model.Record à partir d'un objet importing.model.Record.
    public static Record from (com.arca.importing.model.Record rec) {
        if (rec == null) {
            throw new RepositoryException("The data to create this record is null.");
        }

        Record record = new Record();
        Country country = Country.from(rec.getCountry());
        record.setCountry(country);
        record.setTimestamp(rec.getTimestamp());
        record.setValue(rec.getValue());

        return record;
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
