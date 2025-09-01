package com.gilgamesh06.NoteApp.controller;

import com.gilgamesh06.NoteApp.model.dto.note.CreateNoteDTO;
import com.gilgamesh06.NoteApp.model.dto.note.InfoNoteDTO;
import com.gilgamesh06.NoteApp.service.impl.NotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase Controller que contiene los Endpoints {create,id}
 */
@RestController
@RequestMapping("/note_app/v1/note")
public class NotaController {

    private final NotaService notaService;

    /**
     * Constructor que intancia las dependecias necesarias
     * @param notaService clase que contiene los metodos para {crear,eliminar,actualizar,obtener} nota
     */
    public NotaController(NotaService notaService){
        this.notaService = notaService;
    }

    /**
     * Endpoint: {"/create"} crear una nueva nota a partir del DTO CreateNoteDTO
     * @param createNote DTO: que contiene los atributos necesarios para crear un objeto de la clase Nota
     * @return retrona un StatusCode: 201 CREATED y un DTO InfoUserDTO
     */
    @PostMapping("/create")
    public ResponseEntity<InfoNoteDTO> saveNote(@Valid @RequestBody CreateNoteDTO createNote){
        InfoNoteDTO nota = notaService.save(createNote);
        return new ResponseEntity<>(nota, HttpStatus.CREATED);
    }


    /**
     * Endpoint: {"/{id}"} obtiene la nota que tiene el mismo id
     * @param id parametro que contiene el identificador de la nota
     * @return retorna un StatusCode 200 OK y un DTO InfoUserDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<InfoNoteDTO> getNotaById(@PathVariable Long id) {
        InfoNoteDTO nota = notaService.getNotaById(id);
        return new ResponseEntity<>(nota, HttpStatus.OK);
    }
}
