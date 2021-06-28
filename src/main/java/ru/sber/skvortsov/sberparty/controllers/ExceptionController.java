package ru.sber.skvortsov.sberparty.controllers;

import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sber.skvortsov.sberparty.exception.InternalErrorException;
import ru.sber.skvortsov.sberparty.exception.NotFoundException;


@ControllerAdvice
@Log4j2
public class ExceptionController {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<String> catchConstraintViolationException(Exception exp){
        log.warn(exp.getMessage());
        exp.printStackTrace();
        return new ResponseEntity<>("Attached element not found. Incorrect id.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> catchNotFoundException(Exception exp){
        log.warn(exp.getMessage());
        exp.printStackTrace();
        return new ResponseEntity<>(exp.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalErrorException.class)
    @ResponseBody
    public ResponseEntity<String> catchInternalErrorException(Exception exp){
        log.warn(exp.getMessage());
        exp.printStackTrace();
        return new ResponseEntity<>(exp.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
