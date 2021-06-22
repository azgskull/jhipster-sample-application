package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.SeanceProgramme} entity.
 */
public class SeanceProgrammeDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String nom;

    private String description;

    private String programmeExpression;

    private Double duree;

    private DisciplineDTO discipline;

    private SalleDTO salle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getProgrammeExpression() {
        return programmeExpression;
    }

    public void setProgrammeExpression(String programmeExpression) {
        this.programmeExpression = programmeExpression;
    }

    public Double getDuree() {
        return duree;
    }

    public void setDuree(Double duree) {
        this.duree = duree;
    }

    public DisciplineDTO getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineDTO discipline) {
        this.discipline = discipline;
    }

    public SalleDTO getSalle() {
        return salle;
    }

    public void setSalle(SalleDTO salle) {
        this.salle = salle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeanceProgrammeDTO)) {
            return false;
        }

        SeanceProgrammeDTO seanceProgrammeDTO = (SeanceProgrammeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, seanceProgrammeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeanceProgrammeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", programmeExpression='" + getProgrammeExpression() + "'" +
            ", duree=" + getDuree() +
            ", discipline=" + getDiscipline() +
            ", salle=" + getSalle() +
            "}";
    }
}
