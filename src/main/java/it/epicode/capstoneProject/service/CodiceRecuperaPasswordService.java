package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.exception.NotFoundException;
import it.epicode.capstoneProject.model.entity.CodiceRecuperaPassword;
import it.epicode.capstoneProject.model.entity.Utente;
import it.epicode.capstoneProject.repository.CodiceRecuperaPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodiceRecuperaPasswordService {
    private final CodiceRecuperaPasswordRepository codiceRecuperaPasswordRepository;

    public CodiceRecuperaPassword getByUtente(Utente utente){
        return codiceRecuperaPasswordRepository.getByUtente(utente).orElseThrow(() -> new NotFoundException("Codice recupera password non trovato per l'utente " + utente.getUsername()));
    }

    public CodiceRecuperaPassword save(CodiceRecuperaPassword codiceRecuperaPassword){
        return codiceRecuperaPasswordRepository.save(codiceRecuperaPassword);
    }
}
