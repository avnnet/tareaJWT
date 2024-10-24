package io.javabrains.springsecurityjwt.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository2 extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    void save(UserNew newUser);

}
