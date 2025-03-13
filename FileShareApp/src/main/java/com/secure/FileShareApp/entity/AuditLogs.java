package com.secure.FileShareApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String logId;

    @ManyToOne
    @JoinColumn(name="file_id",nullable=false)
    private UploadedFile file;

}
