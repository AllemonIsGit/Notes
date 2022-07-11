package com.example.validation;

import com.example.domain.model.Note;
import com.example.validation.abstractions.Validator;

import java.util.regex.Pattern;

import static com.example.validation.abstractions.Validators.*;

public class ComplexValidators {

    private static final int MAX_TITLE_LENGTH = 44;
    private static final int MAX_CONTENT_LENGTH = 1000000;
    private static final Pattern ALLOWED_TITLE_CHARACTERS_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$]");

    public static Validator<Note> noteValidator() {
        return allOf(
                lessThan(n -> n.getTitle().length(), MAX_TITLE_LENGTH, "Title length"),
                lessThan(n -> n.getContent().length(), MAX_CONTENT_LENGTH, "Content length"),
                matches(n -> ALLOWED_TITLE_CHARACTERS_PATTERN.matcher(n.getTitle()).matches(),
                        "Title can contain only alphanumeric characters"));
    }
}
