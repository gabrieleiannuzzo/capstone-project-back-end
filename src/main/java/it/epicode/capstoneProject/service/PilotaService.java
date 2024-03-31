package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.ConflictException;
import it.epicode.capstoneProject.exception.InternalServerErrorException;
import it.epicode.capstoneProject.exception.NotFoundException;
import it.epicode.capstoneProject.exception.UnauthorizedException;
import it.epicode.capstoneProject.model.entity.*;
import it.epicode.capstoneProject.model.enums.ActionStatusPilota;
import it.epicode.capstoneProject.model.request.AggiornaGaraRequest;
import it.epicode.capstoneProject.model.request.ChangeStatusPilotaRequest;
import it.epicode.capstoneProject.model.response.CampionatoResponse;
import it.epicode.capstoneProject.repository.PilotaRepository;
import it.epicode.capstoneProject.repository.StatisticaSprintUtenteRepository;
import it.epicode.capstoneProject.repository.StatisticaUtenteRepository;
import it.epicode.capstoneProject.security.JwtTools;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PilotaService {
    private final PilotaRepository pilotaRepository;
    private final JwtTools jwtTools;
    private final UtenteService utenteService;
    private final ScuderiaService scuderiaService;
    private final StatisticaUtenteRepository statisticaUtenteRepository;
    private final StatisticaSprintUtenteRepository statisticaSprintUtenteRepository;
    private final WildCardPerGaraService wildCardPerGaraService;

    public Pilota getById(int id){
        return pilotaRepository.findById(id).orElseThrow(() -> new NotFoundException("Pilota con id = " + id + " non trovato"));
    }

    public List<Pilota> getByUtente(Utente u){
        return pilotaRepository.getByUtente(u);
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

    public Pilota updatePunti(Pilota pilota, int punti){
        pilota.setPunti(pilota.getPunti() + punti);
        return pilotaRepository.save(pilota);
    }

    @Transactional
    public void updateStatistiche(Gara gara, AggiornaGaraRequest aggiornaGaraRequest, Pilota pilota){
        if (pilota.getUtente() == null) return;
        StatisticaUtente statisticaUtente = statisticaUtenteRepository.getByUserId(pilota.getUtente().getId());
        statisticaUtente.setPosizioneMediaGara(aggiornaMedia(aggiornaGaraRequest.getRace(), pilota, statisticaUtente.getPosizioneMediaGara(), statisticaUtente.getNumeroGareDisputate()));
        statisticaUtente.setPosizioneMediaQualifica(aggiornaMedia(aggiornaGaraRequest.getQuali(), pilota, statisticaUtente.getPosizioneMediaQualifica(), statisticaUtente.getNumeroGareDisputate()));
        statisticaUtente.setNumeroGareDisputate(statisticaUtente.getNumeroGareDisputate() + 1);
        if (isInTop(aggiornaGaraRequest.getRace(), pilota, 1)) statisticaUtente.setNumeroVittorie(statisticaUtente.getNumeroVittorie() + 1);
        if (isInTop(aggiornaGaraRequest.getQuali(), pilota, 1)) statisticaUtente.setNumeroPolePositions(statisticaUtente.getNumeroPolePositions() + 1);
        if (isInTop(aggiornaGaraRequest.getRace(), pilota, 3)) statisticaUtente.setPosizionamentiTop3(statisticaUtente.getPosizionamentiTop3() + 1);
        if (isInTop(aggiornaGaraRequest.getRace(), pilota, 10)) statisticaUtente.setPosizionamentiTop10(statisticaUtente.getPosizionamentiTop10() + 1);
        if (aggiornaGaraRequest.getRetired().contains(pilota.getId())) statisticaUtente.setNumeroRitiri(statisticaUtente.getNumeroRitiri() + 1);
        if (aggiornaGaraRequest.getPenalties().contains(pilota.getId())) statisticaUtente.setNumeroPenalita(statisticaUtente.getNumeroPenalita() + 1);
        statisticaUtenteRepository.save(statisticaUtente);
    }

    @Transactional
    public void updateStatisticheSprint(Gara gara, AggiornaGaraRequest aggiornaGaraRequest, Pilota pilota){
        if (pilota.getUtente() == null) return;
        StatisticaSprintUtente statisticaSprintUtente = statisticaSprintUtenteRepository.getByUserId(pilota.getUtente().getId());
        statisticaSprintUtente.setPosizioneMediaGara(aggiornaMedia(aggiornaGaraRequest.getSprintRace(), pilota, statisticaSprintUtente.getPosizioneMediaGara(), statisticaSprintUtente.getNumeroSprintDisputate()));
        statisticaSprintUtente.setNumeroSprintDisputate(statisticaSprintUtente.getNumeroSprintDisputate() + 1);
        if (isInTop(aggiornaGaraRequest.getSprintRace(), pilota, 1)) statisticaSprintUtente.setNumeroVittorie(statisticaSprintUtente.getNumeroVittorie() + 1);
        if (isInTop(aggiornaGaraRequest.getSprintRace(), pilota, 3)) statisticaSprintUtente.setPosizionamentiTop3(statisticaSprintUtente.getPosizionamentiTop3() + 1);
        if (aggiornaGaraRequest.getSprintRetired().contains(pilota.getId())) statisticaSprintUtente.setNumeroRitiri(statisticaSprintUtente.getNumeroRitiri() + 1);
        if (aggiornaGaraRequest.getSprintPenalties().contains(pilota.getId())) statisticaSprintUtente.setNumeroPenalita(statisticaSprintUtente.getNumeroPenalita() + 1);
        statisticaSprintUtenteRepository.save(statisticaSprintUtente);
    }

    public double aggiornaMedia(List<Integer> event, Pilota pilota, double vecchiaMedia, int vecchioNumeroGare){
        int nuovoValore = 0;
        for (int i = 0; i < event.size(); i++) {
            if (event.get(i) == pilota.getId()) {
                nuovoValore = i + 1;
                break;
            }
        }
        if (nuovoValore == 0) throw new InternalServerErrorException();
        double nuovaMedia = ((vecchiaMedia * vecchioNumeroGare) + nuovoValore) / (vecchioNumeroGare + 1);
        return nuovaMedia;
    }

    public boolean isInTop(List<Integer> event, Pilota pilota, int position){
        for (int i = 0; i < position; i++) {
            if (event.get(i) == pilota.getId()) return true;
        }
        return false;
    }

    public Scuderia getScuderiaFromPilotaAndGara(Pilota p, Gara g){
        if (!p.isWildCard()) return p.getScuderia();
        return wildCardPerGaraService.getByIdPilotaAndIdGara(p.getId(), g.getId()).getScuderia();
    }
}
