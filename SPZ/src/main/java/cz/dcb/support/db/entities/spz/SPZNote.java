/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.entities.spz;

import java.util.Date;

/**
 *
 * @author bar
 */
class SPZNote {
    private long id;
    private short externalNote;
    private Date noteDate;
    private String noteText;
    private String issuerLogin;
    private long stateId;
    private long ts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getExternalNote() {
        return externalNote;
    }

    public void setExternalNote(short externalNote) {
        this.externalNote = externalNote;
    }

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

    public String getIssuerLogin() {
        return issuerLogin;
    }

    public void setIssuerLogin(String issuerLogin) {
        if(issuerLogin.length()>32){
            throw new IllegalArgumentException("Issuer login too long.");
        }
        this.issuerLogin = issuerLogin;
    }

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SPZNote other = (SPZNote) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
