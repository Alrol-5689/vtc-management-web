package com.vtc.model.agreement;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.vtc.util.DurationToMinutesConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "agreement_annex")
public class AgreementAnnex {

    //===>> FIELDS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(optional = false) 
    @JoinColumn(
        name = "collective_agreement_id", 
        nullable = false,
        foreignKey= @ForeignKey(name="fk_annex_agreement"))
    private CollectiveAgreement agreement;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "name")
    private String name;

    @Column(name = "anual_salary", nullable = false)
    private Double annualSalary;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "auxiliary_tasks")
    private Duration auxiliaryTasks;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "full_time_weekly_hours")
    private Duration fullTimeWeeklyHours;

    @OneToMany(mappedBy = "annex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgreementBonus> bonuses; 

    @ElementCollection
    @CollectionTable(
        name = "annex_seniority_map", 
        joinColumns = @JoinColumn(
            name = "annex_id", // FK a AgreementAnnex [ NO TIENE QUE COINCIDIR CON EL NOMBRE DEL ATRIBUTO EN LA CLASE ]
            nullable= false,
            foreignKey = @ForeignKey(name="fk_seniority_annex")),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"annex_id", "min_months"})})
    @MapKeyColumn(name = "min_months")
    @Column(name = "percentage")
    private Map<Integer, Double> seniorityBreakpoints = new TreeMap<>();

    //===>> CONSTRUCTORS <<===//

    public AgreementAnnex() {
    }

    //===>> TO STRING <<===//

    //===>> GETTERS <<===//

    public Long getId() {return id;}
    public CollectiveAgreement getAgreement() {return agreement;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public String getNotes() {return notes;}
    public String getName() {return name;}
    public Double getAnnualSalary() {return annualSalary;}
    public Duration getFullTimeWeeklyHours() {return fullTimeWeeklyHours;}
    public Duration getAuxiliaryTasks() {return auxiliaryTasks;}
    public List<AgreementBonus> getBonuses() {return bonuses;}
    public Map<Integer, Double> getSeniorityBreakpoints() {return seniorityBreakpoints;}

    //===>> SETTERS <<===//

    public void setId(Long id_anexoConvenio) {this.id = id_anexoConvenio;}
    public void setAgreement(CollectiveAgreement convenio) {this.agreement = convenio;}
    public void setStartDate(LocalDate fechaInicio) {this.startDate = fechaInicio;}
    public void setEndDate(LocalDate fechaFin) {this.endDate = fechaFin;}
    public void setNotes(String notas) {this.notes = notas;}
    public void setName(String nombre) {this.name = nombre;}
    public void setAnnualSalary(Double annualSalary) {this.annualSalary = annualSalary;}
    public void setAuxiliaryTasks(Duration tareasAux) {this.auxiliaryTasks = tareasAux;}
    public void setFullTimeWeeklyHours(Duration jornadaCompleta) {this.fullTimeWeeklyHours = jornadaCompleta;}
    public void setBonuses(List<AgreementBonus> pluses) {this.bonuses = pluses;}
    public void setSeniorityBreakpoints(Map<Integer, Double> seniorityBreakpoints) {this.seniorityBreakpoints = seniorityBreakpoints;}

}
