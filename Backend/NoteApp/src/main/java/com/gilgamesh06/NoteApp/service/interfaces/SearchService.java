package com.gilgamesh06.NoteApp.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SearchService<Exit> {

    Page<Exit> findAll(Long id, Long size);

    Page<Exit> findAllByTitle(String title);

    Page<Exit> findAllByState(Boolean state);

    Optional<Exit> findById(Long id);


}
