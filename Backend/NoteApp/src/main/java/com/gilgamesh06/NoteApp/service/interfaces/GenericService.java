package com.gilgamesh06.NoteApp.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Interfaz que define los metodos {delete, update}
 * @param <Entry> define que objeto debe pasarse como parametro al metodo
 * @param <Exit> define que objeto debe retornar el metodo
 */
@Service
public interface GenericService<Entry,Exit> {

    void delete(Long id);

    Optional<Exit> update(Long id , Entry dto);
}
