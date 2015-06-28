/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Configuration;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author bar
 */
public interface ConfigurationManager {

    /**
     * Vytvori  novou konfiguraci, ktera odpovida parametru configuration.
     * @param configuration konfigurace, ktera se ma vytvorit.
     * @throws PreexistingEntityException - Konfigurace jiz existuje.
     * @throws Exception - jina chyba pri pokusu o vytvoreni JPA
     */
    void create(Configuration configuration) throws PreexistingEntityException, Exception;
    /**
     * Snaze konfigurace s klicem id.
     * @param id - klic konfigurace, ktera se ma smazat.
     * @throws IllegalOrphanException - v pripade, ze se je konfigurace pouzivana.
     * @throws NonexistentEntityException - v pripade, ze configurace neexistuje
     */
    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    /**
     * Upravi konfiguraci, ktera odpovida id parametru tak, aby byla shodna
     * s parametrem configuration.
     * @param configuration
     * @throws IllegalOrphanException - v pripade, ze je konfigurace pouzivana.
     * @throws NonexistentEntityException - pokud konfigurace neexistuje.
     * @throws Exception - propoustena vyjimka z JPA.
     */
    void edit(Configuration configuration) throws IllegalOrphanException, NonexistentEntityException, Exception;

    /**
     * Vyhleda v db konfigurace s danym id.
     * @param id - klic hledane konfigurace
     * @return konfigurace s klicem rovnym parametru id.
     */
    Configuration findConfiguration(Integer id);

    /**
     * Vrati vsechny konfigurace v DB.
     * @return seznam vsech konfiguraci v DB.
     */
    List<Configuration> findConfigurationEntities();

    /**
     * Vraci seznam daneho poctu konfiguraci od konfigurace s danou 
     * hodnotou klice.
     * @param maxResults pocet konfiguraci, ktere se maji vracet.
     * @param firstResult hodnota klice prvni vracene konfigurace
     * @return seznam konfiguraci.
     */
    List<Configuration> findConfigurationEntities(int maxResults, int firstResult);

    /**
     * Vraci pocet konfiguraci.
     * @return pocet konfiguraci v DB. 
     */
    int getConfigurationCount();
    
}
