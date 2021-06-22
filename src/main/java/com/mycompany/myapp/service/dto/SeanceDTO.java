package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Seance} entity.
 */
public class SeanceDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String description;

    @NotNull
    private LocalDate date;

    private String heureDebut;

    private String heureFin;

    private TypeSeanceDTO typeSeance;

    private SeanceProgrammeDTO seanceProgramme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public TypeSeanceDTO getTypeSeance() {
        return typeSeance;
    }

    public void setTypeSeance(TypeSeanceDTO typeSeance) {
        this.typeSeance = typeSeance;
    }

    public SeanceProgrammeDTO getSeanceProgramme() {
        return seanceProgramme;
    }

    public void setSeanceProgramme(SeanceProgrammeDTO seanceProgramme) {
        this.seanceProgramme = seanceProgramme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeanceDTO)) {
            return false;
        }

        SeanceDTO seanceDTO = (SeanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, seanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeanceDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", typeSeance=" + getTypeSeance() +
            ", seanceProgramme=" + getSeanceProgramme() +
            "}";
    }
}
