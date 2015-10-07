/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web;

import cz.dcb.support.db.jpa.controllers.AttachmentJpaController;
import cz.dcb.support.db.jpa.controllers.AttachmentManager;
import cz.dcb.support.db.jpa.controllers.SpzIssuerJpaController;
import cz.dcb.support.db.jpa.controllers.SpzIssuerManager;
import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.controllers.SpzStatesJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStatesManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.entities.Attachment;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.SpzStates;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.jpa.entities.Spzstates;
import cz.dcb.support.db.jpa.entities.User;
import cz.dcb.support.web.entities.SPZWebEntity;
import cz.dcb.support.web.entities.SpzStateWebEntity;
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
import java.net.URL;
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
   
    private static final String STATES[] ={"Registrovaná","Nová","Probíhá analýza",
        "Změněna", "Specifikována","Probíhá implementace","Přijata ze strany DCB",
        "Uvolněná", "Změna implementace", "Nová implementace", "Probíhá nová analýza",
        "Zamítnuntá", "Potvrzená", "Zrušená", "Fakturovaná"};
    
    @Override
    public void init(){
        Properties props = new Properties();
        String url; 
        try {
            props.load(getServletContext().getResourceAsStream("/settings/spz.properties"));
            url = props.getProperty("DBURL");
        } catch (IOException ex) {
            url = "jdbc:derby://localhost:1527/support";
            Logger.getLogger(SPZServlet.class.getName()).log(Level.INFO, "Setting url to default." + url, ex);
        }
        Connection con;
        try {
            con = DriverManager.getConnection(url);
            con.close();
            return;
        } catch (SQLException ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Server not running. Trying to start the new one.", ex);
        }
        
        try {
            NetworkServerControl serverControl = new NetworkServerControl();
            LOGGER.log(Level.INFO,"Starting derby");
            PrintWriter log = new PrintWriter("suppport-derby.log");
            serverControl.start(log);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Derby start failed:", ex);
        }
        Path attachDir = Paths.get("./attachments");
        if(!Files.exists(attachDir)){
            try {
                Files.createDirectory(attachDir);
            } catch (IOException ex) {
                Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex);
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
                case "/login"://authenticate(request,response);
                              listSpz(request, response);
                            break;
                case "/addspz":addSpz(request,response);
                            break;
                case "/listspz":listSpz(request,response);
                            break;
                case "/editspz":editSpz(request,response);
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

    private void authenticate(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addSpz(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(checkSpzParams(request.getParameterMap())){
            SpzManager manager= new SpzJpaController(emf);
            EntityManager entMan = emf.createEntityManager();
            EntityTransaction transaction = entMan.getTransaction();
            SpzStateManager stateManager = new SpzStateJpaController(emf);
            SpzStatesManager statesManager = new SpzStatesJpaController(emf);
            Spz spz = requestParamsToSpz(request.getParameterMap());
            Spzstate state = new Spzstate();
          //  createNewState(state,spz);
            spz.setIssuedate(new GregorianCalendar().getTime());
            spz.setShortname(spz.getReqnumber());
            try{
                transaction.begin();

                manager.create(spz);
                spz.setReqnumber(String.format("%05d",spz.getId()));
                manager.edit(spz);
                createNewState(state, spz);

                stateManager.create(state);
                state.setCurrentstate(state.getId());
                stateManager.edit(state);
                Spzstates states = createSpzStates(spz,state);
                statesManager.create(states,entMan);
                
                transaction.commit();
                List<Spz> spzs = manager.findSpzEntities();
                request.setAttribute("spzs", spzToEntities(spzs));
            }catch(Exception ex){
                transaction.rollback();
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
            Spz spz = requestParamsToSpz(request.getParameterMap());
            if(spz!=null){
                request.setAttribute("spz", spzToEntity(spz));
                request.setAttribute("error", "Nektera polozka chybi nebo ma neplatnou hodnotu" );
            }
            request.getRequestDispatcher("/addSPZ.jsp").forward(request,response);
            return;
        }
        listSpz(request, response);
    }

    private void editSpz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jspName;
        Spz spz = null;
        SpzManager manager = new SpzJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        Integer id = getSpzId(request.getParameterMap());
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
                jspName = "/editDCBAcc.jsp";
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
            default:return;
              
        }
        request.getRequestDispatcher(jspName).forward(request, response);
       // listSpz(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    private void addAttachment(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void editAttachment(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addProject(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void editProject(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listSpz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SpzManager spzManager = new SpzJpaController(emf);
        List<Spz> spzs = spzManager.findSpzEntities();
        List<SPZWebEntity> entities = spzToEntities(spzs);
        request.setAttribute("spzs", entities);
        request.getRequestDispatcher("/listSPZ.jsp").forward(request, response);
    }

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

    private Spz requestParamsToSpz(Map<String, String[]> parameterMap) {
        if(!checkSpzParams(parameterMap)){
            return null;
        }
        Spz spz = new Spz();
        /*if(!parameterMap.containsKey("reqnumber")){
            return null;
        }*/
       // spz.setShortname(parameterMap.get("shortname")[0]);
        
        spz.setRequestdescription(parameterMap.get("requestdescription")[0]);
        spz.setContactperson(parameterMap.get("contactperson")[0]);
        if(parameterMap.containsKey("shortname")) spz.setShortname(parameterMap.get("shortname")[0]);
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
        
        spz.setRequesttype(parameterMap.get("reqtype")[0]);
        return spz;
    }

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

    private boolean checkReqNumber(Map<String, String[]> parameterMap) {
        boolean result = parameterMap.containsKey("reqnumber");
        if(!result)return false;
        String reqNumber = parameterMap.get("reqnumber")[0];
        return !reqNumber.isEmpty();
    }

    private boolean checkIssueDate(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("issuedate") && !parameterMap.get("issuedate")[0].isEmpty();
    }

    private boolean checkContactPerson(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("contactperson") && !parameterMap.get("contactperson")[0].isEmpty();
    }

    private boolean checkShortName(Map<String, String[]> parameterMap) {
        boolean result = parameterMap.containsKey("shortname");
        if(!result){
            return false;
        }
        String shortName = parameterMap.get("shortname")[0];
        return !shortName.isEmpty();
    }

    private boolean checkRequestDescription(Map<String, String[]> parameterMap) {
        boolean result = parameterMap.containsKey("requestdescription");
        result = result && !(parameterMap.get("requestdescription")[0].isEmpty());
        return result;
    }

    private boolean checkImplementationAcceptanceDate(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("implementationacceptancedate") && !parameterMap.get("implementationacceptancedate")[0].isEmpty();
    }

    private boolean checkReqType(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("reqtype") && !parameterMap.get("reqtype")[0].isEmpty();
    }

    private List<SPZWebEntity> spzToEntities(List<Spz> spzs) {
        List<SPZWebEntity> entities = new ArrayList<>();
        
        for(Spz spz:spzs){
            SPZWebEntity entity = spzToEntity(spz);
            entities.add(entity);
        }
        return entities;
    }

    private SPZWebEntity spzToEntity(Spz spz) {
        SpzIssuerManager issuerManager = new SpzIssuerJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        SpzStatesManager statesManager = new SpzStatesJpaController(emf);
        UserManager userManger = new UserJpaController(emf);
        List<SpzStateWebEntity> history;
        
        SPZWebEntity entity;
        entity = new SPZWebEntity();
        entity.setId(spz.getId());
        entity.setReqnumber(spz.getReqnumber());
        entity.setIssuedate(spz.getIssuedate());
        entity.setContactperson(spz.getContactperson());
        entity.setReqnumber(spz.getReqnumber());
        entity.setRequesttype(spz.getRequesttype());
        Spzstate current = statesManager.getCurrentState(spz);
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
        entity.setRequestdescription(spz.getRequestdescription());
        entity.setKind(spz.getRequesttype());
        entity.setDate(getLastChangeDate(spz.getId(),stateManager));
        return entity;
    }

    private Date getLastChangeDate(Integer id,SpzStateManager manager) {
        return manager.getLastChange(id);
        
    }

    private Integer getSpzId(Map<String, String[]> parameterMap) {
        if(!parameterMap.containsKey("id")){
            return null;
        }
        String strId = parameterMap.get("id")[0];
        Integer id;
        try{
            id = Integer.parseInt(strId);
        }catch(NumberFormatException nfe){
            return null;
        }
        return id;
    }

    private String getCurrentState(Spz spz) {
        SpzStatesManager manager = new SpzStatesJpaController(emf);
        Spzstate state = manager.getCurrentState(spz);
        return state.getCode();
    }

    private void createNewState(Spzstate state, Spz spz) {
        state.setIdate(new GregorianCalendar().getTime());
        state.setCode(SpzStates.POSTED.toString());
        state.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
    }

    private Spzstates createSpzStates(Spz spz, Spzstate state) {
        Spzstates states= new Spzstates();
        states.setSpzid(spz.getId());
        states.setStateid(state.getId());
        return states;
    }

    private List<SpzStateWebEntity> spzStatesToSpzStateWebEntities(List<Spzstate> spzStates) {
        List<SpzStateWebEntity> result = new ArrayList<>();
        for(Spzstate state:spzStates){
            SpzStateWebEntity entity = spzStateToSpzWebEntity(state);
            result.add(entity);
        }
        
        return result;
    }

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
        return entity;
    }

    private void updateSPZ(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
        Spz spz = requestParamsToSpz(request.getParameterMap());
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

    private void addNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AttachmentManager manager = new AttachmentJpaController(emf);
        Map<String,String[]> params = request.getParameterMap();
        String fileName;
        InputStream in = null;
        Part part1 = request.getPart("soubor1"),
             part2 = request.getPart("soubor2"),
             part3 = request.getPart("soubor3");
        
        if(part1 != null){
            in = part1.getInputStream();
            fileName = new StringBuilder("attachments/").append(part1.getSubmittedFileName()).toString();
            try{
                uploadFile(request, response, manager, fileName, in);
            }catch(ServletException| IOException ex){
                displayError(request, fileName, ex, response);
            }
        }
        
        if(part2!=null){
            fileName = new StringBuilder("attachments/").append(part2.getSubmittedFileName()).toString();
            in = part2.getInputStream();
            try{
                uploadFile(request,response,manager,fileName,in);
            }catch(ServletException | IOException ex){
                displayError(request, fileName, ex, response);
            }
        }
        
        if(part3!=null){
            in = part3.getInputStream();
            fileName = new StringBuilder("attachments/").append(part3.getSubmittedFileName()).toString();
            try{
                uploadFile(request,response,manager,fileName,in);
            }catch(ServletException |IOException ex){
                displayError(request, fileName, ex, response);
            }
            
        }
        SpzManager spzManager = new SpzJpaController(emf);
        List<Spz> spzs = spzManager.findSpzEntities();
        request.setAttribute("spzs", spzs);
        request.getRequestDispatcher("/editPost.jsp").forward(request, response);
        
    }

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

    private void uploadFile(HttpServletRequest request, HttpServletResponse response, AttachmentManager manager,String fileParam, InputStream in) throws IOException, ServletException {
       // String fileName = new StringBuilder("attachments/").append(fileParam).toString();
        try{
            uploadFile(request,response,fileParam,in);
        }catch(ServletException | IOException ex){
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
    
    private void uploadFile(HttpServletRequest request, HttpServletResponse response,String fileName, InputStream in) throws IOException, ServletException {
            
            OutputStream out = null;
            int count = 0;
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

    private Attachment requestToAttachment(HttpServletRequest request) {
        Attachment attachment = new Attachment();
        attachment.setDate(new GregorianCalendar().getTime());
        attachment.setTs(BigInteger.valueOf(new GregorianCalendar().getTimeInMillis()));
        
        return attachment;
    }

    private void deleteSpzState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> params = request.getParameterMap();
        if(!params.containsKey("id")){
            request.setAttribute("error", "Missing spz id to be able to delete last spz state.");
            request.getRequestDispatcher("/editPost.jsp").forward(request, response);
            return;

        }
        int spzId = Integer.parseInt(params.get("id")[0]);
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
            previous = spzStates.get(1);
            previous.setCurrentstate(0);
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
        request.setAttribute("spz", spz);
        request.setAttribute("state", previous);
        request.getRequestDispatcher("/editDeleted.jsp").forward(request, response);
        //listSpz(request, response);
    }

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

    private void changeState(Spz spz, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringState = request.getParameter("newstate");
        SpzStates state = SpzStates.valueOf(stringState);
        Spzstate newState = new Spzstate();
        newState.setCurrentstate(1);
        newState.setCode(state.toString());
        if(request.getParameterMap().containsKey("issuer")){
            newState.setIssuerLogin(request.getParameter("issuer"));
        }
        newState.setIdate(new GregorianCalendar().getTime());
        SpzStateManager spzManager = new SpzStateJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        SpzStatesManager statesManager = new SpzStatesJpaController(emf);
        Spzstates newStates = new Spzstates();
        EntityManager entMan = stateManager.getEntityManager();
        
        try{
            entMan.getTransaction().begin();
            stateManager.create(newState,entMan);
            newStates.setSpzid(spz.getId());
            newStates.setStateid(newState.getId());
            statesManager.create(newStates,entMan);
            entMan.getTransaction().commit();
        }catch(Exception ex){
            entMan.getTransaction().rollback();
            LOGGER.log(Level.SEVERE,"Error set state ANALYSIS on SPZ.",ex);
            request.setAttribute("error", "Chyba při přechodu do stavu Probíhá analýza.");
            return;
        }finally{
            entMan.close();
        }
        return;
    }

}
