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
    private short external;
    private String noteIssuerName;
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

    public short getExternal() {
        return external;
    }

    public void setExternal(short external) {
        this.external = external;
    }

    public String getNoteIssuerName() {
        return noteIssuerName;
    }

    public void setNoteIssuerName(String noteIssuerName) {
        this.noteIssuerName = noteIssuerName;
    }

    public List<AttachmentEntity> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public void setAttachments(List<AttachmentEntity> attachments) {
        this.attachments = attachments;
    }

    
}
