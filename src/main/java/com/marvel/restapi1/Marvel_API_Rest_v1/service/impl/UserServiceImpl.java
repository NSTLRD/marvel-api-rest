/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.service.impl;

import com.marvel.restapi1.Marvel_API_Rest_v1.constants.EmailTemplateName;
import com.marvel.restapi1.Marvel_API_Rest_v1.constants.UserRole;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.LoginDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.LoginResponseDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.Response.UserResponseDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.dto.UserDto;
import com.marvel.restapi1.Marvel_API_Rest_v1.exception.IncorrectPasswordException;
import com.marvel.restapi1.Marvel_API_Rest_v1.exception.TokenExpiredException;
import com.marvel.restapi1.Marvel_API_Rest_v1.exception.UserAlreadyExistsException;
import com.marvel.restapi1.Marvel_API_Rest_v1.exception.UserNotFoundException;
import com.marvel.restapi1.Marvel_API_Rest_v1.model.Role;
import com.marvel.restapi1.Marvel_API_Rest_v1.model.Token;
import com.marvel.restapi1.Marvel_API_Rest_v1.model.User;
import com.marvel.restapi1.Marvel_API_Rest_v1.repository.RoleRepository;
import com.marvel.restapi1.Marvel_API_Rest_v1.repository.TokenRepository;
import com.marvel.restapi1.Marvel_API_Rest_v1.repository.UserRepository;
import com.marvel.restapi1.Marvel_API_Rest_v1.security.JwtTokenProvider;
import com.marvel.restapi1.Marvel_API_Rest_v1.service.IUserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailServiceImpl emailService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Value("${mailing.frontend.activation.activationUrl}")
    private String activationUrl;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, EmailServiceImpl emailService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }


    @Override
    public UserResponseDto registerUser(UserDto userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("El correo ya esta registrado");
        }

        User user = getUser(userDto);

        Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);


        userRepository.save(user);
        //send the verification email
        sendVerificationEmail(user);

        // Now, save and flush the user with the token and associated entities
        userRepository.saveAndFlush(user);

        return new UserResponseDto(user);
    }

    private User getUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(false);
        return user;
    }

    private void sendVerificationEmail( User user) throws MessagingException {
        // generate new token
        var newToken = generateAndSaveActivationToken(user);
        user.setToken(newToken);
        userRepository.save(user);
        // send email
        emailService.sendEmail(user.getEmail(),
                user.FullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    @Override
    public String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(45))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    public String generateActivationCode(int length) {
        String characters = "123456789";
        StringBuilder resultBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            resultBuilder.append(characters.charAt(randomIndex));
        }
        return resultBuilder.toString();
    }

    @Override
    public LoginResponseDto loginAuthenticate(LoginDto loginDto) {
        if (loginDto.getEmail() == null || loginDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El Email es requerido");
        }

        if (loginDto.getPassword() == null || loginDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }

        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            var user = (User) auth.getPrincipal();
            var claims = new HashMap<String, Object>();
            claims.put("name", user.getName());
            claims.put("email", user.getEmail());
            claims.put("id", user.getId());
            claims.put("roles", user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()));
            var jwtToken = jwtTokenProvider.generateToken(claims, user);
            return LoginResponseDto.builder().token(jwtToken).build();
        } catch (AuthenticationException e) {
            if (userRepository.findByEmail(loginDto.getEmail()).isPresent()) {
                throw new IncorrectPasswordException("La contraseña es incorrecta");
            } else {
                throw new UserNotFoundException("No user found with the provided email address");
            }
        }
    }

    @Override
    @Transactional
    public String activateAccount(String token) throws MessagingException, TokenExpiredException {
        Optional<Token> tokenOptional = Optional.ofNullable(tokenRepository.findByToken(token));
        if (!tokenOptional.isPresent()) {
            throw new TokenExpiredException("Invalid token");
        }

        Token savedToken = tokenOptional.get();
        if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            sendVerificationEmail(savedToken.getUser());
            throw new TokenExpiredException("Activation token has expired. A new token has been sent to the email address.");
        }

        User user = savedToken.getUser();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        user.setActive(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
        return token;
    }
}
