package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Presence.
 */
@Entity
@Table(name = "presence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Presence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "heure_debut")
    private String heureDebut;

    @Column(name = "heure_fin")
    private String heureFin;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Role role;

    @ManyToOne
    @JsonIgnoreProperties(value = { "presences", "typeSeance", "seanceProgramme", "echeances" }, allowSetters = true)
    private Seance seance;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "utilisateur", "echeances", "certificats", "adhesions", "presences", "pays", "ville", "typeIdentite", "assurances" },
        allowSetters = true
    )
    private Sportif sportif;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Presence id(Long id) {
        this.id = id;
        return this;
    }

    public String getHeureDebut() {
        return this.heureDebut;
    }

    public Presence heureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
        return this;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return this.heureFin;
    }

    public Presence heureFin(String heureFin) {
        this.heureFin = heureFin;
        return this;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getDescription() {
        return this.description;
    }

    public Presence description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role getRole() {
        return this.role;
    }

    public Presence role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Seance getSeance() {
        return this.seance;
    }

    public Presence seance(Seance seance) {
        this.setSeance(seance);
        return this;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public Sportif getSportif() {
        return this.sportif;
    }

    public Presence sportif(Sportif sportif) {
        this.setSportif(sportif);
        return this;
    }

    public void setSportif(Sportif sportif) {
        this.sportif = sportif;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Presence)) {
            return false;
        }
        return id != null && id.equals(((Presence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Presence{" +
            "id=" + getId() +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
