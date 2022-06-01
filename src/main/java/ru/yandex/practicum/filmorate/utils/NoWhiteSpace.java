package ru.yandex.practicum.filmorate.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = NoWhiteSpaceValidator.class)
public @interface NoWhiteSpace {
    String message() default "{NoWhiteSpace.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}