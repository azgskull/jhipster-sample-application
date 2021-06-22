package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.OrganismeAssurance} entity.
 */
public class OrganismeAssuranceDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganismeAssuranceDTO)) {
            return false;
        }

        OrganismeAssuranceDTO organismeAssuranceDTO = (OrganismeAssuranceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organismeAssuranceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganismeAssuranceDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
