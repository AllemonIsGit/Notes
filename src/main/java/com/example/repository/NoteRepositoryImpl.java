package com.example.repository;

import com.example.domain.model.Note;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class NoteRepositoryImpl implements NoteRepository {

    private final static String DEFAULT_DB = "notes-db";
    private static String DB_ADDRESS;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    private Connection connection;

    public NoteRepositoryImpl() {
        this(DEFAULT_DB);
    }
    public NoteRepositoryImpl(String dbName) {
        try {
            File addressInfo = new File("db-address.txt");
            File loginInfo = new File("db-login-info.txt");
            Scanner scanner = new Scanner(addressInfo);
            DB_ADDRESS = scanner.nextLine();
            Scanner scanner2 = new Scanner(loginInfo);
            DB_USERNAME = scanner2.nextLine();
            DB_PASSWORD = scanner2.nextLine();


            this.connection = DriverManager.getConnection(DB_ADDRESS + dbName, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot connect to database.");
        } catch (FileNotFoundException e) {
            System.out.println("There was a problem getting database information.");
        }
    }
    @Override
    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from notes");
            while (resultSet.next()) {
                Note note = new Note(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"));
                noteList.add(note);
            }
        } catch (SQLException e) {
            System.out.println("There was an error getting notes.");
        }
        return noteList;
    }

    @Override
    public void save(Note note) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("insert into notes (id, title, content) values (?, ?, ?)");
            statement.setString(1, null);
            statement.setString(2, note.getTitle());
            statement.setString(3, note.getContent());
            statement.execute();
            System.out.println("Note added.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int oldNoteId, Note newNote) {
        try {
            PreparedStatement statement = connection.prepareStatement("update notes set title = ?, content = ? where id = ?");
            statement.setString(1, newNote.getTitle());
            statement.setString(2, newNote.getContent());
            statement.setInt(3, oldNoteId);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from notes where id = ?");
            statement.setInt(1, id);
            statement.execute();
            System.out.println("Note deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Note> getById(int id) {
        Note note = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from notes where id = " + id);
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            resultSet.next();
            note = new Note(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(note);
    }
}
