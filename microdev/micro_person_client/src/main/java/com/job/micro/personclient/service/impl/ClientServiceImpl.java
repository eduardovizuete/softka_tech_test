package com.job.micro.personclient.service.impl;

import com.job.micro.personclient.dto.ClientDTO;
import com.job.micro.personclient.entity.Client;
import com.job.micro.personclient.exception.ClientIdAlreadyExistsException;
import com.job.micro.personclient.exception.ClientIdNotFoundException;
import com.job.micro.personclient.exception.PersonIdentAlreadyExistsException;
import com.job.micro.personclient.i18n.Constants;
import com.job.micro.personclient.i18n.MessageUtil;
import com.job.micro.personclient.repository.ClientRepository;
import com.job.micro.personclient.repository.PersonRepository;
import com.job.micro.personclient.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private PersonRepository personRepository;

    private ModelMapper modelMapper;

    @Override
    @Transactional()
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = modelMapper.map(clientDTO, Client.class);

        if (clientRepository.findByClientId(client.getClientId()).isPresent()) {
            throw new ClientIdAlreadyExistsException(
                    MessageUtil.getMessage(Constants.CLIENT_ID_ALREADY_EXISTS_IN_DB)
                            + client.getClientId());
        }

        if (personRepository.findByIdentification(client.getIdentification()).isPresent()) {
            throw new PersonIdentAlreadyExistsException(
                    MessageUtil.getMessage(Constants.PERSON_IDENTIFICATION_ALREADY_EXISTS_IN_DB)
                            + client.getIdentification());
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
                .orElseThrow(() -> new ClientIdNotFoundException(
                        MessageUtil.getMessage(Constants.CLIENT_ID_NOT_FOUND_IN_DB) + clientId));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    @Transactional()
    public ClientDTO updateClient(Long clientId, ClientDTO clientDTO) {
        Client client = clientRepository
                .findByClientId(clientId)
                .orElseThrow(() -> new ClientIdNotFoundException(
                        MessageUtil.getMessage(Constants.CLIENT_ID_NOT_FOUND_IN_DB) + clientId));
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
    @Transactional()
    public void deleteByClientId(Long clientId) {
        Client client = clientRepository
                .findByClientId(clientId)
                .orElseThrow(() -> new ClientIdNotFoundException(
                        MessageUtil.getMessage(Constants.CLIENT_ID_NOT_FOUND_IN_DB) + clientId));
        clientRepository.deleteById(client.getId());
    }

}
