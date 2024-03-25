package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.ConflictException;
import it.epicode.capstoneProject.exception.NotFoundException;
import it.epicode.capstoneProject.exception.UnauthorizedException;
import it.epicode.capstoneProject.model.entity.*;
import it.epicode.capstoneProject.model.enums.ActionStatusPilota;
import it.epicode.capstoneProject.model.request.ChangeStatusPilotaRequest;
import it.epicode.capstoneProject.model.response.CampionatoResponse;
import it.epicode.capstoneProject.repository.PilotaRepository;
import it.epicode.capstoneProject.security.JwtTools;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PilotaService {
    private final PilotaRepository pilotaRepository;
    private final JwtTools jwtTools;
    private final UtenteService utenteService;
    private final ScuderiaService scuderiaService;

    public Pilota getById(int id){
        return pilotaRepository.findById(id).orElseThrow(() -> new NotFoundException("Pilota con id = " + id + " non trovato"));
    }

    public void setPilotaTitolareDaInvito(Utente u, Campionato c, Scuderia s){
        Pilota p = new Pilota();
        p.setUtente(u);
        p.setCampionato(c);
        p.setScuderia(s);
        p.setWildCard(false);
        p.setRetired(false);
        p.setPunti(0);
        pilotaRepository.save(p);
    }

    public void setPilotaTitolareCustom(String nome, Campionato c, Scuderia s){
        Pilota p = new Pilota();
        p.setNome(nome);
        p.setCampionato(c);
        p.setScuderia(s);
        p.setWildCard(false);
        p.setRetired(false);
        p.setPunti(0);
        pilotaRepository.save(p);
    }

    public void setWildCardDaInvito(Utente u, Campionato c){
        Pilota p = new Pilota();
        p.setUtente(u);
        p.setCampionato(c);
        p.setWildCard(true);
        p.setRetired(false);
        p.setPunti(0);
        pilotaRepository.save(p);
    }

    public void setWildCardCustom(String nome, Campionato c){
        Pilota p = new Pilota();
        p.setNome(nome);
        p.setCampionato(c);
        p.setWildCard(true);
        p.setRetired(false);
        p.setPunti(0);
        pilotaRepository.save(p);
    }

    public void partecipaComePilotaTitolare(Utente u, Campionato c, Scuderia s){
        Pilota p = new Pilota();
        p.setUtente(u);
        p.setCampionato(c);
        p.setScuderia(s);
        p.setWildCard(false);
        p.setRetired(false);
        p.setPunti(0);
        pilotaRepository.save(p);
    }

    public void partecipaComeWildCard(Utente u, Campionato c){
        Pilota p = new Pilota();
        p.setUtente(u);
        p.setCampionato(c);
        p.setWildCard(true);
        p.setRetired(false);
        p.setPunti(0);
        pilotaRepository.save(p);
    }

    public CampionatoResponse changeStatusPilota(ChangeStatusPilotaRequest changeStatusPilotaRequest, HttpServletRequest request){
        Utente u = utenteService.getByUsername(jwtTools.extractUsernameFromAuthorizationHeader(request));
        Pilota p = getById(changeStatusPilotaRequest.getIdPilota());

        boolean isAdmin = false;
        for (Admin a : p.getCampionato().getAdmins()) {
            if (a.getUtente().getId() == u.getId()) {
                isAdmin = true;
                break;
            }
        }
        if (p.getCampionato().getCreator().getId() != u.getId() && !isAdmin) throw new UnauthorizedException("Non sei autorizzato a gestire questo campionato");
        if (changeStatusPilotaRequest.getAction() == ActionStatusPilota.TO_PILOTA_TITOLARE && changeStatusPilotaRequest.getIdNuovaScuderia() == null) throw new ConflictException("Devi inserire la scuderia");
        if (changeStatusPilotaRequest.getIdNuovaScuderia() != null) {
            boolean match = false;
            for (Scuderia s : p.getCampionato().getScuderie()) {
                if (s.getId() == changeStatusPilotaRequest.getIdNuovaScuderia()) {
                    match = true;
                    if (s.getPiloti().size() == 2) throw new ConflictException("La scuderia selezionata è piena");
                    break;
                }
            }
            if (!match) throw new ConflictException("La scuderia selezionata non fa parte del campionato");
        }
        if (changeStatusPilotaRequest.getAction() == ActionStatusPilota.TO_PILOTA_TITOLARE) {
            if (p.getScuderia() != null && changeStatusPilotaRequest.getIdNuovaScuderia() == p.getScuderia().getId()) throw new ConflictException("Devi cambiare la scuderia");
        }
        if (p.isWildCard() && !p.isRetired() && changeStatusPilotaRequest.getAction() == ActionStatusPilota.TO_WILD_CARD) throw new ConflictException("Il pilota è già wild card");
        if (p.isRetired() && changeStatusPilotaRequest.getAction() == ActionStatusPilota.RITIRO) throw new ConflictException("Il pilota è già ritirato");

        if (changeStatusPilotaRequest.getAction() == ActionStatusPilota.TO_PILOTA_TITOLARE) {
            return spostaInPilotiTitolari(p, scuderiaService.getById(changeStatusPilotaRequest.getIdNuovaScuderia()));
        } else if (changeStatusPilotaRequest.getAction() == ActionStatusPilota.TO_WILD_CARD) {
            return spostaInWildCards(p);
        } else {
            return spostaInPilotiRitirati(p);
        }
    }

    public CampionatoResponse spostaInPilotiTitolari(Pilota p, Scuderia s){
        p.setWildCard(false);
        p.setScuderia(s);
        p.setRetired(false);
        pilotaRepository.save(p);
        return CampionatoResponse.createByCampionato(p.getCampionato());
    }

    public CampionatoResponse spostaInWildCards(Pilota p){
        p.setWildCard(true);
        p.setScuderia(null);
        p.setRetired(false);
        pilotaRepository.save(p);
        return CampionatoResponse.createByCampionato(p.getCampionato());
    }

    public CampionatoResponse spostaInPilotiRitirati(Pilota p){
        p.setScuderia(null);
        p.setRetired(true);
        pilotaRepository.save(p);
        return CampionatoResponse.createByCampionato(p.getCampionato());
    }
}
