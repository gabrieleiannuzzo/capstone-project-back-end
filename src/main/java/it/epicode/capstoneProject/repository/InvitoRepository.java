package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.Invito;
import it.epicode.capstoneProject.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitoRepository extends JpaRepository<Invito, Integer> {
    public List<Invito> getByToUserAndUpdatedAtIsNull(Utente toUser);
}
