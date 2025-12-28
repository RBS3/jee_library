package org.example.demo.api;

import org.example.demo.entity.BorrowingRecord;
import org.example.demo.service.BorrowingRecordService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

/**
 * REST Controller for BorrowingRecord Management
 * Endpoints: /api/borrowing-records
 */
@Path("/borrowing-records")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BorrowingRecordResource {

    @Inject
    private BorrowingRecordService borrowingRecordService;

    @Context
    private UriInfo uriInfo;

    // ==================== CREATE ====================
    @POST
    public Response createBorrowingRecord(BorrowingRecord record) {
        try {
            BorrowingRecord created = borrowingRecordService.createBorrowingRecord(record);
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
    public Response getAllBorrowingRecords() {
        List<BorrowingRecord> records = borrowingRecordService.getAllBorrowingRecords();
        return Response.ok(records).build();
    }

    @GET
    @Path("{id}")
    public Response getBorrowingRecord(@PathParam("id") Long id) {
        try {
            BorrowingRecord record = borrowingRecordService.getBorrowingRecord(id);
            return Response.ok(record).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Not Found", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("user/{userId}")
    public Response getUserBorrowingRecords(@PathParam("userId") Long userId) {
        try {
            List<BorrowingRecord> records = borrowingRecordService.getUserBorrowingRecords(userId);
            return Response.ok(records).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Error", e.getMessage()))
                    .build();
        }
    }

    // ==================== UPDATE ====================
    @PUT
    @Path("{id}")
    public Response updateBorrowingRecord(@PathParam("id") Long id, BorrowingRecord recordData) {
        try {
            BorrowingRecord updated = borrowingRecordService.updateBorrowingRecord(id, recordData);
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
    public Response deleteBorrowingRecord(@PathParam("id") Long id) {
        try {
            borrowingRecordService.deleteBorrowingRecord(id);
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
