package io.javabrains.springsecurityjwt.models;

import java.util.List;

public class RegisterUserNewDto {
    
    private String name;
    private String email;
    private String password;
    private List<PhoneNewDto> phones;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<PhoneNewDto> getPhones() {
        return phones;
    }
    public void setPhones(List<PhoneNewDto> phones) {
        this.phones = phones;
    }

    // Getters y setters
}
