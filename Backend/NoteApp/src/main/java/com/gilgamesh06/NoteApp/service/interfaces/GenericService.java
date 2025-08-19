package com.gilgamesh06.NoteApp.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface GenericService<Entry,Exit> {

    Optional<Exit> save(Entry dto);

    void delete(Long id);

    Optional<Exit> update(Long id , Entry dto);
}
