/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;

import cz.dcb.support.db.exceptions.SPZException;
import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.jpa.entities.Project;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.xml.HTMLTransformer;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entitni trida reprezentujici jednu SPZ pro potreby editace a vypisu v JSP
 * @author Ludek Bartek
 */
public class SPZWebEntity {
     private Integer id;
     private String reqNumber;
     private String kind;
     private String issuer;
     private String contactPerson;
     private String shortName;
     private String analyst;
     private String developer;
     private Date issueDate;
     private String requestDescription;
     private Date specDate;
     private double workLoadEstimation;
     private Date installDate;
     private double workLoadReal;
     private String spzState;
     private Date date;
     private Short priority;
     private Short category;
     private String requestType;
     private Date implementationAcceptDate;
     private Spzstate revisedRequest;
     private String revised;
     private String solution;
     private String relNotes;
     private String solutionInfo;
     private Project project;
     private Configuration config;
     
     private List<SpzStateWebEntity> history;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }
     
     
    public String getSolutionInfo() {
        return solutionInfo;
    }

    public void setSolutionInfo(String solutionInfo) {
        this.solutionInfo = solutionInfo;
    }

    public String getRelNotes() {
        return relNotes;
    }

    public void setRelNotes(String relNotes) {
        this.relNotes = relNotes;
    }

     
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

     
    public String getRevised() {
        return revised;
    }

    public void setRevised(String revised) {
        this.revised = revised;
    }

    public Short getCategory() {
        return category;
    }

    public void setCategory(Short category) {
        this.category = category;
    }

    public Spzstate getRevisedRequest() {
        return revisedRequest;
    }

    public void setRevisedRequest(Spzstate revisedRequest) {
        this.revisedRequest = revisedRequest;
    }
     
    public List<SpzStateWebEntity> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void setHistory(List<SpzStateWebEntity> history) {
        this.history = history;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getAnalyst() {
        return analyst;
    }

    public void setAnalyst(String analyst) {
        this.analyst = analyst;
    }
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shorname) {
        this.shortName = shorname;
    }

    public Date getImplementationAcceptDate() {
        return implementationAcceptDate;
    }

    public void setImplementationAcceptDate(Date implementationAcceptDate) {
        this.implementationAcceptDate = implementationAcceptDate;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
     
    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReqNumber() {
        return reqNumber;
    }

    public void setReqNumber(String number) {
        this.reqNumber = number;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contact) {
        this.contactPerson = contact;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getRequestDescription() {
        HTMLTransformer transformer = new HTMLTransformer();
        String description;
        try {
            transformer.convert(requestDescription);
            description = transformer.getResult();
        } catch (SPZException ex) {
            Logger.getLogger(SPZWebEntity.class.getName()).log(Level.SEVERE, "Chyba v popisu pozadavku.", ex);
            description = this.requestDescription;
        }
        return description;
    }

    public void setRequestDescription(String description) {
        this.requestDescription = description;
    }

    public Date getSpecDate() {
        return specDate;
    }

    public void setSpecDate(Date specDate) {
        this.specDate = specDate;
    }

    public double getWorkLoadEstimation() {
        return workLoadEstimation;
    }

    public void setWorkLoadEstimation(double workLoadEstimation) {
        this.workLoadEstimation = workLoadEstimation;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public double getWorkLoadReal() {
        return workLoadReal;
    }

    public void setWorkLoadReal(double workLoadReal) {
        this.workLoadReal = workLoadReal;
    }

    public String getSpzState() {
        return spzState;
    }

    public void setSpzState(String spzState) {
        this.spzState = spzState;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final SPZWebEntity other = (SPZWebEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
