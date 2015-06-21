/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.entities.spz;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author bar
 */
class Attachment {
    private long id;
    private Date date;
    private String content;
    private String location;
    private String type;
    private Map<String, SPZNote> notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        if(content.length()>255){
            throw new IllegalArgumentException("Content is too long.");
        }
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if(location.length()>255){
            throw new IllegalArgumentException("Location is too long.");
        }
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(type.length()>255){
            throw new IllegalArgumentException("Type is too long.");
        }
        this.type = type;
    }

    public Collection<SPZNote> getNotes() {
        return Collections.unmodifiableCollection(notes.values());
    }

    public void addNote(String key,SPZNote note){
        if(key==null){
            throw new NullPointerException("Note key is null");
        }
        if(note==null){
            throw new NullPointerException("Note is null");
        }
        if(notes.keySet().contains(key)){
            throw new InvalidParameterException("Note key is duplicit.");
        }
        notes.put(key, note);
    }
    
    
    
}
