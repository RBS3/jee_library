package org.example.demo.service;

import org.example.demo.entity.User;
import org.example.demo.repository.UserRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 * Service for User Business Logic
 */
@Stateless
public class UserService {

    @Inject
    private UserRepository repository;

    // ==================== CREATE ====================
    @Transactional
    public User createUser(User user) {

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (repository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        return repository.create(user);
    }

    // ==================== READ ====================
    public User getUser(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        User user = repository.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        return user;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // ==================== UPDATE ====================
    @Transactional
    public User updateUser(Long id, User data) {

        User user = repository.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (data.getName() != null && !data.getName().trim().isEmpty()) {
            user.setName(data.getName());
        }

        if (data.getEmail() != null && !data.getEmail().trim().isEmpty()) {
            // If email is changing, check for uniqueness
            if (!data.getEmail().equals(user.getEmail()) && repository.findByEmail(data.getEmail()) != null) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(data.getEmail());
        }

        if (data.getMembershipType() != null) {
            user.setMembershipType(data.getMembershipType());
        }

        return repository.update(user);
    }

    // ==================== AUTHENTICATION ====================
    @Transactional
    public User register(User user) {
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (user.getRole() == null) {
            user.setRole("USER"); // Default role
        }
        return createUser(user);
    }

    public User login(String email, String password) {
        User user = repository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // ==================== DELETE ====================
    @Transactional
    public void deleteUser(Long id) {

        User user = repository.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        repository.delete(id);
    }
}
