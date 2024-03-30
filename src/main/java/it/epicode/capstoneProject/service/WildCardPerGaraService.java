package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.Gara;
import it.epicode.capstoneProject.model.entity.Pilota;
import it.epicode.capstoneProject.model.entity.Scuderia;
import it.epicode.capstoneProject.model.entity.WildCardPerGara;
import it.epicode.capstoneProject.repository.WildCardPerGaraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WildCardPerGaraService {
    private final WildCardPerGaraRepository wildCardPerGaraRepository;

    public WildCardPerGara getByIdPilotaAndIdGara(int idPilota, int idGara){
        return wildCardPerGaraRepository.getByIdPilotaAndIdGara(idPilota, idGara);
    }

    public WildCardPerGara save(Pilota p, Scuderia s, Gara g){
        WildCardPerGara w = new WildCardPerGara();
        w.setPilota(p);
        w.setScuderia(s);
        w.setGara(g);
        return wildCardPerGaraRepository.save(w);
    }
}
