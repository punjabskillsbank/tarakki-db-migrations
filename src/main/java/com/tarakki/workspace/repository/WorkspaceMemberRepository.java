package com.tarakki.workspace.repository;

import com.tarakki.workspace.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {

    List<WorkspaceMember> findByWorkspaceId(Long workspaceId);

    List<WorkspaceMember> findByUserId(UUID userId);

    Optional<WorkspaceMember> findByUserIdAndWorkspaceId(UUID userId, Long workspaceId);

    Long countByWorkspaceId(Long workspaceId);

    Long countByUserId(UUID userId);

    boolean existsByUserIdAndWorkspaceId(UUID userId, Long workspaceId);

    @Query("SELECT wm FROM WorkspaceMember wm " +
           "JOIN FETCH wm.user " +
           "WHERE wm.workspaceId = :workspaceId")
    List<WorkspaceMember> findByWorkspaceIdWithUserDetails(@Param("workspaceId") Long workspaceId);
}
