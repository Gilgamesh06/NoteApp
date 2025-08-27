package com.gilgamesh06.NoteApp.service.impl;

import com.gilgamesh06.NoteApp.model.dto.login.CreateUserDTO;
import com.gilgamesh06.NoteApp.model.dto.login.InfoUserDTO;
import com.gilgamesh06.NoteApp.model.entity.Persona;
import com.gilgamesh06.NoteApp.repository.PersonaRepository;
import com.gilgamesh06.NoteApp.service.interfaces.GenericService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clase PersonaService que implementa los metodos
 * Para Crear, Actualizar, Eliminar y Obtener una o varias Personas
 */

@Service
public class PersonaService implements GenericService<CreateUserDTO, InfoUserDTO> {

    /**
     * Atributo para crear una instacia de la clase PersonaRepository
     * @param personaRepository
     */
    private final PersonaRepository personaRepository;

    /**
     * Atributo para crear una instacia de la clase UsuarioService
     * @param usuarioService
     */
    private final UsuarioService usuarioService;

    /**
     * Constructor que me asigna las instacia defininas en mis atributos
     * @param  personaRepository
     * @param usuarioService
     */
    public PersonaService(PersonaRepository personaRepository, UsuarioService usuarioService){
        this.personaRepository = personaRepository;
        this.usuarioService = usuarioService;
    }


    /**
     * @method CreateObjetPersona
     * @param {CreateUsrDTO} (Dto que contiene los datos para crear el objeto persona y el objeto usuario)
     * @return {Persona} (Retorna un objeto de tipo persona
     */
    public Persona createObjectPersona(CreateUserDTO createUserDTO){
        return Persona.builder()
                .nombre(createUserDTO.getNombre())
                .apellido(createUserDTO.getApellido())
                .correoElectronico(createUserDTO.getCorreoElectronico())
                .fechaNacimiento(createUserDTO.getFechaNacimiento())
                .build();
    }

    /**
     * Metodo para guardar un objeto de tipo persona que a su vez llama al metdo save que guarda a un usuario
     * todo a traves de un dto de entrada de tipo CreateUser DTO
     * @param createUserDTO
     * @return infoUserDTO
     */
    public Optional<InfoUserDTO> save(CreateUserDTO createUserDTO){
        Persona persona = createObjectPersona(createUserDTO);
        Optional<Persona> personaOpt = personaRepository.save(persona);
        if(personaOpt.isPresent()){
            return usuarioService.save(createUserDTO,personaOpt.get());
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