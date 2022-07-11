package com.example.domain.exception;

import lombok.Getter;

import java.util.List;

public class IllegalNoteException extends Exception {
    @Getter
    private final List<String> errorMessages;

    public IllegalNoteException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
