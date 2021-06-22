package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Presence} entity.
 */
public class PresenceDTO implements Serializable {

    private Long id;

    private String heureDebut;

    private String heureFin;

    private String description;

    private RoleDTO role;

    private SeanceDTO seance;

    private SportifDTO sportif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public SeanceDTO getSeance() {
        return seance;
    }

    public void setSeance(SeanceDTO seance) {
        this.seance = seance;
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
        if (!(o instanceof PresenceDTO)) {
            return false;
        }

        PresenceDTO presenceDTO = (PresenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, presenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PresenceDTO{" +
            "id=" + getId() +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", description='" + getDescription() + "'" +
            ", role=" + getRole() +
            ", seance=" + getSeance() +
            ", sportif=" + getSportif() +
            "}";
    }
}
