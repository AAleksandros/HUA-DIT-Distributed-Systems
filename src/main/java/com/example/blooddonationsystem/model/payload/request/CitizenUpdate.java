package com.example.blooddonationsystem.model.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CitizenUpdate {
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String phoneNumber;
    private String area;
    private String bloodType;
    private Integer age;

    public CitizenUpdate() {
    }

    public CitizenUpdate(String firstName, String lastName, String password, String email, String phoneNumber, String area, String bloodType, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.area = area;
        this.bloodType = bloodType;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getArea() {
        return area;
    }

    public String getBloodType() {
        return bloodType;
    }

    public Integer getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
