package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.Gara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GaraRepository extends JpaRepository<Gara, Integer> {
}
