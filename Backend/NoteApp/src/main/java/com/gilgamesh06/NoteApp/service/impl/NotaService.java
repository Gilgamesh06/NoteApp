package com.gilgamesh06.NoteApp.service.impl;

import com.gilgamesh06.NoteApp.model.dto.note.CreateNoteDTO;
import com.gilgamesh06.NoteApp.model.dto.note.InfoNoteDTO;
import com.gilgamesh06.NoteApp.model.entity.Nota;
import com.gilgamesh06.NoteApp.repository.NotaRepository;
import com.gilgamesh06.NoteApp.service.interfaces.GenericService;
import com.gilgamesh06.NoteApp.service.interfaces.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotaService implements GenericService<CreateNoteDTO,InfoNoteDTO> , SearchService<InfoNoteDTO> {

    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository){
        this.notaRepository = notaRepository;
    }

    /*
     @

     */
    public InfoNoteDTO converInfoNoteDTO(Nota nota){
        return InfoNoteDTO.builder()
                .id(nota.getId())
                .titulo(nota.getTitulo())
                .descripcion(nota.getDescripcion())
                .build();
    }

    public Nota convertNote(CreateNoteDTO createNoteDTO){
        return Nota.builder()
                .titulo(createNoteDTO.getTitulo())
                .descripcion(createNoteDTO.getDescripcion())
                .estado(createNoteDTO.getEstado())
                .build();
    }

    public Nota updateNote(CreateNoteDTO createNoteDTO, Nota nota){
        nota.setTitulo(createNoteDTO.getTitulo());
        nota.setDescripcion(createNoteDTO.getDescripcion());
        nota.setEstado(createNoteDTO.getEstado());
        return nota;
    }

    public Optional<InfoNoteDTO> noteIsPresent(Optional<Nota> notaOpt){
        if(notaOpt.isPresent()){
            InfoNoteDTO NotaDTO = converInfoNoteDTO(notaOpt.get());
            return Optional.of(NotaDTO);
        }
        return Optional.empty();
    }

    public Optional<Nota> searchNoteById(Long id){
        return notaRepository.findById(id);
    }

    @Override
    public Optional<InfoNoteDTO> save(CreateNoteDTO dto) {
        Optional<Nota> notaOpt = notaRepository.save(convertNote(dto));
        return noteIsPresent(notaOpt);
    }

    @Override
    public void delete(Long id) {
        notaRepository.deleteById(id);
    }



    @Override
    public Optional<InfoNoteDTO> update(Long id, CreateNoteDTO dto) {
        Optional<Nota> notaOpt = searchNoteById(id);
        if(notaOpt.isPresent()){
            Nota nota = updateNote(dto,notaOpt.get());
            notaRepository.save(nota);
            return Optional.of(converInfoNoteDTO(nota));
        }
        return Optional.empty();
    }

    @Override
    public Page<InfoNoteDTO> findAll(Long id, Long size) {
        return null;
    }

    @Override
    public Page<InfoNoteDTO> findAllByTitle(String title) {
        return null;
    }

    @Override
    public Page<InfoNoteDTO> findAllByState(Boolean state) {
        return null;
    }

    @Override
    public Optional<InfoNoteDTO> findById(Long id) {
        Optional<Nota> notaOpt = searchNoteById(id);
        return noteIsPresent(notaOpt);
    }
}
