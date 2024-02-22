package com.example.demo.MyExceptions;

public class EmptyEntryException extends RuntimeException {
    public EmptyEntryException(String message) {
        super(message);
    }
}
