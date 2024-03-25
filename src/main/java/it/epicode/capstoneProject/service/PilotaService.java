package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.*;
import it.epicode.capstoneProject.repository.PilotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PilotaService {
    private final PilotaRepository pilotaRepository;

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
}
