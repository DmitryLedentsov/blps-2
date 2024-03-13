package blps.labs.controller;


import blps.labs.dto.UserSignInDTO;
import blps.labs.dto.UserSignUpDTO;
import blps.labs.entity.Role;
import blps.labs.entity.User;
import blps.labs.security.jwt.TokenProvider;
import blps.labs.service.RoleService;
import blps.labs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;

    @Autowired
    public AuthController(UserService userService, RoleService roleService, AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
        User user = new User(userSignUpDTO.getUsername(), userSignUpDTO.getPassword());
        Set<Role> roles = new HashSet<>();
        for (String roleName : userSignUpDTO.getRoleNames()) {
            Role role = roleService.findByName(roleName);
            roles.add(role);
        }
        user.setRoles(roles);
        log.debug("POST request to register user {}", user.getUsername());
        boolean isSaved = userService.saveUser(user);
        return isSaved ? new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK) :
                new ResponseEntity<>("Пользователь с таким именем уже существует", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserSignInDTO userSignInDTO) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userSignInDTO.getUsername(),
                        userSignInDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
