package ro.lavinia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.lavinia.dto.AttendanceDto;
import ro.lavinia.entity.Attendance;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    Attendance AttendanceDtoToAttendanceEntity(AttendanceDto attendanceDto);

    AttendanceDto AttendanceEntityToAttendanceDto(Attendance attendance);

}
