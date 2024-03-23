package it.epicode.capstoneProject.service;

import it.epicode.capstoneProject.model.entity.Admin;
import it.epicode.capstoneProject.model.entity.Invito;
import it.epicode.capstoneProject.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public void setAdmin(Invito i){
        Admin a = new Admin();
        a.setUtente(i.getToUser());
        a.setCampionato(i.getCampionato());
        adminRepository.save(a);
    }
}
