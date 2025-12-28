package org.example.demo.repository;

import org.example.demo.entity.Book;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

/**
 * Repository for Product Entity
 * Handles all database operations for Product
 * 
 * @Stateless = EJB component (TomEE manages it)
 */
@Stateless
public class BookRepository {

    @PersistenceContext
    private EntityManager em;

    // ==================== CREATE ====================
    public Book create(Book book) {
        em.persist(book);
        return book;
    }

    // ==================== READ ====================
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createNamedQuery("Book.findAll", Book.class)
                .getResultList();
    }

    public Book findByISBN(String isbn) {
        try {
            return em.createNamedQuery("Book.findByISBN", Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ==================== UPDATE ====================
    public Book update(Book book) {
        return em.merge(book);
    }

    // ==================== DELETE ====================
    public void delete(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }

    // ==================== BUSINESS QUERIES ====================
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String pattern = "%" + keyword.toLowerCase() + "%";
        return em.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.title) LIKE :pattern OR LOWER(b.author) LIKE :pattern OR LOWER(b.isbn) LIKE :pattern",
                Book.class)
                .setParameter("pattern", pattern)
                .getResultList();
    }

}
