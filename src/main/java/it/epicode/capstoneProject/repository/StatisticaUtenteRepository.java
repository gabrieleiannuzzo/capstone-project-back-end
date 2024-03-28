package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.StatisticaUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticaUtenteRepository extends JpaRepository<StatisticaUtente, Integer> {
    @Query("SELECT s FROM StatisticaUtente s WHERE s.utente.id = :userId")
    public StatisticaUtente getByUserId(int userId);
}
