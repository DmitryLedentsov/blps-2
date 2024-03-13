package blps.labs.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserSignUpDTO {
    private String username;
    private String password;
    private Set<String> roleNames;
}
