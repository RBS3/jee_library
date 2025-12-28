package org.example.demo.repository;

import org.example.demo.entity.BorrowingRecord;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Repository for BorrowingRecord Entity
 */
@Stateless
public class BorrowingRecordRepository {

    @PersistenceContext
    private EntityManager em;

    // ==================== CREATE ====================
    public BorrowingRecord create(BorrowingRecord record) {
        em.persist(record);
        return record;
    }

    // ==================== READ ====================
    public BorrowingRecord findById(Long id) {
        return em.find(BorrowingRecord.class, id);
    }

    public List<BorrowingRecord> findAll() {
        return em.createNamedQuery("BorrowingRecord.findAll", BorrowingRecord.class)
                .getResultList();
    }

    public List<BorrowingRecord> findByUserId(Long userId) {
        return em.createNamedQuery("BorrowingRecord.findByUserId", BorrowingRecord.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<BorrowingRecord> findByBookId(Long bookId) {
        return em.createNamedQuery("BorrowingRecord.findByBookId", BorrowingRecord.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    // ==================== UPDATE ====================
    public BorrowingRecord update(BorrowingRecord record) {
        return em.merge(record);
    }

    // ==================== DELETE ====================
    public void delete(Long id) {
        BorrowingRecord record = em.find(BorrowingRecord.class, id);
        if (record != null) {
            em.remove(record);
        }
    }
}
