package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.security.InvalidParameterException;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="attachment")
public class Attachment implements Serializable {

    @Column(name="date",table="attachment",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date date;
    @Column(name="location",table="attachment",nullable=false)
    @Basic
    private String location;
    @Column(name="id",table="attachment",nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Id
    private Integer id;
    @ManyToOne(optional=false,targetEntity = Spznote.class)
    @JoinColumn(name="spznote_id",referencedColumnName="id")
    private Spznote spznoteId;
    @Column(name="type",table="attachment",nullable=false)
    @Basic
    private String type;
    @Column(name="content",table="attachment",nullable=false)
    @Basic
    private String content;
    @Column(name="ts",table="attachment",nullable=false)
    @Basic
    private int ts;

    public Attachment() {

    }
   
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        if(date==null){
            throw new NullPointerException("Date is null");
        }
        this.date = date;
    }
   
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        if(location == null){
            throw new NullPointerException("Location is null");
        }
        this.location = location;
    }
   
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        if(id==null){
            throw new NullPointerException("Id is null");
        }
        this.id = id;
    }
   
    public Spznote getSpznoteId() {
        return this.spznoteId;
    }

    public void setSpznoteId(Spznote spznoteId) {
        if(spznoteId==null){
            throw new NullPointerException("Spz note is null");
        }
        this.spznoteId = spznoteId;
    }
   
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        if(type==null){
            throw new NullPointerException("SPZ type is null");
        }
        this.type = type;
    }
   
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        if(content==null){
            throw new NullPointerException("Spz content is null");
        }
        this.content = content;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        if(ts<0)
        {
            throw new InvalidParameterException("Time stamp is negative number");
        }
        this.ts = ts;
    }
}
