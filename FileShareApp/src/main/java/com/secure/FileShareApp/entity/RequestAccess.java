package com.secure.FileShareApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RequestAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false) // Renamed column to "requester_id" for clarity
    @NotNull(message = "Requester cannot be null")
    private User requester; // The user requesting access

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    @NotNull(message = "File cannot be null")
    private UploadedFile file; // The file being requested

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Requested permission cannot be null")
    private PermissionType requestedPermission; // VIEW, DOWNLOAD, EDIT, DELETE

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Request status cannot be null")
    private RequestStatus status; // PENDING, APPROVED, REJECTED

    @NotNull(message = "Request date cannot be null")
    private LocalDateTime requestDate = LocalDateTime.now(); // Default to current time
}
