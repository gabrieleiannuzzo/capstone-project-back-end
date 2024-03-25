package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.request.InvitoRequest;
import it.epicode.capstoneProject.model.request.ManageInvitoRequest;
import it.epicode.capstoneProject.model.response.ErrorResponse;
import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.InvitoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inviti")
@RequiredArgsConstructor
public class InvitoController {
    private final InvitoService invitoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse invita(@RequestBody @Validated InvitoRequest invitoRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        invitoService.save(invitoRequest, request);
        return new SuccessResponse(HttpStatus.CREATED.value(), null);
    }

    @PostMapping("/{id}/manage")
    public SuccessResponse manageInvito(@PathVariable int id, @RequestBody @Validated ManageInvitoRequest manageInvitoRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        invitoService.manageInvito(id, manageInvitoRequest, request);
        return new SuccessResponse();
    }
}
