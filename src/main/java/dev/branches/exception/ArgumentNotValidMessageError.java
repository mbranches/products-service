package dev.branches.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public record ArgumentNotValidMessageError(
        @Schema(description = "response status code", example = "404")
        int status,
        @Schema(description = "response status", example = "Bad Request")
        String error,
        @Schema(description = "error message", example = "The field 'XX' is required")
        String message
) {}
