package com.tarakki.workspace.repository;

import com.tarakki.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    List<Workspace> findByCreatedBy(UUID createdBy);

    @Query("SELECT w FROM Workspace w WHERE LOWER(w.workspaceName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Workspace> findByWorkspaceNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT DISTINCT w FROM Workspace w " +
           "JOIN w.members m " +
           "WHERE m.userId = :userId")
    List<Workspace> findWorkspacesByMemberId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(w) FROM Workspace w WHERE w.createdBy = :createdBy")
    Long countByCreatedBy(@Param("createdBy") UUID createdBy);
}
