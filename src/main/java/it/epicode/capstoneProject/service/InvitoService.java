package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.ConflictException;
import it.epicode.capstoneProject.exception.UnauthorizedException;
import it.epicode.capstoneProject.model.entity.*;
import it.epicode.capstoneProject.model.enums.RuoloInvito;
import it.epicode.capstoneProject.model.request.InvitoRequest;
import it.epicode.capstoneProject.model.response.InvitoResponse;
import it.epicode.capstoneProject.repository.InvitoRepository;
import it.epicode.capstoneProject.security.JwtTools;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitoService {
    private final InvitoRepository invitoRepository;
    private final CampionatoService campionatoService;
    private final UtenteService utenteService;
    private final ScuderiaService scuderiaService;
    private final JwtTools jwtTools;

    public List<InvitoResponse> getInvitiRicevuti(String username){
        Utente toUser = utenteService.getByUsername(username);
        List<Invito> inviti = invitoRepository.getByToUser(toUser);
        List<InvitoResponse> response = new ArrayList<>();
        for (Invito i : inviti) response.add(InvitoResponse.createFromInvito(i));
        return response;
    }

    public void save(InvitoRequest invitoRequest, HttpServletRequest request){
        Utente fromUser = utenteService.getByUsername(jwtTools.extractUsernameFromAuthorizationHeader(request));
        Campionato campionato = campionatoService.getById(invitoRequest.getIdCampionato());
        if (campionato.getCreator().getId() != fromUser.getId() || checkIdInAdminsList(fromUser.getUsername(), campionato.getAdmins())) throw new UnauthorizedException("Non puoi inviare inviti per questo campionato");
        if (fromUser.getUsername().equals(invitoRequest.getToUserUsername())) throw new ConflictException("Non puoi invitare te stesso");
        if (invitoRequest.getRuoloInvito() == RuoloInvito.ADMIN && checkIdInAdminsList(invitoRequest.getToUserUsername(), campionato.getAdmins())) throw new ConflictException("L'utente selezionato è già un admin");
        if (invitoRequest.getRuoloInvito() != RuoloInvito.ADMIN && (checkIdInPilotiList(invitoRequest.getToUserUsername(), campionato.getPiloti()))) throw new ConflictException("L'utente selezionato è già pilota");
        if (invitoRequest.getRuoloInvito() == RuoloInvito.PILOTA_TITOLARE && invitoRequest.getIdScuderia() == null) throw new ConflictException("Devi inserire la scuderia");
        if (invitoRequest.getIdScuderia() != null) {
            if (checkIdInScuderieList(invitoRequest.getIdScuderia(), campionato.getScuderie())) throw new ConflictException("Non esistono scuderie con questo id");
            if (extractScuderiaFromScuderieList(invitoRequest.getIdScuderia(), campionato.getScuderie()).getPiloti().size() == 2) throw new ConflictException("La scuderia è piena");
        }
        //INSERIRE CONTROLLO CHE NON CI SIA GIA L'INVITO
        Utente toUser = utenteService.getByUsername(invitoRequest.getToUserUsername());
        Scuderia scuderia = invitoRequest.getRuoloInvito() == RuoloInvito.PILOTA_TITOLARE ? scuderiaService.getById(invitoRequest.getIdScuderia()) : null;

        Invito invito = new Invito();
        invito.setCampionato(campionato);
        invito.setFromUser(fromUser);
        invito.setToUser(toUser);
        invito.setRuoloInvito(invitoRequest.getRuoloInvito());
        invito.setScuderia(scuderia);
        invito.setAccepted(false);
        invito.setCreatedAt(LocalDateTime.now());
        invitoRepository.save(invito);
    }

    public boolean checkIdInAdminsList(String username, List<Admin> admins){
        for (Admin a : admins) {
            if (a.getUtente().getUsername() == username) return true;
        }
        return false;
    }

    public boolean checkIdInPilotiList(String username, List<Pilota> piloti){
        for (Pilota p : piloti) {
            if (p.getUtente().getUsername() == username) return true;
        }
        return false;
    }

    public boolean checkIdInScuderieList(int id, List<Scuderia> scuderie){
        for (Scuderia s : scuderie) {
            if (s.getId() == id) return true;
        }
        return false;
    }

    public Scuderia extractScuderiaFromScuderieList(int id, List<Scuderia> scuderie){
        Scuderia scuderia = null;
        for (Scuderia s : scuderie) {
            if (s.getId() == id) {
                scuderia = s;
                break;
            }
        }
        return scuderia;
    }
}
