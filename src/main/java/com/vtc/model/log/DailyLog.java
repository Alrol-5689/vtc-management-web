package com.vtc.model.log;

import java.time.Duration;
import java.time.LocalDate;

import com.vtc.model.user.Driver;
import com.vtc.util.DurationToMinutesConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
    name = "daily_log", 
    uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "driver_id"})})
public class DailyLog {

    //===>> FIELDS <<===//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; 

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "driver_id", 
        nullable = false,
        foreignKey= @ForeignKey(name="fk_daily_log_driver"))
    private Driver driver;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "connection")
    private Duration connection;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "presence")
    private Duration presence;

    @Convert(converter = DurationToMinutesConverter.class)
    @Column(name = "auxiliary_tasks")
    private Duration auxiliaryTasks;

    @Column(name = "billing_amount")
    private double billingAmount;
    
    //===>> CONSTRUCTORS <<===//

    public DailyLog() {}

    //===>> TO STRING <<===//

    //===>> Getters y setters

    public Long getId() {return id;}
    public LocalDate getDate() { return date; }
    public Duration getConnection() { return connection; }
    public Duration getPresence() { return presence; }
    public Duration getAuxiliaryTasks() { return auxiliaryTasks; }
    public double getBillingAmount() { return billingAmount; }
    public Driver getDriver() { return driver; }
    public Duration getBalance() { return getWorkingHours(); }

    public void setConnection(Duration connection) { this.connection = connection; }
    public void setPresence(Duration presence) { this.presence = presence; }
    public void setAuxiliaryTasks(Duration auxiliaryTasks) { this.auxiliaryTasks = auxiliaryTasks; }
    public void setBillingAmount(double billingAmount) { this.billingAmount = billingAmount; }
    
    @Transient
    public Duration getWorkingHours() {
        return zeroIfNull(connection)
                .plus(zeroIfNull(presence))
                .plus(zeroIfNull(auxiliaryTasks));
    }

    private static Duration zeroIfNull(Duration d) { return d == null ? Duration.ZERO : d; }

}
