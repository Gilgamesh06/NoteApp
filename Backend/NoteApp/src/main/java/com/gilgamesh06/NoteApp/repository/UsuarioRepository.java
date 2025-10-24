package com.gilgamesh06.NoteApp.repository;

import com.gilgamesh06.NoteApp.model.entity.Persona;
import com.gilgamesh06.NoteApp.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Clase de para crear los metodos que extienden de JpaRepository o psql de la clase Usuario
 * @see JpaRepository mirar funcionamiento
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Override
    Optional<Usuario> findById(Long aLong);

    Optional<Usuario> findByNickname(String nickname);

    Optional<Usuario> findByPersona(Persona persona);

}
