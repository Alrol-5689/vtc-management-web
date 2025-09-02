package com.vtc.model.user;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrators")
public class Administrator implements Serializable{

    //===>> ATRIBUTOS <<===//

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    //===>> getters y setters <<===//

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getPassword() {return password;}
    public void setId(Long id) {this.id = id;}
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }

}
