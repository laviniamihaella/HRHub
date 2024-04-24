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
import ro.lavinia.dto.UserDto;

import java.util.Map;

public interface UserSwagger {

    String EXAMPLE = "{\n" +
            "  \"name\": \"\",\n" +
            "  \"password\": \"\",\n" +
            "  \"email\": \"\"\n" +
            "}";

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> createUser(
            @Parameter(description = "Leave Request to be saved")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Create", value = EXAMPLE)}
                    )) @RequestBody UserDto userDto) {
        return null;
    }


    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> updateUserWithPatch(
            @Parameter(description = "Leave Request to be partially updated")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Patch", value = EXAMPLE)}
                    )) @RequestBody Map<String, Object> updatedUser, @PathVariable("id") Integer existingId) {
        return null;
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> updateUserWithPut(
            @Parameter(description = "Leave Request to be updated ")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Put", value = EXAMPLE)}
                    )) @RequestBody UserDto updatedLeaveRequest, @PathVariable("id") Integer existingId) {
        return null;
    }
}
