package com.vtc.model.agreement;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "collective_agreement")
public class CollectiveAgreement {

    //===>> FIELDS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "agreement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgreementAnnex> annexes; 
    
    //===>> CONSTRUCTORS <<===//
    
    public CollectiveAgreement() {
    }

    //===>> TO STRING <<===//

    //===>> GETTERS <<===//
    
    public Long getId() {return id;}
    public LocalDate getStartDate() { return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public String getNotes() {return notes;}
    public String getName() {return name;}
    public List<AgreementAnnex> getAnnexes() {return annexes;}
    
    //===>> SETTERS <<===//
    
    public void setId(Long id_convenio) {this.id = id_convenio;}
    public void setStartDate(LocalDate fechaInicio) {this.startDate = fechaInicio;}
    public void setEndDate(LocalDate fechaFin) {this.endDate = fechaFin;}
    public void setNotes(String notas) {this.notes = notas;}
    public void setName(String nombre) {this.name = nombre;}
    public void setAnnexes(List<AgreementAnnex> annexes) {this.annexes = annexes;}

}
