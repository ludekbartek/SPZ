/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.utils;

import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.jpa.entities.Spzissuer;
import cz.dcb.support.db.jpa.entities.Useraccess;
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
