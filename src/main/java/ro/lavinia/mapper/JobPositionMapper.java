package ro.lavinia.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.entity.JobPosition;

@Mapper
public interface JobPositionMapper {

    JobPositionMapper INSTANCE = Mappers.getMapper(JobPositionMapper.class);

    JobPosition JobPositionDtoToJobPositionEntity(JobPositionDto jobPositionDto);

    JobPositionDto JobPositionEntityToJobPositionDto(JobPosition jobPosition);
}
