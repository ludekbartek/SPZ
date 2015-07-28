/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Configuration;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface ConfigurationManager {

    void create(Configuration configuration);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Configuration configuration) throws NonexistentEntityException, Exception;

    Configuration findConfiguration(Integer id);

    List<Configuration> findConfigurationEntities();

    List<Configuration> findConfigurationEntities(int maxResults, int firstResult);

    int getConfigurationCount();

    EntityManager getEntityManager();
    
}
