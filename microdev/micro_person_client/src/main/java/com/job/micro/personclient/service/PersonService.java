package com.job.micro.personclient.service;

import com.job.micro.personclient.dto.PersonDTO;

import java.util.List;

public interface PersonService {

    PersonDTO createPerson(PersonDTO personDTO);

    List<PersonDTO> getAllPersons();

    PersonDTO getPersonById(Long personId);

    PersonDTO updatePerson(Long personId, PersonDTO personDTO);

    void deletePerson(Long personId);

}
