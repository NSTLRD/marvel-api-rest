/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiRequestLogResponse {

    private Long id;
    private String endpoint;
    private String timestamp;


    public ApiRequestLogResponse(Long id, String endpoint, String timestamp) {
        this.id = id;
        this.endpoint = endpoint;
        this.timestamp = timestamp;
    }

    public ApiRequestLogResponse(String endpoint, LocalDateTime timestamp) {
        this.endpoint = endpoint;
        this.timestamp = timestamp != null ? timestamp.toString() : null;
    }
}
