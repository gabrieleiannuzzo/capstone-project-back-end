package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.exception.UnauthorizedException;
import it.epicode.capstoneProject.model.request.*;
import it.epicode.capstoneProject.model.response.ErrorResponse;
import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.security.JwtTools;
import it.epicode.capstoneProject.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtTools jwtTools;

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

    @PutMapping("/{username}/update-username")
    public SuccessResponse updateUsername(@PathVariable String username, @RequestBody @Validated UpdateUsernameRequest updateUsernameRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        if (!jwtTools.extractUsernameFromAuthorizationHeader(request).equals(username)) throw new UnauthorizedException("Non puoi modificare questo username");
        return new SuccessResponse(utenteService.updateUsername(username, updateUsernameRequest));
    }

    @PutMapping("/{username}/update-email")
    public SuccessResponse updateEmail(@PathVariable String username, @RequestBody @Validated UpdateEmailRequest updateEmailRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        if (!jwtTools.extractUsernameFromAuthorizationHeader(request).equals(username)) throw new UnauthorizedException("Non puoi modificare questa email");
        return new SuccessResponse(utenteService.updateEmail(username, updateEmailRequest));
    }

    @PutMapping("/{username}/update-password")
    public SuccessResponse updatePassword(@PathVariable String username, @RequestBody @Validated UpdatePasswordRequest updatePasswordRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        if (!jwtTools.extractUsernameFromAuthorizationHeader(request).equals(username)) throw new UnauthorizedException("Non puoi modificare questa password");
        return new SuccessResponse(utenteService.updatePassword(username, updatePasswordRequest));
    }
}
