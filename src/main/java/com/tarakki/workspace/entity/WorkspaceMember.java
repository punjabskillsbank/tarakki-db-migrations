package com.tarakki.workspace.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "workspace_members")
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_member_id")
    private Long workspaceMemberId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", insertable = false, updatable = false)
    private Workspace workspace;

    public WorkspaceMember() {}

    public WorkspaceMember(UUID userId, Long workspaceId) {
        this.userId = userId;
        this.workspaceId = workspaceId;
    }

    public Long getWorkspaceMemberId() {
        return workspaceMemberId;
    }

    public void setWorkspaceMemberId(Long workspaceMemberId) {
        this.workspaceMemberId = workspaceMemberId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
}
