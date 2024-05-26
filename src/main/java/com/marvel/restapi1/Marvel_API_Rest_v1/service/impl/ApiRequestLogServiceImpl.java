/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.service.impl;

import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.ApiRequestLogResponse;
import com.marvel.restapi1.Marvel_API_Rest_v1.model.ApiRequestLog;
import com.marvel.restapi1.Marvel_API_Rest_v1.repository.ApiRequestLogRepository;
import com.marvel.restapi1.Marvel_API_Rest_v1.service.ApiRequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiRequestLogServiceImpl implements ApiRequestLogService {

   private final ApiRequestLogRepository apiRequestLogRepository;

    @Override
    public void logRequest(String endpoint) {
        ApiRequestLog log = new ApiRequestLog();
        log.setEndpoint(endpoint);
        log.setTimestamp(LocalDateTime.now());
        apiRequestLogRepository.save(log);
    }

    @Override
    public List<ApiRequestLogResponse> getApiRequestLogs() {
        return apiRequestLogRepository.findAll().stream()
                .map(log -> new ApiRequestLogResponse(log.getId(), log.getEndpoint(), log.getTimestamp().toString()))
                .toList();
    }
}
