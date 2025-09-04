package com.vtc.model.contract;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.vtc.util.DurationToMinutesConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "contract_appendix", 
    uniqueConstraints = {@UniqueConstraint(columnNames = {"contract_id", "start_date"})})
public class ContractAppendix {

    //===>> FIELDS <<===//

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id") 
    private Long id;

    @ManyToOne(optional = false) 
    @JoinColumn(name = "contract_id", nullable = false) //===>> La FK se llama contract_id ===>> No podrá ser null
    private Contract contract;

    @Column(name = "start_date", nullable = false) //===>> La fecha de inicio no puede ser nula
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "auxiliary_tasks")
    private Duration auxiliaryTasks; //===>> DurationToMinutesConverter lo convierte a minutos (Long) en la BD

    @Column(name = "anual_salary")
    private Double annualSalary;

    @ElementCollection
    @CollectionTable(
        name = "appendix_weeklySchedule",
        joinColumns = @JoinColumn(name = "appendix_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "day_of_week") // almacenado como texto o número
    @Column(name = "minutes")
    private Map<DayOfWeek, Long> weeklySchedule; // minutos trabajados por día

    @OneToMany(mappedBy = "appendix"/*Atributo en PoliticaComision*/, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommissionPolicy> commissionPolicies; //===>> Políticas de comisión asociadas a este anejo
    
    @OneToMany(mappedBy = "appendix", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BonusPolicy> bonusPolicies;

    //===>> CONSTRUCTORS <<===//
    
    public ContractAppendix() {}
    
    //===>> TO STRING <<===//

    //===>> GETTERS <<===//
    
    public Long getId() {return id;}
    public Contract getContract() {return contract;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public Duration getAuxiliaryTasks() {return auxiliaryTasks;}
    public Double getAnnualSalary() {return annualSalary;}
    public List<CommissionPolicy> getCommissionPolicies() {return commissionPolicies;}
    public List<BonusPolicy> getBonusPolicies() {return bonusPolicies;}
    public Map<DayOfWeek, Long> getWeeklySchedule() {return weeklySchedule;}

    //===>> SETTERS <<===//
    
    public void setEndDate(LocalDate fechaFin) {this.endDate = fechaFin;}
    public void setCommissionPolicies(List<CommissionPolicy> politicaComision) {
        this.commissionPolicies = politicaComision;
    }
    public void setBonusPolicies(List<BonusPolicy> politicaGratificacions) {
        this.bonusPolicies = politicaGratificacions;
    }
    
    public void setContract(Contract contrato) {
        if (this.contract != null) 
            throw new UnsupportedOperationException(
                "El contrato no se puede cambiar una vez asignado.");   
        if (this.contract == null) this.contract = contrato;
    }

    public void setStartDate(LocalDate startDate) {
        if (this.startDate != null) throw new UnsupportedOperationException(
                "La fecha de inicio no se puede cambiar una vez asignada.");
        if (startDate == null) throw new IllegalArgumentException(
                "La fecha de inicio no puede ser nula.");
        if (this.contract == null) throw new IllegalStateException(
                "Debe establecerse el contrato antes de la fecha de inicio.");
        List<ContractAppendix> anejos = contract.getAppendix();
        ContractAppendix anterior = anejos.stream()
            .filter(a -> a.getContract().equals(this.contract))
            .filter(a -> a.getEndDate() != null)
            .filter(a -> a.getEndDate().isBefore(startDate))
            .max((a1, a2) -> a1.getEndDate().compareTo(a2.getEndDate()))
            .orElse(null);
        if (anterior != null) {
            LocalDate esperado = anterior.getEndDate().plusDays(1);
            if (!startDate.equals(esperado)) throw new IllegalArgumentException(
                    "La fecha de inicio debe ser el día siguiente a la fecha de fin del anejo anterior.");
        }
        this.startDate = startDate;
    }

    public void setFechaInicio_forzarAnterior(LocalDate nuevaFechaInicio) {
        if (this.startDate != null) throw new UnsupportedOperationException(
                "La fecha de inicio no se puede cambiar una vez asignada.");
        if (nuevaFechaInicio == null) throw new IllegalArgumentException(
                "La fecha de inicio no puede ser nula.");
        if (this.contract == null) throw new IllegalStateException(
                "Debe establecerse el contrato antes de la fecha de inicio.");
        List<ContractAppendix> anejos = contract.getAppendix();
        ContractAppendix anterior = anejos.stream()
            .filter(a -> a.getContract().equals(this.contract))
            .filter(a -> a.getStartDate().isBefore(nuevaFechaInicio))
            .max((a1, a2) -> a1.getStartDate().compareTo(a2.getStartDate()))
            .orElse(null);
        if (anterior != null) {
            LocalDate fechaFinEsperada = startDate.minusDays(1);
            if (anterior.getEndDate() == null || !anterior.getEndDate().equals(fechaFinEsperada)) 
                anterior.setEndDate(fechaFinEsperada);
        }
        this.startDate = nuevaFechaInicio;
    }

}
