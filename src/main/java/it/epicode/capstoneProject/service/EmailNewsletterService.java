package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.ConflictException;
import it.epicode.capstoneProject.model.classes.RichiestaCollaborazione;
import it.epicode.capstoneProject.model.classes.SegnalazioneProblema;
import it.epicode.capstoneProject.model.classes.Utility;
import it.epicode.capstoneProject.model.entity.EmailNewsletter;
import it.epicode.capstoneProject.model.request.EmailNewsletterRequest;
import it.epicode.capstoneProject.repository.EmailNewsletterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNewsletterService {
    private final EmailNewsletterRepository emailNewsletterRepository;
    private final JavaMailSenderImpl javaMailSender;

    public EmailNewsletter save(EmailNewsletterRequest request){
        EmailNewsletter e = emailNewsletterRepository.getByEmail(request.getEmail());
        if (e != null) throw new ConflictException("La mail esiste già");

        EmailNewsletter emailNewsletter = new EmailNewsletter();
        emailNewsletter.setEmail(request.getEmail());
        return emailNewsletterRepository.save(emailNewsletter);
    }

    public void segnalaUnProblema(SegnalazioneProblema segnalazioneProblema){
        String testo = "NOME: " + segnalazioneProblema.getNome() + "\n\nEMAIL: " + segnalazioneProblema.getEmail() + "\n\nTESTO: " + segnalazioneProblema.getTesto();
        Utility.sendEmail(javaMailSender, "gabrieleiannuzzo10@gmail.com", "SEGNALA UN PROBLEMA - RACEHUB", testo, false);
    }

    public void richiediCollaborazione(RichiestaCollaborazione richiestaCollaborazione){
        String testo = "NOME: " + richiestaCollaborazione.getNome() + "\n\nEMAIL: " + richiestaCollaborazione.getEmail();
        Utility.sendEmail(javaMailSender, "gabrieleiannuzzo10@gmail.com", "RICHIESTA COLLABORAZIONE - RACEHUB", testo, false);
    }
}
