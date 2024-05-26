/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response;

import com.marvel.restapi1.Marvel_API_Rest_v1.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponseDto {
    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;

    public UserResponseDto(User user) {
        if (user != null) {
            this.id = user.getId();
            this.created = user.getCreated();
            this.modified = user.getModified();
            this.lastLogin = user.getLastLogin();
            this.token = user.getToken();
            this.isActive = user.isActive();
        }
    }

    public UserResponseDto(String userId, LocalDateTime created, LocalDateTime modified, LocalDateTime lastLogin, String token, boolean isActive) {
        this.id = userId;
        this.created = created;
        this.modified = modified;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = isActive;
    }
}
