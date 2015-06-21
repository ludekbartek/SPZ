/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.entities.spz;

import cz.dcb.support.db.entities.usermanagement.User;
import java.util.Date;

/**
 *
 * @author bar
 */
public class HistoryItem {
    private long id;
    private Date changeDate;
    private User operator;
    private String note;
    private State oldState,newState;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if(note.length()>255){
            throw new IllegalArgumentException("Note is too long.");
        }
        this.note = note;
    }

    public State getOldState() {
        return oldState;
    }

    public void setOldState(State oldState) {
        this.oldState = oldState;
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }
}
