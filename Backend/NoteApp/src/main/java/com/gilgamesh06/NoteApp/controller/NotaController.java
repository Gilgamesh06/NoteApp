package com.gilgamesh06.NoteApp.controller;

import com.gilgamesh06.NoteApp.model.dto.note.CreateNoteDTO;
import com.gilgamesh06.NoteApp.model.dto.note.InfoNoteDTO;
import com.gilgamesh06.NoteApp.service.impl.NotaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Clase Controller que contiene los Endpoints {create,id}
 */
@Validated
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

    /**
     * Endpoint: {/"all"} obtiene lista de notas paginadas
     * @param page numero de pagina
     * @param size tamaño de la pagina
     * @param orderBy orden true: ascending, flase: desending
     * @return Page que cotiene la lista de InfoNoteDTO
     */
    @GetMapping("/all")
    public ResponseEntity<Page<InfoNoteDTO>> getAllNota(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(defaultValue = "true") Boolean orderBy){

        Page<InfoNoteDTO> pageNota = notaService.getAllNota(page,size,orderBy);
        return new ResponseEntity<>(pageNota, HttpStatus.OK);
    }

    /**
     * Endpoint: {"/all/{status}"} obtiene lista paginada de notas filtradas por status
     * @param status estado de la nota: activa o inactiva type Boolean
     * @param page numero de pagina
     * @param size tamaño de la pagina
     * @param orderBy orden true: ascending, flase: desending
     * @return Page que contiene la lista de InfoNoteDTO
     */
    @GetMapping("/all/{status}")
    public ResponseEntity<Page<InfoNoteDTO>> getNotaByEstado(
            @PathVariable Boolean status,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(defaultValue = "true") Boolean orderBy){

        Page<InfoNoteDTO> pageNota = notaService.getAllNoteByEstado(page,size,orderBy,status);
        return new ResponseEntity<>(pageNota, HttpStatus.OK);
    }
}
