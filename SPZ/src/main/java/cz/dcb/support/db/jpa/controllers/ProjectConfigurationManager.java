/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.jpa.entities.Project;
import cz.dcb.support.db.jpa.entities.Projectconfiguration;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface ProjectConfigurationManager {

    /**
     * Vytvori novy zaznam o konfiguraci v danem pojektu.
     * @param projectconfiguration Entitni trida s id projektu a id konfigurace
     */
    void create(Projectconfiguration projectconfiguration);

    /**
     * Smaze zaznam s danym id
     * @param id id zaznamu o konfiguraci daneho projektu, ktery ma byt smazan.
     * @throws NonexistentEntityException zaznam s danym id neexistuje.
     */
    void destroy(Integer id) throws NonexistentEntityException;

    /**
     * Upravi udaje o zaznamu konfigurace daneho projektu
     * @param projectconfiguration zaznam, ktery ma byt zmenen.
     * @throws NonexistentEntityException zaznam projectconfiguration nenalezen
     * @throws Exception jina chyba pri praci s DB.
     */
    void edit(Projectconfiguration projectconfiguration) throws NonexistentEntityException, Exception;

    /**
     * Vraci zaznam o konfiguraci pro dany projekt
     * @param id id zaznamu
     * @return odpovidajici zaznam
     */
    Projectconfiguration findProjectconfiguration(Integer id);

    /**
     * Vraci seznam zaznamu o konfiguracich pro projekty.
     * @return seznam zaznamu o vsech konfiguracich pro vsechny projekty. 
     */
    List<Projectconfiguration> findProjectconfigurationEntities();

    /**
     * Vraci seznam zaznamu o konfiguracich  pro projekty pocitanej firstResultym zaznamem 
     * o delce nejvyse maxResult
     * @param maxResults maximalni pocet vracenych zaznamu.
     * @param firstResult index prvniho zaznamu
     * @return 
     */
    List<Projectconfiguration> findProjectconfigurationEntities(int maxResults, int firstResult);

    /**
     * Vraci entitni manazer pro daneho spravce
     * @return entitni manager
     */
    EntityManager getEntityManager();

    /**
     * Vraci pocet zaznamu o konfiguracich projektu
     * @return poce konfiguraci projektu
     */
    int getProjectconfigurationCount();

    /**
     * Vraci id projektu pro dane cislo konfigurace.
     * @param confId cislo konfigurace
     * @return Pozadovane cislo projektu
     */
    public Integer getProjectIdFor(Integer confId);

    /**
     * Vraci seznam konfiguraci pro projekt s danym ID
     * @param projId id projektu jehoz konfigurace se maji vratit
     * @return seznam konfiguraci.
     */
    public List<Configuration> getProjectConfigurations(Integer projId);
    
    /**
     * Vraci zaznam pro danou konfiguraci a pro dany projekt
     * @param project projekt
     * @param config konfigurace
     * @return zaznam o konfiguraci projektu
     */
    public Projectconfiguration getProjecConfiguration(Project project,Configuration config);
    
}
