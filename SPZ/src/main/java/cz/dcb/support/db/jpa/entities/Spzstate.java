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
@Table(name = "SPZSTATE", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spzstate.findAll", query = "SELECT s FROM Spzstate s"),
    @NamedQuery(name = "Spzstate.findById", query = "SELECT s FROM Spzstate s WHERE s.id = :id"),
    @NamedQuery(name = "Spzstate.findByCode", query = "SELECT s FROM Spzstate s WHERE s.code = :code"),
    @NamedQuery(name = "Spzstate.findByTs", query = "SELECT s FROM Spzstate s WHERE s.ts = :ts"),
    @NamedQuery(name = "Spzstate.findByIssuerLogin", query = "SELECT s FROM Spzstate s WHERE s.issuerLogin = :issuerLogin"),
    @NamedQuery(name = "Spzstate.findByRevisedrequestdescription", query = "SELECT s FROM Spzstate s WHERE s.revisedrequestdescription = :revisedrequestdescription"),
    @NamedQuery(name = "Spzstate.findBySolutiondescription", query = "SELECT s FROM Spzstate s WHERE s.solutiondescription = :solutiondescription"),
    @NamedQuery(name = "Spzstate.findByAssumedmandays", query = "SELECT s FROM Spzstate s WHERE s.assumedmandays = :assumedmandays"),
    @NamedQuery(name = "Spzstate.findByMandays", query = "SELECT s FROM Spzstate s WHERE s.mandays = :mandays"),
    @NamedQuery(name = "Spzstate.findByReleasenotes", query = "SELECT s FROM Spzstate s WHERE s.releasenotes = :releasenotes"),
    @NamedQuery(name = "Spzstate.findByClasstype", query = "SELECT s FROM Spzstate s WHERE s.classtype = :classtype"),
    @NamedQuery(name = "Spzstate.findByIdate", query = "SELECT s FROM Spzstate s WHERE s.idate = :idate"),
    @NamedQuery(name = "Spzstate.findByCurrentstate", query = "SELECT s FROM Spzstate s WHERE s.currentstate = :currentstate")})
public class Spzstate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Size(max = 50)
    @Column(name = "CODE", length = 50)
    private String code;
    @Column(name = "TS")
    private BigInteger ts;
    @Size(max = 32)
    @Column(name = "ISSUER_LOGIN", length = 32)
    private String issuerLogin;
    @Size(max = 9000)
    @Column(name = "REVISEDREQUESTDESCRIPTION", length = 9000)
    private String revisedrequestdescription;
    @Size(max = 9000)
    @Column(name = "SOLUTIONDESCRIPTION", length = 9000)
    private String solutiondescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ASSUMEDMANDAYS", precision = 52)
    private Double assumedmandays;
    @Column(name = "MANDAYS", precision = 52)
    private Double mandays;
    @Size(max = 9000)
    @Column(name = "RELEASENOTES", length = 9000)
    private String releasenotes;
    @Column(name = "CLASSTYPE")
    private Short classtype;
    @Column(name = "IDATE")
    @Temporal(TemporalType.DATE)
    private Date idate;
    @Column(name = "CURRENTSTATE")
    private Integer currentstate;

    public Spzstate() {
    }

    public Spzstate(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigInteger getTs() {
        return ts;
    }

    public void setTs(BigInteger ts) {
        this.ts = ts;
    }

    public String getIssuerLogin() {
        return issuerLogin;
    }

    public void setIssuerLogin(String issuerLogin) {
        this.issuerLogin = issuerLogin;
    }

    public String getRevisedrequestdescription() {
        return revisedrequestdescription;
    }

    public void setRevisedrequestdescription(String revisedrequestdescription) {
        this.revisedrequestdescription = revisedrequestdescription;
    }

    public String getSolutiondescription() {
        return solutiondescription;
    }

    public void setSolutiondescription(String solutiondescription) {
        this.solutiondescription = solutiondescription;
    }

    public Double getAssumedmandays() {
        return assumedmandays;
    }

    public void setAssumedmandays(Double assumedmandays) {
        this.assumedmandays = assumedmandays;
    }

    public Double getMandays() {
        return mandays;
    }

    public void setMandays(Double mandays) {
        this.mandays = mandays;
    }

    public String getReleasenotes() {
        return releasenotes;
    }

    public void setReleasenotes(String releasenotes) {
        this.releasenotes = releasenotes;
    }

    public Short getClasstype() {
        return classtype;
    }

    public void setClasstype(Short classtype) {
        this.classtype = classtype;
    }

    public Date getIdate() {
        return idate;
    }

    public void setIdate(Date idate) {
        this.idate = idate;
    }

    public Integer getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(Integer currentstate) {
        this.currentstate = currentstate;
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
        if (!(object instanceof Spzstate)) {
            return false;
        }
        Spzstate other = (Spzstate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.dcb.support.db.jpa.entities.Spzstate[ id=" + id + " ]";
    }
    
}
