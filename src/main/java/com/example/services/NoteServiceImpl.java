package com.example.services;

import com.example.domain.exception.IllegalNoteException;
import com.example.domain.exception.NoteNotFoundException;
import com.example.domain.model.Note;
import com.example.repository.NoteRepository;
import com.example.repository.NoteRepositoryImpl;

import java.util.List;

public class NoteServiceImpl implements NoteService {
    private static final String ILLEGAL_NOTE_EXCEPTION =
            "Title has to be max 45 characters, and content has to be max 1000000 characters.";

    private final NoteRepository noteRepository;

    public NoteServiceImpl() {
        this.noteRepository = new NoteRepositoryImpl();
    }

    @Override
    public void create(String title, String content) throws IllegalNoteException {
        Note note = new Note(title, content);
        if (isValidNote(note)) {
            noteRepository.save(note);
        } else throw new IllegalNoteException(ILLEGAL_NOTE_EXCEPTION);

    }

    @Override
    public void update(int oldId, Note newNote) throws IllegalNoteException {
        if (isValidNote(newNote)) {
            noteRepository.update(oldId, newNote);
        } else throw new IllegalNoteException(ILLEGAL_NOTE_EXCEPTION);

    }

    @Override
    public void destroy(int id) {
        if (noteRepository.getById(id).isEmpty()) {
            System.err.println("There's no note with id " + id);
        } else
        noteRepository.delete(id);
    }

    @Override
    public List<Note> getAll() {
        return noteRepository.getNotes();
    }


    @Override
    public Note getById(int id) {
        return noteRepository.getById(id)
                .orElseThrow( () ->
                        new NoteNotFoundException(String.format("Note with id %d was not found.", id)));
    }

    private boolean isValidNote(Note note) {
        return note.getTitle().length() <= 43 && note.getContent().length() <= 1e6;
    }
}