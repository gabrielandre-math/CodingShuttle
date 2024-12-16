package com.example.coddingshuttle.RestFullApi.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {EmployeeRoleValidator.class})

public @interface EmployeeRoleValidation {
    String message() default "O cargo do funcionário deve ser USER ou ADMIN";

    Class<?>[] groups() default {}; // Corrigido para um array vazio

    Class<? extends Payload>[] payload() default {};
}
