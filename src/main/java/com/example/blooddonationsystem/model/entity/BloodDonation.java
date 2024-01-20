package com.example.blooddonationsystem.model.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
@Entity
@Table
public class BloodDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Integer id;
    @Column
    private Date date;
    @Column
    private String location;
    @Column
    private Double quantity;
    @ManyToMany(cascade = {CascadeType.PERSIST ,CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable
                (name="donation_citizen",
                joinColumns =@JoinColumn(name="donation_id"),
                inverseJoinColumns = @JoinColumn(name = "citizen_id"))
    private List<Citizen> citizens;

    public BloodDonation() {
    }
    public BloodDonation(Date date, String location, Double quantity) {
        this.date = date;
        this.location = location;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public List<Citizen> getCitizens() {
        return citizens;
    }

    public void setCitizens(List<Citizen> citizens) {
        this.citizens = citizens;
    }
}
