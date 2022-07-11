package com.example.repository;

import com.example.domain.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    List<Note> getNotes();
    void save(Note note);
    void update(int oldNoteId, Note newNote);
    void delete(int id);
    Optional<Note> getById(int id);
}
