package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.interfacesForSwagger.JobPositionForSwagger;
import ro.lavinia.service.JobPositionServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobPosition")
public class JobPositionController implements JobPositionForSwagger {
    
    private final JobPositionServiceImpl jobPositionServiceImpl;

    @Operation(summary = "Save a new job position")
    @PostMapping("/create/{departmentId}")
    public void createJobPosition(@RequestBody JobPositionDto jobPositionDto,@PathVariable Integer departmentId){
        jobPositionServiceImpl.save(jobPositionDto,departmentId);
    }

    @Operation(summary = "Get a job position by Id.")
    @GetMapping("/get-by-id/{id}")
    public Optional<JobPositionDto> getById(@PathVariable Integer id) {
        return jobPositionServiceImpl.getAJobPositionById(id);
    }

    @Operation(summary = "Get all job position.")
    @GetMapping("/all-jobPosition")
    public List<JobPositionDto> getAllJobPosition() {
        return jobPositionServiceImpl.getAllJobPosition();
    }


    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/update-jobPosition-patch/{id}")
    public void updateJobPositionWithPatch(
            @RequestBody Map<String, Object> jobPosition,
            @PathVariable("id") Integer existingId) {
        jobPositionServiceImpl.updatePatch(existingId, jobPosition);
    }

    @Operation(summary = "Update job position with put.")
    @PutMapping("/update-jobPosition-put/{id}")
    public void updateJobPositionWithPut(
            @RequestBody JobPositionDto jobPositionDto ,
            @PathVariable("id") Integer existingId) {
        jobPositionServiceImpl.updatePut(existingId, jobPositionDto);
    }

    @Operation(summary = "Delete the job position by an id.")
    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable Integer id) {
        jobPositionServiceImpl.deleteById(id);
    }


}
