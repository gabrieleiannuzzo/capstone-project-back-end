package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.request.LoginRequest;
import it.epicode.capstoneProject.model.request.RecuperaPasswordRequest;
import it.epicode.capstoneProject.model.request.UtenteRequest;
import it.epicode.capstoneProject.model.response.ErrorResponse;
import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/register")
    public SuccessResponse register(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        ErrorResponse.checkRequestBody(bindingResult);
        return new SuccessResponse(utenteService.save(utenteRequest));
    }

    @PostMapping("/login")
    public SuccessResponse login(@RequestBody LoginRequest loginRequest){
        return new SuccessResponse(utenteService.login(loginRequest));
    }

    @PostMapping("/recupera-password")
    public SuccessResponse recuperaPassword(@RequestBody RecuperaPasswordRequest recuperaPasswordRequest){
        utenteService.recuperaPassword(recuperaPasswordRequest);
        return new SuccessResponse();
    }
}
