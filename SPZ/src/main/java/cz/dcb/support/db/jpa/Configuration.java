package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="configuration")
public class Configuration implements Serializable {

    @OneToMany(targetEntity = Spz.class,mappedBy = "configurationId")
    private Collection<Spz> spzCollection;
    @Column(name="code",table="configuration",nullable=false,length=32)
    @Basic
    private String code;
    @Column(name="seqnumber",table="configuration",nullable=false)
    @Basic
    private int seqnumber;
    @ManyToOne(optional=false,targetEntity = Project.class)
    @JoinColumn(name="project_code",referencedColumnName="name")
    private Project projectCode;
    @OneToMany(targetEntity = Useraccess.class,mappedBy = "configurationId")
    private Collection<Useraccess> useraccessCollection;
    @Column(name="description",table="configuration",nullable=false)
    @Basic
    private String description;
    @Column(name="id",table="configuration",nullable=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name="ts",table="configuration",nullable=false)
    @Basic
    private int ts;

    public Configuration() {

    }
   
    public Collection<Spz> getSpzCollection() {
        return this.spzCollection;
    }

    public void setSpzCollection(Collection<Spz> spzCollection) {
        this.spzCollection = spzCollection;
    }
   
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
   
    public int getSeqnumber() {
        return this.seqnumber;
    }

    public void setSeqnumber(int seqnumber) {
        this.seqnumber = seqnumber;
    }
   
    public Project getProjectCode() {
        return this.projectCode;
    }

    public void setProjectCode(Project projectCode) {
        this.projectCode = projectCode;
    }
   
    public Collection<Useraccess> getUseraccessCollection() {
        return this.useraccessCollection;
    }

    public void setUseraccessCollection(Collection<Useraccess> useraccessCollection) {
        this.useraccessCollection = useraccessCollection;
    }
   
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
