package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.CodiceRecuperaPassword;
import it.epicode.capstoneProject.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodiceRecuperaPasswordRepository extends JpaRepository<CodiceRecuperaPassword, Integer> {
    public Optional<CodiceRecuperaPassword> getByUtente(Utente utente);
}
