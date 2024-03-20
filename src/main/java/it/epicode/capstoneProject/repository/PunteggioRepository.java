package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.Punteggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PunteggioRepository extends JpaRepository<Punteggio, Integer> {
}
