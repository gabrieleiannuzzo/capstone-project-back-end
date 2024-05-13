package it.epicode.capstoneProject.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UtenteRequest {
    @NotNull(message = "Username obbligatorio")
    @Pattern(regexp = "^(?=[a-zA-ZÀ-ÿ0-9_.,(){}\\[\\]&*#$%';\"|\\+\\=\\!\\?\\-]{4,20}$)(?!.*\\s).*$", message = "Formato username non valido")
    private String username;
    @NotNull(message = "Email obbligatoria")
    @Pattern(regexp = "^([\\w\\.-]+)@[a-zA-Z\\d-]+(\\.[a-zA-Z\\d-]+)+$", message = "Formato email non valido")
    private String email;
    @NotNull(message = "Password obbligatoria")
    @Pattern(regexp = "^(?=[a-zA-ZÀ-ÿ0-9@._\\-&*#$%'()\\[\\]{}+/,;\"+=!?]{6,20}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Formato password non valido")
    private String password;
}
