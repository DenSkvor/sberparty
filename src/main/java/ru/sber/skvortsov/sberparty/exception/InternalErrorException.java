package ru.sber.skvortsov.sberparty.exception;

public class InternalErrorException extends RuntimeException{

    public InternalErrorException(String message) {
        super(message);
    }

    public InternalErrorException() {
    }
}
