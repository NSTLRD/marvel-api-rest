/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */

package com.marvel.restapi1.Marvel_API_Rest_v1.exception;

public class TokenExpiredException extends Exception {
    public TokenExpiredException(String message) {
        super(message);
    }
}
