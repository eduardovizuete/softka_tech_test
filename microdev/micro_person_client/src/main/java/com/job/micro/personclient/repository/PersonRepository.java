package com.job.micro.personclient.repository;

import com.job.micro.personclient.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByIdentification(String identification);

}
