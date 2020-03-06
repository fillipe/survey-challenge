package br.com.fill.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fill.authserver.entity.Credentials;

public interface CredentialRepository extends JpaRepository<Credentials,Long> {
	
    Credentials findByName(String name);
    
}
