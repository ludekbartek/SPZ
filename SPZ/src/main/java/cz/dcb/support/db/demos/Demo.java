/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.demos;
import cz.dcb.support.db.jpa.Attachment;
import cz.dcb.support.db.jpa.Spznote;
import cz.dcb.support.db.jpa.User;
import cz.dcb.support.db.managers.AttachmentJpaController;
import cz.dcb.support.db.managers.AttachmentManager;
import cz.dcb.support.db.managers.UserJpaController;
import cz.dcb.support.db.managers.UserManager;
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
        note.setIssuerLogin(user);
        attach.setContent("blabla");
        attach.setLocation("somewhere");
        attach.setDate(new Date());
        attach.setType("application/none");
        attach.setSpznoteId(note);
        man.create(attach);
    }
}
