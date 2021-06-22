package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Echeance} entity.
 */
public class EcheanceDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private LocalDate datePrevuPaiement;

    @NotNull
    private Double montant;

    private Boolean payeTotalement;

    private AssuranceDTO assurance;

    private CertificatDTO certificat;

    private Set<SeanceDTO> seances = new HashSet<>();

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

    public LocalDate getDatePrevuPaiement() {
        return datePrevuPaiement;
    }

    public void setDatePrevuPaiement(LocalDate datePrevuPaiement) {
        this.datePrevuPaiement = datePrevuPaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean getPayeTotalement() {
        return payeTotalement;
    }

    public void setPayeTotalement(Boolean payeTotalement) {
        this.payeTotalement = payeTotalement;
    }

    public AssuranceDTO getAssurance() {
        return assurance;
    }

    public void setAssurance(AssuranceDTO assurance) {
        this.assurance = assurance;
    }

    public CertificatDTO getCertificat() {
        return certificat;
    }

    public void setCertificat(CertificatDTO certificat) {
        this.certificat = certificat;
    }

    public Set<SeanceDTO> getSeances() {
        return seances;
    }

    public void setSeances(Set<SeanceDTO> seances) {
        this.seances = seances;
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
        if (!(o instanceof EcheanceDTO)) {
            return false;
        }

        EcheanceDTO echeanceDTO = (EcheanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, echeanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EcheanceDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", datePrevuPaiement='" + getDatePrevuPaiement() + "'" +
            ", montant=" + getMontant() +
            ", payeTotalement='" + getPayeTotalement() + "'" +
            ", assurance=" + getAssurance() +
            ", certificat=" + getCertificat() +
            ", seances=" + getSeances() +
            ", sportif=" + getSportif() +
            "}";
    }
}
