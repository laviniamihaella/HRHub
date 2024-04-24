package ro.lavinia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.dto.UserDto;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.entity.User;

@Mapper
public interface UserMapper {


    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User UserDtoDtoToUserEntity(UserDto userDto);

    UserDto UserEntityToUserDto(User user);
}
