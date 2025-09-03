package com.vtc.model.log;

import java.time.Duration;
import java.time.LocalDate;

import com.vtc.model.user.Driver;
import com.vtc.util.DurationToMinutesConverter;

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
    name = "daily_log", 
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fecha", "id_conductor"})
    }
)
public class DailyLog {

    //===>> ATRIBUTOS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; 

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_conductor")
    private Driver driver;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "jornada")   
    private Duration jornada;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "conexion")
    private Duration conexion;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "presencia")
    private Duration presencia;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "tareas_aux")
    private Duration tareasAux;

    @Column(name = "facturacion")
    private double facturacion;
    
    //===>> CONSTRUCTORES <<===//

    public DailyLog() {
    }

    //===>> Getters y setters
    public Long getId() {return id;}
    public LocalDate getFecha() { return fecha; }
    public Duration getConexion() { return conexion; }
    public Duration getPresencia() { return presencia; }
    public Duration getTareasAux() { return tareasAux; }
    public double getFacturacion() { return facturacion; }
    public Driver getDriver() { return driver; }
    public Duration getBalance() { return conexion.plus(presencia).plus(tareasAux).minus(jornada); }

    public void setJornada(Duration jornada) { this.jornada = jornada; }
    public void setConexion(Duration conexion) { this.conexion = conexion; }
    public void setPresencia(Duration presencia) { this.presencia = presencia; }
    public void setTareasAux(Duration tareasAux) { this.tareasAux = tareasAux; }
    public void setFacturacion(double facturacion) { this.facturacion = facturacion; }









}
