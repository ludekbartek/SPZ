package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.String;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="project")
public class Project implements Serializable {

    @Column(name="name",table="project",nullable=false,length=32)
    @Id
    private String name;
    @Column(name="description",table="project")
    @Basic
    private String description;
    @OneToMany(targetEntity = Configuration.class,mappedBy = "projectCode")
    private Collection<Configuration> configurationCollection;
    @Column(name="ts",table="project",nullable=false)
    @Basic
    private int ts;

    public Project() {

    }
   
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
    public Collection<Configuration> getConfigurationCollection() {
        return this.configurationCollection;
    }

    public void setConfigurationCollection(Collection<Configuration> configurationCollection) {
        this.configurationCollection = configurationCollection;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
