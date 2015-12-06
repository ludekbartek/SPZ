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
@Table(name = "SPZSTATES", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spzstates.findAll", query = "SELECT s FROM Spzstates s"),
    @NamedQuery(name = "Spzstates.findById", query = "SELECT s FROM Spzstates s WHERE s.id = :id"),
    @NamedQuery(name = "Spzstates.findBySpzid", query = "SELECT s FROM Spzstates s WHERE s.spzid = :spzid"),
    @NamedQuery(name = "Spzstates.findByStateid", query = "SELECT s FROM Spzstates s WHERE s.stateid = :stateid")})
public class Spzstates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "SPZID")
    private Integer spzid;
    @Column(name = "STATEID")
    private Integer stateid;

    public Spzstates() {
    }

    public Spzstates(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpzid() {
        return spzid;
    }

    public void setSpzid(Integer spzid) {
        this.spzid = spzid;
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
        if (!(object instanceof Spzstates)) {
            return false;
        }
        Spzstates other = (Spzstates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.dcb.support.db.jpa.entities.Spzstates[ id=" + id + " ]";
    }
    
}
