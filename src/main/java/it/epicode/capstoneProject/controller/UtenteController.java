package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utenti")
@RequiredArgsConstructor
public class UtenteController {
    private final UtenteService utenteService;

    @GetMapping("/username/containing/{username}")
    public SuccessResponse getUtentiContainingUsername(@PathVariable String username){
        return new SuccessResponse(utenteService.getUtentiByPartialUsername(username));
    }
}
