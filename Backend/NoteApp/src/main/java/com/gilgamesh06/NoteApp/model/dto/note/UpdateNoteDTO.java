package com.gilgamesh06.NoteApp.model.dto.note;

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
public class UpdateNoteDTO {

    @NotNull(message = "El Id no puede ser nulo")
    @Positive(message = "El Id no puede ser negativo o cero")
    private  Long id;

    private String titulo;

    private String Descripcion;

}
