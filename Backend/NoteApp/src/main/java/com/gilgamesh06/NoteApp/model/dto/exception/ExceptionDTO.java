package com.gilgamesh06.NoteApp.model.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExceptionDTO {

    private int statusCode;

    private String message;

    private LocalDateTime date;
}
