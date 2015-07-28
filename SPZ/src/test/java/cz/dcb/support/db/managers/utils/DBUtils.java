/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers.utils;

import cz.dcb.support.db.jpa.Attachment;
import cz.dcb.support.db.jpa.Configuration;
import cz.dcb.support.db.jpa.Project;
import cz.dcb.support.db.jpa.Spz;
import cz.dcb.support.db.jpa.Spznote;
import cz.dcb.support.db.jpa.Spzstate;
import cz.dcb.support.db.jpa.User;
import cz.dcb.support.db.managers.ConfigurationJpaController;
import cz.dcb.support.db.managers.ConfigurationManager;
import cz.dcb.support.db.managers.ProjectJpaController;
import cz.dcb.support.db.managers.ProjectManager;
import cz.dcb.support.db.managers.UserJpaController;
import cz.dcb.support.db.managers.UserManager;
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
    
    public static Spz createSpz() throws Exception {
        EntityManagerFactory emf = getEntityManagerFactory();
        User user = createUser();
        UserManager userManager = new UserJpaController(emf);
        userManager.create(user);
        ConfigurationManager confManager = new ConfigurationJpaController(emf);
        Configuration config = DBUtils.createConfiguration();
        confManager.create(config);
        
        GregorianCalendar cal = new GregorianCalendar(2015, 7, 29);
        Spz spz = new Spz();
        spz.setAnalystLogin(user);
        spz.setDeveloperLogin(user);
        spz.setContactPerson(user.getName());
        spz.setImplementationAcceptDate(cal.getTime());
        spz.setIssueDate(cal.getTime());
        spz.setIssuerLogin(user.getLogin());
        spz.setReqNumber("1");
        spz.setRequestDescription("Some description");
        spz.setRequestType("radna");
        spz.setShortName("test");
        spz.setConfigurationId(config);
        return spz;
    }

    private static Configuration createConfiguration() throws Exception {
        EntityManagerFactory emf = DBUtils.getEntityManagerFactory();
        ProjectManager projMan = new ProjectJpaController(emf);
        Project project = DBUtils.createProject();
        projMan.create(project);
        
        Configuration config = new Configuration();
        
        config.setCode("Some code");
        config.setDescription("Some description");
        config.setProjectCode(project);
        config.setSeqnumber(1);
        
        return config;
    }

    private static Project createProject() {
        Project project = new Project();
        project.setDescription("Some project description");
        project.setName("Test project");
        project.setTs(1);
        return project;
    }
}
