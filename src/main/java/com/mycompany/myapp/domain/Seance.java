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
 * A Seance.
 */
@Entity
@Table(name = "seance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Seance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "heure_debut")
    private String heureDebut;

    @Column(name = "heure_fin")
    private String heureFin;

    @OneToMany(mappedBy = "seance")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "role", "seance", "sportif" }, allowSetters = true)
    private Set<Presence> presences = new HashSet<>();

    @ManyToOne
    private TypeSeance typeSeance;

    @ManyToOne
    @JsonIgnoreProperties(value = { "seances", "adhesions", "discipline", "salle" }, allowSetters = true)
    private SeanceProgramme seanceProgramme;

    @ManyToMany(mappedBy = "seances")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assurance", "certificat", "seances", "sportif", "paiements" }, allowSetters = true)
    private Set<Echeance> echeances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seance id(Long id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Seance nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Seance description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Seance date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHeureDebut() {
        return this.heureDebut;
    }

    public Seance heureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
        return this;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return this.heureFin;
    }

    public Seance heureFin(String heureFin) {
        this.heureFin = heureFin;
        return this;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public Set<Presence> getPresences() {
        return this.presences;
    }

    public Seance presences(Set<Presence> presences) {
        this.setPresences(presences);
        return this;
    }

    public Seance addPresence(Presence presence) {
        this.presences.add(presence);
        presence.setSeance(this);
        return this;
    }

    public Seance removePresence(Presence presence) {
        this.presences.remove(presence);
        presence.setSeance(null);
        return this;
    }

    public void setPresences(Set<Presence> presences) {
        if (this.presences != null) {
            this.presences.forEach(i -> i.setSeance(null));
        }
        if (presences != null) {
            presences.forEach(i -> i.setSeance(this));
        }
        this.presences = presences;
    }

    public TypeSeance getTypeSeance() {
        return this.typeSeance;
    }

    public Seance typeSeance(TypeSeance typeSeance) {
        this.setTypeSeance(typeSeance);
        return this;
    }

    public void setTypeSeance(TypeSeance typeSeance) {
        this.typeSeance = typeSeance;
    }

    public SeanceProgramme getSeanceProgramme() {
        return this.seanceProgramme;
    }

    public Seance seanceProgramme(SeanceProgramme seanceProgramme) {
        this.setSeanceProgramme(seanceProgramme);
        return this;
    }

    public void setSeanceProgramme(SeanceProgramme seanceProgramme) {
        this.seanceProgramme = seanceProgramme;
    }

    public Set<Echeance> getEcheances() {
        return this.echeances;
    }

    public Seance echeances(Set<Echeance> echeances) {
        this.setEcheances(echeances);
        return this;
    }

    public Seance addEcheance(Echeance echeance) {
        this.echeances.add(echeance);
        echeance.getSeances().add(this);
        return this;
    }

    public Seance removeEcheance(Echeance echeance) {
        this.echeances.remove(echeance);
        echeance.getSeances().remove(this);
        return this;
    }

    public void setEcheances(Set<Echeance> echeances) {
        if (this.echeances != null) {
            this.echeances.forEach(i -> i.removeSeance(this));
        }
        if (echeances != null) {
            echeances.forEach(i -> i.addSeance(this));
        }
        this.echeances = echeances;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seance)) {
            return false;
        }
        return id != null && id.equals(((Seance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seance{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            "}";
    }
}
