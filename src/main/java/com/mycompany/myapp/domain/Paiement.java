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
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private ModePaiement modePaiement;

    @ManyToOne
    private PaiementStatus status;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_paiement__echeance",
        joinColumns = @JoinColumn(name = "paiement_id"),
        inverseJoinColumns = @JoinColumn(name = "echeance_id")
    )
    @JsonIgnoreProperties(value = { "assurance", "certificat", "seances", "sportif", "paiements" }, allowSetters = true)
    private Set<Echeance> echeances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paiement id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Paiement code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Paiement date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Paiement montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDescription() {
        return this.description;
    }

    public Paiement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModePaiement getModePaiement() {
        return this.modePaiement;
    }

    public Paiement modePaiement(ModePaiement modePaiement) {
        this.setModePaiement(modePaiement);
        return this;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public PaiementStatus getStatus() {
        return this.status;
    }

    public Paiement status(PaiementStatus paiementStatus) {
        this.setStatus(paiementStatus);
        return this;
    }

    public void setStatus(PaiementStatus paiementStatus) {
        this.status = paiementStatus;
    }

    public Set<Echeance> getEcheances() {
        return this.echeances;
    }

    public Paiement echeances(Set<Echeance> echeances) {
        this.setEcheances(echeances);
        return this;
    }

    public Paiement addEcheance(Echeance echeance) {
        this.echeances.add(echeance);
        echeance.getPaiements().add(this);
        return this;
    }

    public Paiement removeEcheance(Echeance echeance) {
        this.echeances.remove(echeance);
        echeance.getPaiements().remove(this);
        return this;
    }

    public void setEcheances(Set<Echeance> echeances) {
        this.echeances = echeances;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return id != null && id.equals(((Paiement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            ", montant=" + getMontant() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
