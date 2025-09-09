package com.vtc.model.company;

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

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 120)
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    @ManyToOne(optional = true)
    @JoinColumn(name = "administrator_id", foreignKey = @ForeignKey(name = "fk_company_admin"))
    private Administrator administrator; // puede ser null

    public Company() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLogoUrl() { return logoUrl; }
    public Administrator getAdministrator() { return administrator; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public void setAdministrator(Administrator administrator) { this.administrator = administrator; }
}

