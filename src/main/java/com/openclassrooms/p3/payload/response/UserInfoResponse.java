package com.openclassrooms.p3.payload.response;

import java.sql.Date;

/**
 * Response payload for retrieving user information.
 */
public record UserInfoResponse(Long id, String name, String email, Date created_at, Date updated_at) {
}
