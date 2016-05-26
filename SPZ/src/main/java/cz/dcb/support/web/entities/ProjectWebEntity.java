/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web.entities;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author bar
 */
public class ProjectWebEntity {
    private Integer id;
    private String name;
    private String description;
    private List<ConfigurationWebEntity> configs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ConfigurationWebEntity> getConfigs() {
        return Collections.unmodifiableList(configs);
    }

    public void setConfigs(List<ConfigurationWebEntity> configs) {
        this.configs = configs;
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final ProjectWebEntity other = (ProjectWebEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
