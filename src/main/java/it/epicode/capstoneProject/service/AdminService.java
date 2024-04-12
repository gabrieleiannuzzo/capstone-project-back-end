package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.Admin;
import it.epicode.capstoneProject.model.entity.Campionato;
import it.epicode.capstoneProject.model.entity.Utente;
import it.epicode.capstoneProject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public void setAdmin(Utente u, Campionato c){
        Admin a = new Admin();
        a.setUtente(u);
        a.setCampionato(c);
        adminRepository.save(a);
    }

    public void deleteAll(){
        adminRepository.deleteAll();
    }
}
