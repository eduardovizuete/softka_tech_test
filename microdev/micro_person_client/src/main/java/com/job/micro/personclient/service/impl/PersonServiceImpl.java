package com.job.micro.personclient.service.impl;

import com.job.micro.personclient.dto.PersonDTO;
import com.job.micro.personclient.entity.Person;
import com.job.micro.personclient.exception.PersonIdNotFoundException;
import com.job.micro.personclient.exception.PersonIdentAlreadyExistsException;
import com.job.micro.personclient.i18n.Constants;
import com.job.micro.personclient.i18n.MessageUtil;
import com.job.micro.personclient.repository.PersonRepository;
import com.job.micro.personclient.service.PersonService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private ModelMapper modelMapper;

    @Override
    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = modelMapper.map(personDTO, Person.class);

        if (personRepository.findByIdentification(person.getIdentification()).isPresent()) {
            throw new PersonIdentAlreadyExistsException(
                    MessageUtil.getMessage(Constants.PERSON_IDENTIFICATION_ALREADY_EXISTS_IN_DB)
                            + person.getIdentification());
        }

        Person savePerson = personRepository.save(person);
        return modelMapper.map(savePerson, PersonDTO.class);
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        return Arrays.asList(
                modelMapper.map(personRepository.findAll(), PersonDTO[].class)
        );
    }

    @Override
    public PersonDTO getPersonById(Long personId) {
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> new PersonIdNotFoundException(
                        MessageUtil.getMessage(Constants.PERSON_ID_NOT_FOUND_IN_DB)
                                + personId));
        return modelMapper.map(person, PersonDTO.class);
    }

    @Override
    public PersonDTO updatePerson(Long personId, PersonDTO personDTO) {
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> new PersonIdNotFoundException(
                        MessageUtil.getMessage(Constants.PERSON_ID_NOT_FOUND_IN_DB)
                                + personId));
        person.setName(personDTO.getName());
        person.setGender(personDTO.getGender());
        person.setAge(personDTO.getAge());
        person.setIdentification(personDTO.getIdentification());
        person.setAddress(personDTO.getAddress());
        person.setTelephone(personDTO.getTelephone());

        Person savePerson = personRepository.save(person);
        return modelMapper.map(savePerson, PersonDTO.class);
    }

    @Override
    public void deletePerson(Long personId) {
        Person person = personRepository
                .findById(personId)
                .orElseThrow(() -> new PersonIdNotFoundException(
                        MessageUtil.getMessage(Constants.PERSON_ID_NOT_FOUND_IN_DB)
                                + personId));
        personRepository.deleteById(person.getId());
    }

}
