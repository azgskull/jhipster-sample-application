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
 * A Structure.
 */
@Entity
@Table(name = "structure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Structure implements Serializable {

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

    @OneToMany(mappedBy = "structure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "seanceProgrammes", "employes", "pays", "ville", "structure" }, allowSetters = true)
    private Set<Salle> salles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "villes" }, allowSetters = true)
    private Pays pays;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays" }, allowSetters = true)
    private Ville ville;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_structure__discipline",
        joinColumns = @JoinColumn(name = "structure_id"),
        inverseJoinColumns = @JoinColumn(name = "discipline_id")
    )
    @JsonIgnoreProperties(value = { "disciplines" }, allowSetters = true)
    private Set<Discipline> disciplines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Structure id(Long id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Structure nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Structure description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteWeb() {
        return this.siteWeb;
    }

    public Structure siteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
        return this;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Structure adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public Structure logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public Structure logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getEmail() {
        return this.email;
    }

    public Structure email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Structure telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Salle> getSalles() {
        return this.salles;
    }

    public Structure salles(Set<Salle> salles) {
        this.setSalles(salles);
        return this;
    }

    public Structure addSalle(Salle salle) {
        this.salles.add(salle);
        salle.setStructure(this);
        return this;
    }

    public Structure removeSalle(Salle salle) {
        this.salles.remove(salle);
        salle.setStructure(null);
        return this;
    }

    public void setSalles(Set<Salle> salles) {
        if (this.salles != null) {
            this.salles.forEach(i -> i.setStructure(null));
        }
        if (salles != null) {
            salles.forEach(i -> i.setStructure(this));
        }
        this.salles = salles;
    }

    public Pays getPays() {
        return this.pays;
    }

    public Structure pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Ville getVille() {
        return this.ville;
    }

    public Structure ville(Ville ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Set<Discipline> getDisciplines() {
        return this.disciplines;
    }

    public Structure disciplines(Set<Discipline> disciplines) {
        this.setDisciplines(disciplines);
        return this;
    }

    public Structure addDiscipline(Discipline discipline) {
        this.disciplines.add(discipline);
        discipline.getDisciplines().add(this);
        return this;
    }

    public Structure removeDiscipline(Discipline discipline) {
        this.disciplines.remove(discipline);
        discipline.getDisciplines().remove(this);
        return this;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Structure)) {
            return false;
        }
        return id != null && id.equals(((Structure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Structure{" +
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
