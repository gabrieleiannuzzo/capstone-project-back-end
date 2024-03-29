package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.InvitoService;
import it.epicode.capstoneProject.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utenti")
@RequiredArgsConstructor
public class UtenteController {
    private final UtenteService utenteService;
    private final InvitoService invitoService;

    @GetMapping("/username/containing/{username}")
    public SuccessResponse getUtentiContainingUsername(@PathVariable String username){
        return new SuccessResponse(utenteService.getUtentiByPartialUsername(username));
    }

    @GetMapping("/{username}/inviti-ricevuti")
    public SuccessResponse getInvitiRicevuti(@PathVariable String username){
        return new SuccessResponse(invitoService.getInvitiRicevuti(username));
    }
}
