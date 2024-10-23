package io.javabrains.springsecurityjwt.models;

import io.javabrains.springsecurityjwt.models.PhoneNewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneNewDto, Long> {
    // JpaRepository provides all basic CRUD operations, no need to add anything unless you have custom queries
}

