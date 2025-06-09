package dev.branches.dto.response;

import dev.branches.model.Role.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UserGetResponse(
        @Schema(description = "user id saved in database", example = "fkJn4hTb6uw$#")
        String id,
        @Schema(description = "user first name", example = "Marcus")
        String firstName,
        @Schema(description = "user last name", example = "Branches")
        String lastName,
        @Schema(description = "user login", example = "m.branches9")
        String login,
        @Schema(description = "encrypted user password saved in database", example = "encRyPt3dPa5sw0rD")
        String encryptedPassword,
        List<RoleType> roles
) {}
