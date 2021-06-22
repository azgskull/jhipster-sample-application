package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Sportif} entity.
 */
public class SportifDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private String photo;

    private LocalDate dateNaissance;

    private String numeroIdentite;

    private String adresse;

    private String telephone;

    private String email;

    private UserDTO utilisateur;

    private PaysDTO pays;

    private VilleDTO ville;

    private TypeIdentiteDTO typeIdentite;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNumeroIdentite() {
        return numeroIdentite;
    }

    public void setNumeroIdentite(String numeroIdentite) {
        this.numeroIdentite = numeroIdentite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UserDTO utilisateur) {
        this.utilisateur = utilisateur;
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

    public TypeIdentiteDTO getTypeIdentite() {
        return typeIdentite;
    }

    public void setTypeIdentite(TypeIdentiteDTO typeIdentite) {
        this.typeIdentite = typeIdentite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SportifDTO)) {
            return false;
        }

        SportifDTO sportifDTO = (SportifDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sportifDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SportifDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", numeroIdentite='" + getNumeroIdentite() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", utilisateur=" + getUtilisateur() +
            ", pays=" + getPays() +
            ", ville=" + getVille() +
            ", typeIdentite=" + getTypeIdentite() +
            "}";
    }
}
