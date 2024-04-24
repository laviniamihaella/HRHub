package ro.lavinia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.DepartmentDto;
import ro.lavinia.dto.UserDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.User;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.exception.IncompleteFieldsException;
import ro.lavinia.mapper.DepartmentMapper;
import ro.lavinia.mapper.UserMapper;
import ro.lavinia.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

private final UserRepository userRepository;
    public ResponseEntity<?> save(UserDto userDto) {

        try {
            if (userDto.getName() == null || userDto.getName().isEmpty() ||userDto.getEmail() == null|| userDto.getEmail().isEmpty()||
                    userDto.getPassword()== null || userDto.getPassword().isEmpty() ) {
               throw new IncompleteFieldsException("User information is incomplete.");
            }
            User user = UserMapper.INSTANCE.UserDtoDtoToUserEntity(userDto);
            userRepository.save(user);
        } catch (IncompleteFieldsException e) {
            return new  ResponseEntity<>("User is not authorized to save an user", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("User has been successfully saved.", HttpStatus.OK);
    }

    public ResponseEntity<?> getAnUserById(Integer existingId) {
        try {
            Optional<User> userOptional = userRepository.findById(existingId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserDto userDto = UserMapper.INSTANCE.UserEntityToUserDto(user);
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("User with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User with ID " + existingId + "  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> userList = userRepository.findAll();
            if (userList.isEmpty()) {
                throw new EntityNotFoundException("User list  NOT Found.");
            } else {
                List<UserDto> userDtoList = userList.stream().toList()
                        .stream().map(UserMapper.INSTANCE::UserEntityToUserDto)
                        .toList();
                return new ResponseEntity<>(userDtoList, HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User list  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateUserWithPatch(Integer existingId, Map<String, Object> updatedUser) {

        try {

            var userOptional = userRepository.findById(existingId);
            if (userOptional.isEmpty()) {
                throw new EntityNotFoundException("User with ID " + existingId + " not found");
            }
            User user = userOptional.get();
            for (Map.Entry<String, Object> entry : updatedUser.entrySet()) {
                String key = entry.getKey().toLowerCase();
                switch (key) {
                    case "name":
                        user.setName((String) entry.getValue());
                        break;
                    case "password":
                        user.setPassword((String) entry.getValue());
                        break;
                    case "email":
                        user.setEmail((String) entry.getValue());
                        break;
                    default:
                        throw new FieldNotFoundException("Unknown field: " + key);
                }
            }
            userRepository.save(user);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("User with ID " + existingId + " has been successfully updated with patched.", HttpStatus.OK);
    }

    public ResponseEntity<?> updateUserWithPut(Integer existingId, UserDto updatedDepartment) {
        try {

            var userOptional = userRepository.findById(existingId);
            if (userOptional.isEmpty()) {
                throw new EntityNotFoundException("User with ID " + existingId + " not found");
            }
            User existingUser = userOptional.get();

            existingUser.setName(updatedDepartment.getName());
            existingUser.setPassword(updatedDepartment.getPassword());
            existingUser.setEmail(updatedDepartment.getEmail());

            if (existingUser.getName() == null || existingUser.getName().isEmpty() ||
                    existingUser.getPassword() == null || existingUser.getPassword().isEmpty() ||
                    existingUser.getEmail() == null || existingUser.getEmail().isEmpty()) {
                return new ResponseEntity<>("User information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            userRepository.save(existingUser);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User with ID " + existingId + " has been successfully updated with put.", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteById(Integer existingId) {
        try {
            Optional<User> optionalUser = userRepository.findById(existingId);
            if (optionalUser.isPresent()) {
                userRepository.deleteById(existingId);
                return new ResponseEntity<>("User with ID " + existingId + " has been successfully deleted.", HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("User with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("User with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public String determineRedirectURL(Authentication authentication) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
