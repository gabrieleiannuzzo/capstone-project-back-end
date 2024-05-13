package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmailNewsletterRequest {
    @NotNull(message = "Email richiesta")
    @Pattern(regexp = "^([\\w\\.-]+)@[a-zA-Z\\d-]+(\\.[a-zA-Z\\d-]+)+$", message = "Formato email non valido")
    private String email;
}
