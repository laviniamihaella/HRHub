package ro.lavinia.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ro.lavinia.entity.Role;
import ro.lavinia.token.Token;

import java.util.List;

@Data
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private Integer id;
    private String name;
    private String email;
    private String password;

    @JsonBackReference
    @JsonIgnore
    private Role role;

    @JsonBackReference
    @JsonIgnore
    private List<Token> tokens;
}
