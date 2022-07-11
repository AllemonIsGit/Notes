package com.example.UI;

import com.example.domain.exception.IllegalNoteException;
import com.example.domain.model.Note;
import com.example.services.NoteService;
import com.example.services.NoteServiceImpl;

import java.util.*;

public class UserInterface {
    private final NoteService noteService;
    private final Scanner scanner;
    private Map<Integer, Integer> numberToIdMap;


    public UserInterface() {
        this.noteService = new NoteServiceImpl();
        this.scanner = new Scanner(System.in);
        this.numberToIdMap = new HashMap<>();
    }

    public void printAll() {
        int i = 1;
        List<Note> list = noteService.getAll();

        for (Note note : list) {
            System.out.printf("%d. %s%n", i, note.getTitle());
            numberToIdMap.put(i++, note.getId());
        }
    }

    public void printNote(int id) {
        Note note = noteService.getById(id);
        System.out.printf("%s %n%s", note.getTitle(), note.getContent());
    }

    public void createNote() {
        System.out.println("Give your note a title:\n");
        String title = scanner.nextLine();
        System.out.println("Type your shit in:\n");
        String content = scanner.nextLine();
        try {
            noteService.create(title, content);
        } catch (IllegalNoteException e) {
            e.printStackTrace();
        }
    }

    public void update(int id) {
        Note oldNote = noteService.getById(id);

        System.out.println("Give me new title (press enter to skip):\n");
        String title = scanner.nextLine();
        System.out.println("Give me new content (press enter to skip):\n");
        String content = scanner.nextLine();
        String titleToSave = title.equals("") ? oldNote.getTitle() : title;
        String contentToSave = content.equals("") ? oldNote.getContent() : content;
        try {
            noteService.update(id, new Note(titleToSave, contentToSave));
            System.out.println("Note updated.");
        } catch (IllegalNoteException e) {
            System.out.println(e.getMessage());
        }

    }


    public void delete(int id) {
        noteService.destroy(id);
    }

    //TODO will crash if user gives input "print all" // shit format can't be arsed
    public void menu() {
        while (true) {
            String userInput = scanner.nextLine();
            userInput = userInput.toLowerCase();
            String[] split = userInput.split(" ");
            String command = split[0];
            String args;
            if (split.length > 1) {
                args = split[1];
            } else args = "";

            switch (command) {
                case "printall" -> {
                    printAll();
                    System.out.println();
                }

                case "print" -> {
                    int mapKey = Integer.parseInt(args);
                    if (!isValidMapObject(mapKey)) {
                        System.out.println("Invalid id.");
                        break;
                    }
                    printNote(numberToIdMap.get(mapKey));
                    System.out.println();
                }

                case "create" -> {
                    createNote();
                    System.out.println();
                    printAll();
                }

                case "delete" -> {
                    int mapKey = Integer.parseInt(args);
                    if (!isValidMapObject(mapKey)) {
                        System.out.println("Invalid id");
                        break;
                    }
                    System.out.println("Are you sure you want to delete this note? (type yes to continue)");
                    if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                        printAll();
                        break;
                    }
                    delete(numberToIdMap.get(mapKey));
                    System.out.println();
                    printAll();
                    System.out.println();
                }

                case "edit" -> {
                    int mapKey = Integer.parseInt(args);
                    if (!isValidMapObject(mapKey)) {
                        System.out.println("Invalid id");
                        break;
                    }
                    update(numberToIdMap.get(mapKey));
                }

                case "help" -> {
                    System.out.println("Printall - prints all notes");
                    System.out.println("Print x - prints a note");
                    System.out.println("Create - creates a new note");
                    System.out.println("Delete x - deletes a note");
                    System.out.println("Edit x - edits a note");
                }
            }
        }
    }

    private boolean isValidMapObject(int mapKey) {
        return numberToIdMap.containsKey(mapKey);

    }
}
