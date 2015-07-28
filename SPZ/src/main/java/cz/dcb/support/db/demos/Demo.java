/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.demos;
import cz.dcb.support.db.jpa.controllers.AttachmentJpaController;
import cz.dcb.support.db.jpa.controllers.AttachmentManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.entities.Attachment;
import cz.dcb.support.db.jpa.entities.Spznote;
import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.db.utils.DBUtils;
import java.util.Date;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author bar
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        AttachmentManager man = new AttachmentJpaController(emf);
        UserManager userMan = new UserJpaController(emf);
        Attachment attach = new Attachment();
        Spznote note = new Spznote();
        User user = new User();
        user.setName("Jan Novak");
        user.setLogin("xnovak");
        user.setEmail("xnovak@dcb.cz");
        user.setPassword("blabla");
        userMan.create(user);
        attach.setContent("blabla");
        attach.setLocation("somewhere");
        attach.setDate(new Date());
        attach.setType("application/none");
        man.create(attach);
    }
}
