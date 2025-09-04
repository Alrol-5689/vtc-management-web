package com.vtc.model.agreement;

import com.vtc.util.BooleanArrayToStringConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "agreement_bonus")
public class AgreementBonus {

    //===>> FIELDS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    public static enum BonusType {QUALITY, LONGEVITY, UNIFORM, OTHER} 
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BonusType type;

    @ManyToOne(optional = false) 
    @JoinColumn(
        name = "annex_id", 
        nullable = false,
        foreignKey= @ForeignKey(name="fk_bonus_annex")) 
    private AgreementAnnex annex; 

    @Column(name = "notes")
    private String notes;
    
    @Column(name = "annual_amount", nullable = false)
    private double annualAmount;

    @Convert(converter = BooleanArrayToStringConverter.class)
    @Column(name = "paid_in_month", length = 12, nullable = false)
    private boolean[] paidInMonth = new boolean[12];

    @Column(name = "required_months")
    private int requiredMonths; // -- Solo para pluses de tipo PERMANENCIA
    
    //===>> CONSTRUCTORS <<===//
    
    public AgreementBonus() {
    }
    
    //===>> TO STRING <<===//

    //===>> GETTERS <<===//
    
    public Long getId() { return id; }
    public AgreementAnnex getAnnex() {return annex;}
    public String getNotes() { return notes; }
    public double getAnnualAmount() { return annualAmount; }
    public boolean[] getPaidInMonth() { return paidInMonth; }
    public int getRequiredMonths() { return requiredMonths; }
    public BonusType getType() { return type; }

    //===>> SETTERS <<===//

    public void setId(Long id) { this.id = id; }
    public void setAnnex(AgreementAnnex anexoConvenio) {this.annex = anexoConvenio;}
    public void setNotes(String notes) { this.notes = notes;}
    public void setAnnualAmount(double annualAmount) { this.annualAmount = annualAmount; }
    public void setPaidInMonth(boolean[] paidInMonth) {this.paidInMonth = paidInMonth;}
    public void setRequiredMonths(int requiredMonths) { this.requiredMonths = requiredMonths; }
    public void setType(BonusType type) { this.type = type; }

}
