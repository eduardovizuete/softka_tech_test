package com.job.micro.personclient.service;

import com.job.micro.personclient.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(ClientDTO clientDTO);

    List<ClientDTO> getAllClients();

    ClientDTO getClientByClientId(Long clientId);

    ClientDTO updateClient(Long clientId, ClientDTO clientDTO);

    void deleteByClientId(Long clientId);

}
