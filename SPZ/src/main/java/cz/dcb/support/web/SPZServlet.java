/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web;

import cz.dcb.support.db.entities.usermanagement.UserAccess;
import cz.dcb.support.db.exceptions.SPZException;
import cz.dcb.support.db.jpa.controllers.AttachmentJpaController;
import cz.dcb.support.db.jpa.controllers.AttachmentManager;
import cz.dcb.support.db.jpa.controllers.AttachmentNoteJpaController;
import cz.dcb.support.db.jpa.controllers.AttachmentNoteManager;
import cz.dcb.support.db.jpa.controllers.ConfigurationJpaController;
import cz.dcb.support.db.jpa.controllers.ConfigurationManager;
import cz.dcb.support.db.jpa.controllers.ConfigurationSpzJpaController;
import cz.dcb.support.db.jpa.controllers.ConfigurationSpzManager;
import cz.dcb.support.db.jpa.controllers.ProjectConfigurationJpaController;
import cz.dcb.support.db.jpa.controllers.ProjectConfigurationManager;
import cz.dcb.support.db.jpa.controllers.ProjectJpaController;
import cz.dcb.support.db.jpa.controllers.ProjectManager;
import cz.dcb.support.db.jpa.controllers.RolesJpaController;
import cz.dcb.support.db.jpa.controllers.RolesManager;
import cz.dcb.support.db.jpa.controllers.SpzAnalystJpaController;
import cz.dcb.support.db.jpa.controllers.SpzAnalystManager;
import cz.dcb.support.db.jpa.controllers.SpzIssuerJpaController;
import cz.dcb.support.db.jpa.controllers.SpzIssuerManager;
import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.SpzNoteJpaController;
import cz.dcb.support.db.jpa.controllers.SpzNoteManager;
import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.controllers.SpzStateNoteJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateNoteManager;
import cz.dcb.support.db.jpa.controllers.SpzStatesJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStatesManager;
import cz.dcb.support.db.jpa.controllers.UserAccessJpaController;
import cz.dcb.support.db.jpa.controllers.UserAccessManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Attachment;
import cz.dcb.support.db.jpa.entities.Attachmentnote;
import cz.dcb.support.db.jpa.entities.Configuration;
import cz.dcb.support.db.jpa.entities.Project;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.SpzStates;
import cz.dcb.support.db.jpa.entities.Spzanalyst;
import cz.dcb.support.db.jpa.entities.Spzissuer;
import cz.dcb.support.db.jpa.entities.Spznote;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.jpa.entities.Spzstatenote;
import cz.dcb.support.db.jpa.entities.Spzstates;
import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.db.jpa.entities.Useraccess;
import cz.dcb.support.web.entities.AttachmentEntity;
import cz.dcb.support.web.entities.Roles;
import cz.dcb.support.web.entities.SPZWebEntity;
import cz.dcb.support.web.entities.SpzNoteEntity;
import cz.dcb.support.web.entities.SpzStateWebEntity;
import cz.dcb.support.web.entities.UserWebEntity;
import cz.dcb.support.web.entities.ConfigurationWebEntity;
import cz.dcb.support.xml.HTMLTransformer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.derby.drda.NetworkServerControl;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import cz.dcb.support.db.jpa.entities.Configurationspz;
import cz.dcb.support.db.jpa.entities.Projectconfiguration;
import cz.dcb.support.web.entities.ProjectWebEntity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import javax.persistence.NoResultException;

/**
 *
 * @author bar
 */
