package com.gilgamesh06.NoteApp.service.impl;

import com.gilgamesh06.NoteApp.auth.dto.login.RegisterDTO;
import com.gilgamesh06.NoteApp.auth.dto.login.InfoUserDTO;
import com.gilgamesh06.NoteApp.model.entity.Persona;
import com.gilgamesh06.NoteApp.repository.PersonaRepository;
import com.gilgamesh06.NoteApp.service.interfaces.GenericService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clase para crear los servicios de la entidad persona {crear, eliminar, actualizar, obtener}
 */
@Service
public class PersonaService implements GenericService<RegisterDTO, InfoUserDTO> {


    private final PersonaRepository personaRepository;


    /**
     * Constructor que me asigna la instancia necesaria
     * @see org.springframework.data.jpa.repository.JpaRepository
     * @param  personaRepository clase que extiende de JpaRepository permite crear metodos de este o usar psql
     */
    public PersonaService(PersonaRepository personaRepository){
        this.personaRepository = personaRepository;
    }


    /**
     * Metodo para crear un objeto de la clase Persona por medio del DTO: RegisterDTO
     * @see Persona mirar estructura de la clase persona
     * @param register DTO: que contiene los atributos para crear un objeto de tipo Persona
     * @return retorna un objeto de la clase Persona con la inforacion del DTO: RegisterDTO
     */
    public Persona createObjectPersona(RegisterDTO register){
        return Persona.builder()
                .nombre(register.getNombre())
                .apellido(register.getApellido())
                .correoElectronico(register.getCorreoElectronico())
                .fechaNacimiento(register.getFechaNacimiento())
                .build();
    }

    /**
     * Metodo para almacenar el objeto de tipo persona a partir del DTO: RegisterDTO
     * @see RegisterDTO mirar estructura del DTO register
     * @param register DTO: que contiene los atributos necesarios para crear un objeto de tipo Persona
     * @return retorna un objeto de tipo Persona
     */
    public Persona save(RegisterDTO register){
        Persona persona = createObjectPersona(register);
        return personaRepository.save(persona);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<InfoUserDTO> update(Long id, RegisterDTO dto) {
        return Optional.empty();
    }
}