package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.Pilota;
import it.epicode.capstoneProject.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PilotaRepository extends JpaRepository<Pilota, Integer> {
    public List<Pilota> getByUtente(Utente utente);
}
