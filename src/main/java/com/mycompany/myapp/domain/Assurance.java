package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Assurance.
 */
@Entity
@Table(name = "assurance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Assurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "details")
    private String details;

    @ManyToOne
    private OrganismeAssurance organismeAssurance;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_assurance__sportif",
        joinColumns = @JoinColumn(name = "assurance_id"),
        inverseJoinColumns = @JoinColumn(name = "sportif_id")
    )
    @JsonIgnoreProperties(
        value = { "utilisateur", "echeances", "certificats", "adhesions", "presences", "pays", "ville", "typeIdentite", "assurances" },
        allowSetters = true
    )
    private Set<Sportif> sportifs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Assurance id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Assurance code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Assurance dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Assurance dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getDetails() {
        return this.details;
    }

    public Assurance details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public OrganismeAssurance getOrganismeAssurance() {
        return this.organismeAssurance;
    }

    public Assurance organismeAssurance(OrganismeAssurance organismeAssurance) {
        this.setOrganismeAssurance(organismeAssurance);
        return this;
    }

    public void setOrganismeAssurance(OrganismeAssurance organismeAssurance) {
        this.organismeAssurance = organismeAssurance;
    }

    public Set<Sportif> getSportifs() {
        return this.sportifs;
    }

    public Assurance sportifs(Set<Sportif> sportifs) {
        this.setSportifs(sportifs);
        return this;
    }

    public Assurance addSportif(Sportif sportif) {
        this.sportifs.add(sportif);
        sportif.getAssurances().add(this);
        return this;
    }

    public Assurance removeSportif(Sportif sportif) {
        this.sportifs.remove(sportif);
        sportif.getAssurances().remove(this);
        return this;
    }

    public void setSportifs(Set<Sportif> sportifs) {
        this.sportifs = sportifs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assurance)) {
            return false;
        }
        return id != null && id.equals(((Assurance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Assurance{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
