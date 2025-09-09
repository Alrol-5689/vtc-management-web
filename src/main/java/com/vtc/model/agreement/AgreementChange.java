package com.vtc.model.agreement;

import java.time.LocalDateTime;

import com.vtc.model.user.Administrator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(name = "agreement_change",
       indexes = {
           @Index(name = "idx_change_agreement", columnList = "agreement_id"),
           @Index(name = "idx_change_annex", columnList = "annex_id"),
           @Index(name = "idx_change_admin", columnList = "administrator_id")
       })
public class AgreementChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agreement_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_change_agreement"))
    private CollectiveAgreement agreement;

    @ManyToOne(optional = true)
    @JoinColumn(name = "annex_id",
            foreignKey = @ForeignKey(name = "fk_change_annex"))
    private AgreementAnnex annex; // null si el cambio es del convenio

    @ManyToOne(optional = false)
    @JoinColumn(name = "administrator_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_change_admin"))
    private Administrator administrator;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt = LocalDateTime.now();

    @Column(name = "type", nullable = false, length = 40)
    private String type;

    @Column(name = "message", length = 1000)
    private String message;

    public AgreementChange() {}

    public Long getId() { return id; }
    public CollectiveAgreement getAgreement() { return agreement; }
    public AgreementAnnex getAnnex() { return annex; }
    public Administrator getAdministrator() { return administrator; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public String getType() { return type; }
    public String getMessage() { return message; }

    public void setId(Long id) { this.id = id; }
    public void setAgreement(CollectiveAgreement agreement) { this.agreement = agreement; }
    public void setAnnex(AgreementAnnex annex) { this.annex = annex; }
    public void setAdministrator(Administrator administrator) { this.administrator = administrator; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
    public void setType(String type) { this.type = type; }
    public void setMessage(String message) { this.message = message; }
}

