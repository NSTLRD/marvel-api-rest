/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */
package com.marvel.restapi1.Marvel_API_Rest_v1.constants;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    RESET_PASSWORD("reset-password");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}