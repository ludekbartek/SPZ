/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.controllers.exceptions.PreexistingEntityException;
import cz.dcb.support.db.jpa.entities.Configurationspz;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface ConfiguratinspzManager extends Serializable {

    void create(Configurationspz configurationspz) throws PreexistingEntityException, Exception;

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Configurationspz configurationspz) throws NonexistentEntityException, Exception;

    Configurationspz findConfigurationspz(Integer id);

    List<Configurationspz> findConfigurationspzEntities();

    List<Configurationspz> findConfigurationspzEntities(int maxResults, int firstResult);

    int getConfigurationspzCount();

    EntityManager getEntityManager();
    
}
