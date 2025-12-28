package org.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "borrowingRecors")
@NamedQueries({
        @NamedQuery(name = "BorrowingRecord.findAll", query = "SELECT b FROM BorrowingRecord b ORDER BY b.borrowDate DESC"),
        @NamedQuery(name = "BorrowingRecord.findByUserId", query = "SELECT b FROM BorrowingRecord b WHERE b.user.id = :userId ORDER BY b.borrowDate DESC"),
        @NamedQuery(name = "BorrowingRecord.findByBookId", query = "SELECT b FROM BorrowingRecord b WHERE b.book.id = :bookId ORDER BY b.borrowDate DESC")
})
@Data
public class BorrowingRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowRecord_id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Column(name = "status", length = 200, nullable = false)
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "borrowDate")
    private Date borrowDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dueDate")
    private Date dueDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "returnDate")
    private Date returnDate;

    // Constructors
    public BorrowingRecord() {

        this.borrowDate = new Date();
        this.dueDate = new Date();
        this.returnDate = new Date();
    }

    public BorrowingRecord(User user, Long id, Book book, String status) {
        this();
        this.user = user;
        this.id = id;
        this.book = book;
        this.status = status;

    }

    // Business logic method

    @Override
    public String toString() {
        return "BorrowingRecord{" +
                "id=" + id +
                ", user=" + user +
                ", book=" + book +
                ", status='" + status + '\'' +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
