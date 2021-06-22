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
 * A Sportif.
 */
@Entity
@Table(name = "sportif")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sportif implements Serializable {

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

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "photo")
    private String photo;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "numero_identite")
    private String numeroIdentite;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private User utilisateur;

    @OneToMany(mappedBy = "sportif")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assurance", "certificat", "seances", "sportif", "paiements" }, allowSetters = true)
    private Set<Echeance> echeances = new HashSet<>();

    @OneToMany(mappedBy = "sportif")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "templateCertificat", "seance", "sportif" }, allowSetters = true)
    private Set<Certificat> certificats = new HashSet<>();

    @OneToMany(mappedBy = "sportif")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "role", "seanceProgramme", "sportif" }, allowSetters = true)
    private Set<Adhesion> adhesions = new HashSet<>();

    @OneToMany(mappedBy = "sportif")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "role", "seance", "sportif" }, allowSetters = true)
    private Set<Presence> presences = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "villes" }, allowSetters = true)
    private Pays pays;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays" }, allowSetters = true)
    private Ville ville;

    @ManyToOne
    private TypeIdentite typeIdentite;

    @ManyToMany(mappedBy = "sportifs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organismeAssurance", "sportifs" }, allowSetters = true)
    private Set<Assurance> assurances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sportif id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Sportif code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return this.nom;
    }

    public Sportif nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Sportif prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhoto() {
        return this.photo;
    }

    public Sportif photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public Sportif dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNumeroIdentite() {
        return this.numeroIdentite;
    }

    public Sportif numeroIdentite(String numeroIdentite) {
        this.numeroIdentite = numeroIdentite;
        return this;
    }

    public void setNumeroIdentite(String numeroIdentite) {
        this.numeroIdentite = numeroIdentite;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Sportif adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Sportif telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Sportif email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUtilisateur() {
        return this.utilisateur;
    }

    public Sportif utilisateur(User user) {
        this.setUtilisateur(user);
        return this;
    }

    public void setUtilisateur(User user) {
        this.utilisateur = user;
    }

    public Set<Echeance> getEcheances() {
        return this.echeances;
    }

    public Sportif echeances(Set<Echeance> echeances) {
        this.setEcheances(echeances);
        return this;
    }

    public Sportif addEcheance(Echeance echeance) {
        this.echeances.add(echeance);
        echeance.setSportif(this);
        return this;
    }

    public Sportif removeEcheance(Echeance echeance) {
        this.echeances.remove(echeance);
        echeance.setSportif(null);
        return this;
    }

    public void setEcheances(Set<Echeance> echeances) {
        if (this.echeances != null) {
            this.echeances.forEach(i -> i.setSportif(null));
        }
        if (echeances != null) {
            echeances.forEach(i -> i.setSportif(this));
        }
        this.echeances = echeances;
    }

    public Set<Certificat> getCertificats() {
        return this.certificats;
    }

    public Sportif certificats(Set<Certificat> certificats) {
        this.setCertificats(certificats);
        return this;
    }

    public Sportif addCertificat(Certificat certificat) {
        this.certificats.add(certificat);
        certificat.setSportif(this);
        return this;
    }

    public Sportif removeCertificat(Certificat certificat) {
        this.certificats.remove(certificat);
        certificat.setSportif(null);
        return this;
    }

    public void setCertificats(Set<Certificat> certificats) {
        if (this.certificats != null) {
            this.certificats.forEach(i -> i.setSportif(null));
        }
        if (certificats != null) {
            certificats.forEach(i -> i.setSportif(this));
        }
        this.certificats = certificats;
    }

    public Set<Adhesion> getAdhesions() {
        return this.adhesions;
    }

    public Sportif adhesions(Set<Adhesion> adhesions) {
        this.setAdhesions(adhesions);
        return this;
    }

    public Sportif addAdhesion(Adhesion adhesion) {
        this.adhesions.add(adhesion);
        adhesion.setSportif(this);
        return this;
    }

    public Sportif removeAdhesion(Adhesion adhesion) {
        this.adhesions.remove(adhesion);
        adhesion.setSportif(null);
        return this;
    }

    public void setAdhesions(Set<Adhesion> adhesions) {
        if (this.adhesions != null) {
            this.adhesions.forEach(i -> i.setSportif(null));
        }
        if (adhesions != null) {
            adhesions.forEach(i -> i.setSportif(this));
        }
        this.adhesions = adhesions;
    }

    public Set<Presence> getPresences() {
        return this.presences;
    }

    public Sportif presences(Set<Presence> presences) {
        this.setPresences(presences);
        return this;
    }

    public Sportif addPresence(Presence presence) {
        this.presences.add(presence);
        presence.setSportif(this);
        return this;
    }

    public Sportif removePresence(Presence presence) {
        this.presences.remove(presence);
        presence.setSportif(null);
        return this;
    }

    public void setPresences(Set<Presence> presences) {
        if (this.presences != null) {
            this.presences.forEach(i -> i.setSportif(null));
        }
        if (presences != null) {
            presences.forEach(i -> i.setSportif(this));
        }
        this.presences = presences;
    }

    public Pays getPays() {
        return this.pays;
    }

    public Sportif pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Ville getVille() {
        return this.ville;
    }

    public Sportif ville(Ville ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public TypeIdentite getTypeIdentite() {
        return this.typeIdentite;
    }

    public Sportif typeIdentite(TypeIdentite typeIdentite) {
        this.setTypeIdentite(typeIdentite);
        return this;
    }

    public void setTypeIdentite(TypeIdentite typeIdentite) {
        this.typeIdentite = typeIdentite;
    }

    public Set<Assurance> getAssurances() {
        return this.assurances;
    }

    public Sportif assurances(Set<Assurance> assurances) {
        this.setAssurances(assurances);
        return this;
    }

    public Sportif addAssurance(Assurance assurance) {
        this.assurances.add(assurance);
        assurance.getSportifs().add(this);
        return this;
    }

    public Sportif removeAssurance(Assurance assurance) {
        this.assurances.remove(assurance);
        assurance.getSportifs().remove(this);
        return this;
    }

    public void setAssurances(Set<Assurance> assurances) {
        if (this.assurances != null) {
            this.assurances.forEach(i -> i.removeSportif(this));
        }
        if (assurances != null) {
            assurances.forEach(i -> i.addSportif(this));
        }
        this.assurances = assurances;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sportif)) {
            return false;
        }
        return id != null && id.equals(((Sportif) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sportif{" +
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
            "}";
    }
}
