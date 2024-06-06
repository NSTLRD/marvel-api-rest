package com.marvel.restapi1.Marvel_API_Rest_v1;

import com.marvel.marvelapiconsumer.model.CharacterDataWrapper;
import com.marvel.marvelapiconsumer.service.MarvelApiServiceConsumer;
import com.marvel.restapi1.Marvel_API_Rest_v1.repository.ApiRequestLogRepository;
import com.marvel.restapi1.Marvel_API_Rest_v1.service.MarvelServiceApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarvelApiRestApplicationTests {

	@Mock
	private MarvelApiServiceConsumer marvelApiService;

	@Mock
	private ApiRequestLogRepository apiRequestLogRepository;

	@InjectMocks
	private MarvelServiceApi marvelService;

	@BeforeEach
	public void setup() {
		// No es necesario usar MockitoAnnotations.openMocks(this) cuando se usa @ExtendWith(MockitoExtension.class)
	}

	@Test
	public void testGetAllCharacters() {
		// Configurar el comportamiento simulado para que el método devuelva un objeto no nulo
		CharacterDataWrapper characterDataWrapper = new CharacterDataWrapper();
		when(marvelApiService.getAllCharacters()).thenReturn(characterDataWrapper);

		// Llamar al método que estás probando
		CharacterDataWrapper result = marvelService.getAllCharacters();

		// Asegúrate de que el resultado no sea nulo
		assertNotNull(result);


//		verify(apiRequestLogRepository, times(1)).save(any(ApiRequestLog.class));
	}

	@Test
	public void testGetCharacterById() {
		// Configurar el comportamiento simulado para que el método devuelva un objeto no nulo
		CharacterDataWrapper characterDataWrapper = new CharacterDataWrapper();
		when(marvelApiService.getCharacterById(anyInt())).thenReturn(characterDataWrapper);

		// Llamar al método que estás probando
		CharacterDataWrapper result = marvelService.getCharacterById(1);

		// Asegúrate de que el resultado no sea nulo
		assertNotNull(result);


//		verify(apiRequestLogRepository, times(1)).save(any(ApiRequestLog.class));
	}
}