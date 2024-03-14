package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.BadRequestException;
import it.epicode.capstoneProject.exception.ConflictException;
import it.epicode.capstoneProject.exception.NotFoundException;
import it.epicode.capstoneProject.model.classes.Utility;
import it.epicode.capstoneProject.model.entity.CodiceRecuperaPassword;
import it.epicode.capstoneProject.model.entity.Utente;
import it.epicode.capstoneProject.model.enums.Ruolo;
import it.epicode.capstoneProject.model.request.*;
import it.epicode.capstoneProject.model.response.LoginResponse;
import it.epicode.capstoneProject.model.response.UtenteResponse;
import it.epicode.capstoneProject.repository.UtenteRepository;
import it.epicode.capstoneProject.security.JwtTools;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtenteService {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder encoder;
    private final JavaMailSenderImpl javaMailSender;
    private final JwtTools jwtTools;
    private final CodiceRecuperaPasswordService codiceRecuperaPasswordService;

    public List<Utente> getAll(){
        return utenteRepository.findAll();
    }

    public Utente getById(int id){
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente con id = " + id + " non trovato"));
    }

    public Utente getByUsername(String username){
        return utenteRepository.getByUsername(username).orElseThrow(() -> new NotFoundException("Username non registrato"));
    }

    public Utente getByEmail(String email){
        return utenteRepository.getByEmail(email).orElseThrow(() -> new NotFoundException("Email non registrata"));
    }

    @Transactional
    public UtenteResponse save(UtenteRequest utenteRequest){
        Utente utenteByUsername = null, utenteByEmail = null;

        try {
            utenteByUsername = getByUsername(utenteRequest.getUsername().trim());
        } catch (NotFoundException e){}

        try {
            utenteByEmail = getByEmail(utenteRequest.getEmail().trim());
        } catch (NotFoundException e){}

        if (utenteByUsername != null && utenteByEmail != null) throw new ConflictException("Username e email già in uso");
        if (utenteByUsername != null) throw new ConflictException("Username già in uso");
        if (utenteByEmail != null) throw new ConflictException("Email già in uso");

        Utente utente = new Utente();
        utente.setUsername(utenteRequest.getUsername().trim());
        utente.setEmail(utenteRequest.getEmail().trim());
        utente.setPassword(encoder.encode(utenteRequest.getPassword()));
        utente.setRuolo(Ruolo.USER);
        utente.setDataCreazione(LocalDateTime.now());
        utente.setVerificato(false);
        utenteRepository.save(utente);

        String emailText = Utility.readFile("./email-registrazione.html");
        Utility.sendEmail(javaMailSender, utente.getEmail(), "Registrazione avvenuta con successo", emailText, true);

        return UtenteResponse.createFromUtente(utente);
    }

    public LoginResponse login(LoginRequest loginRequest){
        Utente utente = loginRequest.getUser().contains("@") ? getByEmail(loginRequest.getUser()) : getByUsername(loginRequest.getUser());
        if (!encoder.matches(loginRequest.getPassword(), utente.getPassword())) throw new BadRequestException("Password errata");
        return new LoginResponse(jwtTools.createToken(utente), UtenteResponse.createFromUtente(utente));
    }

    public LoginResponse updateUsername(String username, UpdateUsernameRequest updateUsernameRequest){
        Utente utente = getByUsername(username);
        if (utente.getUsername().equals(updateUsernameRequest.getNewUsername().trim())) throw new BadRequestException("Devi cambiare l'username");
        utente.setUsername(updateUsernameRequest.getNewUsername().trim());
        utenteRepository.save(utente);
        return new LoginResponse(jwtTools.createToken(utente), UtenteResponse.createFromUtente(utente));
    }

    public LoginResponse updateEmail(String username, UpdateEmailRequest updateEmailRequest){
        Utente utente = getByUsername(username);
        if (utente.getEmail().equals(updateEmailRequest.getNewEmail().trim())) throw new BadRequestException("Devi cambiare l'email");
        utente.setEmail(updateEmailRequest.getNewEmail().trim());
        utenteRepository.save(utente);
        return new LoginResponse(jwtTools.createToken(utente), UtenteResponse.createFromUtente(utente));
    }

    public LoginResponse updatePassword(String username, UpdatePasswordRequest updatePasswordRequest){
        Utente utente = getByUsername(username);
        if (!encoder.matches(updatePasswordRequest.getOldPassword(), utente.getPassword())) throw new BadRequestException("Password errata");
        if (encoder.matches(updatePasswordRequest.getNewPassword(), utente.getPassword())) throw new BadRequestException("Devi cambiare la password");
        utente.setPassword(encoder.encode(updatePasswordRequest.getNewPassword()));
        utenteRepository.save(utente);
        return new LoginResponse(jwtTools.createToken(utente), UtenteResponse.createFromUtente(utente));
    }

    public Utente updateRuolo(String username, UpdateRuoloRequest updateRuoloRequest){
        Utente utente = getByUsername(username);
        if (utente.getRuolo() == updateRuoloRequest.getNewRuolo()) throw new BadRequestException("Non puoi assegnare all'utente lo stesso ruolo che già ha");
        utente.setRuolo(updateRuoloRequest.getNewRuolo());
        return utenteRepository.save(utente);
    }

    @Transactional
    public void recuperaPassword(RecuperaPasswordRequest request){
        Utente utente = request.getUser().contains("@") ? getByEmail(request.getUser()) : getByUsername(request.getUser());
        CodiceRecuperaPassword codiceRecuperaPassword = null;
        try {
            codiceRecuperaPassword = codiceRecuperaPasswordService.getByUtente(utente);
        } catch (NotFoundException e){}

        CodiceRecuperaPassword newCodice = codiceRecuperaPassword == null ? new CodiceRecuperaPassword(utente) : codiceRecuperaPassword;
        newCodice.setCodice();
        codiceRecuperaPasswordService.save(newCodice);
        String code = newCodice.getCodice();

        String emailText = Utility.readFile("./email-recupera-password.html").replace("{username}", utente.getUsername()).replace("{code}", code);
        Utility.sendEmail(javaMailSender, utente.getEmail(), "Recupero della password", emailText, true);
    }

    public void deleteByUsername(String username){
        Utente utente = getByUsername(username);
        utenteRepository.delete(utente);
    }
}
