package dev.branches.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginPostResponse(
        @Schema(description = "access token", example = "YoUr.jWt.tokEn12131")
        String token
) {}
