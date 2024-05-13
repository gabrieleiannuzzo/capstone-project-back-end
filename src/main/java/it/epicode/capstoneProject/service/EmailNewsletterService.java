package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.ConflictException;
import it.epicode.capstoneProject.model.entity.EmailNewsletter;
import it.epicode.capstoneProject.model.request.EmailNewsletterRequest;
import it.epicode.capstoneProject.repository.EmailNewsletterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNewsletterService {
    private final EmailNewsletterRepository emailNewsletterRepository;

    public EmailNewsletter save(EmailNewsletterRequest request){
        EmailNewsletter e = emailNewsletterRepository.getByEmail(request.getEmail());
        if (e == null) throw new ConflictException("La mail esiste già");

        EmailNewsletter emailNewsletter = new EmailNewsletter();
        emailNewsletter.setEmail(request.getEmail());
        return emailNewsletterRepository.save(emailNewsletter);
    }
}
