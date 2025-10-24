package com.gilgamesh06.NoteApp.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Interfaz que define los metodos: {findAll, findById}
 * @param <Exit> define que objeto debe retornar el metodo
 */
@Service
public interface SearchService<Exit> {

    Page<Exit> findAll(Long id, Long size);

    Optional<Exit> findById(Long id);


}
