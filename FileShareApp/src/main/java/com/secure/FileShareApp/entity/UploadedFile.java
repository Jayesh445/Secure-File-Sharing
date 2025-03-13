package com.secure.FileShareApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fileId;
    private String fileName;
    private String fileType;
    private String filePath;
    private String fileSize;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User uploadedBy;
    private LocalDateTime createdAt;
}
