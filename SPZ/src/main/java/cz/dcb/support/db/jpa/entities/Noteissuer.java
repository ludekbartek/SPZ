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
@Table(name = "NOTEISSUER", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Noteissuer.findAll", query = "SELECT n FROM Noteissuer n"),
    @NamedQuery(name = "Noteissuer.findById", query = "SELECT n FROM Noteissuer n WHERE n.id = :id"),
    @NamedQuery(name = "Noteissuer.findByNoteid", query = "SELECT n FROM Noteissuer n WHERE n.noteid = :noteid"),
    @NamedQuery(name = "Noteissuer.findByUserid", query = "SELECT n FROM Noteissuer n WHERE n.userid = :userid")})
public class Noteissuer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "NOTEID")
    private Integer noteid;
    @Column(name = "USERID")
    private Integer userid;

    public Noteissuer() {
    }

    public Noteissuer(Integer id) {
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
        if (!(object instanceof Noteissuer)) {
            return false;
        }
        Noteissuer other = (Noteissuer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Noteissuer{" + "id=" + id + ", noteid=" + noteid + ", userid=" + userid + '}';
    }

    
    
}
