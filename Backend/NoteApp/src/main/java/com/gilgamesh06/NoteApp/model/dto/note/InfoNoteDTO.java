package com.gilgamesh06.NoteApp.model.dto.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO: para mostar la informacion de la clase Nota
 * @see com.gilgamesh06.NoteApp.model.entity.Nota mirar estructura del objeto Nota
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InfoNoteDTO {

    @NotNull(message = "El id no puede ser nulo.")
    private Long id;

    @NotBlank(message = "El titulo no puede ser vacio.")
    @Size(min = 1, max = 100 , message = "La longitud del titulo debe ser menor que 100.")
    private String titulo;

    private String descripcion;
}
