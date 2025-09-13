package com.gilgamesh06.NoteApp.service.impl;

import com.gilgamesh06.NoteApp.exception.NotaNotFoundException;
import com.gilgamesh06.NoteApp.model.dto.note.CreateNoteDTO;
import com.gilgamesh06.NoteApp.model.dto.note.InfoNoteDTO;
import com.gilgamesh06.NoteApp.model.dto.note.UpdateNoteDTO;
import com.gilgamesh06.NoteApp.model.entity.Nota;
import com.gilgamesh06.NoteApp.model.entity.Usuario;
import com.gilgamesh06.NoteApp.repository.NotaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clase para crear los servicios de la entidad nota: (Crear, eliminar, actualizar, obtener}
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

    // Metodo para validar Id
    protected void validId(Long id){
        if(id == null){
           throw new IllegalArgumentException("El Id No puede ser nulo");
        } else if (id <= 0) {
            throw new IllegalArgumentException("El Id no puede ser menor o igual a cero");
        }
    }

    // Metodos para obtener el usuario

    /**
     * Metodo que obtiene el nickname del usuario que esta logueado
     * @return String nickname
     */
    private String getNicknameUserLogin(){
        // 1. Obtener autenticación del contexto
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. Obtener el nickname (username del UserDetails)
        return authentication.getName();
    }

    /**
     * Metodo que retorna el objeto Usuario que esta logueado
     * @return Usuario
     */
    protected Usuario getUserAuthenticated(){

        String nickname = getNicknameUserLogin();

        Optional<Usuario> usuarioOpt = usuarioService.getUserByNickname(nickname);
        if(usuarioOpt.isEmpty()){
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuarioOpt.get();
    }

    // Metodos para crear Objetos

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

    // Metodos para obtener una Nota

    /**
     * Metodo que busca una nota a traves del Id
     * @param id identificador de la nota
     * @return Nota
     */
    protected Nota getNota(Long id){
        // validar id
        validId(id);

        // obtener usuario
        Usuario usuario = getUserAuthenticated();

        Optional<Nota> notaOpt = notaRepository.findByUsuarioAndId(usuario,id);

        if(notaOpt.isEmpty()){
            throw new NotaNotFoundException("Nota no encontrada");
        }
        return notaOpt.get();
    }

    /**
     * Metodo que retorna un Optianal Nota buscando por el titulo
     * @param title parametro que contiene el titulo de la nota
     * @return Un Optional Nota
     */
    protected Optional<Nota> getNoteByTitle( String title){
        Usuario usuario = getUserAuthenticated();
        return notaRepository.findByUsuarioAndTitulo(usuario,title);

    }

    // Metodo para guardar una nota

    /**
     * Metodo para guardar un objeto de tipo nota a partir del DTO CreateNote y el Usuario Logeado
     * @see Authentication mirar esquema de autenticacion que contiene el nickname del usuario
     * @param createNota DTO: que contiene los atributos para crear un objeto de la clase Nota
     * @return retorna un DTO la clase InfoNoteDTO si se almacena la nota correctamente
     * @throws RuntimeException retorna el mensaje: {Usuario no encontrado}
     */
    public InfoNoteDTO save(CreateNoteDTO createNota){

        // Verifica si no existe una nota con el mismo titulo
        Optional<Nota> notaOpt = getNoteByTitle(createNota.getTitulo());

        if(notaOpt.isEmpty()){

            // obtengo el usuario que esta logeado
            Usuario usuario = getUserAuthenticated();

            // creo la nota y la vinculo al usuario
            Nota nota = createObjetNota(createNota,usuario);

            // retorno el DTO InfoNoteDTO
            return createObjectInfoNote(notaRepository.save(nota));
        }else{
            throw new IllegalArgumentException("Ya existe una nota con ese titulo.");
        }


    }

    // Metodo para actualizar una nota

    /**
     * Metodo para actualizar una nota
     * @param update DTO que contiene los campos titulo y descripcion
     * @return un DTO InfoNoteDTO con la nueva nota
     */
    public InfoNoteDTO update(UpdateNoteDTO update){

        String title = update.getTitulo();
        String description = update.getDescripcion();

        // Obtenemos la nota
        Nota nota = getNota(update.getId());

        // La descripcion no es nula
        if(!description.isEmpty()){
            // guarda la nueva descripcion
            nota.setDescripcion(description);
        }
        // Si el título no es nulo y no es vacio
        if(!title.isBlank()){

            // Verifica que el titulo no tenga mas de 100 caracteres
            if(title.length() > 100){
                throw new IllegalArgumentException("El titulo no puede tener mas de 100 caracteres");
            }

            // Busca si ya existe una nota con ese titulo
            Optional<Nota> notaOpt = getNoteByTitle(title);

            // True si no existe la nota
            if(notaOpt.isEmpty()){
                // Actualiza el título
                nota.setTitulo(title);
            }else{
                throw new IllegalArgumentException("Ya existe una nota con ese titulo");
            }
        }
        Nota notaSave = notaRepository.save(nota);
        return createObjectInfoNote(notaSave);
    }

    // Metodo para actualizar estado

    /**
     * Metodo para activar o archivar una nota
     * @param id identifacador de la nota
     * @return String
     */
    public String updateStatus(Long id){
        Optional<Nota> notaOpt = notaRepository.findById(id);
        if(notaOpt.isEmpty()){
            throw new NotaNotFoundException("Nota no encontrada");
        }
        boolean estado = !notaOpt.get().getEstado();
        notaOpt.get().setEstado(estado);
        return estado ? "La nota esta Activa" : "La nota esta Archivada";

    }

    // Metodo de eliminacion

    /**
     * Metodo para eliminar una nota
     * @param id identificador de la nota
     */
    public void delete(Long id){

        // validar id
        validId(id);

        // Se pasa el usuario para evitar que se elimine notas que no son del Usuario
        Usuario usuario = getUserAuthenticated();

        notaRepository.deleteByUsuarioAndId(usuario,id);
    }

    // Metodos de busqueda

    /**
     * Retorna El objeto PageRequest con los parametros ingresados
     * @param page numero de pagina
     * @param size tamaño de la pagina
     * @param orderBy orden true: ascending, flase: desending
     * @return Pageable
     */
    protected Pageable getPageable(int page, int size, Boolean orderBy){
        if(orderBy){
            return PageRequest.of(page,size,Sort.by("titulo").ascending());
        }
        else{
            return PageRequest.of(page,size,Sort.by("titulo").descending());
        }
    }

    /**
     * Obtiene una nota a partir del Id y la trasforma en un DTO InfoNoteDTO
     * @param id Long que contiene el identificador del objeto usuario
     * @return InfoNoteDTO si la nota existe si no
     * @throws RuntimeException retorna el mensaje: {Nota no encontrada}
     */
    public InfoNoteDTO getNotaById(Long id){

        Nota nota = getNota(id);
        return createObjectInfoNote(nota);
    }


    /**
     * Obtiene lista paginada de notas
     * @param page numero de la  pagina
     * @param size tamaño de la pagina
     * @param orderBy orden true: ascending, flase: desending
     * @return lista pagian de InfoNoteDTO
     */
    public Page<InfoNoteDTO> getAllNota(Integer page, Integer size, Boolean orderBy){

        Usuario usuario = getUserAuthenticated();

        Pageable pageable = getPageable(page,size,orderBy);

        Page<Nota> pageNota = notaRepository.findByUsuario(usuario,pageable);

        return pageNota.map(nota -> InfoNoteDTO.builder()
                .id(nota.getId())
                .titulo(nota.getTitulo())
                .descripcion(nota.getDescripcion())
                .build());
    }

    /**
     * Metodo que retorna lista paginada de notas por estado
     * @param page numero de la pagina
     * @param size tamaño de la pagina
     * @param orderBy orden true: ascending, flase: desending
     * @param estado parametro que deternima si una nota esta activa o no boolean
     * @return Page: lista paginada de InfoNoteDTO filtradas por estado
     */
    public Page<InfoNoteDTO> getAllNoteByEstado(Integer page, Integer size, Boolean orderBy, boolean estado){

        Usuario usuario = getUserAuthenticated();

        Pageable pageable = getPageable(page,size,orderBy);

        Page<Nota> pageNota = notaRepository.findByUsuarioAndEstado(usuario,estado, pageable);

        return pageNota.map(nota -> InfoNoteDTO.builder()
                .id(nota.getId())
                .titulo(nota.getTitulo())
                .descripcion(nota.getDescripcion())
                .build());
    }

}
