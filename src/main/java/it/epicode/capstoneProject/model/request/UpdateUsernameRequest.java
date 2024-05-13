package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUsernameRequest {
    @NotNull(message = "Username obbligatorio")
    @Pattern(regexp = "^(?=[a-zA-ZÀ-ÿ0-9_.,(){}\\[\\]&*#$%';\"|\\+\\=\\!\\?\\-]{4,20}$)(?!.*\\s).*$", message = "Formato username non valido")
    private String newUsername;
}
