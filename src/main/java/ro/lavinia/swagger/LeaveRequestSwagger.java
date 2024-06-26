package ro.lavinia.swagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.LeaveRequest;

import java.util.Map;

public interface LeaveRequestSwagger {

    String EXAMPLE = "{\n" +
            "  \"startDate\": \"0000-00-00\",\n" +
            "  \"endDate\": \"0000-00-00\",\n" +
            "  \"reason\": \"\",\n" +
            "  \"status\": \"\"\n" +
            "}";

            @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> createLeaveRequest(
            @Parameter(description = "Leave Request to be saved")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Create", value = EXAMPLE)}
                    )) @RequestBody LeaveRequestDto leaveRequestDto, @PathVariable Integer employeeId) {
                return null;
    }


            @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> updateLeaveRequestWithPatch(
            @Parameter(description = "Leave Request to be partially updated")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Patch", value = EXAMPLE)}
                    )) @RequestBody Map<String, Object> updatedLeaveRequest, @PathVariable("id") Integer existingId) {
                return null;
    }

            @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> updateLeaveRequestWithPut(
                    @Parameter(description = "Leave Request to be updated ")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Put", value = EXAMPLE)}
                    )) @RequestBody LeaveRequestDto updatedLeaveRequest, @PathVariable("id") Integer existingId) {
                return null;
    }

}
