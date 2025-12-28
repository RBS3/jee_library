package org.example.demo.api;

import org.example.demo.entity.Notification;
import org.example.demo.service.NotificationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * REST Controller for Notification Management
 * Endpoints: /api/notifications
 */
@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    private NotificationService notificationService;

    @Context
    private UriInfo uriInfo;

    // ==================== CREATE ====================
    @POST
    public Response createNotification(Notification notification) {
        try {
            Notification created = notificationService.createNotification(notification);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(created.getId()))
                    .build();
            return Response.created(uri).entity(created).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Validation Error", e.getMessage()))
                    .build();
        }
    }

    // ==================== READ ====================
    @GET
    public Response getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return Response.ok(notifications).build();
    }

    @GET
    @Path("{id}")
    public Response getNotification(@PathParam("id") Long id) {
        try {
            Notification notification = notificationService.getNotification(id);
            return Response.ok(notification).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Not Found", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("user/{userId}")
    public Response getUserNotifications(@PathParam("userId") Long userId) {
        try {
            List<Notification> notifications = notificationService.getUserNotifications(userId);
            return Response.ok(notifications).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Error", e.getMessage()))
                    .build();
        }
    }

    // ==================== UPDATE ====================
    @PUT
    @Path("{id}")
    public Response updateNotification(@PathParam("id") Long id, Notification notificationData) {
        try {
            Notification updated = notificationService.updateNotification(id, notificationData);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Not Found", e.getMessage()))
                    .build();
        }
    }

    // ==================== DELETE ====================
    @DELETE
    @Path("{id}")
    public Response deleteNotification(@PathParam("id") Long id) {
        try {
            notificationService.deleteNotification(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Not Found", e.getMessage()))
                    .build();
        }
    }

    // Helper class
    public static class ErrorResponse {
        public String error;
        public String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
