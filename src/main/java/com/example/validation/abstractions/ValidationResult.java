package com.example.validation.abstractions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ValidationResult {
    private final List<String> errorMessages;

    private ValidationResult(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Stream<String> getErrors() {
        return errorMessages.stream();
    }

    public boolean isFailure() {
        return !errorMessages.isEmpty();
    }

    public static ValidationResult success() {
        return new ValidationResult(Collections.emptyList());
    }

    public static ValidationResult of(List<String> errorMessages) {
        return new ValidationResult(errorMessages);
    }

    public static ValidationResult failure(String errorMessage) {
        return new ValidationResult(Collections.singletonList(errorMessage));
    }
}
