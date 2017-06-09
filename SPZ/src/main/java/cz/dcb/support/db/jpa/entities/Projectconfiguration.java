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
@Table(name = "PROJECTCONFIGURATION", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projectconfiguration.findAll", query = "SELECT p FROM Projectconfiguration p"),
    @NamedQuery(name = "Projectconfiguration.findById", query = "SELECT p FROM Projectconfiguration p WHERE p.id = :id"),
    @NamedQuery(name = "Projectconfiguration.findByProjectid", query = "SELECT p FROM Projectconfiguration p WHERE p.projectid = :projectid"),
    @NamedQuery(name = "Projectconfiguration.findByConfigurationid", query = "SELECT p FROM Projectconfiguration p WHERE p.configurationid = :configurationid")})
public class Projectconfiguration implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "PROJECTID")
    private Integer projectid;
    @Column(name = "CONFIGURATIONID")
    private Integer configurationid;

    public Projectconfiguration() {
    }

    public Projectconfiguration(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
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
        if (!(object instanceof Projectconfiguration)) {
            return false;
        }
        Projectconfiguration other = (Projectconfiguration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Projectconfiguration{" + "id=" + id + ", projectid=" + projectid + ", configurationid=" + configurationid + '}';
    }

    
    
}
