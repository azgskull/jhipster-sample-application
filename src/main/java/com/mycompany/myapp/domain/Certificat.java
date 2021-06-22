package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Certificat.
 */
@Entity
@Table(name = "certificat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Certificat implements Serializable {

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

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Lob
    @Column(name = "fichier")
    private byte[] fichier;

    @Column(name = "fichier_content_type")
    private String fichierContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "discipline", "typeCertificat" }, allowSetters = true)
    private TemplateCertificat templateCertificat;

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

    public Certificat id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Certificat code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return this.nom;
    }

    public Certificat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Certificat description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Certificat date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Certificat dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public byte[] getFichier() {
        return this.fichier;
    }

    public Certificat fichier(byte[] fichier) {
        this.fichier = fichier;
        return this;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    public String getFichierContentType() {
        return this.fichierContentType;
    }

    public Certificat fichierContentType(String fichierContentType) {
        this.fichierContentType = fichierContentType;
        return this;
    }

    public void setFichierContentType(String fichierContentType) {
        this.fichierContentType = fichierContentType;
    }

    public TemplateCertificat getTemplateCertificat() {
        return this.templateCertificat;
    }

    public Certificat templateCertificat(TemplateCertificat templateCertificat) {
        this.setTemplateCertificat(templateCertificat);
        return this;
    }

    public void setTemplateCertificat(TemplateCertificat templateCertificat) {
        this.templateCertificat = templateCertificat;
    }

    public Seance getSeance() {
        return this.seance;
    }

    public Certificat seance(Seance seance) {
        this.setSeance(seance);
        return this;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public Sportif getSportif() {
        return this.sportif;
    }

    public Certificat sportif(Sportif sportif) {
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
        if (!(o instanceof Certificat)) {
            return false;
        }
        return id != null && id.equals(((Certificat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Certificat{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", fichier='" + getFichier() + "'" +
            ", fichierContentType='" + getFichierContentType() + "'" +
            "}";
    }
}
