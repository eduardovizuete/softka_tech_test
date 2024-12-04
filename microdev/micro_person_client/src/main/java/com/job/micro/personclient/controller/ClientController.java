package com.job.micro.personclient.controller;

import com.job.micro.personclient.dto.ClientDTO;
import com.job.micro.personclient.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(
        name = "Client API service - ClientController",
        description = "ClientController exposes REST APIs for client service"
)

@RestController
@AllArgsConstructor
@RequestMapping("/api/clients")
@Transactional
public class ClientController {

    private ClientService clientService;

    @Operation(
            summary = "Save client REST API",
            description = "Save client REST API is used to save a client object in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    // build create client REST API
    // http://localhost:8080/api/clients/
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(
            @RequestBody @Valid ClientDTO clientDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        ClientDTO savedClient = clientService.createClient(clientDTO);

        URI locationURI = uriComponentsBuilder
                .path("/api/clients/" + savedClient.getId())
                .buildAndExpand(uriComponentsBuilder.toUriString())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(locationURI)
                .body(savedClient);
    }

    @Operation(
            summary = "Get all clients REST API",
            description = "Get all clients REST API is used to get all clients from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get all clients by id REST API
    // http://localhost:8080/api/clients
    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @Operation(
            summary = "Get client REST API",
            description = "Get client REST API is used to get a client from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get client by id REST API
    // http://localhost:8080/api/clients/1
    @GetMapping("{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") Long clientId) {
        ClientDTO client = clientService.getClientByClientId(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(
            summary = "Update client REST API",
            description = "Update client REST API is used to update a client from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build update client REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/clients/1
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("id") Long clientId,
                                                  @RequestBody @Valid ClientDTO clientDTO) {
        ClientDTO updatedClient = clientService.updateClient(clientId, clientDTO);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete client REST API",
            description = "Delete client REST API is used to delete a client from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build delete client REST API
    @DeleteMapping("{id}")
    // http://localhost:8080/api/clients/1
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long clientId) {
        clientService.deleteByClientId(clientId);
        return new ResponseEntity<>("Client successfully deleted!", HttpStatus.OK);
    }

}
