package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Paiement} entity.
 */
public class PaiementDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private LocalDate date;

    @NotNull
    private Double montant;

    private String description;

    private ModePaiementDTO modePaiement;

    private PaiementStatusDTO status;

    private Set<EcheanceDTO> echeances = new HashSet<>();

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModePaiementDTO getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiementDTO modePaiement) {
        this.modePaiement = modePaiement;
    }

    public PaiementStatusDTO getStatus() {
        return status;
    }

    public void setStatus(PaiementStatusDTO status) {
        this.status = status;
    }

    public Set<EcheanceDTO> getEcheances() {
        return echeances;
    }

    public void setEcheances(Set<EcheanceDTO> echeances) {
        this.echeances = echeances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaiementDTO)) {
            return false;
        }

        PaiementDTO paiementDTO = (PaiementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paiementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            ", montant=" + getMontant() +
            ", description='" + getDescription() + "'" +
            ", modePaiement=" + getModePaiement() +
            ", status=" + getStatus() +
            ", echeances=" + getEcheances() +
            "}";
    }
}
