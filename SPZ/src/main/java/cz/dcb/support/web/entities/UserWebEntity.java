/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author bar
 */
public class UserWebEntity {
   private long id;
   private String name;
   private String login;
   private String company;
   private String phone;
   private String fax;
   private String email;
   private Set<Integer> roles = new HashSet<>();
   private int classType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Integer> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public boolean getIsUser(){
        return roles.contains(Roles.CLIENT.ordinal());
    }
    
    public boolean getIsAnalyst(){
        return roles.contains(Roles.ANALYST.ordinal());
    }
    
    public boolean getIsManager(){
        return roles.contains(Roles.PROJECT_MANAGER.ordinal());
    }
    
    public boolean getIsAdmin(){
        return roles.contains(Roles.ADMIN.ordinal());
    }
    
    public void addRole(Integer role){
        roles.add(role);
    }
    
    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserWebEntity other = (UserWebEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
   
}
