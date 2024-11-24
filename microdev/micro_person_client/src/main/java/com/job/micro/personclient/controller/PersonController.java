package com.job.micro.personclient.controller;

import com.job.micro.personclient.dto.PersonDTO;
import com.job.micro.personclient.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(
        name = "Person API service - PersonController",
        description = "PersonController exposes REST APIs for person service"
)

@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
@Transactional
public class PersonController {

    private PersonService personService;

    @Operation(
            summary = "Save person REST API",
            description = "Save person REST API is used to save a person object in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    // build create person REST API
    // http://localhost:8080/api/persons/
    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO, UriComponentsBuilder uriComponentsBuilder) {
        PersonDTO savedPerson = personService.createPerson(personDTO);

        URI locationURI = uriComponentsBuilder
                .path("/api/persons/" + savedPerson.getId())
                .buildAndExpand(uriComponentsBuilder.toUriString())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(locationURI)
                .body(savedPerson);
    }

    @Operation(
            summary = "Get all persons REST API",
            description = "Get all persons REST API is used to get all persons from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get all persons by id REST API
    // http://localhost:8080/api/persons
    @GetMapping()
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> persons = personService.getAllPersons();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @Operation(
            summary = "Get person REST API",
            description = "Get person REST API is used to get a person from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get person by id REST API
    // http://localhost:8080/api/persons/1
    @GetMapping("{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable("id") Long personId) {
        PersonDTO person = personService.getPersonById(personId);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @Operation(
            summary = "Update person REST API",
            description = "Update person REST API is used to update a person from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build update person REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/persons/1
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable("id") Long personId,
                                               @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPerson = personService.updatePerson(personId, personDTO);
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete person REST API",
            description = "Delete person REST API is used to delete a person from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build delete person REST API
    @DeleteMapping("{id}")
    // http://localhost:8080/api/persons/1
    public ResponseEntity<String> deletePerson(@PathVariable("id") Long personId) {
        personService.deletePerson(personId);
        return new ResponseEntity<>("Person successfully deleted!", HttpStatus.OK);
    }

}
