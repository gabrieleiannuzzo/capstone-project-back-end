package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.request.LoginRequest;
import it.epicode.capstoneProject.model.request.RecuperaPasswordRequest;
import it.epicode.capstoneProject.model.request.UpdatePasswordRequest;
import it.epicode.capstoneProject.model.request.UtenteRequest;
import it.epicode.capstoneProject.model.response.ErrorResponse;
import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UtenteService utenteService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse register(@RequestBody @Validated UtenteRequest utenteRequest, BindingResult bindingResult){
        ErrorResponse.checkRequestBody(bindingResult);
        return new SuccessResponse(HttpStatus.CREATED.value(), utenteService.save(utenteRequest));
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

    @PostMapping("/reset-password/{username}/{code}")
    public SuccessResponse resetPassword(@RequestBody @Validated UpdatePasswordRequest updatePasswordRequest, BindingResult bindingResult, @PathVariable String username, @PathVariable String code){
        ErrorResponse.checkRequestBody(bindingResult);
        utenteService.resetPassword(updatePasswordRequest, username, code);
        return new SuccessResponse();
    }
}
