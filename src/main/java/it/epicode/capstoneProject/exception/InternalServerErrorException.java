package it.epicode.capstoneProject.exception;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String msg){
        super(msg);
    }

    public InternalServerErrorException(){
        super("Si è verificato un errore");
    }
}
