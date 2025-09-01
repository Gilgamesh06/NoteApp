package com.gilgamesh06.NoteApp.service.impl;

import com.gilgamesh06.NoteApp.model.dto.note.CreateNoteDTO;
import com.gilgamesh06.NoteApp.model.dto.note.InfoNoteDTO;
import com.gilgamesh06.NoteApp.model.entity.Nota;
import com.gilgamesh06.NoteApp.model.entity.Usuario;
import com.gilgamesh06.NoteApp.repository.NotaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clase para crear los servicios de la entidad nota (Crear, eliminar, actualizar, obtener}
 */
@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final UsuarioService usuarioService;

    /**
     * Constructor que inicializa las dependencias necesarias.
     * @param notaRepository repository de notas permite manejar consultas psql o metodos de JpaRepository
     * @see org.springframework.data.jpa.repository.JpaRepository;
     * @param usuarioService servicio de la clase usuario
     */
    public NotaService(NotaRepository notaRepository, UsuarioService usuarioService){
        this.usuarioService = usuarioService;
        this.notaRepository = notaRepository;
    }


    /**
     * Crea un objeto de la clase Nota a partir de un dto para poder almacenarlo
     * @see Nota mirar estructura de la clase nota
     * @param createNote DTO: que contiene los atributos para crear un objeto de la clase Nota
     * @param usuario objeto de la clase Usuario
     * @return retorna una objeto de tipo nota con los parametros del dto CreateNotaDTO y el objeto Usuario
     */
    public Nota createObjetNota(CreateNoteDTO createNote, Usuario usuario){
        return Nota.builder()
                .titulo(createNote.getTitulo())
                .descripcion(createNote.getDescripcion())
                .estado(createNote.getEstado())
                .usuario(usuario)
                .build();
    }

    /**
     * Crea un DTO a partir de un objeto de tipo nota
     * @param nota objeto de la clase Nota
     * @return retorna un DTO de tipo infNoteDTO con los parametros del objeto nota
     */
    public InfoNoteDTO createObjectInfoNote(Nota nota){
        return InfoNoteDTO.builder()
                .id(nota.getId())
                .titulo(nota.getTitulo())
                .descripcion(nota.getDescripcion())
                .build();
    }

    /**
     * Metodo para guardar un objeto de tipo nota a partir del DTO CreateNote y el Usuario Logeado
     * @see Authentication mirar esquema de autenticacion que contiene el nickname del usuario
     * @param createNota DTO: que contiene los atributos para crear un objeto de la clase Nota
     * @return retorna un DTO la clase InfoNoteDTO si se almacena la nota correctamente
     * @throws RuntimeException retorna el mensaje: {Usuario no encontrado}
     */
    public InfoNoteDTO save(CreateNoteDTO createNota){
        // 1. Obtener autenticación del contexto
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. Obtener el nickname (username del UserDetails)
        String nickname = authentication.getName();

        Optional<Usuario> usuarioOpt = usuarioService.getUserByNickname(nickname);
        if(usuarioOpt.isEmpty()){
            throw new RuntimeException("Usuario no encontrado");
        }
        Nota nota = createObjetNota(createNota, usuarioOpt.get());
        return createObjectInfoNote(notaRepository.save(nota));
    }

    /**
     * Obtiene una nota a partir del Id y la trasforma en un DTO InfoNoteDTO
     * @param id Long que contiene el identificador del objeto usuario
     * @return InfoNoteDTO si la nota existe si no
     * @throws RuntimeException retorna el mensaje: {Nota no encontrada}
     */
    public InfoNoteDTO getNotaById(Long id){
        Optional<Nota> notaOpt = notaRepository.findById(id);
        if(notaOpt.isEmpty()){
            throw new RuntimeException("Nota no encontrada");
        }
        return createObjectInfoNote(notaOpt.get());
    }
}
