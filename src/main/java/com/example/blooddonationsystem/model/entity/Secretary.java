package com.example.blooddonationsystem.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "secretary")
public class Secretary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Secretary() {
    }

    public Secretary(String firstName, String lastName, String password, String email) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Secretary{" +
                "id=" + id +
                '}';
    }
}
