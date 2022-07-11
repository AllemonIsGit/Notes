package com.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Note {
    private int id;
    private String title;
    private String content;


    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }
}