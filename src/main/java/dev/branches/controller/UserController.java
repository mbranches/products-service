package dev.branches.controller;

import dev.branches.dto.response.UserGetResponse;
import dev.branches.dto.request.UserPostRequest;
import dev.branches.dto.response.UserPostResponse;
import dev.branches.exception.ArgumentNotValidMessageError;
import dev.branches.exception.DefaultMessageError;
import dev.branches.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Users", description = "Operations with users")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @Operation(
            summary = "List all users",
            responses = {
                    @ApiResponse(
                            description = "all users listed successfully",
                            responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = UserGetResponse.class))
                            )
                    ),
                    @ApiResponse(
                            description = "the requesting user does not have at least the MANAGER role",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UserGetResponse>> findAll() {
        List<UserGetResponse> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create user",
            responses = {
                    @ApiResponse(
                            description = "user created successfully",
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = UserPostResponse.class))
                    ),
                    @ApiResponse(
                            description = "given login already registered or required field not given",
                            responseCode = "400",
                            content = @Content(
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
                    ),
                    @ApiResponse(
                            description = "the requesting user does not have the ADMIN role",
                            responseCode = "403",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<UserPostResponse> createUser(@Valid @RequestBody UserPostRequest request) {
        UserPostResponse response = service.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
