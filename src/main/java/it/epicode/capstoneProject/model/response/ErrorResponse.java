package it.epicode.capstoneProject.model.response;

import it.epicode.capstoneProject.exception.BadRequestException;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

@Data
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(){}

    public ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public static void checkRequestBody(BindingResult bindingResult){
        if (bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().get(0).getDefaultMessage());
    }
}
