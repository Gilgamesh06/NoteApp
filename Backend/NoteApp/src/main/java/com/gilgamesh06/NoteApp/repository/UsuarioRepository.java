package com.gilgamesh06.NoteApp.repository;

import com.gilgamesh06.NoteApp.model.entity.Persona;
import com.gilgamesh06.NoteApp.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Override
    Optional<Usuario> findById(Long aLong);

    Optional<Usuario> save(Usuario usuario);

    Optional<Usuario> findByPersona(Persona persona);

}
