package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Certificat} entity.
 */
public class CertificatDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String nom;

    private String description;

    @NotNull
    private LocalDate date;

    private LocalDate dateFin;

    @Lob
    private byte[] fichier;

    private String fichierContentType;
    private TemplateCertificatDTO templateCertificat;

    private SeanceDTO seance;

    private SportifDTO sportif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    public String getFichierContentType() {
        return fichierContentType;
    }

    public void setFichierContentType(String fichierContentType) {
        this.fichierContentType = fichierContentType;
    }

    public TemplateCertificatDTO getTemplateCertificat() {
        return templateCertificat;
    }

    public void setTemplateCertificat(TemplateCertificatDTO templateCertificat) {
        this.templateCertificat = templateCertificat;
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
        if (!(o instanceof CertificatDTO)) {
            return false;
        }

        CertificatDTO certificatDTO = (CertificatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, certificatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificatDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", fichier='" + getFichier() + "'" +
            ", templateCertificat=" + getTemplateCertificat() +
            ", seance=" + getSeance() +
            ", sportif=" + getSportif() +
            "}";
    }
}
