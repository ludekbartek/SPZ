/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers.utils;

import cz.dcb.support.db.jpa.Attachment;
import cz.dcb.support.db.jpa.Spznote;
import cz.dcb.support.db.jpa.Spzstate;
import cz.dcb.support.db.jpa.User;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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
    
    public static Attachment createAttachment(){
        Attachment atach = new Attachment();
        atach.setContent("Some content");
        atach.setDate(new GregorianCalendar().getTime());
        atach.setLocation("/some/path/");
        Spzstate state = createSpzState();
        atach.setSpznoteId(createSpznote(state));
        atach.setType("text/plain");
        atach.setTs(1000);
        return atach;
    }
    
    public static Spznote createSpznote(Spzstate state){
        Spznote note = new Spznote();
        note.setExternalNote((short)0);
        note.setIssuerLogin(createUser());
        note.setNoteDate(new GregorianCalendar().getTime());
        note.setNtext("Text poznamky");
        note.setStateId(state);
        Set<Spznote> notes = new HashSet<>();
        notes.add(note);
        state.setSpznoteCollection(notes);
        note.setTs(new Random().nextInt()%1000);
        return note;
    }
    
    public static Spzstate createSpzState(){
        Spzstate state = new Spzstate();
        state.setAssumedManDays(1.5);
        state.setCode("Started");
        state.setIssuerLogin(createUser());
        
        
        return state;
    }
    
    public static User createUser(){
        User user = new User();
        user.setCompany("aaa");
        user.setEmail("someb@aaa.cz");
        user.setFax("123");
        user.setLogin("aaa");
        user.setName("A Aa");
        user.setPassword("bbb");
        user.setTel("213456");
        user.setTs(1);
        return user;
    }
}
