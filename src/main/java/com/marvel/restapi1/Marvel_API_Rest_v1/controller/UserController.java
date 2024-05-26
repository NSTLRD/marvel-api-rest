/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.controller;

import com.marvel.restapi1.Marvel_API_Rest_v1.dto.LoginDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.LoginResponseDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.UserResponseDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.UserDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.exception.IncorrectPasswordException;
import com.marvel.restapi1.Marvel_API_Rest_v1.exception.TokenExpiredException;
import com.marvel.restapi1.Marvel_API_Rest_v1.exception.UserNotFoundException;
import com.marvel.restapi1.Marvel_API_Rest_v1.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Manejo de usuarios")
public class UserController {

    private final IUserService userService;


    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user and provides a JWT for authentication.")@ApiResponses(   value = {
            @ApiResponse( responseCode = "201", description = "Usuario registrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class), examples = @ExampleObject(  name = "UserSuccessResponse", value = "{\"id\": \"d1de2fc4-8395-4942-9e5d-8887f2fd06e5\", \"created\": \"2024-04-13T00:09:22.2176616\", \"modified\": \"2024-04-13T00:09:22.2177783\", \"lastLogin\": \"2024-04-13T00:09:22.2177783\", \"token\": \"GH8HE9\", \"active\": true}"))),
            @ApiResponse(responseCode = "400", description = "Correo ya registrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "UserExists", value = "{\"mensaje\": \"Correo ya registrado\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "ServerError", value = "{\"mensaje\": \"Error interno del servidor\"}")))
    })
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDto userDto) throws Exception {
        UserResponseDto userResponse = userService.registerUser(userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and issues a JWT.")@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario logueado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class), examples = @ExampleObject(name = "LoginSuccess", value = "{\"token\": \"eyJhei...\", \"type\": \"Bearer\"}", summary = "Login exitoso"))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "CredentialsError", value = "{\"mensaje\": \"Credenciales incorrectas\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "UserNotFound", value = "{\"mensaje\": \"Usuario no encontrado\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "ServerError", value = "{\"mensaje\": \"Error interno del servidor\"}")))
    })
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginDto loginDto) {
        try {
            LoginResponseDto loginResponse = userService.loginAuthenticate(loginDto);
            return ResponseEntity.ok(loginResponse);
        } catch (UserNotFoundException | IncorrectPasswordException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Internal Server Error", ex);
        }
    }

    @GetMapping("/activate-account")
    @Operation(summary = "Activate account", description = "Activates a user account.")
    @ApiResponse(responseCode = "200", description = "Cuenta activada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = @ExampleObject(name = "AccountActivated", value = "Cuenta activada exitosamente")))
    @ApiResponse(responseCode = "400", description = "Token inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "InvalidToken", value = "{\"mensaje\": \"Token inválido\"}")))
    public void activateAccount(@RequestParam String token) throws TokenExpiredException, MessagingException {
        userService.activateAccount(token);
    }



}