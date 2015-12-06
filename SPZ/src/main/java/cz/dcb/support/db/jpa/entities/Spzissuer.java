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
@Table(name = "SPZISSUER", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spzissuer.findAll", query = "SELECT s FROM Spzissuer s"),
    @NamedQuery(name = "Spzissuer.findById", query = "SELECT s FROM Spzissuer s WHERE s.id = :id"),
    @NamedQuery(name = "Spzissuer.findBySpzid", query = "SELECT s FROM Spzissuer s WHERE s.spzid = :spzid"),
    @NamedQuery(name = "Spzissuer.findByUserid", query = "SELECT s FROM Spzissuer s WHERE s.userid = :userid")})
public class Spzissuer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "SPZID")
    private Integer spzid;
    @Column(name = "USERID")
    private Integer userid;

    public Spzissuer() {
    }

    public Spzissuer(Integer id) {
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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
        if (!(object instanceof Spzissuer)) {
            return false;
        }
        Spzissuer other = (Spzissuer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.dcb.support.db.jpa.entities.Spzissuer[ id=" + id + " ]";
    }
    
}
