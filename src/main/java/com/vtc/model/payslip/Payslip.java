package com.vtc.model.payslip;

import java.time.YearMonth;

import com.vtc.model.user.Driver;
import com.vtc.util.YearMonthToStringConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "payslip", 
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_driver", "month"}),
    }
)
public class Payslip {

    //===>> ATRIBUTOS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; 

    @ManyToOne
    @JoinColumn(name = "id_driver", nullable = false)
    private Driver driver;
    
    @Convert(converter = YearMonthToStringConverter.class)
    @Column(name = "month", nullable = false)
    private YearMonth month;

    @Column(name = "salarioBase", nullable = false)
    private double salarioBase;

    @Column(name = "pppe", nullable = false)
    private double pppe;

    @Column(name = "comision", nullable = false)
    private double comision;

    @Column(name = "gratificaciones", nullable = false)
    private double gratificaciones;

    @Column(name = "plusVestuario", nullable = false)
    private double plusVestuario;

    @Column(name = "plusCalidad", nullable = false)
    private double plusCalidad;

    @Column(name = "plusPermanencia", nullable = false)
    private double plusPermanencia; // único campo

    @Column(name = "otrosPluses", nullable = false)
    private double otrosPluses;

    //===>> CONSTRUCTORES <<===//

    public Payslip() {}

    //===>> GETTERS <<===//

    public Long getId() {return id;}
    public Driver getDriver() {return driver;}
    public double getPlusPermanencia() { return plusPermanencia; }
    public double getPlusCalidad() { return plusCalidad; }
    public YearMonth getMonth() {return month;}
    public double getSalarioBase() {return salarioBase;}
    public double getPppe() {return pppe;}
    public double getGratificaciones() {return gratificaciones;}
    public double getPlusVestuario() {return plusVestuario;}
    public double getOtrosPluses() {return otrosPluses;}
    public double getComision() {return comision;}
    
    public void setMonth(YearMonth mes) {this.month = mes;}
    public void setOtrosPluses(double otrosPluses) {this.otrosPluses = otrosPluses;}
    public void setPlusPermanencia(double plusPermanencia) {this.plusPermanencia = plusPermanencia;}
    public void setPlusVestuario(double plusVestuario) {this.plusVestuario = plusVestuario;}
    public void setSalarioBase(double salarioBase) {this.salarioBase = salarioBase;}
    public void setPppe(double pppe) {this.pppe = pppe;}
    public void setComision(double comision) {this.comision = comision;}
    public void setGratificaciones(double gratificaciones) {this.gratificaciones = gratificaciones;}
    public void setPlusCalidad(double plusCalidad) {this.plusCalidad = plusCalidad;}
    public void setDriver(Driver conductor) {this.driver = conductor;}
}
