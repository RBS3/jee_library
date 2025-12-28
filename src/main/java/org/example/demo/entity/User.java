package org.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u ORDER BY u.name"),
        @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")

})
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "name", length = 200, nullable = false)
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "membershipType")
    private String membershipType;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "membershipDate")
    private Date membershipDate;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role; // USER, ADMIN
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<BorrowingRecord> borrowRecords = new ArrayList<>();

    // Constructors
    public User() {
        this.membershipDate = new Date();
    }

    public User(String name, String email, String membershipType) {
        this();
        this.email = email;
        this.name = name;
        this.membershipType = membershipType;
    }

    // Business logic method

    @Override
    public String toString() {
        return "User{name='" + name + "', email=" + email + "', membershiptype= " + membershipType + "}";
    }

}
