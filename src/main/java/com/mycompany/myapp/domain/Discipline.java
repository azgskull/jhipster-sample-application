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
 * A Discipline.
 */
@Entity
@Table(name = "discipline")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Discipline implements Serializable {

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

    @Column(name = "photo")
    private String photo;

    @ManyToMany(mappedBy = "disciplines")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "salles", "pays", "ville", "disciplines" }, allowSetters = true)
    private Set<Structure> disciplines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Discipline id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Discipline code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return this.nom;
    }

    public Discipline nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Discipline description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return this.photo;
    }

    public Discipline photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Structure> getDisciplines() {
        return this.disciplines;
    }

    public Discipline disciplines(Set<Structure> structures) {
        this.setDisciplines(structures);
        return this;
    }

    public Discipline addDiscipline(Structure structure) {
        this.disciplines.add(structure);
        structure.getDisciplines().add(this);
        return this;
    }

    public Discipline removeDiscipline(Structure structure) {
        this.disciplines.remove(structure);
        structure.getDisciplines().remove(this);
        return this;
    }

    public void setDisciplines(Set<Structure> structures) {
        if (this.disciplines != null) {
            this.disciplines.forEach(i -> i.removeDiscipline(this));
        }
        if (structures != null) {
            structures.forEach(i -> i.addDiscipline(this));
        }
        this.disciplines = structures;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Discipline)) {
            return false;
        }
        return id != null && id.equals(((Discipline) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Discipline{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", photo='" + getPhoto() + "'" +
            "}";
    }
}
