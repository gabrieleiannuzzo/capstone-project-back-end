package it.epicode.capstoneProject.controller;

import it.epicode.capstoneProject.model.response.SuccessResponse;
import it.epicode.capstoneProject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManageController {
    private final InvitoService invitoService;
    private final CampionatoService campionatoService;
    private final PilotaService pilotaService;
    private final ScuderiaService scuderiaService;
    private final GaraService garaService;
    private final AdminService adminService;
    private final PunteggioService punteggioService;
    private final WildCardPerGaraService wildCardPerGaraService;

    @DeleteMapping("/clean")
    public SuccessResponse clean(){
        wildCardPerGaraService.deleteAll();
        invitoService.deleteAll();
        garaService.deleteAll();
        pilotaService.deleteAll();
        scuderiaService.deleteAll();
        adminService.deleteAll();
        punteggioService.deleteAll();
        campionatoService.deleteAll();
        return new SuccessResponse();
    }
}
