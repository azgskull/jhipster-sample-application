package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TemplateCertificat} entity.
 */
public class TemplateCertificatDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String nom;

    private String description;

    @Lob
    private byte[] fichier;

    private String fichierContentType;
    private DisciplineDTO discipline;

    private TypeCertificatDTO typeCertificat;

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

    public DisciplineDTO getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineDTO discipline) {
        this.discipline = discipline;
    }

    public TypeCertificatDTO getTypeCertificat() {
        return typeCertificat;
    }

    public void setTypeCertificat(TypeCertificatDTO typeCertificat) {
        this.typeCertificat = typeCertificat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateCertificatDTO)) {
            return false;
        }

        TemplateCertificatDTO templateCertificatDTO = (TemplateCertificatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateCertificatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateCertificatDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", fichier='" + getFichier() + "'" +
            ", discipline=" + getDiscipline() +
            ", typeCertificat=" + getTypeCertificat() +
            "}";
    }
}
