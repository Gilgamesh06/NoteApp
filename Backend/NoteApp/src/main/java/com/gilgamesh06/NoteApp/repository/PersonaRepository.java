package com.gilgamesh06.NoteApp.repository;

import com.gilgamesh06.NoteApp.model.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {
}

