package org.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "notifications")
@NamedQueries({
        @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n ORDER BY n.cretedDate DESC"),
        @NamedQuery(name = "Notification.findByUserId", query = "SELECT n FROM Notification n WHERE n.user.id = :userId ORDER BY n.cretedDate DESC")
})
@Data
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "message", length = 200, nullable = false)
    private String message;
    @Column(name = "type")
    private String type;
    @Column(name = "isRead")
    private Boolean isRead;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate")
    private Date cretedDate;

    // Constructors
    public Notification() {
        this.cretedDate = new Date();
    }

    public Notification(String message, String type) {
        this();
        this.message = message;
        this.type = type;
    }

    // Business logic method

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", user=" + user +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", isRead=" + isRead +
                ", cretedDate=" + cretedDate +
                '}';
    }
}
