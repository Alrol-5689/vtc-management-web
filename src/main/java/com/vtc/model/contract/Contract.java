package com.vtc.model.contract;

import java.time.LocalDate;
import java.util.List;

import com.vtc.model.user.Driver;
import com.vtc.model.company.Company;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contract")
public class Contract {

    //===>> FIELDS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(optional = false) 
    @JoinColumn(
        name = "driver_id", 
        nullable = false,
        foreignKey= @ForeignKey(name="fk_contract_driver"))
    private Driver driver; 

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "company_join_date")
    private LocalDate companyJoinDate; 

    @Size(max = 255)
    @ManyToOne(optional = false)
    @JoinColumn(
        name = "company_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_contract_company"))
    private Company company;

    @Column(name = "notes")
    private String notes; 

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractAppendix> appendix;
    
    //===>> CONSTRUCTORS <<===//

    public Contract() {}

    //===>> TO STRING <<===//

    //===>> GETTERS <<===//
        
    public Long getId() {return id;}
    public Driver getDriver() {return driver;}
    public LocalDate getStartDate() {return startDate;}
    public LocalDate getEndDate() {return endDate;}
    public LocalDate getCompanyJoinDate() {return companyJoinDate;}
    public Company getCompany() {return company;}
    public String getNotes() {return notes;}
    public List<ContractAppendix> getAppendix() {return appendix;}
    
    //===>> SETTERS <<===//

    protected void setId(Long id_contrato) {this.id = id_contrato;}
    public void setDriver(Driver driver) {this.driver = driver;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}
    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}
    public void setCompanyJoinDate(LocalDate companyJoinDate) {this.companyJoinDate = companyJoinDate;}
    public void setCompany(Company company) {this.company = company;}
    public void setNotes(String notes) {this.notes = notes;}
    public void setAppendix(List<ContractAppendix> appendix) {this.appendix = appendix;}


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
