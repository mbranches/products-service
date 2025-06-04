package dev.branches.service;

import dev.branches.controller.LoginPostResponse;
import dev.branches.dto.LoginPostRequest;
import dev.branches.dto.RegisterPostRequest;
import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
import dev.branches.model.User;
import dev.branches.model.UserRole;
import dev.branches.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public LoginPostResponse login(LoginPostRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.login(), request.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = (User) authentication.getPrincipal();

        return new LoginPostResponse(jwtTokenService.generateKey(user));
    }

    public void createUser(RegisterPostRequest request) {
        if (repository.findByLogin(request.login()).isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login is already registered");

        Role basicRole = roleService.findByName(RoleType.BASIC);

        User userToSave = new User();
        userToSave.setLogin(request.login());
        userToSave.setPassword(passwordEncoder.encode(request.password()));

        UserRole userRole = UserRole.builder().user(userToSave).role(basicRole).build();

        userToSave.setRoles(List.of(userRole));

        repository.save(userToSave);
    }
}
