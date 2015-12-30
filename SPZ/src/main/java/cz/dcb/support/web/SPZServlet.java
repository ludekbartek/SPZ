/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web;

import cz.dcb.support.db.exceptions.SPZException;
import cz.dcb.support.db.jpa.controllers.AttachmentJpaController;
import cz.dcb.support.db.jpa.controllers.AttachmentManager;
import cz.dcb.support.db.jpa.controllers.AttachmentNoteJpaController;
import cz.dcb.support.db.jpa.controllers.AttachmentNoteManager;
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
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.SpzStates;
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
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

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
   
    private static Path attachDir; 
    private static final String STATES[] ={"Registrovaná","Nová","Probíhá analýza",
        "Změněna", "Specifikována","Probíhá implementace","Přijata ze strany DCB",
        "Uvolněná", "Změna implementace", "Nová implementace", "Probíhá nová analýza",
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        request.setAttribute("error", "Pouzita metoda get misto post.");
        listSpz(request, response);
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
            //String action = request.getParameter("action");
            switch(action.toLowerCase()){
                case "/login":authenticate(request,response);
                              listSpz(request, response);
                            break;
                case "/addspz":addSpz(request,response);
                            break;
                case "/listspz":listSpz(request,response);
                            break;
                case "/editspz":editSpz(request,response);
                            break;
                case "/spzsolution":getSpzSolution(request,response);
                            break;
                case "/updatespz":updateSPZ(request,response);
                            break;
                case "/adduser":addUser(request,response);
                            break;
                case "/addnote":addNote(request,response);
                            break;
                case "/edituser":editUser(request,response);
                            break;
                case "/addattachment":addAttachment(request,response);
                            break;
                case "/editattachment":editAttachment(request,response);
                            break;
                case "/addproject":addProject(request,response);
                            break;
                case "/editproject":editProject(request,response);
                            break;
                case "/delete":deleteSpz(request,response);
                            break;
                case "/removestate":deleteSpzState(request,response);
                            break;
                case "/changeanalyst":changeAnalyst(request,response);
                            break;
                case "/acceptsolution":acceptSolution(request,response);
                            break;
                case "/editref":refineSolution(request,response);
                            break;
                case "/acceptimpl":acceptImpl(request,response);
                            break;
                default:
                    StringBuilder errorMesg = new StringBuilder("Invalid action").append(action).append(". Using list instead.");
                    LOGGER.log(Level.INFO,errorMesg.toString());
                    listSpz(request,response);
                    
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
            request.setAttribute("user",userWeb);
            request.getRequestDispatcher("/SPZServlet/listspz").forward(request, response);
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
            Spz spz = null;
            try {
                spz = requestParamsToSpz(request.getParameterMap());
            } catch (SPZException ex) {
                LOGGER.log(Level.SEVERE, "Chyba pri prevodu parametru na SPZ.", ex);
                request.setAttribute("errror", "Chyba pri prevodu parametru na SPZ:" + ex);
                request.getRequestDispatcher("./addSPZ.jsp").forward(request, response);
                return;
            }
            Spzstate state = new Spzstate();
          //  createNewState(state,spz);
            spz.setIssuedate(new GregorianCalendar().getTime());
//            spz.setShortName(spz.getReqnumber());
            try{
                transaction.begin();

                manager.create(spz);
                transaction.commit();
                transaction.begin();
                spz.setReqnumber(String.format("%05d",spz.getId()));
                manager.edit(spz);
                createNewState(state, spz);

                stateManager.create(state);
                state.setCurrentstate(1);
                stateManager.edit(state);
                Spzstates states = createSpzStates(spz,state);
                statesManager.create(states,entMan);
                transaction.commit();
                
                if(request.getParameterMap().containsKey("desc")){
                    request.setAttribute("jsp", "./list.jsp");
                    addNote(request, response,spz.getId());
                }
                
                List<Spz> spzs = manager.findSpzEntities();
                request.setAttribute("spzs", spzToEntities(spzs));
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
            try{
                spz = requestParamsToSpz(request.getParameterMap());
            }catch(SPZException ex){
                request.setAttribute("error", "Chyba pri prevodu parametru na SPZ: "+ex);
            }
            if(spz!=null){
                request.setAttribute("spz", spzToEntity(spz));
                request.setAttribute("error", "Nektera polozka chybi nebo ma neplatnou hodnotu" );
            }
            request.getRequestDispatcher("/addSPZ.jsp").forward(request,response);
            return;
        }
        listSpz(request, response);
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
        User user = getUserByParameter(request);
        UserWebEntity userWeb = userToEntity(user);
        request.setAttribute("user", userWeb);
        spz = manager.findSpz(id);
        if(request.getParameterMap().containsKey("newstate")){
            
            changeState(spz,request,response);
            request.getRequestDispatcher("./listSPZ.jsp").forward(request, response);
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
                jspName = "/reclaimed.jsp";
                break;
            case "CONFIRMED":
                jspName = "/confirmed.jsp";
                break;
            case "CANCELED":
                jspName = "/editCanceled.jsp";
                break;
            case "INVOICED":
                jspName = "/invoiced.jsp";
                break;
            case "ACCEPTED":
                jspName="/editAccepted.jsp";
                break;
            default:return;
              
        }
        request.getRequestDispatcher(jspName).forward(request, response);
       // listSpz(request, response);
    }

    /**
     * Adds new user
     * @param request http request containing required data
     * @param response http response 
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Modifies user data
     * @param request http request containing required data
     * @param response http response 
     */
    private void editUser(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
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
    private void addProject(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Edits an existing project
     * @param request http request containing id of the project to be modified as well as 
     *                the modified information
     * @param response http response
     */
    private void editProject(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        if(parameterMap.containsKey("shortname")) spz.setShortName(parameterMap.get("shortname")[0]);
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
        UserManager userManger = new UserJpaController(emf);
        List<SpzStateWebEntity> history;
        
        SPZWebEntity entity;
        entity = new SPZWebEntity();
        entity.setId(spz.getId());
        entity.setShortName(spz.getShortName());
        entity.setReqNumber(spz.getReqnumber());
        entity.setIssueDate(spz.getIssuedate());
        entity.setContactPerson(spz.getContactperson());
        entity.setReqNumber(spz.getReqnumber());
        entity.setRequestType(spz.getRequesttype());
        Spzstate current = statesManager.getCurrentState(spz);
        entity.setSpzState(current.getCode());
        User user = null;
        if(current!=null){
            user = userManger.findUserByLogin(current.getIssuerLogin());
        }
        entity.setIssuer((user!=null?user.getLogin():"Nenastaven"));
        history = spzStatesToSpzStateWebEntities(statesManager.findSpzstates(spz));
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
    private void createNewState(Spzstate state, Spz spz) {
        state.setIdate(new GregorianCalendar().getTime());
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
        UserManager userMan = new UserJpaController(emf);
        SpzStateWebEntity entity = new SpzStateWebEntity();
        entity.setId(state.getId());
        entity.setAssumedManDays(state.getAssumedmandays());
        entity.setMandays(state.getMandays());
        entity.setClassType(state.getClasstype());
        entity.setCode(STATES[SpzStates.valueOf(state.getCode()).ordinal()]);
        entity.setCurrentState(Long.valueOf(state.getCurrentstate()));
        entity.setIssueDate(state.getIdate());
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
        Attachment file = requestToAttachment(request);
        file.setContent(fileParam);
        file.setType(Files.probeContentType(Paths.get(fileParam)));
        manager.create(file);
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
        List<Spzstate> spzStates = statesManager.findSpzstates(spz);
        Collections.sort(spzStates,new Comparator<Spzstate>() {

            @Override
            public int compare(Spzstate o1, Spzstate o2) {
                return o2.getIdate().compareTo(o1.getIdate());
            }
        });
        Spzstate previous = null;
        if(spzStates.size()>1){
            Spzstate current = stateManager.getCurrentState(spz);
            current.setCurrentstate(0);
            previous = spzStates.get(1);
            previous.setCurrentstate(1);
            previous.setIdate(new GregorianCalendar().getTime());
            try {
                stateManager.edit(previous);
                
            } catch (Exception ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Error deleting current state flag.", ex);
            
                request.setAttribute("error", "Nepodarilo se zrusit priznak aktualniho stavu.");
                listSpz(request, response);
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
                listSpz(request, response);
            } catch (ServletException | IOException ex1) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
        }
        Spzstate spzState = new Spzstate();
        spzState.setAssumedmandays(currentState.getAssumedmandays());
        spzState.setClasstype(currentState.getClasstype());
        spzState.setCode(currentState.getCode());
        spzState.setCurrentstate(1);
        spzState.setAssumedmandays(0.0);
        spzState.setIdate(currentState.getIdate());
        spzState.setIssuerLogin(currentState.getIssuerLogin());
        spzState.setMandays(currentState.getMandays());
        spzState.setReleasenotes(currentState.getReleasenotes());
        spzState.setRevisedrequestdescription(currentState.getRevisedrequestdescription());
        spzState.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
        stateManager.create(spzState);
        
        Spzstates states = new Spzstates();
        states.setSpzid(spzId);
        states.setStateid(spzState.getId());
        statesManager.create(states);
        try {
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
        
        newState.setIdate(new GregorianCalendar().getTime());
        newState.setTs(BigInteger.valueOf(new Date().getTime()));
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
                 spzState.setAssumedmandays(estWorkLoad);
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
        request.setAttribute("spz", entity);
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
        AttachmentNoteManager manager= new AttachmentNoteJpaController(emf);
        List<Attachment> attachments = manager.getAttachmentsForNote(note.getId());
        
        List<AttachmentEntity> atachmentsEntities = attachmentsToWebEntity(attachments);
                
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
        if(part1 != null){
            try{
                in = part1.getInputStream();
                fileName = new StringBuilder(attachDir.toString()).append("/").append(part1.getSubmittedFileName()).toString();
                try{
                    uploadFile(request, response, manager, fileName, in);
                }catch(IOException ex){
                    displayError(request, fileName, ex, response);
                    return;
                }
            }catch(IOException ex){
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null,ex);
            }
        }
        
        if(part2!=null){
            fileName = new StringBuilder(attachDir.toString()).append("/").append(part2.getSubmittedFileName()).toString();
            
            try{
                in = part2.getInputStream();
                uploadFile(request,response,manager,fileName,in);
            }catch(IOException ex){
                displayError(request, fileName, ex, response);
                return;
            }
        }
        
        if(part3!=null){
            
            try{
                in = part3.getInputStream();
                fileName = new StringBuilder(attachDir.toString()).append("/").append(part3.getSubmittedFileName()).toString();
                uploadFile(request,response,manager,fileName,in);
            }catch(IOException ex){
                displayError(request, fileName, ex, response);
                return;
            }
            
        }
        SpzManager spzManager = new SpzJpaController(emf);
        SpzStateNoteManager stateNoteManager = new SpzStateNoteJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        SpzNoteManager noteManager = new SpzNoteJpaController(emf);
        
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
            request.getRequestDispatcher(jsp).forward(request,response);
            return;
        }
        LOGGER.log(Level.INFO,"external ",externalStr);
        short external = (short)(externalStr!=null&&externalStr.compareToIgnoreCase("on")==0?1:0);
        note.setExternalnote(external);
        if(noteText==null){
            request.setAttribute("error", "Missing note description.");
            //request.getRequestDispatcher("/editPost.jsp").forward(request, response);
            editSpz(request, response);
            return;
        }
        note.setNotetext(noteText);
        note.setNotedate(new GregorianCalendar().getTime());
        note.setExternalnote(external);
        note.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
        noteManager.create(note);
        stateNote.setNoteid(note.getId());
        stateNote.setStateid(state.getId());
        stateNoteManager.create(stateNote);
        jsp = (String)request.getAttribute("jsp");
        if(jsp!=null && jsp.contains("list")){
            listSpz(request, response);
            return;
        }
        request.setAttribute("spz", spzToEntity(spz));
        request.getRequestDispatcher("/editPost.jsp").forward(request, response);
        
    }

    private void changeAnalyst(HttpServletRequest request, HttpServletResponse response){
        RolesManager rolesMan = new RolesJpaController(emf);
        UserAccessManager accessMan = new UserAccessJpaController(emf);
        SpzAnalystManager analystManager = new SpzAnalystJpaController(emf);
        UserManager userManager = new UserJpaController(emf);
        SpzManager spzManager = new SpzJpaController(emf);
        //SpzAnalystManager spzAnalystManager = new SpzAnalystJpaController(emf);
        int spzId = getSpzId(request.getParameterMap());
        
        try {
            request.getRequestDispatcher("./editAnalyst.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            try {
                request.setAttribute("error", "Nelze zmenit analytika (vice viz log).");
                LOGGER.log(Level.SEVERE,"Chyba pri prechodu na editAnalyst.jsp:",ex);
                listSpz(request, response);
            } catch (ServletException | IOException ex1) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private UserWebEntity requestToUserWebEntity(HttpServletRequest request) {
        UserWebEntity user = new UserWebEntity();
        if(request.getParameterMap().containsKey("login")){
            String login = request.getParameter("login");
            user.setLogin(login);
            switch(login){
                case "user":
                    user.setName("Franta Uzivatel");
                    user.setRole(Roles.CLIENT.ordinal());
                    user.setId(2);
                    break;
                case "analyst":
                    user.setName("Tonda Vyvojar");
                    user.setRole(Roles.ANALYST.ordinal());
                    user.setId(1);
                    break;
                default: 
                    user.setLogin("root");
                    user.setName("Tomas Korinek");
                    user.setRole(Roles.ANALYST.ordinal());
                    user.setId(0);
                    break;
            }
        }else{
            String strId = request.getParameter("userid");
            if(strId!=null){
                switch(strId){
                    case "1":
                        user.setId(1);
                        user.setLogin("analyst");
                        user.setName("Franta Vyvojar");
                        user.setRole(Roles.ANALYST.ordinal());
                        break;
                    default:
                        user.setId(0);
                        user.setLogin("user");
                        user.setName("Franta Uzivatel");
                        user.setRole(Roles.CLIENT.ordinal());
                     
                }
            }
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
        request.setAttribute("spz", spzEnt);
        request.setAttribute("user", userEnt);
        request.getRequestDispatcher("/acceptSol.jsp").forward(request, response);
    }

    private Spz getSpzByParameter(HttpServletRequest request) throws NumberFormatException {
        String spzIdStr = request.getParameter("spzid");
        int spzId = Integer.parseInt(spzIdStr);
        SpzManager spzMan = new SpzJpaController(emf);
        Spz spz = spzMan.findSpz(spzId);
        return spz;
    }

    private User getUserByParameter(HttpServletRequest request) {
        String userIdStr = request.getParameter("userid");
        int userId = Integer.parseInt(userIdStr);
        UserManager userMan = new UserJpaController(emf);
        User user = userMan.findUser(userId);
        return user;
    }

    private UserWebEntity userToEntity(User user) {
        UserWebEntity entity = new UserWebEntity();
        entity.setId(user.getId());
        entity.setLogin(user.getLogin());
        entity.setName(user.getName());
        List<Useraccess> roles = getUserRoles(user.getId());
        switch(roles.get(0).getRole()){
            case "client":entity.setRole(Roles.CLIENT.ordinal());
                          break;
            default:entity.setRole(Roles.ANALYST.ordinal());
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
        Spz spz = getSpzByParameter(request);
        User user = getUserByParameter(request);
        SPZWebEntity spzWeb = spzToEntity(spz);
        UserWebEntity userWeb = userToEntity(user);
        request.setAttribute("spz", spzWeb);
        request.setAttribute("user", userWeb);
        request.setAttribute("change", true);
        request.getRequestDispatcher("/editRef.jsp").forward(request, response);
        return;
    }

    private void acceptImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Spz spz = getSpzByParameter(request);
        User user = getUserByParameter(request);
        SPZWebEntity spzWeb = spzToEntity(spz);
        UserWebEntity userWeb = userToEntity(user);
        request.setAttribute("spz", spzWeb);
        request.setAttribute("user", userWeb);
        request.setAttribute("change", true);
        request.getRequestDispatcher("/editAccepted.jsp").forward(request, response);
        return;

    }
}
