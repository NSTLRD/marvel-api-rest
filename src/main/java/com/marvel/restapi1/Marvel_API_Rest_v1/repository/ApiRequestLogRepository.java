/**
 * @author Starling Diaz on 5/24/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/24/2024.
 */
package com.marvel.restapi1.Marvel_API_Rest_v1.repository;

import com.marvel.restapi1.Marvel_API_Rest_v1.model.ApiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRequestLogRepository extends JpaRepository<ApiRequestLog, Long> {
}