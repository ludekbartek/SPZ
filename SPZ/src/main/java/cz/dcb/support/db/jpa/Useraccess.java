package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="useraccess")
public class Useraccess implements Serializable {

    @Column(name="role_",table="useraccess",nullable=false,length=32)
    @Basic
    private String role;
    @Column(name="id",table="useraccess",nullable=false)
    @Id
    private Integer id;
    @ManyToOne(optional=false,targetEntity = Configuration.class)
    @JoinColumn(name="configuration_id",referencedColumnName="id")
    private Configuration configurationId;
    @ManyToOne(optional=false,targetEntity = User.class)
    @JoinColumn(name="login",referencedColumnName="login")
    private User login;
    @Column(name="ts",table="useraccess",nullable=false)
    @Basic
    private int ts;

    public Useraccess() {

    }
   
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
   
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
    public Configuration getConfigurationId() {
        return this.configurationId;
    }

    public void setConfigurationId(Configuration configurationId) {
        this.configurationId = configurationId;
    }
   
    public User getLogin() {
        return this.login;
    }

    public void setLogin(User login) {
        this.login = login;
    }
   
    public int getTs() {
        return this.ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }
}
