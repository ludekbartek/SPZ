/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bar
 */
public class SPZWebEntity {
     private Integer id;
     private String number;
     private int kind;
     private String issuer;
     private String contact;
     private Date issueDate;
     private String description;
     private Date specDate;
     private double workLoadEstimation;
     private Date installDate;
     private double workLoadReal;
     private int spzState;
     private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
