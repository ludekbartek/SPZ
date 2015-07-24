package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.Integer;
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
@Table(name="spznote")
public class Spznote implements Serializable {

    @ManyToOne(optional=false,targetEntity = User.class)
    @JoinColumn(name="issuer_login",referencedColumnName="login")
    private User issuerLogin;
    @Column(name="noteDate",table="spznote",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date noteDate;
    @ManyToOne(optional=false,targetEntity = Spzstate.class)
    @JoinColumn(name="state_id",referencedColumnName="id")
    private Spzstate stateId;
    @OneToMany(targetEntity = Attachment.class,mappedBy = "spznoteId")
    private Collection<Attachment> attachmentCollection;
    @Column(name="id",table="spznote",nullable=false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="external_note",table="spznote",nullable=false)
    @Basic
    private short externalNote;
    @Column(name="ntext",table="spznote",nullable=false,length=65535)
    @Lob
    @Basic
    private String ntext;
    @Column(name="ts",table="spznote",nullable=false)
    @Basic
    private int ts;

    public Spznote() {

    }
   
    public User getIssuerLogin() {
        return this.issuerLogin;
    }

    public void setIssuerLogin(User issuerLogin) {
        this.issuerLogin = issuerLogin;
    }
   
    public Date getNoteDate() {
        return this.noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }
   
    public Spzstate getStateId() {
        return this.stateId;
    }

    public void setStateId(Spzstate stateId) {
        this.stateId = stateId;
    }
   
    public Collection<Attachment> getAttachmentCollection() {
        return this.attachmentCollection;
    }

    public void setAttachmentCollection(Collection<Attachment> attachmentCollection) {
        this.attachmentCollection = attachmentCollection;
    }
   
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
    public short getExternalNote() {
        return this.externalNote;
    }

    public void setExternalNote(short externalNote) {
        this.externalNote = externalNote;
    }
   
    public String getNtext() {
        return this.ntext;
    }

    public void setNtext(String ntext) {
        this.ntext = ntext;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
