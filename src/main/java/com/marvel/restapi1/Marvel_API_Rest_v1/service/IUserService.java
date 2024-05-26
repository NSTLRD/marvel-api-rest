/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */
package com.marvel.restapi1.Marvel_API_Rest_v1.service;

import com.marvel.restapi1.Marvel_API_Rest_v1.dto.LoginDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.LoginResponseDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.UserResponseDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.UserDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.exception.TokenExpiredException;
import com.marvel.restapi1.Marvel_API_Rest_v1.model.User;
import jakarta.mail.MessagingException;

public interface IUserService {

    UserResponseDto registerUser(UserDto userDto) throws Exception;
    String generateAndSaveActivationToken(User user);
    String generateActivationCode(int length);
    LoginResponseDto loginAuthenticate(LoginDto loginDto);

    String activateAccount(String token) throws MessagingException, TokenExpiredException;
}
