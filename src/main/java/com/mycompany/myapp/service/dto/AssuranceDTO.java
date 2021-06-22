package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Assurance} entity.
 */
public class AssuranceDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private LocalDate dateDebut;

    @NotNull
    private LocalDate dateFin;

    private String details;

    private OrganismeAssuranceDTO organismeAssurance;

    private Set<SportifDTO> sportifs = new HashSet<>();

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public OrganismeAssuranceDTO getOrganismeAssurance() {
        return organismeAssurance;
    }

    public void setOrganismeAssurance(OrganismeAssuranceDTO organismeAssurance) {
        this.organismeAssurance = organismeAssurance;
    }

    public Set<SportifDTO> getSportifs() {
        return sportifs;
    }

    public void setSportifs(Set<SportifDTO> sportifs) {
        this.sportifs = sportifs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssuranceDTO)) {
            return false;
        }

        AssuranceDTO assuranceDTO = (AssuranceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assuranceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssuranceDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", details='" + getDetails() + "'" +
            ", organismeAssurance=" + getOrganismeAssurance() +
            ", sportifs=" + getSportifs() +
            "}";
    }
}
