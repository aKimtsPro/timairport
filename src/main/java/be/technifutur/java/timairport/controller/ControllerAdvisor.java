package be.technifutur.java.timairport.controller;

import be.technifutur.java.timairport.exceptions.RessourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(RessourceNotFoundException ex){
        return ResponseEntity.notFound()
                .build();
    }

}
