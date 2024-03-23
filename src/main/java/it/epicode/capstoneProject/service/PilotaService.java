package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Invito;
import it.epicode.capstoneProject.model.entity.Pilota;
import it.epicode.capstoneProject.model.entity.Scuderia;
import it.epicode.capstoneProject.repository.PilotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PilotaService {
    private final PilotaRepository pilotaRepository;

    public void setPilotaTitolareDaInvito(Invito i){
        Pilota p = new Pilota();
        p.setUtente(i.getToUser());
        p.setCampionato(i.getCampionato());
        p.setScuderia(i.getScuderia());
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

    public void setWildCardDaInvito(Invito i){
        Pilota p = new Pilota();
        p.setUtente(i.getToUser());
        p.setCampionato(i.getCampionato());
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
}
