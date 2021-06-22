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
 * A Echeance.
 */
@Entity
@Table(name = "echeance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Echeance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "date_prevu_paiement", nullable = false)
    private LocalDate datePrevuPaiement;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "paye_totalement")
    private Boolean payeTotalement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "organismeAssurance", "sportifs" }, allowSetters = true)
    private Assurance assurance;

    @ManyToOne
    @JsonIgnoreProperties(value = { "templateCertificat", "seance", "sportif" }, allowSetters = true)
    private Certificat certificat;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_echeance__seance",
        joinColumns = @JoinColumn(name = "echeance_id"),
        inverseJoinColumns = @JoinColumn(name = "seance_id")
    )
    @JsonIgnoreProperties(value = { "presences", "typeSeance", "seanceProgramme", "echeances" }, allowSetters = true)
    private Set<Seance> seances = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "utilisateur", "echeances", "certificats", "adhesions", "presences", "pays", "ville", "typeIdentite", "assurances" },
        allowSetters = true
    )
    private Sportif sportif;

    @ManyToMany(mappedBy = "echeances")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "modePaiement", "status", "echeances" }, allowSetters = true)
    private Set<Paiement> paiements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Echeance id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Echeance code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDatePrevuPaiement() {
        return this.datePrevuPaiement;
    }

    public Echeance datePrevuPaiement(LocalDate datePrevuPaiement) {
        this.datePrevuPaiement = datePrevuPaiement;
        return this;
    }

    public void setDatePrevuPaiement(LocalDate datePrevuPaiement) {
        this.datePrevuPaiement = datePrevuPaiement;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Echeance montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean getPayeTotalement() {
        return this.payeTotalement;
    }

    public Echeance payeTotalement(Boolean payeTotalement) {
        this.payeTotalement = payeTotalement;
        return this;
    }

    public void setPayeTotalement(Boolean payeTotalement) {
        this.payeTotalement = payeTotalement;
    }

    public Assurance getAssurance() {
        return this.assurance;
    }

    public Echeance assurance(Assurance assurance) {
        this.setAssurance(assurance);
        return this;
    }

    public void setAssurance(Assurance assurance) {
        this.assurance = assurance;
    }

    public Certificat getCertificat() {
        return this.certificat;
    }

    public Echeance certificat(Certificat certificat) {
        this.setCertificat(certificat);
        return this;
    }

    public void setCertificat(Certificat certificat) {
        this.certificat = certificat;
    }

    public Set<Seance> getSeances() {
        return this.seances;
    }

    public Echeance seances(Set<Seance> seances) {
        this.setSeances(seances);
        return this;
    }

    public Echeance addSeance(Seance seance) {
        this.seances.add(seance);
        seance.getEcheances().add(this);
        return this;
    }

    public Echeance removeSeance(Seance seance) {
        this.seances.remove(seance);
        seance.getEcheances().remove(this);
        return this;
    }

    public void setSeances(Set<Seance> seances) {
        this.seances = seances;
    }

    public Sportif getSportif() {
        return this.sportif;
    }

    public Echeance sportif(Sportif sportif) {
        this.setSportif(sportif);
        return this;
    }

    public void setSportif(Sportif sportif) {
        this.sportif = sportif;
    }

    public Set<Paiement> getPaiements() {
        return this.paiements;
    }

    public Echeance paiements(Set<Paiement> paiements) {
        this.setPaiements(paiements);
        return this;
    }

    public Echeance addPaiement(Paiement paiement) {
        this.paiements.add(paiement);
        paiement.getEcheances().add(this);
        return this;
    }

    public Echeance removePaiement(Paiement paiement) {
        this.paiements.remove(paiement);
        paiement.getEcheances().remove(this);
        return this;
    }

    public void setPaiements(Set<Paiement> paiements) {
        if (this.paiements != null) {
            this.paiements.forEach(i -> i.removeEcheance(this));
        }
        if (paiements != null) {
            paiements.forEach(i -> i.addEcheance(this));
        }
        this.paiements = paiements;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Echeance)) {
            return false;
        }
        return id != null && id.equals(((Echeance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Echeance{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", datePrevuPaiement='" + getDatePrevuPaiement() + "'" +
            ", montant=" + getMontant() +
            ", payeTotalement='" + getPayeTotalement() + "'" +
            "}";
    }
}
