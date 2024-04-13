package ro.lavinia.interfacesForSwagger;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.Column;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.dto.ProjectDto;
import ro.lavinia.entity.Project;

import java.time.LocalDate;
import java.util.Map;

public interface ProjectForSwagger {


    String EXAMPLE = "{\n" +
            "  \"name\": \"\",\n" +
            "  \"description\": \"\",\n" +
            "  \"status\": \"\",\n" +
            "  \"startDate\": \"0000-00-00\",\n" +
            "  \"endDate\": \"0000-00-00\",\n" +
            "  \"budget\":  0 \n" +
            "}";

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input")})
    default void createProject(
            @Parameter(description = "Project to be saved")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Create", value = EXAMPLE)}
                    )) @RequestBody ProjectDto projectDto, @PathVariable Integer employeeId,  @PathVariable Integer anotherEmployeeId) {
    }


    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input")})
    default void updateProjectWithPatch(
            @Parameter(description = "Project to be partially updated")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Patch", value = EXAMPLE)}
                    )) @RequestBody Map<String, Object> updateProject, @PathVariable("id") Integer existingId) {
    }

    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input")})
    default void updateProjectWithPut(
            @Parameter(description = "Project to be updated ")
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Put", value = EXAMPLE)}
                    )) @RequestBody Project updatedProject, @PathVariable("id") Integer existingId) {
    }


}
