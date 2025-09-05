package com.vtc.model.user;

import java.util.List;

import com.vtc.model.contract.Contract;
import com.vtc.model.log.DailyLog;
import com.vtc.validation.constraints.DniNie;
import com.vtc.validation.constraints.UniqueUsername;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "drivers")
public class Driver {
	
    //===>> FIELDS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;

    @NotBlank
    @Size(max = 50)
    @UniqueUsername // Hace una consulta y comprueba que no exista otro conductor con ese username
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank
    @Size(max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Size(max = 4) // El pin es opcional
    @Column(name = "pin", nullable = true, length = 4)
    private String pin;

    @NotBlank
    @Size(max = 80)
    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Size(max = 80)
    @Column(name = "last_name", nullable = true, length = 80)
    private String lastName;
    
    @Size(max = 80)
    @Column(name = "second_last_name", nullable = true, length = 80)
    private String secondLastName;
    
    // DNI/NIE con validación de letra de control mediante constraint personalizada
    @NotBlank
    @Size(max = 20)
    @DniNie
    @Column(name = "national_id", nullable = false, unique = true, length = 20)
    private String nationalId;
    
    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[0-9\\s+()-]{6,20}$", message = "Phone format is invalid")
    @Column(name = "phone", nullable = false, unique = true, length = 20)
    private String phone;
    
    @NotBlank
    @Size(max = 120)
    @Email(message = "Email format is invalid")
    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<Contract> contracts;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyLog> dailyLogs;

    /*@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payslip> payslips;*/

    //===>> CONSTRUCTORS <<===//

    public Driver() {}

    public Driver(String username, String password, String nationalId,
                  String firstName, String lastName, String secondLastName,
                  String phone, String email) {
        this.username = username;
        this.password = password;
        this.nationalId = nationalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.phone = phone;
        this.email = email;
    }

    //===>> TO STRING <<===//

    //===>> GETTERS & SETTERS<<===// 

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password;}

    public String getPin() {return pin;}
    public void setPin(String pin) {this.pin = pin;}

    public String getFirstName() { return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getSecondLastName() {return secondLastName;}
    public void setSecondLastName(String secondLastName) {this.secondLastName = secondLastName;}

    public String getNationalId() {return nationalId;}
    public void setNationalId(String nationalId) {this.nationalId = nationalId;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public List<Contract> getContracts() {return contracts;}
    public void setContracts(List<Contract> contracts) {this.contracts = contracts;}

    public List<DailyLog> getDailyLogs() {return dailyLogs;}
    public void setDailyLogs(List<DailyLog> dailyLogs) {this.dailyLogs = dailyLogs;}

    /*public List<Payslip> getPayslips() {return nominas;}
    public void setPayslips(List<Payslip> payslips) {this.payslips = payslips;}*/   


}
