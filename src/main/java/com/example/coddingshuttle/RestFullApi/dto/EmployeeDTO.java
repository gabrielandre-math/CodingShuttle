package com.example.coddingshuttle.RestFullApi.dto;

import com.example.coddingshuttle.RestFullApi.annotations.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDTO {

    private Long id;

    @NotEmpty(message = "O nome do funcionário não pode estar vazio.")
    @NotBlank(message = "O nome do funcionário não pode estar em branco.")
    @Size(min = 3, max = 10, message = "O número de caracteres no nome deve estar entre 3 e 10.")
    private String name;

    @Email(message = "O e-mail deve ser válido.")
    @NotBlank(message = "O e-mail não pode estar em branco.")
    @NotEmpty(message = "O e-mail do funcionário não pode estar vazio.")
    private String email;

    @Max(value = 80, message = "A idade do funcionário não pode ser maior que 80.")
    @Min(value = 18, message = "A idade do funcionário não pode ser menor que 18.")
    @Positive(message = "A idade do funcionário deve ser um número positivo.")
    private Integer age;

    @NotBlank(message = "O cargo do funcionário não pode estar em branco.")
    @NotNull(message = "O cargo do funcionário não pode ser nulo.")
    //@Pattern(regexp = "^(ADMIN|USER)$", message = "O cargo do funcionário deve ser ADMIN ou USER.")
    @EmployeeRoleValidation
    private String role; //ADMIN, USER

    @NotNull(message = "O salário do funcionário não pode ser nulo.")
    @Positive(message = "O salário do funcionário deve ser um valor positivo.")
    @Digits(integer = 6, fraction = 2, message = "O salário deve estar no formato XXXXXX.YY.")
    @DecimalMax(value = "100000.99", message = "O salário não pode ser maior que 100000.99.")
    @DecimalMin(value = "100.50", message = "O salário não pode ser menor que 100.50.")
    private Double salary;

    @PastOrPresent(message = "A data de admissão do funcionário não pode estar no futuro.")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "O funcionário deve estar ativo")
    @JsonProperty("isActive")
    private Boolean isActive;

}
