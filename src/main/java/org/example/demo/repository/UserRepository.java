package org.example.demo.repository;

import org.example.demo.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Repository for User Entity
 * Handles all database operations for User
 */
@Stateless
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    // ==================== CREATE ====================
    public User create(User user) {
        em.persist(user);
        return user;
    }

    // ==================== READ ====================
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class)
                .getResultList();
    }

    public User findByEmail(String email) {
        try {
            return em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    // ==================== UPDATE ====================
    public User update(User user) {
        return em.merge(user);
    }

    // ==================== DELETE ====================
    public void delete(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }
}
