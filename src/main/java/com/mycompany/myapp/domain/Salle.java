package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Salle.
 */
@Entity
@Table(name = "salle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Salle implements Serializable {

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

    @Column(name = "site_web")
    private String siteWeb;

    @Column(name = "adresse")
    private String adresse;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(mappedBy = "salle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "seances", "adhesions", "discipline", "salle" }, allowSetters = true)
    private Set<SeanceProgramme> seanceProgrammes = new HashSet<>();

    @OneToMany(mappedBy = "salle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateur", "typeIdentite", "salle" }, allowSetters = true)
    private Set<Employe> employes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "villes" }, allowSetters = true)
    private Pays pays;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays" }, allowSetters = true)
    private Ville ville;

    @ManyToOne
    @JsonIgnoreProperties(value = { "salles", "pays", "ville", "disciplines" }, allowSetters = true)
    private Structure structure;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Salle id(Long id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Salle nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Salle description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteWeb() {
        return this.siteWeb;
    }

    public Salle siteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
        return this;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Salle adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Salle logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Salle logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getEmail() {
        return this.email;
    }

    public Salle email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Salle telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<SeanceProgramme> getSeanceProgrammes() {
        return this.seanceProgrammes;
    }

    public Salle seanceProgrammes(Set<SeanceProgramme> seanceProgrammes) {
        this.setSeanceProgrammes(seanceProgrammes);
        return this;
    }

    public Salle addSeanceProgramme(SeanceProgramme seanceProgramme) {
        this.seanceProgrammes.add(seanceProgramme);
        seanceProgramme.setSalle(this);
        return this;
    }

    public Salle removeSeanceProgramme(SeanceProgramme seanceProgramme) {
        this.seanceProgrammes.remove(seanceProgramme);
        seanceProgramme.setSalle(null);
        return this;
    }

    public void setSeanceProgrammes(Set<SeanceProgramme> seanceProgrammes) {
        if (this.seanceProgrammes != null) {
            this.seanceProgrammes.forEach(i -> i.setSalle(null));
        }
        if (seanceProgrammes != null) {
            seanceProgrammes.forEach(i -> i.setSalle(this));
        }
        this.seanceProgrammes = seanceProgrammes;
    }

    public Set<Employe> getEmployes() {
        return this.employes;
    }

    public Salle employes(Set<Employe> employes) {
        this.setEmployes(employes);
        return this;
    }

    public Salle addEmploye(Employe employe) {
        this.employes.add(employe);
        employe.setSalle(this);
        return this;
    }

    public Salle removeEmploye(Employe employe) {
        this.employes.remove(employe);
        employe.setSalle(null);
        return this;
    }

    public void setEmployes(Set<Employe> employes) {
        if (this.employes != null) {
            this.employes.forEach(i -> i.setSalle(null));
        }
        if (employes != null) {
            employes.forEach(i -> i.setSalle(this));
        }
        this.employes = employes;
    }

    public Pays getPays() {
        return this.pays;
    }

    public Salle pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Ville getVille() {
        return this.ville;
    }

    public Salle ville(Ville ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public Salle structure(Structure structure) {
        this.setStructure(structure);
        return this;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Salle)) {
            return false;
        }
        return id != null && id.equals(((Salle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Salle{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", siteWeb='" + getSiteWeb() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }
}
