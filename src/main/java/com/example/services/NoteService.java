package com.example.services;

import com.example.domain.exception.IllegalNoteException;
import com.example.domain.model.Note;

import java.util.List;

public interface NoteService {
    void create(String title, String content) throws IllegalNoteException;
    void update(int oldId, Note newNote) throws IllegalNoteException;
    void destroy(int id);
    List<Note> getAll();
    Note getById(int id);
}
