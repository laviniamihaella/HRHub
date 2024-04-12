package ro.lavinia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.localTime.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceDto {
    private  Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate data;

    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime arrivalTime;

    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime departureTime;

    @JsonManagedReference
    @JsonIgnore
    private Employee employee;

    @JsonManagedReference
    @JsonIgnore
    private Department department;
}
