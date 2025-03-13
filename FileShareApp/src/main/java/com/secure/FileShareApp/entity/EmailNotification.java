package com.secure.FileShareApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String emailNotificationId;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    @NotNull(message = "Recipient cannot be null")
    private User recipient;

    @NotNull(message = "Subject cannot be null")
    @Size(min = 3, max = 255, message = "Subject must be between 3 and 255 characters")
    private String subject;

    @NotNull(message = "Body cannot be null")
    @Size(min = 5, max = 2000, message = "Body must be between 5 and 2000 characters")
    private String body;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    @NotNull(message = "File cannot be null")
    private UploadedFile file;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    @NotNull(message = "Sender cannot be null")
    private User sender;
}
