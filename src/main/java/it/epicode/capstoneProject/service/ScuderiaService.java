package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.NotFoundException;
import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Scuderia;
import it.epicode.capstoneProject.model.request.ScuderiaRequest;
import it.epicode.capstoneProject.repository.ScuderiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScuderiaService {
    private final ScuderiaRepository scuderiaRepository;

    public Scuderia getById(int id){
        return scuderiaRepository.findById(id).orElseThrow(()-> new NotFoundException("Scuderia con id = " + id + " non trovata"));
    }

    public Scuderia save(ScuderiaRequest scuderiaRequest, Campionato campionato){
        Scuderia scuderia = new Scuderia();
        scuderia.setCampionato(campionato);
        scuderia.setNome(scuderiaRequest.getNome().trim());
        scuderia.setCodiceColore(scuderiaRequest.getCodiceColore());
        scuderia.setPunti(0);
        return scuderiaRepository.save(scuderia);
    }
}
