package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.request.AggiornaGaraRequest;
import it.epicode.capstoneProject.model.request.CampionatoRequest;
import it.epicode.capstoneProject.model.request.ChangeStatusPilotaRequest;
import it.epicode.capstoneProject.model.request.InvitoRequest;
import it.epicode.capstoneProject.model.response.ErrorResponse;
import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.CampionatoService;
import it.epicode.capstoneProject.service.GaraService;
import it.epicode.capstoneProject.service.InvitoService;
import it.epicode.capstoneProject.service.PilotaService;
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
    private final InvitoService invitoService;
    private final PilotaService pilotaService;
    private final GaraService garaService;

    @GetMapping("/{id}")
    public SuccessResponse getById(@PathVariable int id){
        return new SuccessResponse(campionatoService.getCampionatoResponseById(id));
    }

    @GetMapping("/partialNome/{partialNome}")
    public SuccessResponse getByPartialNome(@PathVariable String partialNome){
        return new SuccessResponse(campionatoService.getByPartialNome(partialNome));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse save(@RequestBody @Validated CampionatoRequest campionatoRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        return new SuccessResponse(HttpStatus.CREATED.value(), campionatoService.save(campionatoRequest, request));
    }

    @PostMapping("/aggiungi-pilota")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse aggiungiPilotaCustom(@RequestBody @Validated InvitoRequest invitoRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        invitoService.invitoCustomPilota(invitoRequest, request);
        return new SuccessResponse(HttpStatus.CREATED.value(), null);
    }

    @PostMapping("/partecipa")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse partecipa(@RequestBody @Validated InvitoRequest invitoRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        invitoService.partecipaACampionato(invitoRequest, request);
        return new SuccessResponse(HttpStatus.CREATED.value(), null);
    }

    @PutMapping("/change-status-pilota")
    public SuccessResponse changeStatusPilota(@RequestBody @Validated ChangeStatusPilotaRequest changeStatusPilotaRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        return new SuccessResponse(pilotaService.changeStatusPilota(changeStatusPilotaRequest, request));
    }

    @PostMapping("/aggiorna-gara")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse aggiornaGara(@RequestBody @Validated AggiornaGaraRequest aggiornaGaraRequest, BindingResult bindingResult, HttpServletRequest request){
        ErrorResponse.checkRequestBody(bindingResult);
        garaService.aggiornaGara(aggiornaGaraRequest, request);
        return new SuccessResponse(HttpStatus.CREATED.value(), null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponse deleteById(@PathVariable int id, HttpServletRequest request){
        campionatoService.deleteById(id, request);
        return new SuccessResponse(HttpStatus.NO_CONTENT.value(), null);
    }
}
