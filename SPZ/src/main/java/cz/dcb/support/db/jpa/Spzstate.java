package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Short;
import java.lang.String;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="spzstate")
public class Spzstate implements Serializable {

    @OneToMany(targetEntity = Spznote.class,mappedBy = "stateId")
    private Collection<Spznote> spznoteCollection;
    @Column(name="idate",table="spzstate",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date idate;
    @Column(name="code",table="spzstate",nullable=false,length=50)
    @Basic
    private String code;
    @ManyToOne(optional=false,targetEntity = User.class)
    @JoinColumn(name="issuer_login",referencedColumnName="login")
    private User issuerLogin;
    @Column(name="solutionDescription",table="spzstate",length=65535)
    @Lob
    @Basic
    private String solutionDescription;
    @Column(name="manDays",table="spzstate",precision=22)
    @Basic
    private Double manDays;
    @Column(name="revisedRequestDescription",table="spzstate",length=65535)
    @Lob
    @Basic
    private String revisedRequestDescription;
    @ManyToOne(optional=false,targetEntity = Spz.class)
    @JoinColumn(name="spz_id",referencedColumnName="id")
    private Spz spzId;
    @Column(name="releaseNotes",table="spzstate",length=65535)
    @Lob
    @Basic
    private String releaseNotes;
    @Column(name="id",table="spzstate",nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Column(name="current_state",table="spzstate")
    @Basic
    private Short currentState;
    @Column(name="assumedManDays",table="spzstate",precision=22)
    @Basic
    private Double assumedManDays;
    @Column(name="class_type",table="spzstate",nullable=false)
    @Basic
    private short classType;
    @Column(name="ts",table="spzstate",nullable=false)
    @Basic
    private int ts;

    public Spzstate() {

    }
   
    public Collection<Spznote> getSpznoteCollection() {
        return this.spznoteCollection;
    }

    public void setSpznoteCollection(Collection<Spznote> spznoteCollection) {
        this.spznoteCollection = spznoteCollection;
    }
   
    public Date getIdate() {
        return this.idate;
    }

    public void setIdate(Date idate) {
        this.idate = idate;
    }
   
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
   
    public User getIssuerLogin() {
        return this.issuerLogin;
    }

    public void setIssuerLogin(User issuerLogin) {
        this.issuerLogin = issuerLogin;
    }
   
    public String getSolutionDescription() {
        return this.solutionDescription;
    }

    public void setSolutionDescription(String solutionDescription) {
        this.solutionDescription = solutionDescription;
    }
   
    public Double getManDays() {
        return this.manDays;
    }

    public void setManDays(Double manDays) {
        this.manDays = manDays;
    }
   
    public String getRevisedRequestDescription() {
        return this.revisedRequestDescription;
    }

    public void setRevisedRequestDescription(String revisedRequestDescription) {
        this.revisedRequestDescription = revisedRequestDescription;
    }
   
    public Spz getSpzId() {
        return this.spzId;
    }

    public void setSpzId(Spz spzId) {
        this.spzId = spzId;
    }
   
    public String getReleaseNotes() {
        return this.releaseNotes;
    }

    public void setReleaseNotes(String releaseNotes) {
        this.releaseNotes = releaseNotes;
    }
   
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
    public Short getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(Short currentState) {
        this.currentState = currentState;
    }
   
    public Double getAssumedManDays() {
        return this.assumedManDays;
    }

    public void setAssumedManDays(Double assumedManDays) {
        this.assumedManDays = assumedManDays;
    }
   
    public short getClassType() {
        return this.classType;
    }

    public void setClassType(short classType) {
        this.classType = classType;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
