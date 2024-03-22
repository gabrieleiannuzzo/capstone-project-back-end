package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.request.CampionatoRequest;
import it.epicode.capstoneProject.model.response.ErrorResponse;
import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.CampionatoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campionati")
@RequiredArgsConstructor
public class CampionatoController {
    private final CampionatoService campionatoService;

    @GetMapping("/{id}")
    public SuccessResponse getById(@PathVariable int id){
        return new SuccessResponse(campionatoService.getCampionatoResponseById(id));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse save(@RequestBody @Validated CampionatoRequest campionatoRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        return new SuccessResponse(HttpStatus.CREATED.value(), campionatoService.save(campionatoRequest, request));
    }
}
