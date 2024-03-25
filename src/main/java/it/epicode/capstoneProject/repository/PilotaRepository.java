package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.Pilota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotaRepository extends JpaRepository<Pilota, Integer> {
}
