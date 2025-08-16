package com.gilgamesh06.NoteApp.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@Builder
public class CreateUserDTO {

    @NotBlank(message = "El nombre no puede estar vacio.")
    private String nombre;

    private String apellido;

    @NotBlank(message = "El correo no puede ser vacio.")
    @Email(message = "El correo debe tener un formato valido")
    private String correoElectronico;

    @NotNull(message = "La fecha de nacimiento no puede ser nula.")
    @Past(message = "La fecha debe ser anterio a la fecha actual")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String nickname;

    @NotBlank(message = "La contraseña no puede estar vacia")
    private String password;
}
