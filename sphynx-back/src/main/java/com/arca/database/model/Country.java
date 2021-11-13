package com.arca.database.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;

// Ici, on a @Embeddable car la classe Country est embarquée dans l'entité Record.
@Embeddable
public class Country {

  // Nom du pays
  private String name;

  // Fonction qui permet de créer un objet database.model.Country à partir d'un objet importing.model.Country.
  public static Country from(com.arca.importing.model.Country c) {
    if (c == null) {
      return null;
    }

    Country country = new Country();
    country.setName(c.getName());
    return country;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Country country = (Country) o;
    return Objects.equals(name, country.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
