package it.epicode.capstoneProject.repository;

import it.epicode.capstoneProject.model.entity.EmailNewsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNewsletterRepository extends JpaRepository<EmailNewsletter, Integer> {
    public EmailNewsletter getByEmail(String email);
}
