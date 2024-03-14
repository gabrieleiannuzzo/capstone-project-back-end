package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    public Optional<Utente> getByUsername(String username);

    @Query("SELECT u FROM Utente u WHERE LOWER(u.email) = LOWER(:email)")
    public Optional<Utente> getByEmail(String email);
}
