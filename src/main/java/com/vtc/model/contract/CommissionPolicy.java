package com.vtc.model.contract;

import java.time.YearMonth;

import com.vtc.util.YearMonthToStringConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "commission_policy")
public class CommissionPolicy {

    //===>> ATRIBUTOS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false) 
    @JoinColumn(
        name = "appendix_id", 
        nullable = false,
        foreignKey= @ForeignKey(name="fk_commission_policy_appendix")) 
    private ContractAppendix appendix;
    
    @Convert(converter = YearMonthToStringConverter.class)
    @Column(name = "month", nullable = false)
    private YearMonth month;
    
    @Column(name = "threshold", nullable = false)
    private double threshold;
    
    @Column(name = "percentage", nullable = false)
    private double percentage;
    
    @Column(name = "real_commission", nullable = false)
    private boolean realCommission = false; // false -> Lo guardamos en una lista en Conductor, true -> en una lista en Contrato
    
    //===>> CONSTRUCTORES <<===//

    public CommissionPolicy() {}

    //===>> TO STRING <<===//

    @Override
    public String toString() {
        return "[ Umbral: " + threshold + " € ] [ Comisión: " + percentage +
         " % ] [ Mes: " + month + " ] [ Reconocida: " + realCommission + " ]";
    }

    //===>> GETTERS <<===//
    
    public Long getId() {return id;}
    public ContractAppendix getAppendix() {return appendix;}
    public YearMonth getMonth() { return month; }
    public double getThreshold() { return threshold;}
    public double getPercentage() { return percentage; }
    public boolean isRealCommission() {return realCommission;}
    
    //===>> SETTERS <<===//
    
    public void setId(Long id) {this.id = id;}
    public void setAppendix(ContractAppendix contratoAnejo) {this.appendix = contratoAnejo;}
    public void setMonth(YearMonth month) {this.month = month;}
    public void setThreshold(double umbral) { this.threshold = umbral; }
    public void setPercentage(double porcentaje) { this.percentage = porcentaje; }
    public void setRealCommission(boolean reconocida) {this.realCommission = reconocida;}

}
