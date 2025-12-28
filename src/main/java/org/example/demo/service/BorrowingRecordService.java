package org.example.demo.service;

import org.example.demo.entity.Book;
import org.example.demo.entity.BorrowingRecord;
import org.example.demo.entity.User;
import org.example.demo.repository.BookRepository;
import org.example.demo.repository.BorrowingRecordRepository;
import org.example.demo.repository.UserRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Service for BorrowingRecord Business Logic
 */
@Stateless
public class BorrowingRecordService {

    @Inject
    private BorrowingRecordRepository repository;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private UserRepository userRepository;

    // ==================== CREATE ====================
    @Transactional
    public BorrowingRecord createBorrowingRecord(BorrowingRecord record) {

        if (record.getUser() == null) {
            throw new IllegalArgumentException("Borrowing record must be assigned to a user");
        }

        if (record.getBook() == null) {
            throw new IllegalArgumentException("Borrowing record must be assigned to a book");
        }

        // Validate User existence
        User user = userRepository.findById(record.getUser().getId());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Check for 5 books limit
        List<BorrowingRecord> userRecords = repository.findByUserId(record.getUser().getId());
        long activeRecords = userRecords.stream()
                .filter(r -> "BORROWED".equalsIgnoreCase(r.getStatus()))
                .count();
        if (activeRecords >= 5) {
            throw new IllegalArgumentException("Cannot borrow more than 5 books");
        }

        // Validate Book existence and availability
        Book book = bookRepository.findById(record.getBook().getId());
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        // Simple logic: if availableCopies > 0, decrease it
        // Note: book.availableCopies is String in Entity, should probably ideally be
        // Int, but keeping as String to match existing entity
        // Assuming we need to parse it.
        try {
            int available = Integer.parseInt(book.getAvailableCopies());
            if (available <= 0) {
                throw new IllegalArgumentException("Book is not available");
            }
            book.setAvailableCopies(String.valueOf(available - 1));
            bookRepository.update(book);
        } catch (NumberFormatException e) {
            // Fallback or ignore if not a number, but ideally should be validated
            throw new IllegalArgumentException("Invalid book copies format");
        }

        // Set defaults if missing
        if (record.getBorrowDate() == null) {
            record.setBorrowDate(new Date());
        }

        if (record.getStatus() == null) {
            record.setStatus("BORROWED");
        }

        return repository.create(record);
    }

    // ==================== READ ====================
    public BorrowingRecord getBorrowingRecord(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid record ID");
        }

        BorrowingRecord record = repository.findById(id);
        if (record == null) {
            throw new IllegalArgumentException("Borrowing record not found with ID: " + id);
        }

        return record;
    }

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return repository.findAll();
    }

    public List<BorrowingRecord> getUserBorrowingRecords(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return repository.findByUserId(userId);
    }

    // ==================== UPDATE ====================
    @Transactional
    public BorrowingRecord updateBorrowingRecord(Long id, BorrowingRecord data) {

        BorrowingRecord record = repository.findById(id);
        if (record == null) {
            throw new IllegalArgumentException("Borrowing record not found");
        }

        if (data.getStatus() != null) {
            // If returning book
            if ("RETURNED".equalsIgnoreCase(data.getStatus()) && !"RETURNED".equalsIgnoreCase(record.getStatus())) {
                Book book = bookRepository.findById(record.getBook().getId());
                if (book != null) {
                    try {
                        int available = Integer.parseInt(book.getAvailableCopies());
                        book.setAvailableCopies(String.valueOf(available + 1));
                        bookRepository.update(book);
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                }
                record.setReturnDate(new Date());
            }
            record.setStatus(data.getStatus());
        }

        if (data.getDueDate() != null) {
            record.setDueDate(data.getDueDate());
        }

        if (data.getReturnDate() != null) {
            record.setReturnDate(data.getReturnDate());
        }

        return repository.update(record);
    }

    // ==================== DELETE ====================
    @Transactional
    public void deleteBorrowingRecord(Long id) {

        BorrowingRecord record = repository.findById(id);
        if (record == null) {
            throw new IllegalArgumentException("Borrowing record not found");
        }

        repository.delete(id);
    }

    // ==================== HELPER METHODS ====================
    @Transactional
    public void borrowBook(User user, Book book) {
        BorrowingRecord record = new BorrowingRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(new Date());
        // Default due date: 14 days from now
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DATE, 14);
        record.setDueDate(cal.getTime());

        record.setStatus("BORROWED");
        createBorrowingRecord(record);
    }

    @Transactional
    public void returnBook(Long recordId) {
        BorrowingRecord record = new BorrowingRecord();
        record.setStatus("RETURNED");
        record.setReturnDate(new Date());
        updateBorrowingRecord(recordId, record);
    }

    public List<BorrowingRecord> getBorrowingRecordsByUser(User user) {
        return getUserBorrowingRecords(user.getId());
    }
}
