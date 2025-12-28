package org.example.demo.service;

import org.example.demo.entity.Notification;
import org.example.demo.repository.NotificationRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * Service for Notification Business Logic
 */
@Stateless
public class NotificationService {

    @Inject
    private NotificationRepository repository;

    // ==================== CREATE ====================
    @Transactional
    public Notification createNotification(Notification notification) {

        if (notification.getMessage() == null || notification.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Notification message cannot be empty");
        }

        if (notification.getUser() == null) {
            throw new IllegalArgumentException("Notification must be assigned to a user");
        }

        // Default values
        if (notification.getIsRead() == null) {
            notification.setIsRead(false);
        }

        return repository.create(notification);
    }

    // ==================== READ ====================
    public Notification getNotification(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid notification ID");
        }

        Notification notification = repository.findById(id);
        if (notification == null) {
            throw new IllegalArgumentException("Notification not found with ID: " + id);
        }

        return notification;
    }

    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    public List<Notification> getUserNotifications(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return repository.findByUserId(userId);
    }

    // ==================== UPDATE ====================
    @Transactional
    public Notification updateNotification(Long id, Notification data) {

        Notification notification = repository.findById(id);
        if (notification == null) {
            throw new IllegalArgumentException("Notification not found");
        }

        if (data.getMessage() != null && !data.getMessage().trim().isEmpty()) {
            notification.setMessage(data.getMessage());
        }

        if (data.getType() != null) {
            notification.setType(data.getType());
        }

        if (data.getIsRead() != null) {
            notification.setIsRead(data.getIsRead());
        }

        return repository.update(notification);
    }

    @Transactional
    public void markAsRead(Long id) {
        Notification notification = repository.findById(id);
        if (notification == null) {
            throw new IllegalArgumentException("Notification not found");
        }
        notification.setIsRead(true);
        repository.update(notification);
    }

    // ==================== DELETE ====================
    @Transactional
    public void deleteNotification(Long id) {

        Notification notification = repository.findById(id);
        if (notification == null) {
            throw new IllegalArgumentException("Notification not found");
        }

        repository.delete(id);
    }
}
