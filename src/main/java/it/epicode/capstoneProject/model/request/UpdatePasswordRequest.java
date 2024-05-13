package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    @NotNull(message = "Password obbligatoria")
    @Pattern(regexp = "^(?=[a-zA-ZÀ-ÿ0-9@._\\-&*#$%'()\\[\\]{}+/,;\"+=!?]{6,20}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Formato password non valido")
    private String oldPassword;
    private String newPassword;
}
