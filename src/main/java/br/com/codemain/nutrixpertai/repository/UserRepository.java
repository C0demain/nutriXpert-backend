package br.com.codemain.nutrixpertai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codemain.nutrixpertai.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>{

    boolean existsByEmailAndIdNot(String email, UUID id);
}
