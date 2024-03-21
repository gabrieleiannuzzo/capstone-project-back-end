package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.Campionato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampionatoRepository extends JpaRepository<Campionato, Integer> {
    @Query("SELECT c FROM Campionato c WHERE c.creator.username = :creatorUsername")
    public List<Campionato> getByCreatorUsername(String creatorUsername);

    public List<Campionato> getByNomeContainingIgnoreCase(String nome);
}
