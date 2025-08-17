package com.tarakki.workspace.repository;

import com.tarakki.workspace.entity.User;
import com.tarakki.workspace.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User> findByIsActiveTrue();

    List<User> findByRole(UserRole role);

    List<User> findByRoleAndIsActiveTrue(UserRole role);

    @Query("SELECT u FROM User u WHERE u.isActive = true AND " +
           "(LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<User> findActiveUsersByNameContaining(@Param("name") String name);
}
