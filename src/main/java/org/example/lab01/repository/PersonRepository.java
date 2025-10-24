package org.example.lab01.repository;

import org.example.lab01.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    // Spring Data JPA会自动实现基本的CRUD操作
}