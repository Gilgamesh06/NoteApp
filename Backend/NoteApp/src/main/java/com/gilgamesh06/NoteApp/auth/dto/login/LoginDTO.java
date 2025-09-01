package com.gilgamesh06.NoteApp.auth.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: para enviar la informacion para realizar login nickname and password
 * @see com.gilgamesh06.NoteApp.model.entity.Usuario mirar estructura de la clase Usuario
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "El nickname no puede ser vacio.")
    private String nickname;

    @NotBlank(message = "La contraseña no puede ser vacia.")
    @Size(min = 8 , message = "La contraseña debe tener minimo 8 caracteres")
    private String password;
}
