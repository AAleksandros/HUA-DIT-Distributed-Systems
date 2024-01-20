package com.example.blooddonationsystem.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="citizen")
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name ")
    private String lastName;
    @Column(name = "age")
    private Integer age;
    @Column(name="address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "blood_type")
    private String bloodType;
    @Column(name ="email")
    private String email;

    public Integer getCitizenID() {
        return id;
    }

    public void setCitizenID(Integer citizenID) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Citizen() {
    }


    public Citizen(Integer citizenID, String firstName, String lastName, int age, String phoneNumber,String email,String address, String bloodType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address=address;
        this.bloodType = bloodType;


    }

    @Override
    public String toString() {
        return "Citizen{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
