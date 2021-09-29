package jrcg.com.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jrcg.com.client.entities.Client;

@Repository
public interface ClienteRepository extends JpaRepository<Client, Long> {

}
