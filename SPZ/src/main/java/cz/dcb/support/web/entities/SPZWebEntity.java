/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;

import cz.dcb.support.db.jpa.entities.Spzstate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Entitni trida reprezentujici jednu SPZ pro potreby editace a vypisu v JSP
 * @author Ludek Bartek
 */
public class SPZWebEntity {
     private Integer id;
     private String reqnumber;
     private String kind;
     private String issuer;
     private String contactperson;
     private String shortname;
     private String analyst;
     private String developer;
     private Date issuedate;
     private String requestdescription;
     private Date specDate;
     private double workLoadEstimation;
     private Date installDate;
     private double workLoadReal;
     private int spzState;
     private Date date;
     private Short priority;
     private Short category;
     private String requesttype;
     private Date implementationacceptdate;
     private Spzstate revisedRequest;
     private List<SpzStateWebEntity> history;

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
    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shorname) {
        this.shortname = shorname;
    }

    public Date getImplementationacceptdate() {
        return implementationacceptdate;
    }

    public void setImplementationacceptdate(Date implementationacceptdate) {
        this.implementationacceptdate = implementationacceptdate;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
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

    public String getReqnumber() {
        return reqnumber;
    }

    public void setReqnumber(String number) {
        this.reqnumber = number;
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

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contact) {
        this.contactperson = contact;
    }

    public Date getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Date issueDate) {
        this.issuedate = issueDate;
    }

    public String getRequestdescription() {
        return requestdescription;
    }

    public void setRequestdescription(String description) {
        this.requestdescription = description;
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

    public int getSpzState() {
        return spzState;
    }

    public void setSpzState(int spzState) {
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
