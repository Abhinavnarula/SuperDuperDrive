package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    
    private String noteId;
    private String title;
    private String description;


    public String getNoteId() {
        return this.noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
