package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="spz")
public class Spz implements Serializable {

    @Column(name="implementationAcceptDate",table="spz")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date implementationAcceptDate;
    @OneToMany(targetEntity = Spzstate.class,mappedBy = "spzId")
    private Collection<Spzstate> spzstateCollection;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="developer_login",referencedColumnName="login")
    private User developerLogin;
    @Column(name="issuer_login",table="spz",nullable=false,length=32)
    @Basic
    private String issuerLogin;
    @Column(name="requestType",table="spz",nullable=false,length=32)
    @Basic
    private String requestType;
    @Column(name="reqNumber",table="spz",nullable=false,length=10)
    @Basic
    private String reqNumber;
    @Column(name="contactPerson",table="spz",nullable=false,length=32)
    @Basic
    private String contactPerson;
    @Column(name="priority",table="spz",nullable=false)
    @Basic
    private short priority;
    @Column(name="requestDescription",table="spz",nullable=false,length=65535)
    @Lob
    @Basic
    private String requestDescription;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="analyst_login",referencedColumnName="login")
    private User analystLogin;
    @Column(name="id",table="spz",nullable=false)
    @Id
    private Integer id;
    @ManyToOne(optional=false,targetEntity = Configuration.class)
    @JoinColumn(name="configuration_id",referencedColumnName="id")
    private Configuration configurationId;
    @Column(name="issueDate",table="spz",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date issueDate;
    @Column(name="shortName",table="spz",nullable=false,length=50)
    @Basic
    private String shortName;
    @Column(name="ts",table="spz",nullable=false)
    @Basic
    private int ts;

    public Spz() {

    }
   
    public Date getImplementationAcceptDate() {
        return this.implementationAcceptDate;
    }

    public void setImplementationAcceptDate(Date implementationAcceptDate) {
        this.implementationAcceptDate = implementationAcceptDate;
    }
   
    public Collection<Spzstate> getSpzstateCollection() {
        return this.spzstateCollection;
    }

    public void setSpzstateCollection(Collection<Spzstate> spzstateCollection) {
        this.spzstateCollection = spzstateCollection;
    }
   
    public User getDeveloperLogin() {
        return this.developerLogin;
    }

    public void setDeveloperLogin(User developerLogin) {
        this.developerLogin = developerLogin;
    }
   
    public String getIssuerLogin() {
        return this.issuerLogin;
    }

    public void setIssuerLogin(String issuerLogin) {
        this.issuerLogin = issuerLogin;
    }
   
    public String getRequestType() {
        return this.requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
   
    public String getReqNumber() {
        return this.reqNumber;
    }

    public void setReqNumber(String reqNumber) {
        this.reqNumber = reqNumber;
    }
   
    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
   
    public short getPriority() {
        return this.priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }
   
    public String getRequestDescription() {
        return this.requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }
   
    public User getAnalystLogin() {
        return this.analystLogin;
    }

    public void setAnalystLogin(User analystLogin) {
        this.analystLogin = analystLogin;
    }
   
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
    public Configuration getConfigurationId() {
        return this.configurationId;
    }

    public void setConfigurationId(Configuration configurationId) {
        this.configurationId = configurationId;
    }
   
    public Date getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
   
    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
