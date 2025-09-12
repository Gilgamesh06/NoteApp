package com.gilgamesh06.NoteApp.model.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ValidExceptionDTO {

    private int statusCode;

    private Map<String, String> errores;

    private LocalDateTime date;
}
