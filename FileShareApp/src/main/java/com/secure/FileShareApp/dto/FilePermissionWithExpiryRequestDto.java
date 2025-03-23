package com.secure.FileShareApp.dto;

import jakarta.validation.constraints.Future;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class FilePermissionWithExpiryRequestDto extends FilePermissionRequestDto {

    @Future(message = "Expiry time must be in the future")
    private LocalDateTime expiryTime;
}
