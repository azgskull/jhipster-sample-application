package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SeanceProgramme.
 */
@Entity
@Table(name = "seance_programme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SeanceProgramme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "programme_expression")
    private String programmeExpression;

    @Column(name = "duree")
    private Double duree;

    @OneToMany(mappedBy = "seanceProgramme")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "presences", "typeSeance", "seanceProgramme", "echeances" }, allowSetters = true)
    private Set<Seance> seances = new HashSet<>();

    @OneToMany(mappedBy = "seanceProgramme")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "role", "seanceProgramme", "sportif" }, allowSetters = true)
    private Set<Adhesion> adhesions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "disciplines" }, allowSetters = true)
    private Discipline discipline;

    @ManyToOne
    @JsonIgnoreProperties(value = { "seanceProgrammes", "employes", "pays", "ville", "structure" }, allowSetters = true)
    private Salle salle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeanceProgramme id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public SeanceProgramme code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return this.nom;
    }

    public SeanceProgramme nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public SeanceProgramme description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgrammeExpression() {
        return this.programmeExpression;
    }

    public SeanceProgramme programmeExpression(String programmeExpression) {
        this.programmeExpression = programmeExpression;
        return this;
    }

    public void setProgrammeExpression(String programmeExpression) {
        this.programmeExpression = programmeExpression;
    }

    public Double getDuree() {
        return this.duree;
    }

    public SeanceProgramme duree(Double duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Double duree) {
        this.duree = duree;
    }

    public Set<Seance> getSeances() {
        return this.seances;
    }

    public SeanceProgramme seances(Set<Seance> seances) {
        this.setSeances(seances);
        return this;
    }

    public SeanceProgramme addSeance(Seance seance) {
        this.seances.add(seance);
        seance.setSeanceProgramme(this);
        return this;
    }

    public SeanceProgramme removeSeance(Seance seance) {
        this.seances.remove(seance);
        seance.setSeanceProgramme(null);
        return this;
    }

    public void setSeances(Set<Seance> seances) {
        if (this.seances != null) {
            this.seances.forEach(i -> i.setSeanceProgramme(null));
        }
        if (seances != null) {
            seances.forEach(i -> i.setSeanceProgramme(this));
        }
        this.seances = seances;
    }

    public Set<Adhesion> getAdhesions() {
        return this.adhesions;
    }

    public SeanceProgramme adhesions(Set<Adhesion> adhesions) {
        this.setAdhesions(adhesions);
        return this;
    }

    public SeanceProgramme addAdhesion(Adhesion adhesion) {
        this.adhesions.add(adhesion);
        adhesion.setSeanceProgramme(this);
        return this;
    }

    public SeanceProgramme removeAdhesion(Adhesion adhesion) {
        this.adhesions.remove(adhesion);
        adhesion.setSeanceProgramme(null);
        return this;
    }

    public void setAdhesions(Set<Adhesion> adhesions) {
        if (this.adhesions != null) {
            this.adhesions.forEach(i -> i.setSeanceProgramme(null));
        }
        if (adhesions != null) {
            adhesions.forEach(i -> i.setSeanceProgramme(this));
        }
        this.adhesions = adhesions;
    }

    public Discipline getDiscipline() {
        return this.discipline;
    }

    public SeanceProgramme discipline(Discipline discipline) {
        this.setDiscipline(discipline);
        return this;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Salle getSalle() {
        return this.salle;
    }

    public SeanceProgramme salle(Salle salle) {
        this.setSalle(salle);
        return this;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeanceProgramme)) {
            return false;
        }
        return id != null && id.equals(((SeanceProgramme) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeanceProgramme{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", programmeExpression='" + getProgrammeExpression() + "'" +
            ", duree=" + getDuree() +
            "}";
    }
}
