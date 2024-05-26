package com.marvel.restapi1.Marvel_API_Rest_v1;

import com.marvel.marvelapiconsumer.model.CharacterDataWrapper;
import com.marvel.restapi1.Marvel_API_Rest_v1.model.ApiRequestLog;
import com.marvel.restapi1.Marvel_API_Rest_v1.repository.ApiRequestLogRepository;
import com.marvel.restapi1.Marvel_API_Rest_v1.service.MarvelServiceApi;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class MarvelApiRestApplicationTests {

	@Mock
	private com.marvel.marvelapiconsumer.service.MarvelApiServiceConsumer marvelApiService;

	@Mock
	private ApiRequestLogRepository apiRequestLogRepository;

	@InjectMocks
	private MarvelServiceApi marvelService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllCharacters() throws IOException {
		when(marvelApiService.getAllCharacters()).thenReturn(new CharacterDataWrapper());
		CharacterDataWrapper result = marvelService.getAllCharacters();
		assertNotNull(result);
		verify(apiRequestLogRepository, times(1)).save(String.valueOf(any(ApiRequestLog.class)));
	}

	@Test
	public void testGetCharacterById() throws IOException {
		when(marvelApiService.getCharacterById(anyInt())).thenReturn(new CharacterDataWrapper());
		CharacterDataWrapper result = marvelService.getCharacterById(1);
		assertNotNull(result);
		verify(apiRequestLogRepository, times(1)).save(String.valueOf(any(ApiRequestLog.class)));
	}

	private char[] any(Class<ApiRequestLog> apiRequestLogClass) {
		return new char[0];
	}

	private LogManager verify(ApiRequestLogRepository apiRequestLogRepository, VerificationMode times) {
		return null;
	}

}
