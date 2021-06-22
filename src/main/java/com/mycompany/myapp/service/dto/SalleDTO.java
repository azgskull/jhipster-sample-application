package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Salle} entity.
 */
public class SalleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String description;

    private String siteWeb;

    private String adresse;

    @Lob
    private byte[] logo;

    private String logoContentType;
    private String email;

    private String telephone;

    private PaysDTO pays;

    private VilleDTO ville;

    private StructureDTO structure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public PaysDTO getPays() {
        return pays;
    }

    public void setPays(PaysDTO pays) {
        this.pays = pays;
    }

    public VilleDTO getVille() {
        return ville;
    }

    public void setVille(VilleDTO ville) {
        this.ville = ville;
    }

    public StructureDTO getStructure() {
        return structure;
    }

    public void setStructure(StructureDTO structure) {
        this.structure = structure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalleDTO)) {
            return false;
        }

        SalleDTO salleDTO = (SalleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalleDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", siteWeb='" + getSiteWeb() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", logo='" + getLogo() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", pays=" + getPays() +
            ", ville=" + getVille() +
            ", structure=" + getStructure() +
            "}";
    }
}
