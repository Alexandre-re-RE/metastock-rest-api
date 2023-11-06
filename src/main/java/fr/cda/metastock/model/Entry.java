package fr.cda.metastock.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("L")
public class Entry extends Movement {
}
