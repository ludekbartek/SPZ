/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.entities;

import cz.dcb.support.db.SPZException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author bar
 */
public class SPZ {
    private long id;
    private String name;
    private SpzCategory category;
    private SpzPriority priority;
    private User author; 
    private SpzType type;
    private Date creationDate;
    private String specification;
    private User analyst;
    private int specificationRevision;
    private int estimatedWorkLoad;
    private String additionalInfo;
    private int realWorkLoad;
    private List<Attachment> attachments;//hodilo by se rozbit zavislost
    private State state;

    public SPZ(){
        attachments = new ArrayList<>();
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpzCategory getCategory() {
        return category;
    }

    public void setCategory(SpzCategory category) {
        this.category = category;
    }

    public SpzPriority getPriority() {
        return priority;
    }

    public void setPriority(SpzPriority priority) {
        this.priority = priority;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public SpzType getType() {
        return type;
    }

    public void setType(SpzType type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public User getAnalyst() {
        return analyst;
    }

    public void setAnalyst(User analyst) {
        this.analyst = analyst;
    }

    public int getSpecificationRevision() {
        return specificationRevision;
    }

    public void setSpecificationRevision(int specificationRevision) {
        this.specificationRevision = specificationRevision;
    }

    public int getEstimatedWorkLoad() {
        return estimatedWorkLoad;
    }

    public void setEstimatedWorkLoad(int estimatedWorkLoad) {
        this.estimatedWorkLoad = estimatedWorkLoad;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public int getRealWorkLoad() {
        return realWorkLoad;
    }

    public void setRealWorkLoad(int realWorkLoad) {
        this.realWorkLoad = realWorkLoad;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    public void addAttachment(Attachment attachmet)throws SPZException{
        if(this.attachments.size()>=3){
            throw new SPZException("No more attachments allowed.");
        }
        attachments.add(attachmet);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.name);
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
        final SPZ other = (SPZ) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}
