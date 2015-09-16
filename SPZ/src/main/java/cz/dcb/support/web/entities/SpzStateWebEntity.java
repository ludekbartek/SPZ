/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;

import cz.dcb.support.db.jpa.entities.User;
import java.util.Date;

/**
 *
 * @author bar
 */
public class SpzStateWebEntity {
    private long id;
    private String code;
    private User issuer;
    private String revisedRequestDescription;
    private String solutionDescription;
    private Double mandays;
    private Double assumedManDays;
    private String releaseNotes;
    private Short classType;
    private Date issueDate;
    private Long currentState;

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
        return revisedRequestDescription;
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

    public Double getMandays() {
        return mandays;
    }

    public void setMandays(Double mandays) {
        this.mandays = mandays;
    }

    public Double getAssumedManDays() {
        return assumedManDays;
    }

    public void setAssumedManDays(Double assumedManDays) {
        this.assumedManDays = assumedManDays;
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
    
    
}
