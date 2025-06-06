package dev.branches.service;

import dev.branches.config.JwtTokenService;
import dev.branches.dto.request.LoginPostRequest;
import dev.branches.dto.request.RegisterPostRequest;
import dev.branches.dto.request.UserPostRequest;
import dev.branches.dto.response.LoginPostResponse;
import dev.branches.dto.response.UserGetResponse;
import dev.branches.dto.response.UserPostResponse;
import dev.branches.exception.BadRequestException;
import dev.branches.exception.NotFoundException;
import dev.branches.model.Role;
import dev.branches.model.Role.RoleType;
import dev.branches.model.User;
import dev.branches.model.UserRole;
import dev.branches.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public List<UserGetResponse> findAll() {
        List<User> users = repository.findAll();

        return UserGetResponse.userGetResponseListOf(users);
    }

    public LoginPostResponse login(LoginPostRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.login(), request.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        User user = (User) authentication.getPrincipal();

        return new LoginPostResponse(jwtTokenService.generateKey(user));
    }

    public void registerUser(RegisterPostRequest request) {
        if (repository.findByLogin(request.login()).isPresent()) throw new BadRequestException("Login is already registered");

        Role basicRole = roleService.findByName(RoleType.CUSTOMER);

        User userToSave = new User();
        userToSave.setLogin(request.login());
        userToSave.setFirstName(request.firstName());
        userToSave.setLastName(request.lastName());
        userToSave.setPassword(passwordEncoder.encode(request.password()));

        UserRole userRole = UserRole.builder().user(userToSave).role(basicRole).build();

        userToSave.setRoles(List.of(userRole));

        repository.save(userToSave);
    }

    public UserPostResponse createUser(UserPostRequest request) {
        String login = request.login();
        if (repository.findByLogin(login).isPresent()) throw new BadRequestException("Login is already registered");

        List<Role> roles = request.roles().stream().map(roleService::findByName).toList();

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(request.password()));
        List<UserRole> userRoles = roles.stream().map(role -> UserRole.builder().user(user).role(role).build()).toList();
        user.setRoles(userRoles);

        User savedUser = repository.save(user);

        return UserPostResponse.of(savedUser);
    }
}
