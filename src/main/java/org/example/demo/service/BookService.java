package org.example.demo.service;

import org.example.demo.entity.Book;
import org.example.demo.repository.BookRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service for Book Business Logic
 */
@Stateless
public class BookService {

    @Inject
    private BookRepository repository;

    // ==================== CREATE ====================
    @Transactional
    public Book createBook(Book book) {

        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }

        if (repository.findByISBN(book.getIsbn()) != null) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        return repository.create(book);
    }

    // ==================== READ ====================
    public Book getBook(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid book ID");
        }

        Book book = repository.findById(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }

        return book;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    // ==================== UPDATE ====================
    @Transactional
    public Book updateBook(Long id, Book data) {

        Book book = repository.findById(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        if (data.getTitle() != null && !data.getTitle().trim().isEmpty()) {
            book.setTitle(data.getTitle());
        }

        if (data.getAuthor() != null ) {
            book.setAuthor(data.getAuthor());
        }

        if (data.getIsbn() != null ) {
            book.setIsbn(data.getIsbn());
        }

        if (data.getTotalCopies() != null) {
            book.setTotalCopies(data.getTotalCopies());
        }

        if (data.getAvailableCopies() != null) {
            book.setAvailableCopies(data.getAvailableCopies());
        }


        return repository.update(book);
    }

    // ==================== DELETE ====================
    @Transactional
    public void deleteBook(Long id) {

        Book book = repository.findById(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        repository.delete(id);
    }

    // ==================== BUSINESS LOGIC ====================

}
