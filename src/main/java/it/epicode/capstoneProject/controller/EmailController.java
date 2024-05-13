package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.classes.RichiestaCollaborazione;
import it.epicode.capstoneProject.model.classes.SegnalazioneProblema;
import it.epicode.capstoneProject.model.request.EmailNewsletterRequest;
import it.epicode.capstoneProject.model.response.ErrorResponse;
import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.EmailNewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailNewsletterService emailNewsletterService;

    @PostMapping("/iscriviti-alla-newsletter")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse iscrivitiAllaNewsletter(@RequestBody @Validated EmailNewsletterRequest emailNewsletterRequest, BindingResult bindingResult){
        ErrorResponse.checkRequestBody(bindingResult);
        return new SuccessResponse(HttpStatus.CREATED.value(), emailNewsletterService.save(emailNewsletterRequest));
    }

    @PostMapping("/richiedi-collaborazione")
    public SuccessResponse richiediCollaborazione(@RequestBody @Validated RichiestaCollaborazione richiestaCollaborazione, BindingResult bindingResult){
        ErrorResponse.checkRequestBody(bindingResult);
        emailNewsletterService.richiediCollaborazione(richiestaCollaborazione);
        return new SuccessResponse();
    }

    @PostMapping("/segnala-un-problema")
    public SuccessResponse segnalaUnProblema(@RequestBody @Validated SegnalazioneProblema segnalazioneProblema, BindingResult bindingResult){
        ErrorResponse.checkRequestBody(bindingResult);
        emailNewsletterService.segnalaUnProblema(segnalazioneProblema);
        return new SuccessResponse();
    }
}
