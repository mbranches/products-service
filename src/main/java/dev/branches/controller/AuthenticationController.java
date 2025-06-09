package dev.branches.controller;

import dev.branches.dto.request.LoginPostRequest;
import dev.branches.dto.response.LoginPostResponse;
import dev.branches.dto.request.RegisterPostRequest;
import dev.branches.exception.ArgumentNotValidMessageError;
import dev.branches.exception.DefaultMessageError;
import dev.branches.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Authentication operations")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService service;

    @Operation(
            summary = "Authenticate user",
            responses = {
                    @ApiResponse(
                            description = "user successfully authenticated",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LoginPostResponse.class))
                    ),
                    @ApiResponse(
                            description = "invalid credentials given",
                            responseCode = "401",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = DefaultMessageError.class,
                                            example = """
                                                    {
                                                        "status": 401,
                                                        "message": "Login or password invalid"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "required field not given",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ArgumentNotValidMessageError.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginPostResponse> login(@Valid @RequestBody LoginPostRequest request) {
        LoginPostResponse response = service.login(request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Register user",
            responses = {
                    @ApiResponse(
                            description = "user with CUSTOMER role registered successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "given login already registered or required field not given",
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = Object.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "the given login belongs to another user",
                                                    summary = "given login already registered",
                                                    value = """
                                                            {
                                                                "status": 400,
                                                                "message": "Login is already registered"
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "a required field was not given",
                                                    summary = "required field not given",
                                                    value = """
                                                            {
                                                                "status": 400,
                                                                "error": "Bad Request",
                                                                "message": "The field 'XX' is required"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterPostRequest request) {
        service.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
