/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Attachment;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 * @author bar
 * Rozhrani definujici chovani manazeru priloh
 */
public interface AttachmentManager {

    /**
     * Prida do databaze prilohu
     * @param attachment priloha, ktera se ma pridat
     * @throws PreexistingEntityException - v pripade, ze priloha s danym 
     *                                      klicem jiz v DB existuje
     * @throws Exception - propoustena vyjimka z JPA v pripade, ze priloha
     *                     v DB neexistuje.
     */
    void create(Attachment attachment) throws PreexistingEntityException, Exception;
    
    /**
     * Smaze prilohu s danym id z DB.
     * @param id - klic prilohy, ktera se ma smazat
     * @throws NonexistentEntityException - v pripade, ze priloha v DB 
     *                                      neexistuje.
     */
    void destroy(Integer id) throws NonexistentEntityException;

    /**
     * Upravi prilohu v DB tak, aby odpovidala danemu parametru. Priloha,
     * ktera se ma upravit je urcena atributem id parametru.
     * @param attachment - priloha po upravach
     * @throws NonexistentEntityException - v pripade, ze priloha v DB
     *                                      neexistuje
     * @throws Exception - propoustena vyjimka z JPA v pripade, ze nema 
     *                     nasteveno chybove hlaseni.
     */
    void edit(Attachment attachment) throws NonexistentEntityException, Exception;

    /**
     * Vrati prilohu s danym id.
     * @param id - klic hledane prilohy
     * @return prilohu s danou hodnotou klice.
     */
    Attachment findAttachment(Integer id);

    /**
     * Vrati vsechny prilohy v DB.
     * @return seznam vsech priloh v DB.
     */
    List<Attachment> findAttachmentEntities();

    /**
     * Vraci seznam, ktery obsahuje maxResults priloh pocinaje prilohou
     * s hodnotou klice id rovnou firstResult.
     * @param maxResults
     * @param firstResult
     * @return seznam priloh
     */
    List<Attachment> findAttachmentEntities(int maxResults, int firstResult);

    /**
     * Vraci pocet priloh
     * @return pocet priloh.
     */
    int getAttachmentCount();
    
}
