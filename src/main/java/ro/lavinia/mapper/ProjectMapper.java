package ro.lavinia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.dto.ProjectDto;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project ProjectDtoToProjectEntity(ProjectDto projectDto);

    ProjectDto ProjectEntityToProjectDto(Project project);

}
