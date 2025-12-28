package org.example.demo.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "Book.findAll",
                query = "SELECT b FROM Book b ORDER BY b.title"),
        @NamedQuery(name="Book.findById",
                query = "SELECT b FROM Book b WHERE b.id= :id"),
        @NamedQuery(name = "Book.findByISBN",
                query = "SELECT b FROM Book b WHERE b.isbn = :isbn")

})
@Data
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @Column(name = "title", length = 200, nullable = false)
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "totalCopies")
    private String totalCopies;
    @Column(name = "availableCopies")
    private String availableCopies;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publishDate")
    private Date publishDate;
    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowRecords= new ArrayList<>();

    // Constructors
    public Book() {
        this.publishDate = new Date();
    }
    public Book(String title, String author, String isbn, String totalCopies, String availableCopies){
        this();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies=totalCopies;
        this.availableCopies=availableCopies;
    }

    // Business logic method

    @Override
    public String toString() {
        return "Book{title='" + title + "', author=" + author + "', isbn= "+isbn+ "',totalCopies=" +totalCopies+"',availableCopies= "+availableCopies+ "}";
    }

}

