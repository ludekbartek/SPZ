/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bar
 */
@Entity
@Table(name = "ATTACHMENTNOTE", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachmentnote.findAll", query = "SELECT a FROM Attachmentnote a"),
    @NamedQuery(name = "Attachmentnote.findById", query = "SELECT a FROM Attachmentnote a WHERE a.id = :id"),
    @NamedQuery(name = "Attachmentnote.findByAttachmentid", query = "SELECT a FROM Attachmentnote a WHERE a.attachmentid = :attachmentid"),
    @NamedQuery(name = "Attachmentnote.findBySpznoteid", query = "SELECT a FROM Attachmentnote a WHERE a.spznoteid = :spznoteid")})
public class Attachmentnote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ATTACHMENTID")
    private Integer attachmentid;
    @Column(name = "SPZNOTEID")
    private Integer spznoteid;

    public Attachmentnote() {
    }

    public Attachmentnote(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttachmentid() {
        return attachmentid;
    }

    public void setAttachmentid(Integer attachmentid) {
        this.attachmentid = attachmentid;
    }

    public Integer getSpznoteid() {
        return spznoteid;
    }

    public void setSpznoteid(Integer spznoteid) {
        this.spznoteid = spznoteid;
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
        if (!(object instanceof Attachmentnote)) {
            return false;
        }
        Attachmentnote other = (Attachmentnote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attachmentnote{" + "id=" + id + ", attachmentid=" + attachmentid + ", spznoteid=" + spznoteid + '}';
    }

   
    
}
