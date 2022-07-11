package com.example.validation.abstractions;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
public interface Validator<T> {
    ValidationResult validate(T t);

    default <E extends Exception> void validateOrThrow(T t, Consumer<T> action, Function<List<String>, E> exceptionMapper) throws E {
        ValidationResult result = validate(t);

        if(result.isFailure()) throw exceptionMapper.apply(result.getErrors().toList());
        else action.accept(t);
    }
}
