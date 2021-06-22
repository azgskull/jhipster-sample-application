package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Adhesion} entity.
 */
public class AdhesionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateDebut;

    private LocalDate dateFin;

    private RoleDTO role;

    private SeanceProgrammeDTO seanceProgramme;

    private SportifDTO sportif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public SeanceProgrammeDTO getSeanceProgramme() {
        return seanceProgramme;
    }

    public void setSeanceProgramme(SeanceProgrammeDTO seanceProgramme) {
        this.seanceProgramme = seanceProgramme;
    }

    public SportifDTO getSportif() {
        return sportif;
    }

    public void setSportif(SportifDTO sportif) {
        this.sportif = sportif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdhesionDTO)) {
            return false;
        }

        AdhesionDTO adhesionDTO = (AdhesionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adhesionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdhesionDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", role=" + getRole() +
            ", seanceProgramme=" + getSeanceProgramme() +
            ", sportif=" + getSportif() +
            "}";
    }
}
