package ro.lavinia.swagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ro.lavinia.dto.EmployeeDto;

import java.util.Map;

public interface EmployeeSwagger {

    String EXAMPLE = "{\n" +
            "  \"firstName\": \"\",\n" +
            "  \"lastName\": \"\",\n" +
            "  \"birthDate\": \"0000-00-00\",\n" +
            "  \"address\": \"\",\n" +
            "  \"salary\":  0 \n" +
            "}";

            @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> createEmployee(
            @Parameter(description = "Employee to be saved")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Create", value = EXAMPLE)}
                    )) @RequestBody EmployeeDto employeeDto, @PathVariable Integer departmentId, @PathVariable Integer jobPositionId) {
                return null;
    }


            @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> updateEmployeeWithPatch(
            @Parameter(description = "Employee to be partially updated")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Patch", value = EXAMPLE)}
                    )) @RequestBody Map<String, Object> updatedEmployee, @PathVariable("id") Integer existingId) {
                return null;
    }

            @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> updateEmployeeWithPut(
            @Parameter(description = "Employee to be updated ")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Put", value = EXAMPLE)}
                    )) @RequestBody ro.lavinia.entity.Employee updatedEmployee, @PathVariable("id") Integer existingId) {
                return null;

    }

}
