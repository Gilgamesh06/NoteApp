package com.gilgamesh06.NoteApp.repository;

import com.gilgamesh06.NoteApp.model.entity.Nota;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota,Long> {

    Optional<Nota> findById(Long id);

    Page<Nota> findAll(Pageable pageable);

    Page<Nota> findByEstado(boolean estado, Pageable pageable);

    void deleteById(Long id);
}
