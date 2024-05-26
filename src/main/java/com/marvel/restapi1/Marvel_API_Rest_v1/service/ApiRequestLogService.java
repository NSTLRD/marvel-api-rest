/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */
package com.marvel.restapi1.Marvel_API_Rest_v1.service;

import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.ApiRequestLogResponse;

import java.util.List;

public interface ApiRequestLogService {

    void logRequest(String endpoint);
    List<ApiRequestLogResponse> getApiRequestLogs();
}
