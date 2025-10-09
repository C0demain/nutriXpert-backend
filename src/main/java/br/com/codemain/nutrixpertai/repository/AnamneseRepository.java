package br.com.codemain.nutrixpertai.repository;

import br.com.codemain.nutrixpertai.entity.Anamnese;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnamneseRepository extends JpaRepository<Anamnese, UUID> {
}
