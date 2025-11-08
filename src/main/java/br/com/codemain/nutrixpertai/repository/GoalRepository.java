package br.com.codemain.nutrixpertai.repository;

import br.com.codemain.nutrixpertai.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    
    List<Goal> findByUser_Id(UUID userId);
}
