package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.request.InvitoRequest;
import it.epicode.capstoneProject.model.request.ManageInvitoRequest;
import it.epicode.capstoneProject.model.response.ErrorResponse;
import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.InvitoService;
import it.epicode.capstoneProject.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/invita")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse invita(@RequestBody @Validated InvitoRequest invitoRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        invitoService.save(invitoRequest, request);
        return new SuccessResponse(HttpStatus.CREATED.value(), null);
    }

    @PostMapping("/inviti/{id}/manage")
    public SuccessResponse manageInvito(@RequestBody @Validated ManageInvitoRequest manageInvitoRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        invitoService.manageInvito(manageInvitoRequest, request);
        return new SuccessResponse();
    }
}
