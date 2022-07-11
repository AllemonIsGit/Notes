package com.example.validation.abstractions;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public class Validators {
    public static <V, T extends Comparable<T>> Validator<V> lessThan(Function<V, T> valueMapper, T limit, String valueName) {
        return t -> valueMapper.apply(t).compareTo(limit) < 0 ?
                ValidationResult.success() :
                ValidationResult.failure(valueName + " has to be less than " + limit);
    }

    public static <T> Validator<T> matches(Predicate<T> predicate, String errorMessage) {
        return t -> predicate.test(t) ?
                ValidationResult.success() :
                ValidationResult.failure(errorMessage);
    }

    @SafeVarargs
    public static <T> Validator<T> allOf(Validator<T>... validators) {
        return t -> ValidationResult.of(
                Arrays.stream(validators)
                        .map(v -> v.validate(t))
                        .flatMap(ValidationResult::getErrors)
                        .toList());
    }

    @SafeVarargs
    public static <T> Validator<T> anyOf(Validator<T>... validators) {
        return t -> Arrays.stream(validators)
                .map(v -> v.validate(t))
                .filter(ValidationResult::isFailure)
                .findFirst()
                .orElse(ValidationResult.success());
    }
}
