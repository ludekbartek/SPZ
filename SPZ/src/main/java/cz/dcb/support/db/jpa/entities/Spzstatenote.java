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
@Table(name = "SPZSTATENOTE", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spzstatenote.findAll", query = "SELECT s FROM Spzstatenote s"),
    @NamedQuery(name = "Spzstatenote.findById", query = "SELECT s FROM Spzstatenote s WHERE s.id = :id"),
    @NamedQuery(name = "Spzstatenote.findByNoteid", query = "SELECT s FROM Spzstatenote s WHERE s.noteid = :noteid"),
    @NamedQuery(name = "Spzstatenote.findByStateid", query = "SELECT s FROM Spzstatenote s WHERE s.stateid = :stateid")})
public class Spzstatenote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "NOTEID")
    private Integer noteid;
    @Column(name = "STATEID")
    private Integer stateid;

    public Spzstatenote() {
    }

    public Spzstatenote(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public Integer getStateid() {
        return stateid;
    }

    public void setStateid(Integer stateid) {
        this.stateid = stateid;
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
        if (!(object instanceof Spzstatenote)) {
            return false;
        }
        Spzstatenote other = (Spzstatenote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.dcb.support.db.jpa.entities.Spzstatenote[ id=" + id + " ]";
    }
    
}
