package io.javabrains.springsecurityjwt.models;

import java.time.LocalDateTime;

public class RegisterResponseDto {
    
    private Long id;

    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;
    public void setId(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
    public Long getId() {
        return id;
    }
    public LocalDateTime getCreated() {
        return created;
    }
    public LocalDateTime getModified() {
        return modified;
    }
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    public String getToken() {
        return token;
    }
    public boolean isActive() {
        return isActive;
    }

    // Getters y setters


}
