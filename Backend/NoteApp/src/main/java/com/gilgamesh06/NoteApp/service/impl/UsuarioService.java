package com.gilgamesh06.NoteApp.service.impl;

import com.gilgamesh06.NoteApp.model.dto.login.CreateUserDTO;
import com.gilgamesh06.NoteApp.model.dto.login.InfoUserDTO;
import com.gilgamesh06.NoteApp.model.entity.Persona;
import com.gilgamesh06.NoteApp.model.entity.Usuario;
import com.gilgamesh06.NoteApp.repository.UsuarioRepository;
import com.gilgamesh06.NoteApp.service.interfaces.GenericService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UsuarioService implements GenericService<CreateUserDTO, InfoUserDTO> {


    /**
     * Atributo que genera una instancia unica de UsuarioRepository
     * @param usuarioRepository
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor que asigna la instacia de usuarioRepository
     * @param usuarioRepository
     */
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Metodo que crea una objeto de tipo Usuario a partir de los parametros de entreada utiliza el patron build para crearlo
     * @metod createObjetUsuario
     * @param createUserDTO
     * @param persona
     * @return Usuario
     */
    public Usuario createObjectUsuario(CreateUserDTO createUserDTO, Persona persona){
        return Usuario.builder()
                .persona(persona)
                .nickname(createUserDTO.getNickname())
                .password(createUserDTO.getPassword())
                .build();
    }


    /**
     * Metodo que convierte la informacion del objeto usuario en un dto
     * @param usuario
     * @return infoUserDTO
     */
    public InfoUserDTO createObjectInfoUserDTO(Usuario usuario){
        Persona persona = usuario.getPersona();
        return InfoUserDTO.builder()
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .nickname(usuario.getNickname())
                .correoElectronico(persona.getCorreoElectronico())
                .edad(calculetedAge(persona.getFechaNacimiento()))
                .build();
    }

    /**
     * Metodo para calcular la edad a partir de la fecha de nacimiento de la persona
     * @param fechaNacimiento
     * @return long (retorna la edad de la perssona)
     */
    public Long calculetedAge(LocalDate fechaNacimiento){
        LocalDate fechaActual = LocalDate.now();
        int edad = Period.between(fechaNacimiento, fechaActual).getYears();
        return (long) edad;
    }

    /**
     * Metodo que guarda un objeto de tipo Usuario y retorna un optional que contiene un dto de tipo InfoUserDTO
     * @param createUserDTO
     * @param persona
     * @return infoUserDTO
     */
    public Optional<InfoUserDTO> save(CreateUserDTO createUserDTO, Persona persona){
        Usuario usuario = createObjectUsuario(createUserDTO, persona);
        Optional<Usuario> usuarioOpt = usuarioRepository.save(usuario);
        if(usuarioOpt.isPresent()){
            InfoUserDTO infoUserDTO = createObjectInfoUserDTO(usuarioOpt.get());
            return Optional.of(infoUserDTO);
        }
        return Optional.empty();
    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<InfoUserDTO> update(Long id, CreateUserDTO dto) {
        return Optional.empty();
    }
}