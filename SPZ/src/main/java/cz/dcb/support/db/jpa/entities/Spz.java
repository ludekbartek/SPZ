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
@Table(name = "SPZ", catalog = "", schema = "SUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spz.findAll", query = "SELECT s FROM Spz s"),
    @NamedQuery(name = "Spz.findById", query = "SELECT s FROM Spz s WHERE s.id = :id"),
    @NamedQuery(name = "Spz.findByReqnumber", query = "SELECT s FROM Spz s WHERE s.reqnumber = :reqnumber"),
    @NamedQuery(name = "Spz.findByPriority", query = "SELECT s FROM Spz s WHERE s.priority = :priority"),
    @NamedQuery(name = "Spz.findByIssuedate", query = "SELECT s FROM Spz s WHERE s.issuedate = :issuedate"),
    @NamedQuery(name = "Spz.findByContactperson", query = "SELECT s FROM Spz s WHERE s.contactperson = :contactperson"),
    @NamedQuery(name = "Spz.findByRequesttype", query = "SELECT s FROM Spz s WHERE s.requesttype = :requesttype"),
    @NamedQuery(name = "Spz.findByShortname", query = "SELECT s FROM Spz s WHERE s.shortname = :shortname"),
    @NamedQuery(name = "Spz.findByRequestdescription", query = "SELECT s FROM Spz s WHERE s.requestdescription = :requestdescription"),
    @NamedQuery(name = "Spz.findByImplementationacceptdate", query = "SELECT s FROM Spz s WHERE s.implementationacceptdate = :implementationacceptdate"),
    @NamedQuery(name = "Spz.findByTs", query = "SELECT s FROM Spz s WHERE s.ts = :ts")})
public class Spz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Size(max = 10)
    @Column(name = "REQNUMBER", length = 10)
    private String reqnumber;
    @Column(name = "PRIORITY")
    private Short priority;
    @Column(name = "ISSUEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedate;
    @Size(max = 32)
    @Column(name = "CONTACTPERSON", length = 32)
    private String contactperson;
    @Size(max = 32)
    @Column(name = "REQUESTTYPE", length = 32)
    private String requesttype;
    @Size(max = 50)
    @Column(name = "SHORTNAME", length = 50)
    private String shortname;
    @Size(max = 9000)
    @Column(name = "REQUESTDESCRIPTION", length = 9000)
    private String requestdescription;
    @Column(name = "IMPLEMENTATIONACCEPTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date implementationacceptdate;
    @Column(name = "TS")
    private BigInteger ts;

    public Spz() {
    }

    public Spz(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReqnumber() {
        return reqnumber;
    }

    public void setReqnumber(String reqnumber) {
        this.reqnumber = reqnumber;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public Date getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Date issuedate) {
        this.issuedate = issuedate;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getRequestdescription() {
        return requestdescription;
    }

    public void setRequestdescription(String requestdescription) {
        this.requestdescription = requestdescription;
    }

    public Date getImplementationacceptdate() {
        return implementationacceptdate;
    }

    public void setImplementationacceptdate(Date implementationacceptdate) {
        this.implementationacceptdate = implementationacceptdate;
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
        if (!(object instanceof Spz)) {
            return false;
        }
        Spz other = (Spz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.dcb.support.db.jpa.entities.Spz[ id=" + id + " ]";
    }
    
}
