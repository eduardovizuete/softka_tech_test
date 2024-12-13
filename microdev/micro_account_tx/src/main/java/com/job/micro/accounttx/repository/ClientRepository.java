package com.job.micro.accounttx.repository;

import com.job.micro.accounttx.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("unused")
public interface ClientRepository extends JpaRepository<Client, Long> {

}
