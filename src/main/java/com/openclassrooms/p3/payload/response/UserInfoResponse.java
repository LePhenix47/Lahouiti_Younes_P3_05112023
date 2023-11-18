package com.openclassrooms.p3.payload.response;

import java.time.LocalDateTime;

/**
 * Response payload for retrieving user information.
 */
public record UserInfoResponse(Long id, String name, String email, LocalDateTime created_at, LocalDateTime updated_at) {
}
