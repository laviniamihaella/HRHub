package ro.lavinia.swagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ro.lavinia.dto.DepartmentDto;
import ro.lavinia.entity.Department;

import java.util.Map;

public interface DepartmentSwagger {

    String EXAMPLE = "{\n" +
            "  \"name\": \"\",\n" +
            "  \"description\": \"\"\n" +
            "}";

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> createDepartment(
            @Parameter(description = "Department to be saved")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Create", value = EXAMPLE)}
                    )) @RequestBody DepartmentDto departmentDto) {
        return null;
    }


    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> updateDepartmentWithPatch(
            @Parameter(description = "Department to be partially updated")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Patch", value = EXAMPLE)}
                    )) @RequestBody Map<String, Object> updatedDepartment, @PathVariable("id") Integer existingId) {
        return null;
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input"),
            @ApiResponse(responseCode = "404", description = "Not Found - Resource not found")})
    default ResponseEntity<?> updateDepartmentWithPut(
            @Parameter(description = "Department to be updated ")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Put", value = EXAMPLE)}
                    )) @RequestBody Department updatedDepartment, @PathVariable("id") Integer existingId) {
        return null;
    }
}