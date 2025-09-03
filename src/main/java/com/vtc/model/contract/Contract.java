package com.vtc.model.contract;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import com.vtc.model.user.Driver;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "contract")
public class Contract {

    //===>> ATRIBUTOS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(optional = false) 
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver; 

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "company_join_date")
    private LocalDate companyJoinDate; // no cambia con el contrato si es con la misma empresa

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "notes")
    private String notes; 

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractAppendix> appendix;
    
    //===>> CONSTRUCTORES <<===//

    public Contract() {

    }

    //===>> GETTERS <<===//

    public boolean isContratoReady() { //===>> Verifica si el contrato está listo para ser usado
        return 
            driver != null && 
            startDate != null;
    }
        
    public Long getId() {return id;}
    public Driver getDriver() {return driver;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public LocalDate getCompanyJoinDate() {return companyJoinDate;}
    public String getCompany() {return company;}
    public String getNotes() {return notes;}
    public List<ContractAppendix> getAppendix() {return appendix;}

    public ContractAppendix getAnejoVigente(LocalDate fecha) {
        if (appendix == null || appendix.isEmpty()) return null;
        return appendix.stream()
            .filter(a -> !a.getStartDate().isAfter(fecha))
            .filter(a -> a.getEndDate() == null || !a.getEndDate().isBefore(fecha))
            .max(Comparator.comparing(ContractAppendix::getStartDate))
            .orElse(null);
    }
    
    //===>> SETTERS <<===//

    protected void setId(Long id_contrato) {this.id = id_contrato;}
    public void setStartDate(LocalDate fechaInicio) {this.startDate = fechaInicio;}
    public void setEndDate(LocalDate fechaFin) {this.endDate = fechaFin;}
    public void setCompany(String empresa) {this.company = empresa;}
    public void setNotes(String notas) {this.notes = notas;}

    public void setDriver(Driver conductor) {
        if(this.driver != null) 
            throw new UnsupportedOperationException("El conductor no se puede cambiar una vez asignado.");
        this.driver = conductor;
    }

    public void setCompanyJoinDate(LocalDate fechaAltaEnEmpresa) {this.companyJoinDate = fechaAltaEnEmpresa;}

    // helper opcional para mantener ambos lados sincronizados
    public void addAppendix(ContractAppendix a) {
        appendix.add(a);
        a.setContract(this);
    }
    
    public void removeAppendix(ContractAppendix a) {
        appendix.remove(a);
        a.setContract(null);
    }

}