@WebServlet(urlPatterns = {"/SPZServlet","/SPZServlet/*"})
@MultipartConfig(location = "/tmp")
//@WebServlet(name = "SPZServlet", urlPatterns = {"/SPZServlet/*"})
/*@ServletSecurity(
 @HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL,
         rolesAllowed = {"user"}))
@DeclareRoles({"admin","user"})*/
public class SPZServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SPZServlet.class.getName());
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("support_JPA");
    private static final Set<User> autheticatedUsers = new HashSet<>();
    private static Path attachDir; 
    private String token;
    private static final String STATES[] ={"Registrovaná","Nová","Probíhá analýza",
        "Změněna", "Specifikována","Probíhá implementace","Přijata ze strany DCB",
        "Uvolněná","Instalovana", "Změna implementace", "Nová implementace", "Probíhá nová analýza",
        "Zamítnuntá", "Potvrzená", "Zrušená", "Fakturovaná"};
    
    @Override
    public void init(){
        Properties props = new Properties();
        Properties propsDerby = new Properties();
        String url,derbyHome=null; 
       
        try {
            InputStream stream = getServletContext().getResourceAsStream("/WEB-INF/properties/spz.properties");
            props.load(stream);
            derbyHome = props.getProperty("DERBY_HOME");
            url = props.getProperty("DBURL");
            LOGGER.log(Level.INFO,String.format("DERBY_HOME=%s, DBURL=%s",derbyHome,url));
        } catch (IOException ex) {
            url = "jdbc:derby://localhost:1527/support";
            LOGGER.log(Level.INFO, "Setting url to default." + url, ex);
        }
        Connection con;
        boolean started = false;
        try {
            con = DriverManager.getConnection(url);
            con.close();
            started=true;
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Server not running. Trying to start the new one.", ex);
        }
        NetworkServerControl serverControl = null;
        if(!started){
            try {
                serverControl = new NetworkServerControl();
                LOGGER.log(Level.INFO,"Starting derby");
                PrintWriter log = new PrintWriter("suppport-derby.log");
                Properties derbyProps = serverControl.getCurrentProperties();
                if(derbyHome!=null){
                    derbyProps.setProperty("derby.system.home", derbyHome);
                }
                serverControl.start(log);
            } catch (Exception ex) {
//                if(serverControl!=null){
//                    try {
//                        serverControl.shutdown();
//                    } catch (Exception ex1) {
//                        LOGGER.log(Level.SEVERE, "Derby cannot be shutdown.", ex1);
//                    }
//                }
                LOGGER.log(Level.SEVERE, "Derby start failed:", ex);
            }
        }
        StringBuilder attachRoot = new StringBuilder(props.getProperty("ATTACH_DIR")).append("/attachments");
        
        LOGGER.log(Level.INFO,"Attach root:",attachRoot);
        attachDir = Paths.get(attachRoot.toString());
        if(!Files.exists(attachDir)){
            try {
                Files.createDirectory(attachDir);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        if(!checkProject()){
            Project project = createProject();
            Configuration config = createConfig();
            addConfig(project,config);
            User projectManager = createUser("Pepa Manager",(short)Roles.PROJECT_MANAGER.ordinal(),"manager");
            User developer = createUser("Tonda Vyvojar",(short)Roles.ANALYST.ordinal(),"analyst");
            User client = createUser("Franta Uzivatel",(short)Roles.CLIENT.ordinal(),"user");
            User admin = createUser("Honza Korinek",(short)Roles.ADMIN.ordinal(),"admin");
            
            createAccess(config,Roles.PROJECT_MANAGER,projectManager);
            createAccess(config,Roles.ANALYST,developer);
            createAccess(config,Roles.CLIENT,client);
            createAccess(config, Roles.ADMIN, admin);
        }
            
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the value.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *  Gets info about the SPZs and forward them to the list.jsp to be displayed.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if(action.compareToIgnoreCase("/getattach")==0){
            String strUserId = request.getParameter("userid");
            if(strUserId == null){
                dispError(request, response, "doGet: request is missing mandatory attribute userid.");
                return;
            }
            Integer userId = Integer.parseInt(strUserId);
            UserManager userMan = new UserJpaController(emf);
            User user = userMan.findUser(userId);
            request.setAttribute("user", userToEntity(user));
            String strAttachmentId = request.getParameter("attachmentId");
            if(strAttachmentId==null){
                dispError(request, response, "doGet: Action getattach is missing mandatory parameter attachmentId");
                return;
            }
            Integer attachId = Integer.parseInt(strAttachmentId);
            byte[] attachmentData = null;
            try{
                attachmentData = getAttachment(attachId);
                String attachMime = getAttachmentMime(attachId);
                if(attachMime!=null && !attachMime.isEmpty()){
                    response.setContentType(attachMime);
                }else{
                    response.setContentType("application/octetstream");
                }
                OutputStream out=response.getOutputStream();
                out.write(attachmentData);
                out.flush();
            }catch(IOException ex){
                LOGGER.log(Level.SEVERE,"Unable to load attachment data: ",ex);
                dispError(request, response, "doGet: Action getattach: unable to load attachment data: "+ex.getMessage());
                return;
            }
            listProjects(request, response);
            return;
        }
        request.setAttribute("error", "Pouzita metoda get misto post.");
        listProjects(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * processes the requests to manipulate the entities
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            Map<String,String[]> params = request.getParameterMap();
            for(String key:params.keySet()){
                System.out.println(key+": "+params.get(key)[0]);
            }
//            System.out.println("Action: " + request.getParameter("action"));
            String action = request.getPathInfo();
            LOGGER.log(Level.INFO,"Performing {0} with parameters {1}",new Object[]{action,request.getParameterNames().toString()});
            //String action = request.getParameter("action");
            switch(action.toLowerCase()){
                case "/login":
                              
                              try{
                                authenticate(request,response);
                              }catch(NoResultException ex){
                                dispError(request, response, "Chyba autentizace: "+ex);
                              }
                              listProjects(request, response);
                            break;
                case "/acceptspzreq":
                            acceptSpz(request,response);
                            break;
                case "/addspz":
                            addSpz(request,response);
                            break;
                case "/listspz":
                            listSpz(request,response);
                            break;
                case "/editspz":
                            editSpz(request,response);
                            break;
                case "/spzsolution":
                            getSpzSolution(request,response);
                            break;
                case "/updatespz":
                            updateSPZ(request,response);
                            break;
                case "/adduser":
                            addUser(request,response);
                            break;
                case "/addnote":
                            addNote(request,response);
                            break;
                case "/edituser":
                            editUser(request,response);
                            break;
                case "/addattachment":
                            addAttachment(request,response);
                            break;
                case "/editattachment":
                            editAttachment(request,response);
                            break;
                case "/addproject":
                            addProject(request,response);
                            break;
                case "/editproject":
                            editProject(request,response);
                            break;
                case "/delete":
                            deleteSpz(request,response);
                            break;
                case "/removestate":
                            deleteSpzState(request,response);
                            break;
                case "/changeanalyst":
                            changeAnalyst(request,response);
                            break;
                case "/changedevel":
                            changeDeveloper(request,response);
                            break;
                case "/acceptsolution":
                            acceptSolution(request,response);
                            break;
                case "/editref":
                            refineSolution(request,response);
                            break;
                case "/acceptimpl":
                            acceptImpl(request,response);
                            break;
                case "/releaseversion":
                            releaseVersion(request,response);
                            break;
                case "/startimpl":
                            startImplementation(request,response);
                            break;
                case "/install":
                            install(request,response);
                            break;
                case "/acceptspz":
                            confirm(request,response);
                            break;
                case "/invoicespz":
                            invoice(request,response);
                            break;
                case "/listconfigurations":
                            listConfigs(request,response);
                            break;
                case "/listprojects":
                            listProjects(request,response);
                            break;
                case "/editroles":
                            editRoles(request,response);
                            break;
                case "/listusers":
                            listUsers(request,response);
                            break;
                case "/addconfig":
                            addConfig(request,response);
                            break;
                case "/deleteconfig":
                            removeConfig(request,response);
                            break;
                case "/editconfig":
                            editConfig(request,response);
                            break;
                case "/addrole":
                            addRole(request,response);
                            break;
                case "/deleterole":
                            deleteRole(request,response);
                            break;
                default:
                    StringBuilder errorMesg = new StringBuilder("Invalid action").append(action).append(". Using list instead.");
                    LOGGER.log(Level.INFO,errorMesg.toString());
                    dispError(request, response, "Neplatna akce: " + action);
                    
            }
            
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "System podpory zakazniku";
    }// </editor-fold>
    /**
     * Performs user authetication 
     * @param request http request containing login information
     * @param response http response
     */
    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameterMap().containsKey("login")){
            String login = request.getParameter("login");
            User user = getUserByLogin(login);
            UserWebEntity userWeb = userToEntity(user);
            request.setAttribute("userid",userWeb.getId());
            SPZServlet.autheticatedUsers.add(user);
            //request.getRequestDispatcher("/SPZServlet/listspz").forward(request, response);
            return;
        }
    }

    /**
     * Adds new spz from request parameters
     * @param request http request containing spz attributes 
     * @param response http response
     * @throws IOException may be thrown while forwarding http request to listSPZ.jsp
     * @throws ServletException may be thrown while forwarding http request to listSPZ.jsp
     */
    private void addSpz(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(checkSpzParams(request.getParameterMap())){
            SpzManager manager= new SpzJpaController(emf);
            EntityManager entMan = emf.createEntityManager();
            EntityTransaction transaction = entMan.getTransaction();
            SpzStateManager stateManager = new SpzStateJpaController(emf);
            SpzStatesManager statesManager = new SpzStatesJpaController(emf);
            UserWebEntity user = requestToUserWebEntity(request);
            if(user==null){
                dispError(request, response, "Nelze prevest uzivatele.");
                return;
            }
            Configuration conf = getConfigurationFromRequest(request);
            Project proj = getProjectFromRequest(request);
            
            if(conf==null|| user==null || proj==null){
                request.setAttribute("error", "Missing either configuration or user id.");
                if(conf!=null){
                    request.setAttribute("config", conf);
                }
                if(user!=null){
                    request.setAttribute("user", user);
                }
                if(proj!=null){
                    request.setAttribute("project", proj);
                }
                dispError(request, response, "Request is missing either configid or userid.");
                return;
            }
            Spz spz = null;
            try {
                spz = requestParamsToSpz(request.getParameterMap());
            } catch (SPZException ex) {
                LOGGER.log(Level.SEVERE, "Chyba pri prevodu parametru na SPZ.", ex);
                request.setAttribute("user", user);
                request.setAttribute("config", conf);
                request.setAttribute("project", proj);
                request.setAttribute("errror", "Chyba pri prevodu parametru na SPZ:" + ex);
                request.getRequestDispatcher("./addSPZ.jsp").forward(request, response);
                return;
            }
            Spzstate state = new Spzstate();
          //  createNewState(state,spz);
            spz.setIssuedate(new GregorianCalendar().getTime());
            //  spz.setShortName(spz.getReqnumber());
            try{
                transaction.begin();

                manager.create(spz);
                transaction.commit();
                transaction.begin();
                spz.setReqnumber(String.format("%05d",spz.getId()));
                manager.edit(spz);
                createNewState(state, spz, user.getLogin());
                
                stateManager.create(state);
                state.setCurrentstate(1);
                stateManager.edit(state);
                Spzstates states = createSpzStates(spz,state);
                statesManager.create(states,entMan);
                transaction.commit();
                try{
                    createSpzConfiguration(spz,conf);
                }catch(SPZException ex){
                    LOGGER.log(Level.SEVERE,"Unable to add configuration - spz relation.",ex);
                    dispError(request, response, "Unable to add configuration - spz relation");
                    return;
                }
                
                Spznote note = null;
                if(request.getParameterMap().containsKey("desc")){
                    String desc = request.getParameter("desc");
                    Short external=0;
                    if(request.getParameterMap().containsKey("external")){
                        String ext = request.getParameter("external");
                        external = Short.parseShort(ext);
                    }
                    note = createSpzNote(spz,desc,external,user);
                    Spzstatenote spzStateNote = new Spzstatenote();
                    SpzStateNoteManager stateNoteMan = new SpzStateNoteJpaController(emf);
                    spzStateNote.setNoteid(note.getId());
                    spzStateNote.setStateid(state.getId());
                    stateNoteMan.create(spzStateNote);
                }
                createSpzIssuer(spz, user);
                List<Spz> spzs = manager.findSpzEntities();
                request.setAttribute("spzs", spzToEntities(spzs));
                request.setAttribute("user", user);
                request.setAttribute("config", conf);
                request.setAttribute("project", proj);
            }catch(Exception ex){
                try {
                    manager.destroy(spz.getId());
                } catch (NonexistentEntityException ex1) {
                    Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
                if(transaction.isActive()){
                    transaction.rollback();
                }
                LOGGER.log(Level.SEVERE,"Chyba pri pridavani SPZ.", ex);
                request.setAttribute("error", "Chyba pri pridavani SPZ.");
                request.setAttribute("user", user);
            }finally{
                entMan.close();
            }
            /*request.setAttribute(null, spz);
            request.getRequestDispatcher("/listSPZ.jsp").forward(request,response);
            */
            //request.setAttribute("invspz", spz);
            //request.setAttribute("action", "add");
            //request.setAttribute("spzs", spzs);
            //request.getRequestDispatcher("/editSPZ.jsp").forward(request, response);
        }else{
            request.setAttribute("action","add");
            Spz spz = null;
            Project project = null;
            Configuration conf = null;
            try{
                spz = requestParamsToSpz(request.getParameterMap());
                conf = getConfigurationFromRequest(request);
                project = getProjectFromRequest(request);
            }catch(SPZException ex){
                request.setAttribute("error", "Chyba pri prevodu parametru na SPZ: "+ex);
            }
            if(spz!=null){
                request.setAttribute("spz", spzToEntity(spz));
                request.setAttribute("error", "Nektera polozka chybi nebo ma neplatnou hodnotu" );
            }
            UserWebEntity user = requestToUserWebEntity(request);
            if(user==null){
                dispError(request, response, "Nelze prevest uzivatele.");
                return;
            }
            request.setAttribute("user", user);
            request.setAttribute("config",conf);
            request.setAttribute("project", project);
            request.getRequestDispatcher("/addSPZ.jsp").forward(request,response);
            return;
        }
        listSpz(request, response);
    }

    private void createSpzIssuer(Spz spz, UserWebEntity user) {
        Spzissuer issuer = new Spzissuer();
        SpzIssuerManager issuerManager = new SpzIssuerJpaController(emf);
        issuer.setSpzid(spz.getId());
        issuer.setUserid((int)user.getId());
        issuerManager.create(issuer);
    }
    
    /**
     * Performs modifications of existing SPZ
     * @param request http request with parameters containing required data
     * @param response http response 
     * @throws ServletException thrown while processing request. For details see 
     *                 exception message 
     * @throws IOException thrown while processing request. For details see 
     *                 exception message 
     */
    private void editSpz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jspName;
        Spz spz = null;
        SpzManager manager = new SpzJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        Integer id = getSpzId(request.getParameterMap());
        Configuration conf = getConfigurationFromRequest(request);
        Project proj = getProjectFromRequest(request);
        User user = getUserByParameter(request);
        UserWebEntity userWeb = userToEntity(user);
        request.setAttribute("user", userWeb);
        request.setAttribute("config", conf);
        request.setAttribute("project", proj);
        spz = manager.findSpz(id);
        if(request.getParameterMap().containsKey("newstate")){
            
            if(request.getParameterMap().containsKey("analyst")){
                setAnalyst(spz, request.getParameter("analyst"));
            }
            if(request.getParameterMap().containsKey("developer")){
                setDeveloper(spz,request.getParameter("developer"));
            }
            changeState(spz,request,response);
            updateSpz(spz,request);
            request.setAttribute("user", userWeb);
            request.setAttribute("config", conf);
            request.setAttribute("project", proj);
            listSpz(request, response);
            return;
        }
        SPZWebEntity spzEnt = spzToEntity(spz);
        request.setAttribute("spz", spzEnt);
        String currentState = getCurrentState(spz);
        switch(currentState.toUpperCase()){
            case "POSTED":
                jspName="/editPost.jsp";
                break;
            case "NEW":
                jspName="/editNew.jsp";
                break;
            case "ANALYSIS":
                jspName="/editAnal.jsp";
                break;
            case "REFINE":
                jspName = "/editRef.jsp";
                break;
            case "SPECIFIED":
                jspName = "/editSpec.jsp";
                break;
            case "IMPLEMENTATION":
                jspName = "/editImpl.jsp";
                break;
            case "DCB_ACCEPTED":
                jspName = "/editDcbAccepted.jsp";
                break;
            case "RELEASED":
                jspName="/editRel.jsp";
                break;
            case "IMPLEMENTATION_REFINE":
                jspName = "/editImplRef.jsp";
                break;
            case "RE_IMPLEMENTATION":
                jspName ="/reImpl.jsp";
                break;
            case "RE_ANALYSIS":
                jspName = "/reAnal.jsp";
                break;
            case "RECLAIMED":
                jspName = "/editRecl.jsp";
                break;
            case "CONFIRMED":
                jspName = "/editConfirmed.jsp";
                break;
            case "CANCELED":
                jspName = "/editCanceled.jsp";
                break;
            case "INVOICED":
                jspName = "/editInvoiced.jsp";
                break;
            case "ACCEPTED":
                jspName="/editAccepted.jsp";
                break;
            case "INSTALLED":
                jspName="/editInstalled.jsp";
                break;
            default:
                dispError(request, response, "Neznamy stav SPZ: "+currentState);
                return;
              
        }
        request.getRequestDispatcher(jspName).forward(request, response);
       // listSpz(request, response);
    }

    /**
     * Adds new user
     * @param request http request containing required data
     * @param response http response 
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!authenticate(request)){
            dispError(request, response, "Unable to authenticate addUser request");
            return;
        }
        Map<String,String[]> params = request.getParameterMap();
        String strId = request.getParameter("userid");
        int id = Integer.parseInt(strId);
        
        UserManager userMan = new UserJpaController(emf);
        if(!params.containsKey("login")){
            User user = userMan.findUser(id);
            UserWebEntity userEnt = userToEntity(user);
            SPZServlet.autheticatedUsers.add(user);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/userAdd.jsp").forward(request,response);
            return;
        }
        if(!checkUserParameters(request)){
            User newUser = requestParamsToUser(request,false);
            SPZServlet.autheticatedUsers.add(newUser);
            User user = userMan.findUser(id);
            UserWebEntity userEnt = userToEntity(newUser);
            UserWebEntity currentUser = userToEntity(user);
            request.setAttribute("error", "Chybi nektere udaje.");
            request.setAttribute("user", currentUser);
            request.setAttribute("newUser", userEnt);
            request.setAttribute("error","Chybi zakladni udaje o uzivateli.");
            request.getRequestDispatcher("/userAdd.jsp").forward(request, response);
            return;
        }
     
                
        User user = requestParamsToUser(request,false);
        user.setClassType((short)-1);
        if(user.getPassword()!=null){
            try {
                MessageDigest  md5 = MessageDigest.getInstance("md5");
                String md5Passwd = new String(md5.digest(user.getPassword().getBytes()));
                user.setPassword(md5Passwd);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Hesla jsou ukladana jako plaintext.", ex);
            }
        }
        userMan.create(user);
        User sUser = userMan.findUser(id);
        List<User> users=userMan.findUserEntities();
        List<UserWebEntity> userEnts = new ArrayList<>();
        for(User userItem:users){
            userEnts.add(userToEntity(userItem));
        }
        request.setAttribute("user",userToEntity(sUser));
        request.setAttribute("users", userEnts);
        request.getRequestDispatcher("/usersList.jsp").forward(request, response);
    }

    /**
     * Modifies user data
     * @param request http request containing required data
     * @param response http response 
     */
    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!authenticate(request)){
            dispError(request,response,"Uzivatele nelze autentizovat.");
        }
        
        UserManager man = new UserJpaController(emf);
        Date tokenSource = new Date();
        String tokenValue = tokenSource.getTime()+"";
        if(!checkUserParameters(request)){

            Integer userId = getUserId(request);
            
            if(userId==null){
                dispError(request, response, "Chybi user id.");
                return;
            }
            
            String strEdId = request.getParameter("editeduserid");
            User edited,user=man.findUser(userId);
            
            SPZServlet.autheticatedUsers.add(user);
            
            if(strEdId!=null){
               int editedUserId = Integer.parseInt(strEdId);
                edited=man.findUser(editedUserId);
                
            }else{
               edited = man.findUser(userId);
              // user=edited;
            }
            SPZServlet.autheticatedUsers.remove(user);
            
            request.setAttribute("editeduser", userToEntity(edited));
            UserWebEntity userWeb = userToEntity(user);
            request.setAttribute("user",userWeb);
            request.setAttribute("token",tokenValue);
            token = tokenValue;
            request.getRequestDispatcher("/useredit.jsp").forward(request, response);
            return;
        }else{
            UserAccessManager accessMan = new UserAccessJpaController(emf);
            String passwd = request.getParameter("newPassword");
            String passwdRe = request.getParameter("retypePasswd");
            User user = requestParamsToUser(request, true);
            String strAuthUserId = request.getParameter("userid");
            User authenticated;
            if(strAuthUserId!=null){
                int authUserId = Integer.parseInt(strAuthUserId);
                authenticated = man.findUser(authUserId);
                
            }else{
                authenticated = user;
            }
            SPZServlet.autheticatedUsers.add(authenticated);
            
            if(passwd.compareTo(passwdRe)!=0){
                UserWebEntity userWeb = userToEntity(user);
                request.setAttribute("user", userWeb);
                
                request.setAttribute("token", tokenValue);
                token = tokenValue;
                request.getRequestDispatcher("/useredit.jsp").forward(request, response);
                return;
            }
            int userId = user.getId();
            if(request.getParameterMap().containsKey("editeduserid")){
                int uid = Integer.parseInt(request.getParameter("editeduserid"));
                userId=uid;
            }
            User user1 = man.findUser(userId);
            user1.setName(user.getName());
            user1.setCompany(user.getCompany());
            user1.setEmail(user.getEmail());
            user1.setFax(user.getFax());
            String passwdTest=user.getPassword();
            if(!passwdTest.isEmpty()){
                user1.setPassword(passwdTest);
            }
            user1.setTel(user.getTel());
            User users[] = {user,user1};
            LOGGER.log(Level.INFO,"Setting {1} to {2}", users);
            try {
                man.edit(user1);
                UserWebEntity userEnt = userToEntity(user1);
                request.setAttribute("user", userEnt);
                listProjects(request, response);
            } catch (Exception ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Unable to edit user: ", ex);
                dispError(request, response, "Unable to edit user: "+ex);
            }
        }
    }

    private Integer getUserId(HttpServletRequest request) throws NumberFormatException {
        Integer userId =null;
        if(request.getParameterMap().containsKey("userid")){
            userId = Integer.parseInt(request.getParameter("userid"));
        }
        return userId;
    }

    /**
     * Adds new attachment to an existing SPZ
     * @param request http request containing required data about attachment
     * @param response http response
     */
    private void addAttachment(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Edits an existing attachment
     * @param request http request containing id of attachment to be modified as well as data
     *                that should be modified
     * @param response http response 
     */
    private void editAttachment(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Adds new project.
     * @param request http request containing new project data 
     * @param response http response
     */
    private void addProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectManager projMan = new ProjectJpaController(emf);
        if(containsProjectInfo(request)){
            Project project = requestToProject(request);
            projMan.create(project);
            listProjects(request, response);
        }else{
            Integer userId = getUserId(request);
            if(userId == null){
                dispError(request, response, "Add project: Missing user ID");
                return;
            }
            UserManager  userMan = new UserJpaController(emf);
            User user = userMan.findUser(userId);
            if(user==null){
                dispError(request,response,"Add project: No user with id "+userId+".");
                return;
            }
            UserWebEntity userWeb = userToEntity(user);
            request.setAttribute("user", userWeb);
            request.getRequestDispatcher("/addProject.jsp").forward(request, response);
        }
    }

    /**
     * Edits an existing project
     * @param request http request containing id of the project to be modified as well as 
     *                the modified information
     * @param response http response
     */
    private void editProject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectManager projMan = new ProjectJpaController(emf);
//        User user = requestParamsToUser(request, false);
        String userIdStr = request.getParameter("userid");
        Integer userId = Integer.parseInt(userIdStr);
        UserManager userMan = new UserJpaController(emf);
        User user = userMan.findUser(userId);
        
            
        request.setAttribute("userid", user.getId());
        if(checkProjectParams(request)){
            String strProjId = request.getParameter("projectid");
            if(strProjId==null || strProjId.isEmpty()){
                dispError(request, response, "Edit Project: Parameter project id is missing or null: "+strProjId);
                return;
            }
            Integer projectId = Integer.parseInt(strProjId);
            Project project = projMan.findProject(projectId);
            try {
                project.setDescription(request.getParameter("description"));
                project.setName(request.getParameter("name"));
                projMan.edit(project);
                listProjects(request, response);
            } catch (Exception ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Error editing project "+project+": ", ex);
                dispError(request, response, "Error editing project " + project+ ": "+ex.getMessage());
                return;
            }
        }else{
            ProjectConfigurationManager projConfMan = new ProjectConfigurationJpaController(emf);
            request.setAttribute("user", user);
            String projectIdStr = request.getParameter("projectid");
            Integer projectId = Integer.parseInt(projectIdStr);
            Project project = projMan.findProject(projectId);
            List<Configuration> configs = projConfMan.getProjectConfigurations(project.getId());
            ProjectWebEntity webProject = projectToEntity(project,configs);
            request.setAttribute("project", webProject);
            request.getRequestDispatcher("/editProject.jsp").forward(request, response);
            return ;
        }
    }

    /**
     * Lists all SPZs in the system
     * @param request http request
     * @param response http response
     * @throws ServletException exception that may happend (for more details see 
     *                          exception message 
     * @throws IOException exception that may happend (for more details see 
     *                          exception message 
     */
    private void listSpz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SpzManager spzManager = new SpzJpaController(emf);
        List<Spz> spzs = spzManager.findSpzEntities();
        List<SPZWebEntity> entities = spzToEntities(spzs);
        UserWebEntity user = requestToUserWebEntity(request);
        if(user==null){
            dispError(request, response, "listSpz: Nelze ziskat uzivatele.");
            return;
        }
        Project project = getProjectFromRequest(request);
        Configuration conf = getConfigurationFromRequest(request);
                
        if(request.getParameter("spzid")!=null){
            SPZWebEntity spz = spzToEntity(getSpzByParameter(request));
            request.setAttribute("spz", spz);
        }
        request.setAttribute("user", user);
        request.setAttribute("project", project);
        request.setAttribute("config", conf);
        LOGGER.log(Level.INFO,String.format("userid: %d",user.getId()));
        request.setAttribute("spzs", entities);
        request.getRequestDispatcher("/listSPZ.jsp").forward(request, response);
    }

    /**
     * Checks if the parameter map contains all information needed by SPZ entity
     * @param parameterMap map of information with SPZ attributes 
     * @return true if all required data are contained and are in a correct form. 
     */
    private boolean checkSpzParams(Map<String, String[]> parameterMap) {
        /*if(!checkReqNumber(parameterMap)){
            return false;
        }*/

        if(!checkReqType(parameterMap)){
            return false;
        }
        if(!checkShortName(parameterMap)){
            return false;
        }
       /* if(!checkIssueDate(parameterMap)){
            return false;
        }
        */
        if(!checkContactPerson(parameterMap)){
            return false;
        }
        
        /*if(!checkShortName(parameterMap)){
            return false;
        }*/
        
        if(!checkRequestDescription(parameterMap)){
            return false;
        }
        /*
        if(!checkImplementationAcceptanceDate(parameterMap)){
            return false;
        }   
        */
        return true;
    }

    /**
     * Transforms http request parameters to a SPZ
     * @param parameterMap http request parameters 
     * @return new SPZ corresponding to the parameters if they are correct
     *         null otherwise.
     */
    private Spz requestParamsToSpz(Map<String, String[]> parameterMap) throws SPZException {
        if(!checkSpzParams(parameterMap)){
            return null;
        }
        Spz spz = new Spz();
        /*if(!parameterMap.containsKey("reqnumber")){
            return null;
        }*/
       // spz.setShortname(parameterMap.get("shortname")[0]);
        
        if(parameterMap.containsKey("shortname")){
            spz.setShortName(parameterMap.get("shortname")[0]);
        }
        String requestDescription;
        String strDescription = parameterMap.get("requestdescription")[0];
        if(strDescription.startsWith("<"))
        {
            HTMLTransformer transformer = new HTMLTransformer();
        //try {
            transformer.convert(strDescription);
            requestDescription = transformer.getResult();
        }else{
            requestDescription = strDescription;
        }
        
//        } catch (SPZException ex) {
//            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Chyba v popisu/zadani SPZ", ex);
//            requestDescription = strDescription;
//            request
//        }
        spz.setRequestdescription(requestDescription);
        spz.setContactperson(parameterMap.get("contactperson")[0]);
        //if(parameterMap.containsKey("shortname")) spz.setShortName(parameterMap.get("shortname")[0]);
        Date issueDate = null;
        if(parameterMap.containsKey("issuedate")){
            String issueDateString = parameterMap.get("issuedate")[0];
            issueDate = stringToDate(issueDateString);
        }else{
            Calendar cal = new GregorianCalendar();
            
            issueDate = cal.getTime();
        }
        /*if (issueDate == null) {
            return null;
        }*/
        spz.setIssuedate(issueDate);
        if(checkImplementationAcceptanceDate(parameterMap)){
        
            String implDateStr = parameterMap.get("implementationacceptancedate")[0];
            Date implDate = stringToDate(implDateStr);
            spz.setImplementationacceptdate(implDate);
        }
        long ts = (new GregorianCalendar()).getTimeInMillis();
        spz.setTs(BigInteger.valueOf(ts));
        String strPriority = parameterMap.get("priority")[0];
        String reqType=parameterMap.get("reqtype")[0];
        spz.setRequesttype(reqType);
        short priority = Short.parseShort(strPriority);
        spz.setPriority(priority);
        String strCategory = parameterMap.get("category")[0];
        short category = Short.parseShort(strCategory);
        spz.setCategory(category);
        spz.setRequesttype(parameterMap.get("reqtype")[0]);
        return spz;
    }

    /**
     * Helper method transforming string representing a date into the 
     * java.util.Date object.
     * @param strVal value to be transformed.
     * @return null if the parameter is in incorrect form, corresponding date
     *         otherwise.
     */
    private Date stringToDate(String strVal) {
        DateFormat formater = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("cs"));
        Date value = null;
        if(strVal == null){
            Calendar cal = new GregorianCalendar();
            value = cal.getTime();
            return value;
        }
        try {
            value = formater.parse(strVal);
        } catch (ParseException ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, String.format("Invalid date %s format.",strVal), ex);
            return null;
        }
        return value;
    }

    /**
     * Checks the parameterMap for attribute reqnumber
     * @param parameterMap request parameter map
     * @return true if reqnumber is present and not null, false otherwise.
     */
    private boolean checkReqNumber(Map<String, String[]> parameterMap) {
        boolean result = parameterMap.containsKey("reqnumber");
        if(!result)return false;
        String reqNumber = parameterMap.get("reqnumber")[0];
        return !reqNumber.isEmpty();
    }

    /**
     * Checks the parameter map for attribute issuedate
     * @param parameterMap request parameter map
     * @return true if issuedate is present and not null, false otherwise.
     */
    private boolean checkIssueDate(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("issuedate") && !parameterMap.get("issuedate")[0].isEmpty();
    }

    /**
     * Checks the paramterMap for attribute contactperson
     * @param parameterMap request parameter map.
     * @return true if the contactperson is present and not null, false otherwise
     */
    private boolean checkContactPerson(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("contactperson") && !parameterMap.get("contactperson")[0].isEmpty();
    }

    /**
     * Checks the parameterMap for attribute shortname
     * @param parameterMap request parameter map
     * @return true if the shortname is present and not null, false otherwise.
     */
    private boolean checkShortName(Map<String, String[]> parameterMap) {
        boolean result = parameterMap.containsKey("shortname");
        if(!result){
            return false;
        }
        String shortName = parameterMap.get("shortname")[0];
        return !shortName.isEmpty();
    }

    /**
     * Checks the parameterMap for attribute requestdescription
     * @param parameterMap http request parameterMap
     * @return true if the attribute is present and not null, false otherwise.
     */
    private boolean checkRequestDescription(Map<String, String[]> parameterMap) {
        boolean result = parameterMap.containsKey("requestdescription");
        result = result && !(parameterMap.get("requestdescription")[0].isEmpty());
        return result;
    }

    /**
     * Checks the parameterMap for attribute implementationacceptancedate
     * @param parameterMap http request parameterMap
     * @return true if the attribute is present and not null, false otherwise.
     */
    private boolean checkImplementationAcceptanceDate(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("implementationacceptancedate") && !parameterMap.get("implementationacceptancedate")[0].isEmpty();
    }

    /**
     * Checks the parameterMap for attribute reqtype
     * @param parameterMap http request parameterMap
     * @return true if the attribute is present and not null, false otherwise.
     */
    private boolean checkReqType(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("reqtype") && !parameterMap.get("reqtype")[0].isEmpty();
    }

    /**
     * Transforms the SPZ list into a list of SPZWebEntity used for presentation
     * in a JSP
     * @param spzs list of SPZ to be transformed
     * @return list of SPZWebEntities.
     */
    private List<SPZWebEntity> spzToEntities(List<Spz> spzs) {
        List<SPZWebEntity> entities = new ArrayList<>();
        
        for(Spz spz:spzs){
            SPZWebEntity entity = spzToEntity(spz);
            entities.add(entity);
        }
        return entities;
    }

    /**
     * Transforms particular SPZ into SPZWebEntity instance.
     * @param spz SPZ to be transformed
     * @return Transformed SPZ
     */
    private SPZWebEntity spzToEntity(Spz spz) {
        SpzIssuerManager issuerManager = new SpzIssuerJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        SpzStatesManager statesManager = new SpzStatesJpaController(emf);
        SpzAnalystManager analystManager = new SpzAnalystJpaController(emf);
        ConfigurationSpzManager confSpzManager = new ConfigurationSpzJpaController(emf);
        ProjectConfigurationManager projConfManager = new ProjectConfigurationJpaController(emf);
        ConfigurationManager confMan = new ConfigurationJpaController(emf);
        UserManager userManger = new UserJpaController(emf);
        List<SpzStateWebEntity> history=null;
        
        int analystId = analystManager.findSpzanalystUserId(spz.getId());
        SPZWebEntity entity;
        entity = new SPZWebEntity();
        if(analystId>-1){
            User user = userManger.findUser(analystId);
            if(user!=null){
                entity.setAnalyst(user.getName());
            }
        }
        if(spz.getDeveloperId()>0){
            User user = userManger.findUser(spz.getDeveloperId());
            if(user!=null){
                entity.setDeveloper(user.getName());
            }
        }
        entity.setId(spz.getId());
        Integer confId = confSpzManager.getSpzConfiguration(spz.getId());
        if(confId!=null){
            entity.setConfig(confMan.findConfiguration(confId));
            if(entity.getConfig()!=null){
                ProjectManager projManager = new ProjectJpaController(emf);
                Integer projId = projConfManager.getProjectIdFor(confId);
                entity.setProject(projManager.findProject(projId));
            }
        }
        entity.setShortName(spz.getShortName());
        entity.setReqNumber(spz.getReqnumber());
        entity.setIssueDate(spz.getIssuedate());
        entity.setContactPerson(spz.getContactperson());
        entity.setReqNumber(spz.getReqnumber());
        entity.setRequestType(spz.getRequesttype());
        String solution = stateManager.getSolution(spz);
        String revDesc = stateManager.getRevisedDescription(spz);
        if(solution!=null){
            entity.setSolution(solution);
        }
        if(revDesc!=null){
            entity.setRevised(revDesc);
        }
        Spzstate current = statesManager.getCurrentState(spz);
        User user = null;
        List<Spzstate> statesHistory = statesManager.findSpzstates(spz);
        history = spzStatesToSpzStateWebEntities(statesHistory);
        if(current!=null){
            user = userManger.findUserByLogin(current.getIssuerLogin());
            entity.setSpzState(current.getCode());
            solution = statesManager.findSpzSolution(spz.getId());
            
            if(solution==null){
                solution=findSolution(history);
            }
            entity.setSolution(solution);
            entity.setRevised(current.getRevisedrequestdescription());
            Double mandays = spz.getManDays();
            if(mandays!=null)
                entity.setWorkLoadReal(spz.getManDays());
            else entity.setWorkLoadReal(0.0);
            mandays = spz.getAssumedManDays();
            if(mandays!=null){
                entity.setWorkLoadEstimation(mandays);
            }else{
                entity.setWorkLoadEstimation(0);
            }
            entity.setRelNotes(current.getReleasenotes());
        
        }
        entity.setIssuer((user!=null?user.getName():"Nenastaven"));
        
        entity.setHistory(history);
        
        Integer spzIssuerId = issuerManager.findSpzIssuerIdBySpzId(spz.getId());
        if(spzIssuerId > -1){
            String issuerName = userManger.findUser(spzIssuerId).getName();
            entity.setIssuer(issuerName);
        }
        
        entity.setRequestDescription(spz.getRequestdescription());
        entity.setKind(spz.getRequesttype());
        entity.setDate(getLastChangeDate(spz.getId(),stateManager));
        entity.setCategory(spz.getCategory());
        entity.setPriority(spz.getPriority());
        return entity;
    }

    /**
     * Returns the last change date for SPZ with id
     * @param id SPZ id
     * @param manager SpzStateManager used to access Spzstate table in db.
     * @return Required date.
     */
    private Date getLastChangeDate(Integer id,SpzStateManager manager) {
        return manager.getLastChange(id);
        
    }

    /**
     * Returns the value of attribute id from http request parameterMap
     * @param parameterMap http request parameter map containing require info
     * @return Value of id if the map contains it and it is correct null 
     * otherwise
     */
    private Integer getSpzId(Map<String, String[]> parameterMap) {
        if(!parameterMap.containsKey("spzid")){
           return null;
        }
        String strId=parameterMap.get("spzid")[0];
        if(strId == null){
            return null;
        }        

        Integer id;
        try{
            id = Integer.parseInt(strId);
        }catch(NumberFormatException nfe){
            return null;
        }
        return id;
    }

    /**
     * Returns current state of the spz
     * @param spz the spz we are looking for its present state
     * @return String representation of the spz state.
     */
    private String getCurrentState(Spz spz) {
        SpzStatesManager manager = new SpzStatesJpaController(emf);
        Spzstate state = manager.getCurrentState(spz);
        return state.getCode();
    }

    /**
     * Creates state for a new spz
     * @param state The state to be returned
     * @param spz SPZ we are creating the new state 
     */
    private void createNewState(Spzstate state, Spz spz, String issuerLogin) {
        state.setIdate(new GregorianCalendar().getTime());
        state.setIssuerLogin(issuerLogin);
        state.setCode(SpzStates.POSTED.toString());
        state.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
    }

    /**
     * Create Spzstates entity for the given spz and its state
     * @param spz SPZ we are creating Spzstates for
     * @param state the state of the spz
     * @return Spzstates instance.
     */
    private Spzstates createSpzStates(Spz spz, Spzstate state) {
        Spzstates states= new Spzstates();
        states.setSpzid(spz.getId());
        states.setStateid(state.getId());
        return states;
    }

    /**
     * Converts List of spz states into the list of SpzStateWebEntity
     * @param spzStates List of Spz states to be transformed
     * @return Corresponding SpzStateWebEntity list
     */
    private List<SpzStateWebEntity> spzStatesToSpzStateWebEntities(List<Spzstate> spzStates) {
        List<SpzStateWebEntity> result = new ArrayList<>();
        for(Spzstate state:spzStates){
            SpzStateWebEntity entity = spzStateToSpzWebEntity(state);
            result.add(entity);
        }
        
        return result;
    }

    /**
     * Transforms spz state into the SpzStateWebEntity instance.
     * @param state Spz state to be converted
     * @return Converted Spz state
     */
    private SpzStateWebEntity spzStateToSpzWebEntity(Spzstate state) {
        ResourceBundle states = ResourceBundle.getBundle("changeState");
        UserManager userMan = new UserJpaController(emf);
        SpzStateWebEntity entity = new SpzStateWebEntity();
        entity.setId(state.getId());
        entity.setClassType(state.getClasstype());
        entity.setCode(states.getString(state.getCode()));
//entity.setCode(STATES[SpzStates.valueOf(state.getCode()).ordinal()]);
        entity.setCurrentState(Long.valueOf(state.getCurrentstate()));
        entity.setIssueDate(new Date(state.getTs().longValue()));
        entity.setReleaseNotes(state.getReleasenotes());
        entity.setRevisedRequestDescription(state.getRevisedrequestdescription());
        entity.setSolutionDescription(state.getSolutiondescription());
        entity.setIssuer(userMan.findUserByLogin(state.getIssuerLogin()));
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        List<Spznote> notes = stateManager.getStateNotes(state.getId());
        entity.setNotes(spzStateNotesToWebEntity(notes));
        return entity;
    }

    /**
     * Updates the SPZ according the parameters of the http request
     * @param request http request with required data
     * @param response http response
     * @throws IOException may be thrown while forwarding to the listSPZ.jsp 
     *                     after some exception has occurred in editing.
     * @throws ServletException may be thrown while forwarding to the listSPZ.jsp 
     *                     after some exception has occurred in editing.
     */
    private void updateSPZ(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
        Spz spz = null;
        try{
            spz = requestParamsToSpz(request.getParameterMap());
        }catch(SPZException ex){
            request.setAttribute("error", "Zadane parametry nelze prevest na SPZ:" + ex);
            request.getRequestDispatcher("/listSPZ.jsp").forward(request, response);
            return;
        }
        SpzManager manager = new SpzJpaController(emf);
        try {
            manager.edit(spz);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Error editing SPZ: ",ex);
            request.setAttribute("error", "Chyba pri uprave SPZ. Vice viz log.");
            List<SPZWebEntity> spzs = spzToEntities(manager.findSpzEntities());
            request.setAttribute("spzs", spzs);
            request.getRequestDispatcher("/listSPZ.jsp").forward(request, response);
            
        }
    }

    /**
     * Add files with notes to SPZ.
     * @param request http request parameters with required data
     * @param response http response
     *
     * @throws IOException may be thrown while forwarding to the listSPZ.jsp 
     *                     
     */
    private void addNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Map<String,String[]> params = request.getParameterMap();
        String  strId = request.getParameter("spzid");
        int id = Integer.parseInt(strId);
        addNote(request,response,id);
        
    }

    /**
     * Used to display errors on loading SPZ attachments
     * @param request http request with parameters for editPost.jsp
     * @param fileName file name that has not been loaded
     * @param ex exception that has occurred
     * @param response http response
     * @throws IOException  may be thrown while forwarding to the editPost.jsp 
     * @throws ServletException may be thrown while forwarding to the editPost.jsp 
     */
    private void displayError(HttpServletRequest request, String fileName, final java.lang.Exception ex, HttpServletResponse response) throws IOException, ServletException {
        setEditAttributes(request);
        request.setAttribute("err", String.format("Error loading file %s: %s", fileName, ex));
        request.getRequestDispatcher("/editPost.jsp").forward(request, response);
    }

    private void setEditAttributes(HttpServletRequest request) {
        if(request.getParameterMap().containsKey("desc")){
            request.setAttribute("desc",request.getParameter("desc"));
        }
        if(request.getParameterMap().containsKey("soubor1")){
            request.setAttribute("soubor1", request.getParameter("soubor1"));
        }
        if(request.getParameterMap().containsKey("soubor2")){
            request.setAttribute("soubor2", request.getParameter("soubor2"));
        }
        if(request.getParameterMap().containsKey("soubor3")){
            request.setAttribute("soubor3", request.getParameter("soubor3"));
        }
        if(request.getParameterMap().containsKey("ext")){
            request.setAttribute("ext", request.getParameter("ext"));
        }
    }

    /**
     * Uploads attachment in the correct folder.
     * @param request http request with required data
     * @param response http response
     * @param manager AttachmentManager used to create new Attachment entity in 
     *                the database.
     * @param fileParam the file name of the attachment
     * @param in input stream to load atachment from.
     * @throws IOException may be thrown while displaying the error
     */
    private void uploadFile(HttpServletRequest request, HttpServletResponse response, AttachmentManager manager,String fileParam, InputStream in) throws IOException/*, ServletException*/ {
       // String fileName = new StringBuilder("attachments/").append(fileParam).toString();
        try{
            uploadFile(request,response,fileParam,in);
        }catch(IOException ex){
            LOGGER.log(Level.SEVERE,"Error saving attachment: ",ex);
            /*request.setAttribute("err", "Error loading file "+fileParam+": "+ex);
            request.getRequestDispatcher("/editPost.jsp").forward(request, response);*/
            return;
        }
        /*Attachment file = requestToAttachment(request);
        file.setContent(fileParam);
        file.setType(Files.probeContentType(Paths.get(fileParam)));
        manager.create(file);*/
    }
    
    /**
     * Uploads the SPZ attachment according the parameters
     * @param request http request 
     * @param response http response
     * @param fileName the file name where to store the attachment
     * @param in input stream used to load the attachment
     * @throws IOException IOException that occurred during the attachment 
     * upload
     */
    private void uploadFile(HttpServletRequest request, HttpServletResponse response,String fileName, InputStream in) throws IOException/*, ServletException*/ {
            
            OutputStream out = null;
            int count = 0;
            File output = new File(fileName);
            if(!output.exists()){
                output.createNewFile();
            }
            out = new FileOutputStream(fileName);
            
            try{
                do{
                    byte[] data = new byte[1024];
                    count = in.read(data);
                    if(count!=0){
                        out.write(data);
                    }
                }while(count == 1024);
            }finally{
                try{
                    out.close();
                }catch(IOException ex){
                    LOGGER.log(Level.SEVERE,String.format("Unable to close output file %s.",fileName));
                }

            }
            
    }
    
    
    /**
     * Converts the http request parameters into the Attachment instance.
     * @param request http request
     * @return Attachment instance corresponding the http request parameters.
     */
    private Attachment requestToAttachment(HttpServletRequest request) {
        Attachment attachment = new Attachment();
        attachment.setDate(new GregorianCalendar().getTime());
        attachment.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
        
        return attachment;
    }

    /**
     * Deletes the spz state according the request parameters. Used to remove
     * last spz state.
     * @param request http request with required data
     * @param response http response
     * @throws ServletException may be thrown while forwarding to the either 
     *                          editPost.jsp or editDeleted.jsp 
     * @throws IOException may be thrown while forwarding to the either 
     *                          editPost.jsp or editDeleted.jsp 
     */
    private void deleteSpzState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> params = request.getParameterMap();
        if(!params.containsKey("spzid")){
            request.setAttribute("error", "Missing spz id to be able to delete last spz state.");
            request.getRequestDispatcher("/editPost.jsp").forward(request, response);
            return;

        }
        int spzId = Integer.parseInt(params.get("spzid")[0]);
        SpzManager manager = new SpzJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        SpzStatesManager statesManager = new SpzStatesJpaController(emf);
        Spz spz = manager.findSpz(spzId);
        UserWebEntity user = requestToUserWebEntity(request);
        if(user==null){
            dispError(request, response, "Nelze ziskat uzivatele.");
        }
        List<Spzstate> spzStates = statesManager.findSpzstates(spz);
        Collections.sort(spzStates,new Comparator<Spzstate>() {

            @Override
            public int compare(Spzstate o1, Spzstate o2) {
                return o2.getTs().compareTo(o1.getTs());
            }
        });
        Spzstate previous = null;
        if(spzStates.size()>1){
            Spzstates current = statesManager.getCurrentSpzstatesEntity(spz);
            try{
                statesManager.destroy(current.getId());
                stateManager.destroy(current.getStateid());
                previous = spzStates.get(1);
                previous.setCurrentstate(1);
                previous.setIdate(new GregorianCalendar().getTime());
                listSpz(request, response);
                return;
            }catch(NonexistentEntityException nee){
                String errorMsg = String.format("Unable to remove Spzstates entity with id: %d.",current.getId());
                LOGGER.log(Level.SEVERE, errorMsg);
                dispError(request, response, errorMsg);
                return;
            }
            
            
            
        }
        Spzstates newState = new Spzstates();
        newState.setSpzid(spzId);
        if(previous!=null){
            previous.setCurrentstate(1);
        }else{
            editSpz(request, response);
            return;
//            previous = new Spzstate();
//            previous.setAssumedmandays(0.0);
//            previous.setCurrentstate(SpzStates.CANCELED.ordinal());
//            previous.setIdate(new GregorianCalendar().getTime());
//            previous.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
        }
        stateManager.create(previous);
        newState.setStateid(previous.getId());
        statesManager.create(newState);
        SPZWebEntity entity = spzToEntity(spz);
        SpzStateWebEntity prev = spzStateToSpzWebEntity(previous);
        request.setAttribute("spz", entity);
        request.setAttribute("state", prev);
        if(prev.getCode().compareToIgnoreCase("canceled")!=0){
            
            listSpz(request, response);
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/editDeleted.jsp").forward(request, response);
        //listSpz(request, response);
    }

    /**
     * Transforms the spz into the SpzState CANCELED
     * @param request http request with required parameters
     * @param response http response
     */
    private void deleteSpz(HttpServletRequest request, HttpServletResponse response) {
        SpzManager spzManager = new SpzJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        SpzStatesManager statesManager = new SpzStatesJpaController(emf);
        
        String strSpzId = request.getParameter("spzid");
        Integer spzId = null;
        try{
            spzId = Integer.parseInt(strSpzId);
        }catch(NumberFormatException nfe){
            LOGGER.log(Level.SEVERE,"SPZ id is not a valid number.",nfe);
            request.setAttribute("error", "ID spz neni cislo.");
            try {
                editSpz(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Spz spz = spzManager.findSpz(spzId);
        Spzstate currentState = stateManager.getCurrentState(spz);
        currentState.setCode(SpzStates.CANCELED.toString());
        try {
            stateManager.edit(currentState);
        } catch (Exception ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Unable to remove current state flag.", ex);
            request.setAttribute("error", "Nelze zmenit priznak aktualniho stavu.");
            try {
                UserWebEntity user = requestToUserWebEntity(request);
                if(user == null){
                    dispError(request, response, "Nelze ziskat info o uzivateli.");
                }
                listSpz(request, response);
            } catch (ServletException | IOException ex1) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
        }
        Spzstate spzState = new Spzstate();
        spzState.setClasstype(currentState.getClasstype());
        spzState.setCode(currentState.getCode());
        spzState.setCurrentstate(1);
        spzState.setIdate(currentState.getIdate());
        spzState.setIssuerLogin(currentState.getIssuerLogin());
        spzState.setReleasenotes(currentState.getReleasenotes());
        spzState.setRevisedrequestdescription(currentState.getRevisedrequestdescription());
        spzState.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
        stateManager.create(spzState);
        
        Spzstates states = new Spzstates();
        states.setSpzid(spzId);
        states.setStateid(spzState.getId());
        statesManager.create(states);
        try {
            UserWebEntity user = requestToUserWebEntity(request);
            if(user==null){
                dispError(request, response, "Nelze ziskat info o uzivateli.");
                return;
            }
            listSpz(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Moves the spz into a new SPZ state according request parameters
     * @param spz spz to change its state
     * @param request http request with required parameters
     * @param response http response
     */
    private void changeState(Spz spz, HttpServletRequest request, HttpServletResponse response) /*throws ServletException, IOException */{
        String stringState = request.getParameter("newstate");
        SpzStates state = SpzStates.valueOf(stringState);
        Spzstate currentState;
        
        Spzstate newState = new Spzstate();
        newState.setCurrentstate(1);
        newState.setCode(state.toString());
        if(request.getParameterMap().containsKey("issuer")){
            newState.setIssuerLogin(request.getParameter("issuer"));
        }
        if(request.getParameterMap().containsKey("releasenotes")){
            newState.setReleasenotes(request.getParameter("releasenotes"));
        }
        double realWorkload = 0.0;
        if(request.getParameterMap().containsKey("mandays")){
            String strWorkLoad = request.getParameter("mandays");
            realWorkload += Double.parseDouble(strWorkLoad);
        }
        if(request.getParameterMap().containsKey("outofdevel")){
            String strOutOfDevel = request.getParameter("outofdevel");
            realWorkload += Double.parseDouble(strOutOfDevel);
            spz.setManDays(realWorkload);
            
        }
        if(request.getParameterMap().containsKey("estimatedworkload")){
            String estWork = request.getParameter("estimatedworkload");
            double estimation = Double.parseDouble(estWork);
            spz.setAssumedManDays(estimation);
        }
        User user = getUserByParameter(request);
        newState.setIdate(new GregorianCalendar().getTime());
        newState.setTs(BigInteger.valueOf(new Date().getTime()));
        newState.setIssuerLogin(user.getLogin());
        SpzManager spzManager = new SpzJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        SpzStatesManager statesManager = new SpzStatesJpaController(emf);
        Spzstates newStates = new Spzstates();
        newStates.setSpzid(spz.getId());
        currentState = stateManager.getCurrentState(spz);
        if(currentState!=null){
            currentState.setCurrentstate(0);
        }
        EntityManager entMan = stateManager.getEntityManager();
        
        try{
            entMan.getTransaction().begin();
            stateManager.create(newState);
            /*newStates.setSpzid(spz.getId());
            newStates.setStateid(newState.getId());*/
            statesManager.create(newStates);
            spzManager.edit(spz);
            entMan.getTransaction().commit();
        }catch(Exception ex){
            entMan.getTransaction().rollback();
            LOGGER.log(Level.SEVERE,"Error changing the state on SPZ.",ex);
            request.setAttribute("error", "Chyba při zmene stavu.");
            return;
        }finally{
            entMan.close();
        }
        newStates.setStateid(newState.getId());
        try {
            if(currentState!=null){
                stateManager.edit(currentState);
            }
            statesManager.edit(newStates);
            spzManager.edit(spz);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error setting the state id in SpzStates.", ex);
            request.setAttribute("error", "Chyba pri zmene stavu. Nelze nastavit id stavu.");
        }
        return;
    }

    /**
     * Used to get SPZ solution from user and store it into database.
     * @param request http request
     * @param response http response
     */
    private void getSpzSolution(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int spzId = Integer.parseInt(request.getParameter("spzid"));
        SpzManager manager = new SpzJpaController(emf);
        Spz spz = manager.findSpz(spzId);
        changeState(spz, request, response);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        Spzstate spzState = stateManager.getCurrentState(spz);
        String revisedDesc = request.getParameter("revisedsolutiondescription");
        if(revisedDesc!=null){
            spzState.setRevisedrequestdescription(revisedDesc);
        }
        String solDesc = request.getParameter("solutiondescription");
        if(solDesc!=null){
            spzState.setSolutiondescription(solDesc);
        }
        try{
            if(request.getParameterMap().containsKey("estimatedworkload")){
                 Double estWorkLoad = Double.parseDouble(request.getParameter("estimatedworkload"));
                 spz.setAssumedManDays(estWorkLoad);
            }
        }catch(NumberFormatException ex){
            LOGGER.log(Level.INFO,"Nezadana nebo neplatna hodnota odhadu pracnosti.");
        }
            
        Spznote note = createSpznote(request);
        if(note!=null){
            Spzstatenote stateNote = new Spzstatenote();
            stateNote.setStateid(spzState.getId());
            stateNote.setNoteid(note.getId());
            SpzStateNoteManager stateNoteMan = new SpzStateNoteJpaController(emf);
            stateNoteMan.create(stateNote);
        }
        //Spz spz = requestParamsToSpz(request.getParameterMap());
        SPZWebEntity entity = spzToEntity(spz);
        UserWebEntity user = requestToUserWebEntity(request);
        Project proj = getProjectFromRequest(request);
        Configuration conf = getConfigurationFromRequest(request);
        request.setAttribute("spz", entity);
        request.setAttribute("user", user);
        request.setAttribute("project", proj);
        request.setAttribute("config", conf);
        request.getRequestDispatcher("/setSolution.jsp").forward(request, response);
    }

    private Spznote createSpznote(HttpServletRequest request) {
        Spznote stateNote = new Spznote();
        String note = request.getParameter("desc");
        String ext = request.getParameter("ext");
        
        if(note==null){
            return null;
        } 
        stateNote.setNotetext(note);
        Calendar cal = new GregorianCalendar();
        stateNote.setNotedate(cal.getTime());
        stateNote.setTs(BigInteger.valueOf(cal.getTimeInMillis()));
        
        if(ext!=null){
            short val = 1;
            stateNote.setExternalnote(val);
        }else{
            short val = 0;
            stateNote.setExternalnote(val);
        }
        SpzNoteManager noteManager = new SpzNoteJpaController(emf);
        noteManager.create(stateNote);
        return stateNote;
    }

    private List<SpzNoteEntity> spzStateNotesToWebEntity(List<Spznote> notes) {
        List<SpzNoteEntity> noteEntities = new ArrayList<>();
        for(Spznote note:notes){
            SpzNoteEntity entity = spzStateNoteToWebEntity(note);
            noteEntities.add(entity);
        }
        return noteEntities;
    }

    private SpzNoteEntity spzStateNoteToWebEntity(Spznote note){
        SpzNoteEntity entity = new SpzNoteEntity();
        entity.setNoteDate(note.getNotedate());
        entity.setNoteText(note.getNotetext());
        entity.setExternal(note.getExternalnote());
        entity.setNoteIssuer(note.getIssuer());
        AttachmentNoteManager manager= new AttachmentNoteJpaController(emf);
        List<Attachment> attachments = manager.getAttachmentsForNote(note.getId());
        List<AttachmentEntity> atachmentsEntities = attachmentsToWebEntity(attachments);
        entity.setAttachments(atachmentsEntities);
        
        return entity;
    }

    private List<AttachmentEntity> attachmentsToWebEntity(List<Attachment> attachments) {
        List<AttachmentEntity> entities = new ArrayList<>();
        if(attachments !=null){
            for(Attachment attachment:attachments){
                AttachmentEntity entity = attachmentToWebEntity(attachment);
                entities.add(entity);
            }
        }else{
            LOGGER.log(Level.INFO,"attachments are null");
        }
        return entities;
    }

    private AttachmentEntity attachmentToWebEntity(Attachment attachment) {
        AttachmentEntity entity = new AttachmentEntity();
        entity.setId(attachment.getId());
        entity.setContent(attachment.getContent());
        entity.setDate(attachment.getDate());
        entity.setLocation(attachment.getLocation());
        entity.setType(attachment.getType());
        return entity;
    }

    private void addNote(HttpServletRequest request, HttpServletResponse response, Integer spzId) throws ServletException, IOException {
        AttachmentManager manager = new AttachmentJpaController(emf);
        String fileName = null;
        InputStream in = null;
        Part part1 = null,part2 = null, part3 = null;
        Date date = new GregorianCalendar().getTime();
        Attachment attach1 = null, attach2 = null, attach3 = null;
        String header = request.getHeader("Content-type");
        if(header.contains("multipart")){
            try {
                part1 = request.getPart("soubor1");
                part2 = request.getPart("soubor2");
                part3 = request.getPart("soubor3");
            } catch (IOException ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(part1 != null && part1.getSubmittedFileName()!=null && !part1.getSubmittedFileName().isEmpty()){
            try{
                in = part1.getInputStream();
                fileName = new StringBuilder(attachDir.toString()).append("/").append(part1.getSubmittedFileName()).toString();
                try{
                    uploadFile(request, response, manager, fileName, in);
                    attach1 = new Attachment();
                    attach1.setLocation(fileName);
                    attach1.setContent(part1.getSubmittedFileName());
                    attach1.setType(Files.probeContentType(new File(fileName).toPath()));
                    attach1.setDate(date);
                }catch(IOException ex){
                    displayError(request, fileName, ex, response);
                    return;
                }
            }catch(IOException ex){
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null,ex);
            }
        }
        
        if(part2!=null && part2.getSubmittedFileName()!=null && !part2.getSubmittedFileName().isEmpty()){
            fileName = new StringBuilder(attachDir.toString()).append("/").append(part2.getSubmittedFileName()).toString();
            
            try{
                in = part2.getInputStream();
                uploadFile(request,response,manager,fileName,in);
                attach2 = new Attachment();
                attach2.setLocation(fileName);
                attach2.setContent(part2.getSubmittedFileName());
                attach2.setType(Files.probeContentType(new File(fileName).toPath()));
                attach2.setDate(date);
            }catch(IOException ex){
                displayError(request, fileName, ex, response);
                return;
            }
        }
        
        if(part3!=null && part3.getSubmittedFileName()!=null && !part3.getSubmittedFileName().isEmpty()){
            
            try{
                in = part3.getInputStream();
                fileName = new StringBuilder(attachDir.toString()).append("/").append(part3.getSubmittedFileName()).toString();
                uploadFile(request,response,manager,fileName,in);
                attach3 = new Attachment();
                attach3.setLocation(fileName);
                attach3.setContent(part3.getSubmittedFileName());
                attach3.setType(Files.probeContentType(new File(fileName).toPath()));
                attach3.setDate(date);
            }catch(IOException ex){
                displayError(request, fileName, ex, response);
                return;
            }
            
        }
        SpzManager spzManager = new SpzJpaController(emf);
        SpzStateNoteManager stateNoteManager = new SpzStateNoteJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        SpzNoteManager noteManager = new SpzNoteJpaController(emf);
        Project proj = getProjectFromRequest(request);
        Configuration conf = getConfigurationFromRequest(request);
        
        UserWebEntity user = requestToUserWebEntity(request);
        
        Spz spz = spzManager.findSpz(spzId);
        Spzstate state = stateManager.getCurrentState(spz);
        Spzstatenote stateNote = new Spzstatenote();
        Spznote note = new Spznote();
        String noteText = request.getParameter("desc");
        String externalStr = request.getParameter("external");
        HTMLTransformer transformer = new HTMLTransformer();
        String noteLower;
        String jsp;
        try {
            transformer.convert(noteText);
            noteLower = transformer.getResult();
        } catch (SPZException ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Chyba v poznamce: ",ex);
            request.setAttribute("error", "Chyba v poznamce: "+ex);
            if(request.getParameterMap().containsKey("jsp")){
                jsp=request.getParameter("jsp");
            }else{
                jsp="./listSpz.jsp";
            }
            request.setAttribute("user", user);
            request.setAttribute("config", conf);
            request.setAttribute("project", proj);
            request.getRequestDispatcher(jsp).forward(request,response);
            return;
        }
        LOGGER.log(Level.INFO,"external ",externalStr);
        short external = (short)(externalStr!=null&&(externalStr.compareToIgnoreCase("on")==0||externalStr.compareTo("1")==0)?1:0);
        note.setExternalnote(external);
        if(noteText==null){
            request.setAttribute("error", "Missing note description.");
            request.setAttribute("user", user);
            request.setAttribute("project", proj);
            request.setAttribute("config", conf);
            //request.getRequestDispatcher("/editPost.jsp").forward(request, response);
            editSpz(request, response);
            return;
        }
        note.setNotetext(noteText);
        note.setNotedate(new GregorianCalendar().getTime());
        note.setExternalnote(external);
        note.setIssuer(user.getName());
        note.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
        noteManager.create(note);
        addAttachments(note,attach1,attach2,attach3);
        stateNote.setNoteid(note.getId());
        stateNote.setStateid(state.getId());
        stateNoteManager.create(stateNote);
        SpzIssuerManager issuerManager = new SpzIssuerJpaController(emf);
        Spzissuer issuer = new Spzissuer();
        issuer.setSpzid(spzId);
        issuer.setUserid((int)user.getId());
        
        jsp = (String)request.getAttribute("jsp");
        if(jsp!=null && jsp.contains("list")){
            listSpz(request, response);
            return;
        }
        request.setAttribute("spz", spzToEntity(spz));
        request.setAttribute("user", user);
        request.setAttribute("project", proj);
        request.setAttribute("config", conf);
        request.getRequestDispatcher("/editPost.jsp").forward(request, response);
        
    }

    private void changeAnalyst(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RolesManager rolesMan = new RolesJpaController(emf);
        UserAccessManager accessMan = new UserAccessJpaController(emf);
        SpzAnalystManager analystManager = new SpzAnalystJpaController(emf);
        UserManager userManager = new UserJpaController(emf);
        SpzManager spzManager = new SpzJpaController(emf);
        //SpzAnalystManager spzAnalystManager = new SpzAnalystJpaController(emf);
        int spzId = getSpzId(request.getParameterMap());
        Spz spz = spzManager.findSpz(spzId);
        UserWebEntity user = requestToUserWebEntity(request);
        Project proj = getProjectFromRequest(request);
        Configuration conf = getConfigurationFromRequest(request);
        SPZWebEntity spzWeb = spzToEntity(spz);
        
        if(!request.getParameterMap().containsKey("analyst")){
            List<UserWebEntity> analysts=getAnalysts();
            try {
                
                request.setAttribute("analysts", analysts);
                request.setAttribute("user", user);
                request.setAttribute("spz", spzWeb);
                request.setAttribute("project", proj);
                request.setAttribute("config", conf);
                request.getRequestDispatcher("/editAnalyst.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                try {
                    request.setAttribute("error", "Nelze zmenit analytika (vice viz log).");
                    LOGGER.log(Level.SEVERE,"Chyba pri prechodu na editAnalyst.jsp:",ex);
                    listSpz(request, response);
                } catch (ServletException | IOException ex1) {
                    Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }else{
            setAnalyst(spz,request.getParameter("analyst"));
            List<Spz> spzs = spzManager.findSpzEntities();
            List<SPZWebEntity> spzEntities = spzToEntities(spzs);
            request.setAttribute("spz", spzEntities);
            request.setAttribute("user", user);
             request.setAttribute("project", proj);
            request.setAttribute("config", conf);
            listSpz(request,response);
        }
    }

    private UserWebEntity requestToUserWebEntity(HttpServletRequest request) {
        UserWebEntity user = new UserWebEntity();
        UserManager userMan = new UserJpaController(emf);
        UserAccessManager accMan = new UserAccessJpaController(emf);
        User userEnt;
        if(request.getParameterMap().containsKey("login")){
            String login = request.getParameter("login");
            userEnt = userMan.findUserByLogin(login);
            if(userEnt==null){
                return null;
            }
        }else{
            String strId = request.getParameter("userid");
            int uId = -1;
            if(strId==null || strId.isEmpty()){
                return null;
            }
            uId = Integer.parseInt(strId);
            userEnt = userMan.findUser(uId);
        }
        user.setId(userEnt.getId());
        user.setLogin(userEnt.getLogin());
        user.setName(userEnt.getName());
        List<Useraccess> roles = accMan.findUseraccessEntities(userEnt.getId());
        if(!roles.isEmpty()){
            switch(roles.get(0).getRole().toLowerCase()){
                case "client":
                    user.setRole(Roles.CLIENT.ordinal());
                    break;
                case "developer":;
                case "analyst":
                    user.setRole(Roles.ANALYST.ordinal());
                    break;
                case "admin":
                    user.setRole(Roles.ADMIN.ordinal());
                default:
                    user.setRole(Roles.PROJECT_MANAGER.ordinal());
            }
            
        }else{
            user.setRole(Roles.CLIENT.ordinal());
        }

        
        return user;
    }

    private void acceptSolution(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> parameterMap = request.getParameterMap();
        if(!parameterMap.containsKey("spzid")){
            request.setAttribute("error", "Chybi parametr s spzid.");
            listSpz(request, response);
            return;
        }
        
        Spz spz = getSpzByParameter(request);
        
        if(!parameterMap.containsKey("userid")){
            request.setAttribute("error", "Chybi parametr s userid.");
            listSpz(request, response);
        }
        User user = getUserByParameter(request);
        
        SPZWebEntity spzEnt = spzToEntity(spz);
        UserWebEntity userEnt = userToEntity(user);
        Project proj = getProjectFromRequest(request);
        Configuration conf = getConfigurationFromRequest(request);
        
        request.setAttribute("spz", spzEnt);
        request.setAttribute("user", userEnt);
        request.setAttribute("config", conf);
        request.setAttribute("project", proj);
        request.getRequestDispatcher("/acceptSol.jsp").forward(request, response);
    }

    private void acceptSpz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> parameterMap = request.getParameterMap();
        if(!parameterMap.containsKey("spzid")){
            request.setAttribute("error", "Chybi parametr s spzid.");
            listSpz(request, response);
            return;
        }
        
        Spz spz = getSpzByParameter(request);
        
        if(!parameterMap.containsKey("userid")){
            request.setAttribute("error", "Chybi parametr s userid.");
            listSpz(request, response);
        }
        User user = getUserByParameter(request);
        
        SPZWebEntity spzEnt = spzToEntity(spz);
        UserWebEntity userEnt = userToEntity(user);
        Project proj = getProjectFromRequest(request);
        Configuration conf = getConfigurationFromRequest(request);
        request.setAttribute("config", conf);
        request.setAttribute("project", proj);
        request.setAttribute("user", userEnt);
        request.setAttribute("spz", spzEnt);
        request.setAttribute("newState", "ANALYSIS");
        request.setAttribute("analysts", getAnalysts());
        request.getRequestDispatcher("/setAnalyst.jsp").forward(request, response);
        
    }
    private Spz getSpzByParameter(HttpServletRequest request){
        String spzIdStr = request.getParameter("spzid");
        if(spzIdStr==null){
            return null;
        }
        int spzId=-1;
        try{
            spzId = Integer.parseInt(spzIdStr);
        }catch(NumberFormatException nfe){
            LOGGER.log(Level.SEVERE,"SPZ doesn't contain valid id: ",nfe);
            return null;
        }
        SpzManager spzMan = new SpzJpaController(emf);
        Spz spz = spzMan.findSpz(spzId);
        return spz;
    }

    private User getUserByParameter(HttpServletRequest request) {
        String userIdStr = request.getParameter("userid");
        UserManager userMan = new UserJpaController(emf);
        User user;
        try{
            int userId = Integer.parseInt(userIdStr);
            user = userMan.findUser(userId);
        }catch(NumberFormatException nfe){
            user = userMan.findUserByLogin(request.getParameter("login"));
        }
        
        return user;
    }

    private UserWebEntity userToEntity(User user) {
        UserWebEntity entity = new UserWebEntity();
        
        entity.setId(user.getId());
        entity.setLogin(user.getLogin());
        entity.setName(user.getName());
        entity.setCompany(user.getCompany());
        entity.setEmail(user.getEmail());
        entity.setFax(user.getFax());
        entity.setPhone(user.getTel());
        Short classType = user.getClassType();
        if(classType!=null){
            entity.setClassType(classType);
        }else{
            entity.setClassType(-1);
        }
        
        List<Useraccess> roles = getUserRoles(user.getId());
        if(!roles.isEmpty()){
            if(user.getLogin().equalsIgnoreCase("admin")){
                entity.setRole(Roles.ADMIN.ordinal());
                return entity;
            }
            switch(roles.get(0).getRole().toLowerCase()){
                case "client":entity.setRole(Roles.CLIENT.ordinal());
                              break;
                case "analyst":
                case "developer":entity.setRole(Roles.ANALYST.ordinal());
                              break;
                case "admin":
                    entity.setRole(Roles.ADMIN.ordinal());
                default:entity.setRole(Roles.PROJECT_MANAGER.ordinal());
            }
        }
        return entity;    
        
    }

    private List<Useraccess> getUserRoles(Integer userId) {
        UserAccessManager accessMan = new UserAccessJpaController(emf);
        return accessMan.findUseraccessEntities(userId);
    }

    private User getUserByLogin(String login) {
        UserManager userMan = new UserJpaController(emf);
        User user = userMan.findUserByLogin(login);
        return user;
    }

    private void refineSolution(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        gotoJspWithSpzUserChange(request, response,"/editRef.jsp");
    }

    private void acceptImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        gotoJspWithSpzUserChange(request, response, "/editAccepted.jsp");
        
    }

    private void releaseVersion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("newState", "RELEASED");
        gotoJspWithSpzUserChange(request, response, "/changeState.jsp");
    }
    
    private void startImplementation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Spz spz = getSpzByParameter(request);
        request.setAttribute("developers",getProjectDevelopers(spz));
        request.setAttribute("newState","IMPLEMENTATION");
        gotoJspWithSpzUserChange(request, response, "/changeState.jsp");
    }
    
    private void install(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Spz spz=getSpzByParameter(request);
        String newState = request.getParameter("newState");
        request.setAttribute("newState", "INSTALLED");
        request.setAttribute("state", "RELEASED");
        gotoJspWithSpzUserChange(request, response, "/changeStateInstall.jsp");
    }
    
    private void confirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Spz spz = getSpzByParameter(request);
        request.setAttribute("newState", "CONFIRMED");
        request.setAttribute("state", "INSTALLED");
        gotoJspWithSpzUserChange(request, response, "/changeState.jsp");
    }
    
    private void invoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Spz spz=getSpzByParameter(request);
        request.setAttribute("newState", "INVOICED");
        gotoJspWithSpzUserChange(request, response, "/changeState.jsp");
    }    
    
    private void gotoJspWithSpzUserChange(HttpServletRequest request,HttpServletResponse response,String jsp) throws ServletException, IOException{
        Spz spz = getSpzByParameter(request);
        User user = getUserByParameter(request);
        SPZWebEntity spzWeb = spzToEntity(spz);
        UserWebEntity userWeb = userToEntity(user);
        request.setAttribute("spz", spzWeb);
        request.setAttribute("user", userWeb);
        Project proj = getProjectFromRequest(request);
        Configuration conf =getConfigurationFromRequest(request);
        
        request.setAttribute("change", true);
        request.setAttribute("project", proj);
        request.setAttribute("config", conf);
        request.getRequestDispatcher(jsp).forward(request, response);
    }

    private List<User> getProjectDevelopers(Spz spz) {
        UserAccessManager manager = new UserAccessJpaController(emf);
        return manager.findDevelopers();
    }

    /*private double getSpzManDays() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    private void dispError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("error",message);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }

    private List<UserWebEntity> getAnalysts() {
        UserAccessManager accessManager = new UserAccessJpaController(emf);
        List<User> developers = accessManager.findDevelopers();
        List<UserWebEntity> users = new ArrayList<>();
        for(User user:developers){
            users.add(userToEntity(user));
        }
        return users;
    }

    private void setAnalyst(Spz spz, String analystId) {
        int analystIdInt = Integer.parseInt(analystId);
        SpzAnalystManager analystManager = new SpzAnalystJpaController(emf);
        Spzanalyst spzAnalyst = new Spzanalyst();
        spzAnalyst.setSpzid(spz.getId());
        spzAnalyst.setUserid(analystIdInt);
        analystManager.create(spzAnalyst);
    }

    private void listConfigs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConfigurationSpzManager confSpzMan = new ConfigurationSpzJpaController(emf);
        ProjectConfigurationManager projConfMan = new ProjectConfigurationJpaController(emf);
        Project project = getProjectFromRequest(request);
        Spz spz = getSpzByParameter(request);
        User user = getUserByParameter(request);
        List<Configuration> configs;
        SPZWebEntity spzEnt = null;
        
        if(spz!=null){
            Integer confId = confSpzMan.getSpzConfiguration(spz.getId());
            Integer projId = projConfMan.getProjectIdFor(confId);
            configs = projConfMan.getProjectConfigurations(projId);
            spzEnt = spzToEntity(spz);
        }else{
            UserAccessManager accessMan = new UserAccessJpaController(emf);
            configs = accessMan.getConfigsForUser(user.getId());
        }
        UserWebEntity userEnt = userToEntity(user);
        request.setAttribute("user", userEnt);
        request.setAttribute("configs", configs);
        request.setAttribute("project", project);
        if(spzEnt!=null){
            request.setAttribute("spz", spzEnt);
        }
        request.getRequestDispatcher("/listConfigs.jsp").forward(request, response);
        return;
    }

    private void listProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getUserByParameter(request);
        UserAccessManager accessMan = new UserAccessJpaController(emf);
        ProjectConfigurationManager projConfMan = new ProjectConfigurationJpaController(emf);
        ProjectManager projMan = new ProjectJpaController(emf);
        Set<Project> projects = null;
        if(user.getClassType()!=Roles.ADMIN.ordinal()){
            List<Configuration> configs = accessMan.getConfigsForUser(user.getId());
            projects = new HashSet<>();
            for(Configuration config:configs){
                Integer projId = projConfMan.getProjectIdFor(config.getId());
                projects.add(projMan.findProject(projId));
            }
        }else{
            projects = new HashSet<>(projMan.findProjectEntities());
        }
        request.setAttribute("projects", projects);
        request.setAttribute("user", userToEntity(user));
        request.getRequestDispatcher("/listProjects.jsp").forward(request, response);
    }

    private Project getProjectFromRequest(HttpServletRequest request) {
        String projIdStr = request.getParameter("projectid");
        if(projIdStr==null){
            return null;
        }
        int projId = Integer.parseInt(projIdStr);
        ProjectManager projectMan = new ProjectJpaController(emf);
        return projectMan.findProject(projId);
    }

    private Configuration getConfigurationFromRequest(HttpServletRequest request) {
        String confIdStr = request.getParameter("configid");
        if(confIdStr==null){
            return null;
        }
        int confId = Integer.parseInt(confIdStr);
        ConfigurationManager confMan = new ConfigurationJpaController(emf);
        return confMan.findConfiguration(confId);
    }

    private void createSpzConfiguration(Spz spz, Configuration conf) throws SPZException {
        ConfigurationSpzManager confSpzMan = new ConfigurationSpzJpaController(emf);
        Configurationspz confSpz = new Configurationspz();
        confSpz.setConfigurationid(conf.getId());
        confSpz.setSpzid(spz.getId());
        try {
            confSpzMan.create(confSpz);
        } catch (Exception ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Error adding Configuration Spz entity.", ex);
            throw new SPZException("Unable to create spz-configuration relation entity.",ex);
        }
    }

    private void updateSpz(Spz spz, HttpServletRequest request) {
        SpzStateManager stateMan = new SpzStateJpaController(emf);
        SpzManager spzMan = new SpzJpaController(emf);
        Map<String,String[]> params = request.getParameterMap();
        Spzstate curr = stateMan.getCurrentState(spz);
        boolean change = false;
        if(params.containsKey("solutiondescription")){
            curr.setSolutiondescription(request.getParameter("solutiondescription"));
            change=true;
        }
        if(params.containsKey("revisedrequest")){
            curr.setRevisedrequestdescription(request.getParameter("revisedrequest"));
            change = true;
        }
        if(params.containsKey("estimatedworkload")){
            Double estWork=Double.parseDouble(request.getParameter("estimatedworkload"));
            spz.setAssumedManDays(estWork);
            try {
                spzMan.edit(spz);
            } catch (Exception ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Error updating SPZ: ", ex);
            }
        }
        if(change){
            try {
                stateMan.edit(curr);
            } catch (Exception ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Error updating current state.", ex);
            }
        }
    }

    private void changeDeveloper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RolesManager rolesMan = new RolesJpaController(emf);
        UserAccessManager accessMan = new UserAccessJpaController(emf);
        SpzAnalystManager analystManager = new SpzAnalystJpaController(emf);
        UserManager userManager = new UserJpaController(emf);
        SpzManager spzManager = new SpzJpaController(emf);
        //SpzAnalystManager spzAnalystManager = new SpzAnalystJpaController(emf);
        int spzId = getSpzId(request.getParameterMap());
        Spz spz = spzManager.findSpz(spzId);
        UserWebEntity user = requestToUserWebEntity(request);
        if(user==null){
            dispError(request, response, "Neni takovy uzivatel.");
            return;
        }
        Project proj = getProjectFromRequest(request);
        Configuration conf = getConfigurationFromRequest(request);
        SPZWebEntity spzWeb = spzToEntity(spz);
        
        if(!request.getParameterMap().containsKey("developer")){
            List<UserWebEntity> developers=getDevelopers();
            try {
                
                request.setAttribute("developers", developers);
                request.setAttribute("user", user);
                request.setAttribute("spz", spzWeb);
                request.setAttribute("project", proj);
                request.setAttribute("config", conf);
                request.getRequestDispatcher("/editDeveloper.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                try {
                    request.setAttribute("error", "Nelze zmenit analytika (vice viz log).");
                    LOGGER.log(Level.SEVERE,"Chyba pri prechodu na editAnalyst.jsp:",ex);
                    listSpz(request, response);
                } catch (ServletException | IOException ex1) {
                    Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }else{
            setAnalyst(spz,request.getParameter("developer"));
            List<Spz> spzs = spzManager.findSpzEntities();
            List<SPZWebEntity> spzEntities = spzToEntities(spzs);
            request.setAttribute("spz", spzEntities);
            request.setAttribute("user", user);
             request.setAttribute("project", proj);
            request.setAttribute("config", conf);
            listSpz(request,response);
        }
    }

    private List<UserWebEntity> getDevelopers() {
        return getAnalysts();
    }

    private void setDeveloper(Spz spz, String parameter) {
        UserManager userManager = new UserJpaController(emf);
        User user = userManager.findUserByLogin(parameter);
        spz.setDeveloperId(user.getId());
    }

    private String findSolution(List<SpzStateWebEntity> history) {
        String solution = null;
        for(SpzStateWebEntity webEnt:history){
                solution=webEnt.getSolutionDescription();
        }
        return solution;
    }

    private Spznote createSpzNote(Spz spz, String description, Short external, UserWebEntity user) {
        Spznote spzNote = new Spznote();
        SpzNoteManager spzNoteMan = new SpzNoteJpaController(emf);
        spzNote.setNotetext(description);
        spzNote.setExternalnote(external);
        spzNote.setIssuer(user.getName());
        
        Calendar cal = new GregorianCalendar();
        spzNote.setNotedate(cal.getTime());
        spzNote.setTs(BigInteger.valueOf(cal.getTimeInMillis()));
        spzNoteMan.create(spzNote);
        return spzNote;
    }

    /* Pomocne metody pro 1. etapu*/
    
    /**
     * Kontrola existence potrebnych entit v databazi
     */
    private boolean checkProject() {
        ProjectManager manager = new ProjectJpaController(emf);
        List<Project> projects = manager.findProjectEntities();
        return !projects.isEmpty();
    }

    /**
     * Vytvoreni testovaciho projektu
     * 
     * @return testovaci projekt 
     */
    private Project createProject() {
        ProjectManager manager = new ProjectJpaController(emf);
        Project project = new Project();
        project.setDescription("Testovaci projekt");
        project.setName("bt");
        Calendar cal = new GregorianCalendar();
        project.setTs(BigInteger.valueOf(cal.getTimeInMillis()));
        manager.create(project);
        return project;
    }

    /**
     * Vytvoreni testovaci konfigurace
     * @return testovaci konfigurace
     */
    private Configuration createConfig() {
        ConfigurationManager manager = new ConfigurationJpaController(emf);
        Configuration config = new Configuration();
        config.setCode("cfg1");
        config.setDescription("Testovaci konfigurace");
        Calendar cal = new GregorianCalendar();
        config.setTs(BigInteger.valueOf(cal.getTimeInMillis()));
        manager.create(config);
        return config;
    }

    /**
     * Pridani testovaci konfigurace do testovaciho projektu
     * @param project projekt
     * @param config konfigurace
     */
    private void addConfig(Project project, Configuration config) {
        ProjectConfigurationManager manager = new ProjectConfigurationJpaController(emf);
        Projectconfiguration projectConf = new Projectconfiguration();
        projectConf.setConfigurationid(config.getId());
        projectConf.setProjectid(project.getId());
        manager.create(projectConf);
    }

    /**
     * Vytvoreni uzivatel
     * @param name Cele jmeno
     * @param role role
     * @param login uzivatelske jmeno
     * @return novy uzivatel s danymi parametry
     */
    private User createUser(String name,short role, String login) {
        UserManager manager = new  UserJpaController(emf);
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setClassType(role);
        manager.create(user);
        return user;
        
    }

    /**
     * Vytvori pristup uzivatele k dane konfiguraci
     * @param config konfigurace
     * @param role role v projektu
     * @param user uzivatel
     */
    private void createAccess(Configuration config, Roles role, User user) {
        UserAccessManager manager = new UserAccessJpaController(emf);
        Useraccess access = new Useraccess();
        access.setConfigurationid(config.getId());
        access.setRole(role.toString());
        access.setUserid(user.getId());
        Calendar cal = new GregorianCalendar();
        access.setTs(BigInteger.valueOf(cal.getTimeInMillis()));
        manager.create(access);
    }

    private boolean authenticate(HttpServletRequest request) {
        boolean md5Avail = true;
        User user = null;
        String strId = request.getParameter("userid");
        if(strId==null){
            return false;
        }
        String tokenValue=request.getParameter("token");
        if(tokenValue!=null && tokenValue.equals(token)){
            token = null;
            return true;
        }
        UserManager userMan = new UserJpaController(emf);
        MessageDigest md5 =  null;
        try {
        
            md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.log(Level.SEVERE,"Chybi MD5. Pouzivam otevrene heslo.");
                md5Avail= false;
            }  
        int id = Integer.parseInt(strId);
        user = userMan.findUser(id);
        if(SPZServlet.autheticatedUsers.contains(user)){
                return true;
        }
        String login = request.getParameter("login");
        String oldPasswd = request.getParameter("password");
//        if(user.getPassword()==null){
//            return true;
//        }
        String passwd = user.getPassword();
        String emptyPasswd = "";
        byte[] emptyPasswdHash = md5.digest(emptyPasswd.getBytes());
        if(passwd==null || passwd.isEmpty()|| md5.digest(emptyPasswd.getBytes()).equals(passwd)){
            return true;
        }
        if(oldPasswd==null || oldPasswd.isEmpty()){
            return false;
        }
        String entered=new String(md5.digest(oldPasswd.getBytes()));
        String pass=user.getPassword();
        boolean auth;
        if( md5Avail){
            auth=entered.equals(pass);
        }else{
            auth = user.getPassword().equals(oldPasswd);
        }
        if(auth){
            SPZServlet.autheticatedUsers.add(user);
        }
        return auth;
    }

    private boolean checkUserParameters(HttpServletRequest request) {
        return request.getParameter("login")!=null && 
               request.getParameter("name")!=null;/* &&
               request.getParameter("password")!=null;*/
    }

//    private Integer getUserId(HttpServletRequest request) {
//        String userId = request.getParameter("userid");
//        if(userId == null)
//            return null;
//        return Integer.parseInt(userId);
//    }

    private User requestParamsToUser(HttpServletRequest request, boolean edited) {
        User user = new User();
        
        String val = request.getParameter((edited?"editeduserid":"userid"));
        if(val==null){
            if(edited){
                val= request.getParameter("userid");
            }else{
                return null;
            }
            
        }
        user.setId(Integer.parseInt(val));
        val = request.getParameter("login");
        if(val==null){
            return null;
        }
        user.setLogin(val);
        val = request.getParameter("name");
        if(val==null){
            return null;
        }
        user.setName(val);
        MessageDigest md5 = null;
        try {
            md5=MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        val = request.getParameter("newPassword");
        if(val!=null){
            String val1=request.getParameter("retypePasswd");
            if(val1!=null && val.compareTo(val1)==0){
                if(md5!=null){
                    user.setPassword(new String(md5.digest(val.getBytes())));
                }else{
                    user.setPassword(val);
                }
            }
        }
        val = request.getParameter("company");
        if(val!=null){
            user.setCompany(val);
        }
        val = request.getParameter("phone");
        if(val!=null){
            user.setTel(val);
        }
        val = request.getParameter("fax");
        if(val!=null){
            user.setFax(val);
        }
        val = request.getParameter("email");
        if(val!=null){
            user.setEmail(val);
        }
        
        val = request.getParameter("superuser");
        if(val!=null && val.equalsIgnoreCase("yes")){
            user.setClassType((short)Roles.ADMIN.ordinal());
        }
        else{
            val = request.getParameter("role_user");
            if(val!=null && val.equalsIgnoreCase("yes")){
                user.setClassType((short)Roles.CLIENT.ordinal());
            }
        }
        
        return user;
    }

    private void editRoles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!authenticate(request)){
            dispError(request,response,"Uzivatele nelze autentizovat.");
        }
        UserManager man = new UserJpaController(emf);
        User user = null;
        String editedId = request.getParameter("editeduserid");
        if(request.getParameterMap().containsKey("userid")){
            Integer id = Integer.parseInt(request.getParameter("userid"));
            user = man.findUser(id);
        }
        User editedUser=null;
        if(request.getParameterMap().containsKey("editeduserid")){
            Integer id = Integer.parseInt(request.getParameter("editeduserid"));
            editedUser = man.findUser(id);
        }
        
        if(editedUser == null){
            editedUser = user;
        }
        
        String strSUser = request.getParameter("superuser");
        
        if(strSUser!=null){
            if(strSUser.compareToIgnoreCase("yes")==0){
                editedUser.setClassType((short)Roles.ADMIN.ordinal());
            }else{
                String strUser = request.getParameter("role_user");
                if(strUser!=null && strUser.compareToIgnoreCase("yes")==0){
                    editedUser.setClassType((short)Roles.CLIENT.ordinal());
                }
            } 
            try {
                man.edit(editedUser);
            } catch (Exception ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Pokus o zmenu tridy neexistujiciho uzivatele.", ex);
            }
        }
        if(user!=null){
            SPZServlet.autheticatedUsers.add(user);
        }
        listProjects(request, response);
        
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!authenticate(request)){
            dispError(request, response, "User not authenticated.");
            return;
        }
        User user = getUserByParameter(request);
        if(user==null){
            dispError(request, response, "No authenticated user.");
            return;
        }
        if(user.getClassType()==null||user.getClassType()!=Roles.ADMIN.ordinal()){
            dispError(request, response, "Uzivatel nema opravneni pro tuto akci.");
            return;
        }
        
        UserManager userMan = new UserJpaController(emf);
        List<User> users =userMan.findUserEntities();
        List<UserWebEntity> userEntities = new ArrayList<>();
        for(User userEnt:users){
            userEntities.add(userToEntity(userEnt));
        }
        if(request.getParameterMap().containsKey("userid")){
            Integer currentId = Integer.parseInt(request.getParameter("userid"));
            User current = userMan.findUser(currentId);
            SPZServlet.autheticatedUsers.add(current);
            UserWebEntity curEnt = userToEntity(user);
            request.setAttribute("users", userEntities);
            request.setAttribute("user", curEnt);
            request.getRequestDispatcher("/usersList.jsp").forward(request, response);
            return;
        }
        dispError(request, response, "User id parameter missing.");
    }

    private boolean containsProjectInfo(HttpServletRequest request) {
        return request.getParameter("description")!=null && request.getParameter("name")!=null;
    }

    private Project requestToProject(HttpServletRequest request) {
        Project project = new Project();
        String desc = request.getParameter("description");
        String name = request.getParameter("name");
        if(desc==null || name==null){
            LOGGER.log(Level.SEVERE,"Missing description or name");
            return null;
        }
        project.setDescription(desc);
        project.setName(name);
        Calendar cal = new GregorianCalendar();
        long ts = cal.getTimeInMillis();
        project.setTs(BigInteger.valueOf(ts));
        return project;
    }

    private boolean checkProjectParams(HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();
        return params.containsKey("projectid") && params.containsKey("name") 
            && params.containsKey("description");
    }

    private ProjectWebEntity projectToEntity(Project project, List<Configuration> configs) {
        ProjectWebEntity proj = new ProjectWebEntity();
        proj.setId(project.getId());
        proj.setName(project.getName());
        proj.setDescription(project.getDescription());
        List<ConfigurationWebEntity> confs = new ArrayList<>();
        if(configs!=null && configs.size()>0){
        
            for(Configuration conf:configs){
                ConfigurationWebEntity webConf = configurationToEntity(conf);
                confs.add(webConf);
            }
        }
        proj.setConfigs(confs);
        
        return proj;
    }

    private ConfigurationWebEntity configurationToEntity(Configuration conf) {
        ConfigurationWebEntity webEnt = new ConfigurationWebEntity();
        webEnt.setId(conf.getId());
        webEnt.setName(conf.getCode());
        webEnt.setDescription(conf.getDescription());
        return webEnt;
    }

    private void addConfig(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConfigurationManager confMan = new ConfigurationJpaController(emf);
        ProjectConfigurationManager projConfMan = new ProjectConfigurationJpaController(emf);
        if(!authenticate(request)){
            dispError(request, response, "User is not authorised.");
            return;
        }
        if(checkConfiguration(request)){
            
            String strProjectId = request.getParameter("projectid");
            if(strProjectId==null || strProjectId.isEmpty()){
                dispError(request, response, "/addConfig: Missing project id or project id is empty.");
                return;
            }
            int projectId = Integer.parseInt(strProjectId);
            Configuration config = requestToConfiguration(request);
            confMan.create(config);
            if(config.getId()!=null){
                Projectconfiguration projConf = new Projectconfiguration();
                projConf.setConfigurationid(config.getId());
                projConf.setProjectid(projectId);
                projConfMan.create(projConf);
            }else{
                dispError(request, response, "/addConfig: Unable to create ProjectConfiguration.");
                return;
            }
            listProjects(request, response);
        }
        
    }

    private Configuration requestToConfiguration(HttpServletRequest request) {
        Configuration config = new Configuration();
        config.setCode(request.getParameter("confName"));
        config.setDescription(request.getParameter("confDesc"));
        return config;
    }

    private boolean checkConfiguration(HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();
        return params.containsKey("confName") && params.containsKey("confDesc");
    }

    private void removeConfig(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(!authenticate(request)){
                dispError(request, response, "Delete configuration: User not authorized.");
                return;
            }   if(!checkConfigurationToRemove(request)){
                dispError(request, response, "Delete configuration: Missing some required parameters to remove configuration");
                return;
            }   String strProjId = request.getParameter("projectid");
            String strConfProjId = request.getParameter("cfgid");
            String strUserId = request.getParameter("userid");
            Integer projId = Integer.parseInt(strProjId),
                    confId = Integer.parseInt(strConfProjId),
                    userId = Integer.parseInt(strUserId);
            UserManager userMan = new UserJpaController(emf);
            ProjectManager projMan = new ProjectJpaController(emf);
            Project project = projMan.findProject(projId);
            ProjectConfigurationManager projConfMan = new ProjectConfigurationJpaController(emf);
            ConfigurationManager cfgMan = new ConfigurationJpaController(emf);
            User user = userMan.findUser(userId);
            Configuration config = cfgMan.findConfiguration(confId);
            Projectconfiguration projConf = projConfMan.getProjecConfiguration(project, config);
            cfgMan.destroy(config.getId());
            projConfMan.destroy(projConf.getId());
            request.setAttribute("user", userToEntity(user));
            List<Configuration> configs = projConfMan.getProjectConfigurations(projId);
            request.setAttribute("project", projectToEntity(project, configs));
            listProjects(request, response);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Remove Config: Trying to remove non-existent projectconfiguration or configuration.", ex);
            listProjects(request, response);
        }
    }

    private boolean checkConfigurationToRemove(HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();
        return params.containsKey("projectid") && params.containsKey("userid") && params.containsKey("cfgid");
    }

    private void editConfig(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectManager projManager = new ProjectJpaController(emf);
        ProjectConfigurationManager projConfManager = new ProjectConfigurationJpaController(emf);
        ConfigurationManager configManager = new ConfigurationJpaController(emf);
        UserManager userMan = new UserJpaController(emf);
        UserAccessManager userAccessMan = new UserAccessJpaController(emf);
        
        String strConfigId = request.getParameter("configid"),
               strProjectId = request.getParameter("projectid"),
               strUserId = request.getParameter("userid");
        
        if(strConfigId == null || strConfigId.isEmpty() ||
           strProjectId ==  null || strProjectId.isEmpty()||
           strUserId == null|| strUserId.isEmpty()){
            dispError(request, response, "Edit Configuration: Some of the configid, projectid, userid parameter is missing.");
            return;
        }
        
        Integer configId = Integer.parseInt(strConfigId),
                projectId = Integer.parseInt(strProjectId),
                userId = Integer.parseInt(strUserId);
        User user = userMan.findUser(userId);
        Project project = projManager.findProject(projectId);
        Configuration config = configManager.findConfiguration(configId);
        
        if(!checkConfigParams(request)){
            List<Useraccess> userAccessWithRole = userAccessMan.findUseraccessForConfiguration(config);
        
            List<User> freeUsers = userMan.findUserEntities();
            List<User> usersWithRole = userAccessMan.findUsersForConfig(config);
            freeUsers.removeAll(usersWithRole);
            List<UserWebEntity> users = usersToEntities(usersWithRole);
            List<UserWebEntity> freeUserEntities = usersToEntities(freeUsers);
            request.setAttribute("project", projectToEntity(project, new ArrayList<Configuration>()));
            request.setAttribute("config", configurationToEntity(config));
            request.setAttribute("user", user);
            request.setAttribute("users", freeUserEntities);
            request.setAttribute("usersWithRole", users);
            request.getRequestDispatcher("/editConfig.jsp").forward(request, response);
            return;
        }else{
            Configuration configuration = requestToConfig(request);
            String strConfId= request.getParameter("configid");
            if(strConfId==null || strConfId.isEmpty()){
                dispError(request, response, "Edit Config: Missing id for configuration to edit.");
                return;
            }
            Integer confId = Integer.parseInt(strConfId);
            Configuration toEdit = configManager.findConfiguration(confId);
            String value = configuration.getCode();
            if(value!=null && !value.isEmpty()){
                toEdit.setCode(configuration.getCode());
            }
            value = configuration.getDescription();
            if(value!=null && !value.isEmpty()){
                toEdit.setDescription(value);
            }
            try {
                configManager.edit(toEdit);
            } catch (Exception ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Unable to edit configuration "+toEdit, ex);
                dispError(request, response, "Unable to edit configuration "+toEdit);
                return;
            }
            request.setAttribute("user", userToEntity(user));
            List<Configuration> configs = projConfManager.getProjectConfigurations(projectId);
            request.setAttribute("project", projectToEntity(project, configs));
            listConfigs(request, response);
            
        }
        
        
    }

    private boolean checkConfigParams(HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();
        return params.containsKey("configid") && params.containsKey("desc");
    }

    private List<UserWebEntity> usersToEntities(List<User> users) {
        List<UserWebEntity> usersEntities = new ArrayList<>();
        for(User user:users){
            usersEntities.add(userToEntity(user));
        }
        return usersEntities;
    }

    private Configuration requestToConfig(HttpServletRequest request) {
        Configuration config = new Configuration();
        config.setDescription(request.getParameter("description"));
        return config;
    }

    private void addRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(checkAddRole(request)){
            String strUserId = request.getParameter("userid"),
                   strConfId = request.getParameter("configid"),
                   strProjectId = request.getParameter("projectid"),
                   strRole = request.getParameter("roleid"),
                   strRoleUserId = request.getParameter("roleuserid");
            
            UserAccessManager accessMan = new UserAccessJpaController(emf);
            Useraccess userAccess = new Useraccess();
            UserManager userMan = new UserJpaController(emf);
            
            Integer confId = Integer.parseInt(strConfId);
            Integer roleUserId = Integer.parseInt(strRoleUserId);
            Integer role = Integer.parseInt(strRole);
            Integer userId = Integer.parseInt(strUserId);
            
            User user = userMan.findUser(userId);
            userAccess.setConfigurationid(confId);
            userAccess.setUserid(roleUserId);
            userAccess.setRole(Roles.values()[role].name());
            Calendar cal = new GregorianCalendar();
            userAccess.setTs(BigInteger.valueOf(cal.getTimeInMillis()));
            accessMan.create(userAccess);
            Integer projId = Integer.parseInt(strProjectId);
            ProjectManager projMan = new ProjectJpaController(emf);
            ProjectConfigurationManager projConfMan = new ProjectConfigurationJpaController(emf);
            Project proj = projMan.findProject(projId);
            List<Configuration> configs = projConfMan.getProjectConfigurations(projId);
            List<ConfigurationWebEntity> confEntities = configsToEntities(configs);
            request.setAttribute("project",projectToEntity(proj, configs));
            request.setAttribute("user", userToEntity(user));
            request.setAttribute("configs", confEntities);
            listConfigs(request, response);
        }else{
            dispError(request, response, "Add Role: missing some mandatory parameters.");
            return;
        }
    }

    private boolean checkAddRole(HttpServletRequest request) {
        {
            Map<String,String[]> params = request.getParameterMap();
            return params.containsKey("userid") && params.containsKey("configid")
                   && params.containsKey("projectid") && params.containsKey("roleid") &&
                    params.containsKey("roleuserid");
        }
    }

    private List<ConfigurationWebEntity> configsToEntities(List<Configuration> configs) {
        List<ConfigurationWebEntity> confs = new ArrayList<>();
        for(Configuration conf:configs){
            confs.add(configurationToEntity(conf));
        }
        return confs;
    }

    private void deleteRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!checkRoleParamters(request)){
            dispError(request, response, "Delete role: Missing some parameters");
            return;
        }
        UserAccessManager userAccessMan = new UserAccessJpaController(emf);
        String strRoleUserId = request.getParameter("roleuserid"),
               strRole = request.getParameter("role"),
               strConfId = request.getParameter("configid"),
               strUserId = request.getParameter("userid"),
               strProjectId = request.getParameter("projectid");
        Integer roleUserId = Integer.parseInt(strRoleUserId),
                role = Integer.parseInt(strRole),
                confId = Integer.parseInt(strConfId),
                userId = Integer.parseInt(strUserId),
                projectId = Integer.parseInt(strProjectId);
        ProjectConfigurationManager projConfMan = new ProjectConfigurationJpaController(emf);
        List<Useraccess> accesses = userAccessMan.findUseraccessEntities(roleUserId,confId,Roles.values()[role].name());
        try{
            for(Useraccess access:accesses){
                LOGGER.log(Level.INFO, "Delete Role: {0}", access);
                userAccessMan.destroy(access.getId());
            }
        }catch(NonexistentEntityException nee){
            LOGGER.log(Level.SEVERE,"Unable to delet user access." ,nee );
        }
        ProjectManager projMan = new ProjectJpaController(emf);
        Project project = projMan.findProject(projectId);
        UserManager userMan = new UserJpaController(emf);
        User user = userMan.findUser(userId);
        List<Configuration> configs = projConfMan.getProjectConfigurations(projectId);
        request.setAttribute("project",projectToEntity(project, configs));
        request.setAttribute("user",userToEntity(user));
        listConfigs(request, response);
    }

    private boolean checkRoleParamters(HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();
        return params.containsKey("roleuserid") &&
               params.containsKey("role") &&
               params.containsKey("configid") &&
               params.containsKey("userid") &&
               params.containsKey("projectid");
    }

    private byte[] getAttachment(Integer attachId) throws IOException {
        AttachmentManager attachMan = new AttachmentJpaController(emf);
        Attachment attach = attachMan.findAttachment(attachId);
        File attachFile = new File(attach.getLocation());
        byte[] attachData=Files.readAllBytes(attachFile.toPath());
        return attachData;
    }

    private void addAttachments(Spznote note, Attachment attach1, Attachment attach2, Attachment attach3) {
        AttachmentManager attachMan = new AttachmentJpaController(emf);
        AttachmentNoteManager attachNoteMan = new AttachmentNoteJpaController(emf);
        if(attach1 != null){
            attachMan.create(attach1);
            Attachmentnote attachNote = new Attachmentnote();
            attachNote.setAttachmentid(attach1.getId());
            attachNote.setSpznoteid(note.getId());
            attachNoteMan.create(attachNote);
        }
        if(attach2 != null){
            attachMan.create(attach2);
            Attachmentnote attachNote = new Attachmentnote();
            attachNote.setAttachmentid(attach2.getId());
            attachNote.setSpznoteid(note.getId());
            attachNoteMan.create(attachNote);
        }
        if(attach3 != null){
            attachMan.create(attach3);
            Attachmentnote attachNote = new Attachmentnote();
            attachNote.setAttachmentid(attach3.getId());
            attachNote.setSpznoteid(note.getId());
            attachNoteMan.create(attachNote);
        }
    }

    private String getAttachmentMime(Integer attachId) {
        AttachmentManager attachMan = new AttachmentJpaController(emf);
        Attachment attach = attachMan.findAttachment(attachId);
        return attach.getType();
    }

    
}
