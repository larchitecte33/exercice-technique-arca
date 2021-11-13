package com.arca.importing.model;

import javax.persistence.Entity;
import javax.persistence.Id;

// Classe représentant un pays.
public class Country {

    // Nom du pays
    private String name;

    // Constructeur
    public Country(String name) {
        this.name = name;
    }

    // Accesseurs
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
