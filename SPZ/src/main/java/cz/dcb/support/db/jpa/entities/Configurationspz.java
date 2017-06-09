/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.entities;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bar
 */
@Entity
@Table(name = "CONFIGURATIONSPZ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configurationspz.findAll", query = "SELECT c FROM Configurationspz c"),
    @NamedQuery(name = "Configurationspz.findById", query = "SELECT c FROM Configurationspz c WHERE c.id = :id"),
    @NamedQuery(name = "Configurationspz.findByConfigurationid", query = "SELECT c FROM Configurationspz c WHERE c.configurationid = :configurationid"),
    @NamedQuery(name = "Configurationspz.findBySpzid", query = "SELECT c FROM Configurationspz c WHERE c.spzid = :spzid")})
public class Configurationspz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CONFIGURATIONID")
    private Integer configurationid;
    @Column(name = "SPZID")
    private Integer spzid;

    public Configurationspz() {
    }

    public Configurationspz(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigurationid() {
        return configurationid;
    }

    public void setConfigurationid(Integer configurationid) {
        this.configurationid = configurationid;
    }

    public Integer getSpzid() {
        return spzid;
    }

    public void setSpzid(Integer spzid) {
        this.spzid = spzid;
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
        if (!(object instanceof Configurationspz)) {
            return false;
        }
        Configurationspz other = (Configurationspz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Configurationspz{" + "id=" + id + ", configurationid=" + configurationid + ", spzid=" + spzid + '}';
    }

    
    
}
