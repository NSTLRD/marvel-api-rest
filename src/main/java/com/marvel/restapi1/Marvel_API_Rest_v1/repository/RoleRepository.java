/**
 * @author Starling Diaz on 5/25/2024.
 * @Academy mentorly
 * @version marvel-api-rest 1.0
 * @since 5/25/2024.
 */
package com.marvel.restapi1.Marvel_API_Rest_v1.repository;

import com.marvel.restapi1.Marvel_API_Rest_v1.constants.UserRole;
import com.marvel.restapi1.Marvel_API_Rest_v1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(UserRole name);
}
