package io.javabrains.springsecurityjwt.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Puedes definir métodos de consulta personalizados aquí, si los necesitas
    UserNew findByUsername(String username);

    boolean existsByEmail(String email);
}