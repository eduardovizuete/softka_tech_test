package com.job.micro.personclient.service.impl;

import com.job.micro.personclient.dto.ClientDTO;
import com.job.micro.personclient.entity.Client;
import com.job.micro.personclient.exception.ClientIdAlreadyExistsException;
import com.job.micro.personclient.exception.ClientIdNotFoundException;
import com.job.micro.personclient.exception.PersonIdentAlreadyExistsException;
import com.job.micro.personclient.repository.ClientRepository;
import com.job.micro.personclient.repository.PersonRepository;
import com.job.micro.personclient.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private PersonRepository personRepository;

    private ModelMapper modelMapper;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);

        if (clientRepository.findByClientId(client.getClientId()).isPresent()) {
            throw new ClientIdAlreadyExistsException(
                    "Client id already exists in db! : " + client.getClientId());
        }

        if (personRepository.findByIdentification(client.getIdentification()).isPresent()) {
            throw new PersonIdentAlreadyExistsException(
                    "Person identification already exists in db! : " + client.getIdentification());
        }

        Client savedClient = clientRepository.save(client);
        return modelMapper.map(savedClient, ClientDTO.class);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return Arrays.asList(
                modelMapper.map(clientRepository.findAll(), ClientDTO[].class)
        );
    }

    @Override
    public ClientDTO getClientByClientId(Long clientId) {
        Client client = clientRepository
                .findByClientId(clientId)
                .orElseThrow(() -> new ClientIdNotFoundException("Client id not found in db! : " + clientId));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public ClientDTO updateClient(Long clientId, ClientDTO clientDTO) {
        Client client = clientRepository
                .findByClientId(clientId)
                .orElseThrow(() -> new ClientIdNotFoundException("Client id not found in db! : " + clientId));
        client.setName(clientDTO.getName());
        client.setGender(clientDTO.getGender());
        client.setAge(clientDTO.getAge());
        client.setIdentification(clientDTO.getIdentification());
        client.setAddress(clientDTO.getAddress());
        client.setTelephone(clientDTO.getTelephone());
        client.setPassword(clientDTO.getPassword());
        client.setStatus(clientDTO.getStatus());

        Client savedClient = clientRepository.save(client);
        return modelMapper.map(savedClient, ClientDTO.class);
    }

    @Override
    public void deleteByClientId(Long clientId) {
        Client client = clientRepository
                .findByClientId(clientId)
                .orElseThrow(() -> new ClientIdNotFoundException("Client id not found in db! : " + clientId));
        clientRepository.deleteById(client.getId());
    }

}
