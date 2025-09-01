package com.gilgamesh06.NoteApp.service.impl;

import com.gilgamesh06.NoteApp.auth.dto.login.LoginDTO;
import com.gilgamesh06.NoteApp.auth.dto.login.RegisterDTO;
import com.gilgamesh06.NoteApp.auth.dto.login.InfoUserDTO;
import com.gilgamesh06.NoteApp.model.entity.Persona;
import com.gilgamesh06.NoteApp.model.entity.Usuario;
import com.gilgamesh06.NoteApp.repository.UsuarioRepository;
import com.gilgamesh06.NoteApp.service.interfaces.GenericService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;


/**
 * Clase para crear los servicios de la entidad Usuario {crear, eliminar, actualizar, obtener}
 */
@Service
public class UsuarioService implements GenericService<RegisterDTO, InfoUserDTO> {



    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor que asigna la instacia necesarioas
     * @param usuarioRepository instancias del repositorio de usuario para utilizar psql o metodos de JpaRepository
     * @see  org.springframework.data.jpa.repository.JpaRepository
     * @param passwordEncoder instancias que contiene el metodo (@bean) para encriptar y desencritar la contraseña
     */
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Metodo que crea una objeto de tipo Usuario a partir de los parametros de entreada utiliza el patron build para crearlo
     * @see Usuario mirar estructura de la clase Usuario
     * @param register DTO: contiene los atributos necesarios para crear un objeto de tipo Persona y Usuario
     * @param persona  Objeto de la clase Persona
     * @return retorna un objeto de tipo usuario con los atributos del DTO: RegisterDTO y el objeto Persona
     */
    public Usuario createObjectUsuario(RegisterDTO register, Persona persona){
        return Usuario.builder()
                .persona(persona)
                .nickname(register.getNickname())
                .password(passwordEncoder.encode(register.getPassword()))
                .build();
    }


    /**
     * Metodo que convierte los atributos del objeto usuario en un DTO
     * @see InfoUserDTO mirar estructura del DTO: InfoUserDTO
     * @param usuario objeto de la clase Usuario
     * @return un objeto de tipo InfoUserDTO con los atributos del objeto usuario
     */
    public InfoUserDTO createObjectInfoUserDTO(Usuario usuario){
        Persona persona = usuario.getPersona();
        return InfoUserDTO.builder()
                .nombre(persona.getNombre())
                .nickname(usuario.getNickname())
                .correoElectronico(persona.getCorreoElectronico())
                .edad(calculetedAge(persona.getFechaNacimiento()))
                .build();
    }

    /**
     * Metodo para calcular la edad a partir de la fecha de nacimiento de la persona
     * @see LocalDate mirar metodo para manejar fechas de tipo yyyy/mm/dd
     * @param fechaNacimiento fechas de nacimiento del usuario
     * @return retorna la edad de la persona en un Long
     */
    public Long calculetedAge(LocalDate fechaNacimiento){
        LocalDate fechaActual = LocalDate.now();
        // Calcula el periodo de tiempo en años entre la fecha actual (sistema) y la del usuario fechaNacimiento
        int edad = Period.between(fechaNacimiento, fechaActual).getYears();
        return (long) edad;
    }

    /**
     * Metodo que guarda un objeto de tipo Usuario y retorna el Usuario almacenado
     * @see Usuario mirar la estructura de la clase Usuario
     * @param register DTO: que contiene los datos para crear el objeto usuario
     * @param persona  objeto persona que vincula el usuario con la persona
     * @return retorna un objeto de tipo usuario que fue almacenado
     */
    public Usuario save(RegisterDTO register, Persona persona){
        Usuario usuario = createObjectUsuario(register, persona);
        return usuarioRepository.save(usuario);
    }

    /**
     * Metodo que retorna un Optional<Usuario> por medio del atributo nickname
     * @see Optional mirar como funciona la clase optional
     * @param nickname nombre de usuario con el que se busca el objeto de tipo usuario
     * @return un objeto de tipo Optional<Usuario> puede contener al usuario o estar vacio {@code Optional.Empty()}
     */
    public Optional<Usuario> getUserByNickname(String nickname){
        return usuarioRepository.findByNickname(nickname);
    }

    public Optional<Usuario> getUser(LoginDTO login){
        return getUserByNickname(login.getNickname());
    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<InfoUserDTO> update(Long id, RegisterDTO dto) {
        return Optional.empty();
    }
}