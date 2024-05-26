/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.controller;


import com.marvel.marvelapiconsumer.model.CharacterDataWrapper;
import com.marvel.marvelapiconsumer.service.MarvelApiServiceConsumer;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.ApiRequestLogResponse;
import com.marvel.restapi1.Marvel_API_Rest_v1.service.ApiRequestLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marvel")
@Tag(name = "Marvel API", description = "Marvel API operations")
@RequiredArgsConstructor
public class MarvelController {

    private final MarvelApiServiceConsumer marvelService;
    private final ApiRequestLogService apiRequestLogService;



    @GetMapping("/characters")
    @Operation(summary = "Get all characters")
    @ApiResponse(responseCode = "200", description = "List of all characters")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CharacterDataWrapper getAllCharacters() {
        apiRequestLogService.logRequest("/api/v1/marvel/characters");
        return marvelService.getAllCharacters();
    }

    @GetMapping("/characters/{characterId}")
    @Operation(summary = "Get character by id")
    @ApiResponse(responseCode = "200", description = "Character found")
    @ApiResponse(responseCode = "404", description = "Character not found")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CharacterDataWrapper getCharacterById(@PathVariable int characterId) {
        apiRequestLogService.logRequest("/api/v1/marvel/characters/" + characterId);
        return marvelService.getCharacterById(characterId);
    }

    @GetMapping("/ApiRequestLog")
    @Operation(summary = "Get all ApiRequestLog")
    @ApiResponse(responseCode = "200", description = "List of all ApiRequestLog")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ApiRequestLogResponse> getAllApiRequestLog() {
        return apiRequestLogService.getApiRequestLogs();
    }
}