/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bar
 */
@Entity
@Table(name = "USERACCESS", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Useraccess.findAll", query = "SELECT u FROM Useraccess u"),
    @NamedQuery(name = "Useraccess.findById", query = "SELECT u FROM Useraccess u WHERE u.id = :id"),
    @NamedQuery(name = "Useraccess.findByRole", query = "SELECT u FROM Useraccess u WHERE u.role = :role"),
    @NamedQuery(name = "Useraccess.findByTs", query = "SELECT u FROM Useraccess u WHERE u.ts = :ts"),
    @NamedQuery(name = "Useraccess.findByUserid", query = "SELECT u FROM Useraccess u WHERE u.userid = :userid"),
    @NamedQuery(name = "Useraccess.findByConfigurationid", query = "SELECT u FROM Useraccess u WHERE u.configurationid = :configurationid")})
public class Useraccess implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Size(max = 32)
    @Column(name = "ROLE_", length = 32)
    private String role;
    @Column(name = "TS")
    private BigInteger ts;
    @Column(name = "USERID")
    private Integer userid;
    @Column(name = "CONFIGURATIONID")
    private Integer configurationid;

    public Useraccess() {
    }

    public Useraccess(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BigInteger getTs() {
        return ts;
    }

    public void setTs(BigInteger ts) {
        this.ts = ts;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getConfigurationid() {
        return configurationid;
    }

    public void setConfigurationid(Integer configurationid) {
        this.configurationid = configurationid;
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
        if (!(object instanceof Useraccess)) {
            return false;
        }
        Useraccess other = (Useraccess) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Useraccess{" + "id=" + id + ", role=" + role + ", ts=" + ts + ", userid=" + userid + ", configurationid=" + configurationid + '}';
    }

}
