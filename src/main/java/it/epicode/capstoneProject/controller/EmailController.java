package it.epicode.capstoneProject.controller;

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
}
