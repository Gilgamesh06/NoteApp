package com.gilgamesh06.NoteApp.repository;

import com.gilgamesh06.NoteApp.model.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota,Long> {

    Optional<Nota> findById(Long id);
    

    void deleteById(Long id);
}
