package com.gilgamesh06.NoteApp.model.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InfoUserDTO {

    @NotBlank(message = "El nombre de usuario no debe ser vacio.")
    private String nickname;

    @NotBlank(message = "El nombre no debe ser vacio.")
    private String nombre;

    private String apellido;

    @NotBlank(message = "El correo no puede ser vacio.")
    @Email(message = "El correo debe tener formato valido.")
    private String correoElectronico;

    @NotNull(message = "La edad no puede ser Nula.")
    @Positive(message = "La edad no puede ser negativa.")
    private Long edad;
}
