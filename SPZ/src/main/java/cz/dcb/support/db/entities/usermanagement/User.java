/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.entities.usermanagement;

import java.util.Objects;

/**
 *
 * @author bar
 */
public class User {
    private long id;
    private String login;
    private String password;
    private String name;
    private String email;
    private String fax;
    private String company;
    private String phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if(login.length()>32){
            throw new IllegalArgumentException("Login too long.");
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.length()>50){
            throw new IllegalArgumentException("Password too long.");
        }
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length()>50){
            throw new IllegalArgumentException("Name too long.");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 71 * hash + Objects.hashCode(this.login);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }
    
    
}
