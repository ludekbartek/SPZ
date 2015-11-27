/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;

import java.util.Date;
import java.util.List;

/**
 *
 * @author bar
 */
class AttachmentEntity {
    private Date date;
    private String content;
    private String location;
    private String type;
    private List<SpzNoteEntity> notes;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SpzNoteEntity> getNotes() {
        return notes;
    }

    public void setNotes(List<SpzNoteEntity> notes) {
        this.notes = notes;
    }
    
    
}
