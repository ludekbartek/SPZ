/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.jpa.entities.Projectconfiguration;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface ProjectConfigurationManager {

    void create(Projectconfiguration projectconfiguration);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Projectconfiguration projectconfiguration) throws NonexistentEntityException, Exception;

    Projectconfiguration findProjectconfiguration(Integer id);

    List<Projectconfiguration> findProjectconfigurationEntities();

    List<Projectconfiguration> findProjectconfigurationEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getProjectconfigurationCount();

    public Integer getProjectIdFor(Integer confId);

    public List<Configuration> getProjectConfigurations(Integer projId);
    
}
