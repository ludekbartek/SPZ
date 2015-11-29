/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author bar
 */
public class SpzNoteEntity {
    private Date noteDate;
    private String noteText;
    private short internal;
    private String noteIssuer;
    private List<AttachmentEntity> attachments;

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public short getInternal() {
        return internal;
    }

    public void setInternal(short internal) {
        this.internal = internal;
    }

    public String getNoteIssuer() {
       return noteIssuer;
    }

    public void setNoteIssuer(String issuer) {
        this.noteIssuer = issuer;
    }

    public List<AttachmentEntity> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public void setAttachments(List<AttachmentEntity> attachments) {
        this.attachments = attachments;
    }

    
}
