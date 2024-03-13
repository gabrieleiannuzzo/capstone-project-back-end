package it.epicode.capstoneProject.model.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SuccessResponse {
    private int status;
    private Object response;

    public SuccessResponse(){}

    public SuccessResponse(int status, Object response){
        this.status = status;
        this.response = response;
    }

    public SuccessResponse(Object response){
        status = HttpStatus.OK.value();
        this.response = response;
    }
}
