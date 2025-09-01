package com.gilgamesh06.NoteApp.model.dto.login;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterDTO {

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
    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
    private String password;
}
