package com.example.services;

import com.example.domain.exception.IllegalNoteException;
import com.example.domain.exception.NoteNotFoundException;
import com.example.domain.model.Note;
import com.example.repository.NoteRepository;
import com.example.repository.NoteRepositoryImpl;
import com.example.validation.ComplexValidators;
import com.example.validation.abstractions.Validator;

import java.util.List;

public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final Validator<Note> noteValidator;

    public NoteServiceImpl() {
        this.noteRepository = new NoteRepositoryImpl();
        this.noteValidator = ComplexValidators.noteValidator();
    }

    @Override
    public void create(String title, String content) throws IllegalNoteException {
        Note note = new Note(title, content);
        noteValidator.validateOrThrow(note, noteRepository::save, IllegalNoteException::new);
    }

    @Override
    public void update(int oldId, Note newNote) throws IllegalNoteException {
        noteValidator.validateOrThrow(
                newNote,
                n -> noteRepository.update(oldId, n),
                IllegalNoteException::new);
    }

    @Override
    public void destroy(int id) {
        if (noteRepository.getById(id).isEmpty()) {
            System.out.println("There's no note with id " + id);
        } else
            noteRepository.delete(id);
    }

    @Override
    public List<Note> getAll() {
        return noteRepository.getNotes();
    }


    @Override
    public Note getById(int id) {
        return noteRepository.getById(id).orElseThrow(() ->
                new NoteNotFoundException(String.format("Note with id %d was not found.", id)));
    }
}