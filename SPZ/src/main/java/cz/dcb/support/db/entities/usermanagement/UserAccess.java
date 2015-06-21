/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.entities.usermanagement;

/**
 *
 * @author bar
 */
public class UserAccess {
    private long id;
    private String login;
    private long configurationId;
    private Role role;
    private long ts;

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

    public long getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(long configurationId) {
        this.configurationId = configurationId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final UserAccess other = (UserAccess) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
