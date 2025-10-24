package com.gilgamesh06.NoteApp.exception;

/**
 * Clase que extiende de RuntimeException para generar mensaje de Error Personalizado
 */
public class NotaNotFoundException extends RuntimeException{

    public NotaNotFoundException(String message){
        super(message);
    }

}
