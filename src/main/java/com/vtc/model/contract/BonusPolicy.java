package com.vtc.model.contract;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bonus_policy")
public class BonusPolicy {

    //===>> FIELDS <<===//

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false) 
    @JoinColumn(
        name = "appendix_id", 
        nullable = false,
        foreignKey= @ForeignKey(name="fk_bonus_policy_appendix")) 
    private ContractAppendix appendix;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "goal", nullable = false) // Cantidad para que se cobre el bonus
    private double goal;

    @Column(name = "bonus", nullable = false)
    private double bonus;

    @Column(name = "real_bonus", nullable = false)
    private boolean realBonus = true;

    //===>> CONSTRUCTORS <<===//

    public BonusPolicy() {}

    //===>> TO STRING <<===//

    @Override
    public String toString() {
        return "[ Umbral: " + goal + " € ] [ Comisión: " + bonus + " € ] [ Inicio: " + startDate + 
         " ] [ Fin: " + endDate + " ] [ Reconocida: " + realBonus + " ]";
    }

    //===>> GETTERS <<===//

    public Long getId() {return id;}
    public ContractAppendix getAppendix() {return appendix;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public double getGoal() {return goal;}
    public double getBonus() {return bonus; }
    public boolean isRealBonus() {return realBonus;}
    
    //===>> SETTERS <<===//
    
    public void setId(Long idPoliticaGratificacion) {this.id = idPoliticaGratificacion;}
    public void setAppendix(ContractAppendix anejoContrato) {this.appendix = anejoContrato;}
    public void setStartDate(LocalDate fechaInicio) {this.startDate = fechaInicio;}
    public void setEndDate(LocalDate fechaFin) {this.endDate = fechaFin;}
    public void setGoal(double umbral) {this.goal = umbral;}
    public void setBonus(double porcentaje) {this.bonus = porcentaje;}
    public void setRealBonus(boolean realCommission) {this.realBonus = realCommission;}

}
