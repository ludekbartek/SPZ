package cz.dcb.support.db.jpa;


import java.io.Serializable;
import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Roles implements Serializable {

    @Column(name="role_",table="roles",nullable=false,length=32)
    @Id
    private String role;
    @Column(name="login",table="roles",nullable=false,length=32)
    @Id
    private String login;

    public Roles() {

    }
   
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
   
    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
