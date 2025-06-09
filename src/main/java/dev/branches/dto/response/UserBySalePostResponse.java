package dev.branches.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserBySalePostResponse(
        @Schema(description = "user id saved in database", example = "fkJn4hTb6uw$#")
        String id,
        @Schema(description = "user first name", example = "Marcus")
        String firstName,
        @Schema(description = "user last name", example = "Branches")
        String lastName
) {}
