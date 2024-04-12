package ro.lavinia.interfacesForSwagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ro.lavinia.dto.LeaveRequestDto;

import java.util.Map;

public interface LeaveRequestForSwagger {

    String EXAMPLE = "{\n" +
            "  \"startDate\": \"0000-00-00\",\n" +
            "  \"endDate\": \"0000-00-00\",\n" +
            "  \"reason\": \"\",\n" +
            "  \"status\": \"\"\n" +
            "}";

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input")})
    default void createLeaveRequest(
            @Parameter(description = "JobPosition to be saved")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Create", value = EXAMPLE)}
                    )) @RequestBody LeaveRequestDto leaveRequestDto, @PathVariable Integer employeeId) {
    }


    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input")})
    default void updateLeaveRequestPatch(
            @Parameter(description = "JobPosition to be partially updated")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Patch", value = EXAMPLE)}
                    )) @RequestBody Map<String, Object> leaveRequestDto, @PathVariable("id") Integer existingId) {
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input")})
    default void updateLeaveRequestWithPut(
            @Parameter(description = "JobPosition to be updated ")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Put", value = EXAMPLE)}
                    )) @RequestBody LeaveRequestDto leaveRequestDto, @PathVariable("id") Integer existingId) {
    }

}
