/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author bar
 */
public class DBUtils {
    public static EntityManagerFactory getEntityManagerFactory()
    {
        return Persistence.createEntityManagerFactory("support_JPA");
    }
}
