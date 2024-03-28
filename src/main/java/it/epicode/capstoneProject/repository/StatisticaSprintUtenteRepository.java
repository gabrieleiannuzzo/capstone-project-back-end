package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.StatisticaSprintUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticaSprintUtenteRepository extends JpaRepository<StatisticaSprintUtente, Integer> {
    @Query("SELECT s FROM StatisticaSprintUtente s WHERE s.utente.id = :userId")
    public StatisticaSprintUtente getByUserId(int userId);
}
