package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Adhesion.
 */
@Entity
@Table(name = "adhesion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Adhesion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @ManyToOne
    private Role role;

    @ManyToOne
    @JsonIgnoreProperties(value = { "seances", "adhesions", "discipline", "salle" }, allowSetters = true)
    private SeanceProgramme seanceProgramme;

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

    public Adhesion id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Adhesion dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Adhesion dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Role getRole() {
        return this.role;
    }

    public Adhesion role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public SeanceProgramme getSeanceProgramme() {
        return this.seanceProgramme;
    }

    public Adhesion seanceProgramme(SeanceProgramme seanceProgramme) {
        this.setSeanceProgramme(seanceProgramme);
        return this;
    }

    public void setSeanceProgramme(SeanceProgramme seanceProgramme) {
        this.seanceProgramme = seanceProgramme;
    }

    public Sportif getSportif() {
        return this.sportif;
    }

    public Adhesion sportif(Sportif sportif) {
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
        if (!(o instanceof Adhesion)) {
            return false;
        }
        return id != null && id.equals(((Adhesion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adhesion{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
