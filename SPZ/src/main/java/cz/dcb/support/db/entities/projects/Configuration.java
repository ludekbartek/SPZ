/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.entities.projects;

/**
 *
 * @author bar
 */
public class Configuration {
    private long id;
    private String code;
    private String description;
    private String projectCode;
    private long sequence;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Configuration other = (Configuration) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

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
        if(code.length()>32){
            throw new IllegalArgumentException("Code is longer then 32 characters.");
        }
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.length()>255){
            throw new IllegalArgumentException("Description is too long.");
        }
        this.description = description;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        if(projectCode.length()>50){
            throw new IllegalArgumentException("Project code is too long.");
        }
        this.projectCode = projectCode;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
 
}
