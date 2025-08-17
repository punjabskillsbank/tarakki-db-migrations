package com.tarakki.workspace.controller;

import com.tarakki.workspace.entity.Workspace;
import com.tarakki.workspace.entity.WorkspaceMember;
import com.tarakki.workspace.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspaces")
@CrossOrigin(origins = "*")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }


    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
        return ResponseEntity.ok(workspaces);
    }


    @GetMapping("/{workspaceId}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable Long workspaceId) {
        return workspaceService.getWorkspaceById(workspaceId)
                .map(workspace -> ResponseEntity.ok(workspace))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/by-creator/{creatorId}")
    public ResponseEntity<List<Workspace>> getWorkspacesByCreator(@PathVariable UUID creatorId) {
        List<Workspace> workspaces = workspaceService.getWorkspacesByCreator(creatorId);
        return ResponseEntity.ok(workspaces);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Workspace>> searchWorkspacesByName(@RequestParam String name) {
        List<Workspace> workspaces = workspaceService.searchWorkspacesByName(name);
        return ResponseEntity.ok(workspaces);
    }


    @GetMapping("/by-member/{userId}")
    public ResponseEntity<List<Workspace>> getWorkspacesByMember(@PathVariable UUID userId) {
        List<Workspace> workspaces = workspaceService.getWorkspacesByMember(userId);
        return ResponseEntity.ok(workspaces);
    }


    @GetMapping("/{workspaceId}/members")
    public ResponseEntity<List<WorkspaceMember>> getWorkspaceMembers(
            @PathVariable Long workspaceId,
            @RequestParam(required = false, defaultValue = "false") boolean includeUserDetails) {
        List<WorkspaceMember> members = includeUserDetails ?
                workspaceService.getWorkspaceMembersWithUserDetails(workspaceId) :
                workspaceService.getWorkspaceMembers(workspaceId);
        return ResponseEntity.ok(members);
    }


    @GetMapping("/{workspaceId}/members/count")
    public ResponseEntity<Long> getWorkspaceMemberCount(@PathVariable Long workspaceId) {
        Long memberCount = workspaceService.getWorkspaceMemberCount(workspaceId);
        return ResponseEntity.ok(memberCount);
    }


    @GetMapping("/{workspaceId}/members/{userId}/exists")
    public ResponseEntity<Boolean> isUserMemberOfWorkspace(
            @PathVariable Long workspaceId,
            @PathVariable UUID userId) {
        boolean isMember = workspaceService.isUserMemberOfWorkspace(userId, workspaceId);
        return ResponseEntity.ok(isMember);
    }


    @GetMapping("/memberships/by-user/{userId}")
    public ResponseEntity<List<WorkspaceMember>> getUserWorkspaceMemberships(@PathVariable UUID userId) {
        List<WorkspaceMember> memberships = workspaceService.getUserWorkspaceMemberships(userId);
        return ResponseEntity.ok(memberships);
    }


    @GetMapping("/stats")
    public ResponseEntity<WorkspaceStats> getWorkspaceStats() {
        WorkspaceStats stats = new WorkspaceStats();
        stats.setTotalWorkspaces(workspaceService.getTotalWorkspaceCount());
        return ResponseEntity.ok(stats);
    }


    @GetMapping("/stats/by-creator/{creatorId}")
    public ResponseEntity<CreatorStats> getWorkspaceStatsByCreator(@PathVariable UUID creatorId) {
        CreatorStats stats = new CreatorStats();
        stats.setCreatorId(creatorId);
        stats.setWorkspaceCount(workspaceService.getWorkspaceCountByCreator(creatorId));
        return ResponseEntity.ok(stats);
    }


    @GetMapping("/stats/by-user/{userId}")
    public ResponseEntity<UserWorkspaceStats> getUserWorkspaceStats(@PathVariable UUID userId) {
        UserWorkspaceStats stats = new UserWorkspaceStats();
        stats.setUserId(userId);
        stats.setMembershipCount(workspaceService.getUserWorkspaceCount(userId));
        stats.setCreatedCount(workspaceService.getWorkspaceCountByCreator(userId));
        return ResponseEntity.ok(stats);
    }


    public static class WorkspaceStats {
        private long totalWorkspaces;

        public long getTotalWorkspaces() {
            return totalWorkspaces;
        }

        public void setTotalWorkspaces(long totalWorkspaces) {
            this.totalWorkspaces = totalWorkspaces;
        }
    }

    public static class CreatorStats {
        private UUID creatorId;
        private Long workspaceCount;

        public UUID getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(UUID creatorId) {
            this.creatorId = creatorId;
        }

        public Long getWorkspaceCount() {
            return workspaceCount;
        }

        public void setWorkspaceCount(Long workspaceCount) {
            this.workspaceCount = workspaceCount;
        }
    }

    public static class UserWorkspaceStats {
        private UUID userId;
        private Long membershipCount;
        private Long createdCount;

        public UUID getUserId() {
            return userId;
        }

        public void setUserId(UUID userId) {
            this.userId = userId;
        }

        public Long getMembershipCount() {
            return membershipCount;
        }

        public void setMembershipCount(Long membershipCount) {
            this.membershipCount = membershipCount;
        }

        public Long getCreatedCount() {
            return createdCount;
        }

        public void setCreatedCount(Long createdCount) {
            this.createdCount = createdCount;
        }
    }
}
