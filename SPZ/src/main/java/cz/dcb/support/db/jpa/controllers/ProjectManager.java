/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Project;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface ProjectManager {

    void create(Project project);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Project project) throws NonexistentEntityException, Exception;

    Project findProject(Integer id);

    List<Project> findProjectEntities();

    List<Project> findProjectEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getProjectCount();
    
}
