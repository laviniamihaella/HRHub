package ro.lavinia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.LeaveRequest;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapper {

    LeaveRequestMapper INSTANCE = Mappers.getMapper(LeaveRequestMapper.class);

    LeaveRequest LeaveRequestDtoToLeaveRequestEntity(LeaveRequestDto leaveRequestDto);

    LeaveRequestDto LeaveRequestEntityToLeaveRequestDto(LeaveRequest leaveRequest);

}