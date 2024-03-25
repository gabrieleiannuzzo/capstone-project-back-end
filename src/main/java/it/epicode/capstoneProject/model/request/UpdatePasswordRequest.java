package it.epicode.capstoneProject.model.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    // DA VALIDARE
    private String oldPassword;
    private String newPassword;
}
