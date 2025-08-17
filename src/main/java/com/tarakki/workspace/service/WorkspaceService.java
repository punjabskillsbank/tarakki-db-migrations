package com.tarakki.workspace.service;

import com.tarakki.workspace.entity.Workspace;
import com.tarakki.workspace.entity.WorkspaceMember;
import com.tarakki.workspace.repository.WorkspaceRepository;
import com.tarakki.workspace.repository.WorkspaceMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository, 
                           WorkspaceMemberRepository workspaceMemberRepository) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceMemberRepository = workspaceMemberRepository;
    }

    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    
    public Optional<Workspace> getWorkspaceById(Long workspaceId) {
        return workspaceRepository.findById(workspaceId);
    }

    
    public List<Workspace> getWorkspacesByCreator(UUID createdBy) {
        return workspaceRepository.findByCreatedBy(createdBy);
    }

   
    public List<Workspace> searchWorkspacesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllWorkspaces();
        }
        return workspaceRepository.findByWorkspaceNameContainingIgnoreCase(name.trim());
    }

   
    public List<Workspace> getWorkspacesByMember(UUID userId) {
        return workspaceRepository.findWorkspacesByMemberId(userId);
    }

    
    public Long getWorkspaceCountByCreator(UUID createdBy) {
        return workspaceRepository.countByCreatedBy(createdBy);
    }

   
    public long getTotalWorkspaceCount() {
        return workspaceRepository.count();
    }

   
    public List<WorkspaceMember> getWorkspaceMembers(Long workspaceId) {
        return workspaceMemberRepository.findByWorkspaceId(workspaceId);
    }

   
    public List<WorkspaceMember> getWorkspaceMembersWithUserDetails(Long workspaceId) {
        return workspaceMemberRepository.findByWorkspaceIdWithUserDetails(workspaceId);
    }

    // Get workspace member count
    public Long getWorkspaceMemberCount(Long workspaceId) {
        return workspaceMemberRepository.countByWorkspaceId(workspaceId);
    }

   
    public boolean isUserMemberOfWorkspace(UUID userId, Long workspaceId) {
        return workspaceMemberRepository.existsByUserIdAndWorkspaceId(userId, workspaceId);
    }

  
    public List<WorkspaceMember> getUserWorkspaceMemberships(UUID userId) {
        return workspaceMemberRepository.findByUserId(userId);
    }

   
    public Long getUserWorkspaceCount(UUID userId) {
        return workspaceMemberRepository.countByUserId(userId);
    }
}
