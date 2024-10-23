package io.javabrains.springsecurityjwt.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNewRepository extends JpaRepository<UserNew, Long> {
    // Puedes definir métodos de consulta personalizados aquí, si los necesitas
    User findByUsername(String username);

    boolean existsByEmail(String email);
}