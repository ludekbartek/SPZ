/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;

import cz.dcb.support.db.exceptions.SPZException;
import cz.dcb.support.db.jpa.entities.Spznote;
import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.xml.HTMLTransformer;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entitni trid reprezentujici stav SPZ pro ucely zobrazovani a editace v JSP
 * @author Ludek Bartek
 */
public class SpzStateWebEntity {
    private long id;
    private String code;
    private User issuer;
    private String revisedRequestDescription;
    private String solutionDescription;
    private String releaseNotes;
    private Short classType;
    private Date issueDate;
    private Long currentState;
    private List<SpzNoteEntity> notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public String getRevisedRequestDescription() {
        String correctedValue;
        HTMLTransformer transformer = new HTMLTransformer();
        try {
            transformer.convert(revisedRequestDescription);
            correctedValue = transformer.getResult();
        } catch (SPZException ex) {
            Logger.getLogger(SpzStateWebEntity.class.getName()).log(Level.SEVERE, null, ex);
            correctedValue = revisedRequestDescription;
        }
        return correctedValue;
    }

    public void setRevisedRequestDescription(String revisedRequestDescription) {
        this.revisedRequestDescription = revisedRequestDescription;
    }

    public String getSolutionDescription() {
        return solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }

    public String getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    public Short getClassType() {
        return classType;
    }

    public void setClassType(Short classType) {
        this.classType = classType;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Long getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Long currentState) {
        this.currentState = currentState;
    }
    
    public void setNotes(List<SpzNoteEntity> notes){
        this.notes = notes;
    }
    
    public List<SpzNoteEntity> getNotes(){
        return Collections.unmodifiableList(notes);
    }
    
}
