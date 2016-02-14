/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bar
 */
@Entity
@Table(name = "SPZNOTE", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spznote.findAll", query = "SELECT s FROM Spznote s"),
    @NamedQuery(name = "Spznote.findById", query = "SELECT s FROM Spznote s WHERE s.id = :id"),
    @NamedQuery(name = "Spznote.findByExternalnote", query = "SELECT s FROM Spznote s WHERE s.externalnote = :externalnote"),
    @NamedQuery(name = "Spznote.findByNotedate", query = "SELECT s FROM Spznote s WHERE s.notedate = :notedate"),
    @NamedQuery(name = "Spznote.findByNotetext", query = "SELECT s FROM Spznote s WHERE s.notetext = :notetext"),
    @NamedQuery(name = "Spznote.findByTs", query = "SELECT s FROM Spznote s WHERE s.ts = :ts")})
public class Spznote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "EXTERNALNOTE")
    private Short externalnote;
    @Column(name = "NOTEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notedate;
    @Size(max = 8000)
    @Column(name = "NOTETEXT", length = 8000)
    private String notetext;
    @Column(name = "TS")
    private BigInteger ts;
    @Column(name = "ISSUER", length = 100)
    private String issuer;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Spznote() {
    }

    public Spznote(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getExternalnote() {
        return externalnote;
    }

    public void setExternalnote(Short externalnote) {
        this.externalnote = externalnote;
    }

    public Date getNotedate() {
        return notedate;
    }

    public void setNotedate(Date notedate) {
        this.notedate = notedate;
    }

    public String getNotetext() {
        return notetext;
    }

    public void setNotetext(String notetext) {
        this.notetext = notetext;
    }

    public BigInteger getTs() {
        return ts;
    }

    public void setTs(BigInteger ts) {
        this.ts = ts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spznote)) {
            return false;
        }
        Spznote other = (Spznote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.dcb.support.db.jpa.entities.Spznote[ id=" + id + " ]";
    }
    
}
