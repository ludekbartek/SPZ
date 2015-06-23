package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.String;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user_")
public class User implements Serializable {

    @OneToMany(targetEntity = Spznote.class,mappedBy = "issuerLogin")
    private Collection<Spznote> spznoteCollection;
    @OneToMany(targetEntity = Spzstate.class,mappedBy = "issuerLogin")
    private Collection<Spzstate> spzstateCollection;
    @OneToMany(targetEntity = Spz.class,mappedBy = "analystLogin")
    private Collection<Spz> spzCollection;
    @Column(name="login",table="user_",nullable=false,length=32)
    @Id
    private String login;
    @OneToMany(targetEntity = Spz.class,mappedBy = "developerLogin")
    private Collection<Spz> spzCollection1;
    @Column(name="password",table="user_",nullable=false,length=50)
    @Basic
    private String password;
    @OneToMany(targetEntity = Useraccess.class,mappedBy = "login")
    private Collection<Useraccess> useraccessCollection;
    @Column(name="name",table="user_",nullable=false,length=50)
    @Basic
    private String name;
    @Column(name="company",table="user_",length=50)
    @Basic
    private String company;
    @Column(name="tel",table="user_",length=50)
    @Basic
    private String tel;
    @Column(name="fax",table="user_",length=50)
    @Basic
    private String fax;
    @Column(name="email",table="user_",nullable=false,length=50)
    @Basic
    private String email;
    @Column(name="class_type",table="user_",nullable=false)
    @Basic
    private short classType;
    @Column(name="ts",table="user_",nullable=false)
    @Basic
    private int ts;

    public User() {

    }
   
    public Collection<Spznote> getSpznoteCollection() {
        return this.spznoteCollection;
    }

    public void setSpznoteCollection(Collection<Spznote> spznoteCollection) {
        this.spznoteCollection = spznoteCollection;
    }
   
    public Collection<Spzstate> getSpzstateCollection() {
        return this.spzstateCollection;
    }

    public void setSpzstateCollection(Collection<Spzstate> spzstateCollection) {
        this.spzstateCollection = spzstateCollection;
    }
   
    public Collection<Spz> getSpzCollection() {
        return this.spzCollection;
    }

    public void setSpzCollection(Collection<Spz> spzCollection) {
        this.spzCollection = spzCollection;
    }
   
    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
   
    public Collection<Spz> getSpzCollection1() {
        return this.spzCollection1;
    }

    public void setSpzCollection1(Collection<Spz> spzCollection1) {
        this.spzCollection1 = spzCollection1;
    }
   
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    public Collection<Useraccess> getUseraccessCollection() {
        return this.useraccessCollection;
    }

    public void setUseraccessCollection(Collection<Useraccess> useraccessCollection) {
        this.useraccessCollection = useraccessCollection;
    }
   
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
   
    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
   
    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
   
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    public short getClassType() {
        return this.classType;
    }

    public void setClassType(short classType) {
        this.classType = classType;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
