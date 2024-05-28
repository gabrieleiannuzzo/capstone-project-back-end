package it.epicode.capstoneProject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.epicode.capstoneProject.exception.InternalServerErrorException;
import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Punteggio;
import it.epicode.capstoneProject.model.request.PunteggioRequest;
import it.epicode.capstoneProject.repository.PunteggioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PunteggioService {
    private final PunteggioRepository punteggioRepository;

    public Punteggio save(PunteggioRequest punteggioRequest, Campionato campionato){
        ObjectMapper objectMapper = new ObjectMapper();
        Punteggio punteggio = new Punteggio();
        for (int i = 0; i < 10; i++) punteggioRequest.getSprintPoints().add(0);
        try {
            punteggio.setSprintPoints(objectMapper.writeValueAsString(punteggioRequest.getSprintPoints()));
            punteggio.setRacePoints(objectMapper.writeValueAsString(punteggioRequest.getRacePoints()));
        } catch (JsonProcessingException e){
            throw new InternalServerErrorException("Errore nella lettura dei punteggi");
        }
        punteggio.setCampionato(campionato);
        return punteggioRepository.save(punteggio);
    }

    public void delete(Punteggio p){
        punteggioRepository.delete(p);
    }

    public void deleteAll(){
        punteggioRepository.deleteAll();
    }
}
