package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.response.ProfiloUtenteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfiloUtenteResponseRepository extends JpaRepository<ProfiloUtenteResponse, Integer> {
}
