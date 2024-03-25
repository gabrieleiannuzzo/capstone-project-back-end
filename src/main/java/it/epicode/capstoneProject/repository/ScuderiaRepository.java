package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.Scuderia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScuderiaRepository extends JpaRepository<Scuderia, Integer> {
}