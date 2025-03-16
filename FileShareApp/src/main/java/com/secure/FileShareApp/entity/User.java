package com.secure.FileShareApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secure.FileShareApp.dto.UserDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String userId;

    @NotNull(message = "name cannot be null")
    @Size(min=3,max=30,message = "Name must be between 3 to 30 characters")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Length(min = 8,message = "Password should be of Minimum 8 characters")
    @JsonIgnore
    private String password;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Column(unique=true)
    private String email;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull(message = "Role cannot be null")
    private Role role;

    private boolean enabled;

    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<UploadedFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<FilePermission> filePermissions = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<EmailNotification> senderEmail = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<EmailNotification> recipientEmail = new ArrayList<>();

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<RequestAccess> requestAccesses = new ArrayList<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<AccessCode> accessCodes = new ArrayList<>();

    public User(UserDto userDto){
        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.createdAt = LocalDateTime.now();
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.getRoleType().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
