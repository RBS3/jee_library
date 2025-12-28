package org.example.demo.repository;

import org.example.demo.entity.Notification;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Repository for Notification Entity
 */
@Stateless
public class NotificationRepository {

    @PersistenceContext
    private EntityManager em;

    // ==================== CREATE ====================
    public Notification create(Notification notification) {
        em.persist(notification);
        return notification;
    }

    // ==================== READ ====================
    public Notification findById(Long id) {
        return em.find(Notification.class, id);
    }

    public List<Notification> findAll() {
        return em.createNamedQuery("Notification.findAll", Notification.class)
                .getResultList();
    }

    public List<Notification> findByUserId(Long userId) {
        return em.createNamedQuery("Notification.findByUserId", Notification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // ==================== UPDATE ====================
    public Notification update(Notification notification) {
        return em.merge(notification);
    }

    // ==================== DELETE ====================
    public void delete(Long id) {
        Notification notification = em.find(Notification.class, id);
        if (notification != null) {
            em.remove(notification);
        }
    }
}
