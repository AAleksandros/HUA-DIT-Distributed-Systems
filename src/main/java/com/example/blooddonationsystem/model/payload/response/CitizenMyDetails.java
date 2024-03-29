package com.example.blooddonationsystem.model.payload.response;

public class CitizenMyDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String area;
    private String bloodType;
    private String phoneNumber;
    private Integer age;

    public CitizenMyDetails() {
    }

    public CitizenMyDetails(Long id, String firstName, String lastName, String email, String area, String bloodType, String phoneNumber, Integer age) { // Add age here
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.area = area;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
