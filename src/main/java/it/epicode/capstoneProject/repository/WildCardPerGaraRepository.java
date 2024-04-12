package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.WildCardPerGara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WildCardPerGaraRepository extends JpaRepository<WildCardPerGara, Integer> {
    @Query("SELECT w FROM WildCardPerGara w WHERE w.pilota.id = :idPilota AND w.gara.id = :idGara")
    public WildCardPerGara getByIdPilotaAndIdGara(int idPilota, int idGara);
}
