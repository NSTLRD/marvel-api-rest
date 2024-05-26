/**
 * @author Starling Diaz on 5/24/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/24/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ApiRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;

    private LocalDateTime timestamp;

}
