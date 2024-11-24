package com.job.micro.accounttx.repository;

import com.job.micro.accounttx.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientId(Long clientId);

}
