package org.example.demo.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * REST Application Configuration
 * Enables REST endpoints at /api/* path
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    // Auto-discovers @Path classes
}

