package com.restapi.restwithspringboot.repositories;

import com.restapi.restwithspringboot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
