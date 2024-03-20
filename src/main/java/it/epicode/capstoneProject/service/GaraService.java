package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Gara;
import it.epicode.capstoneProject.model.request.GaraRequest;
import it.epicode.capstoneProject.repository.GaraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GaraService {
    private final GaraRepository garaRepository;

    public Gara save(GaraRequest garaRequest, Campionato campionato, int numeroGara){
        Gara gara = new Gara();
        gara.setCampionato(campionato);
        gara.setNome(garaRequest.getNome().trim());
        gara.setSprint(garaRequest.getSprint());
        gara.setNumeroGara(numeroGara);
        return garaRepository.save(gara);
    }
}
