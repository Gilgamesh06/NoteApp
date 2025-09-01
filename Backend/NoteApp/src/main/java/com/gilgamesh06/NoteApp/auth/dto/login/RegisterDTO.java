package com.gilgamesh06.NoteApp.auth.dto.login;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO: para realizar el registro de un usuario
 * @see com.gilgamesh06.NoteApp.model.entity.Persona mirar estructura del objeto de la clase Persona
 * @see com.gilgamesh06.NoteApp.model.entity.Usuario mirar estructura del objeto de la clase Usuario
 */
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String nickname;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
    private String password;
}
