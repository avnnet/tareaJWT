package io.javabrains.springsecurityjwt.models;

import java.time.LocalDateTime;

public class RegisterResponseDto {
    
    private Long id;

    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;


    
    public void setId(Long i) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'setId'");
        this.id = i;
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
    public void setToken(String token2) {
        this.token = token2;
    }
    public void setModified(LocalDateTime modified2) {       
       this.modified = modified2;
    }
    public void setIsActive(boolean b) {
        // TODO Auto-generated method stub
       this.isActive = b;
    }
    public void setCreated(LocalDateTime created2) {
        // TODO Auto-generated method stub
        this.created = created2;
    }
    public void setLastLogin(LocalDateTime lastLogin2) {
       this.lastLogin = lastLogin2;
    }

    // Getters y setters


}
