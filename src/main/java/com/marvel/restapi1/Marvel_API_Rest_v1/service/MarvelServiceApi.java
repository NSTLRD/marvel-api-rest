package com.marvel.restapi1.Marvel_API_Rest_v1.service; /**
 * @author Starling Diaz on 5/24/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/24/2024.
 */

import com.marvel.marvelapiconsumer.service.MarvelApiServiceConsumer;
import com.marvel.restapi1.Marvel_API_Rest_v1.model.ApiRequestLog;
import com.marvel.restapi1.Marvel_API_Rest_v1.repository.ApiRequestLogRepository;
import org.springframework.stereotype.Service;
import com.marvel.marvelapiconsumer.model.CharacterDataWrapper;

import java.time.LocalDateTime;


@Service
public class MarvelServiceApi{

    //NOTE: for some reason we need to add the rout of this package in file/project structure/libraries to avoid the error cannot resolve symbol
    private final MarvelApiServiceConsumer marvelApiService;

    private final ApiRequestLogRepository apiRequestLogRepository;

    public MarvelServiceApi(MarvelApiServiceConsumer marvelApiService, ApiRequestLogRepository apiRequestLogRepository) {
        this.marvelApiService = marvelApiService;
        this.apiRequestLogRepository = apiRequestLogRepository;
    }


    public CharacterDataWrapper getAllCharacters() {
        logRequest("/characters");
        return marvelApiService.getAllCharacters();
    }

    public CharacterDataWrapper getCharacterById(int characterId) {
        logRequest("/characters/" + characterId);
        return marvelApiService.getCharacterById(characterId);
    }

    private void logRequest(String endpoint) {
        ApiRequestLog log = new ApiRequestLog();
        log.setEndpoint(endpoint);
        log.setTimestamp(LocalDateTime.now());
        apiRequestLogRepository.save(log);
    }
}
