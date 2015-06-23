package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
        this.date = date;
    }
   
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
   
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
    public Spznote getSpznoteId() {
        return this.spznoteId;
    }

    public void setSpznoteId(Spznote spznoteId) {
        this.spznoteId = spznoteId;
    }
   
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
   
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
