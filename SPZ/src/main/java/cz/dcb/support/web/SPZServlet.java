/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web;

import cz.dcb.support.db.jpa.controllers.SpzIssuerJpaController;
import cz.dcb.support.db.jpa.controllers.SpzIssuerManager;
import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.controllers.SpzStateJpaController;
import cz.dcb.support.db.jpa.controllers.SpzStateManager;
import cz.dcb.support.db.jpa.controllers.UserJpaController;
import cz.dcb.support.db.jpa.controllers.UserManager;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.SpzStates;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.web.entities.SPZWebEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.DeclareRoles;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author bar
 */
@WebServlet(urlPatterns = {"/SPZServlet","/SPZServlet/*"})
//@WebServlet(name = "SPZServlet", urlPatterns = {"/SPZServlet/*"})
/*@ServletSecurity(
 @HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL,
         rolesAllowed = {"user"}))
@DeclareRoles({"admin","user"})*/
public class SPZServlet extends HttpServlet {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("support_JPA");
    @Override
    public void init(){
        try {
            NetworkServerControl serverControl = new NetworkServerControl();
                Logger.getLogger(SPZServlet.class.getName()).log(Level.INFO,"Starting derby");
                PrintWriter log = new PrintWriter("suppport-derby.log");
                serverControl.start(log);
        } catch (Exception ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        String action = request.getPathInfo();
        if(action.compareToIgnoreCase("/listSPZ")==0){
            listSpz(request, response);
        }
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
                case "/adduser":addUser(request,response);
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
                default:break;
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
            Spz spz = requestParamsToSpz(request.getParameterMap());
            
            spz.setIssuedate(new GregorianCalendar().getTime());
            spz.setShortname(spz.getReqnumber());
            manager.create(spz);
            
            List<Spz> spzs = manager.findSpzEntities();
            request.setAttribute("spzs", spzs);
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
                request.setAttribute("spz", spz);
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
        spz=manager.findSpz(id);
        request.setAttribute("spz", spz);
        
        switch(getCurrentState(spz)){
            case POSTED:
                jspName="/editPost.jsp";
                break;
            case NEW:
                jspName="/editNew.jsp";
            case ANALYLIS:
                jspName="/editAnal.jsp";
                break;
            case REFINE:
                jspName = "/editRef.jsp";
                break;
            case SPECIFIED:
                jspName = "/editSpec.jsp";
                break;
            case IMPLEMENTATION:
                jspName = "/editImpl.jsp";
                break;
            case DCB_ACCEPTED:
                jspName = "/editDCBAcc.jsp";
                break;
            case RELEASED:
                jspName="/editRel.jsp";
                break;
            case IMPLEMENTATION_REFINE:
                jspName = "/editImplRef.jsp";
                break;
            case RE_IMPLEMENTATION:
                jspName ="/reImpl.jsp";
                break;
            case RE_ANALYSIS:
                jspName = "/reAnal.jsp";
                break;
            case RECLAIMED:
                jspName = "/reclaimed.jsp";
                break;
            case CONFIRMED:
                jspName = "/confirmed.jsp";
                break;
            case CANCELED:
                jspName = "/canceled.jsp";
                break;
            case INVOICED:
                jspName = "/invoiced.jsp";
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
        if(!checkReqNumber(parameterMap)){
            return false;
        }

        if(!checkReqType(parameterMap)){
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
        Spz spz = new Spz();
        if(!parameterMap.containsKey("reqnumber")){
            return null;
        }
        spz.setReqnumber(parameterMap.get("reqnumber")[0]);
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
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        SpzIssuerManager issuerManager = new SpzIssuerJpaController(emf);
        SpzStateManager stateManager = new SpzStateJpaController(emf);
        UserManager userManger = new UserJpaController(emf);
        
        for(Spz spz:spzs){
            SPZWebEntity entity;
            entity = new SPZWebEntity();
            entity.setId(spz.getId());
            entity.setReqnumber(spz.getReqnumber());
            entity.setIssuedate(spz.getIssuedate());
            entity.setContactperson(spz.getContactperson());
            entity.setReqnumber(spz.getReqnumber());
            entity.setRequesttype(spz.getRequesttype());
            Integer spzIssuerId = issuerManager.findSpzIssuerIdBySpzId(spz.getId());
            if(spzIssuerId > -1){
                String issuerName = userManger.findUser(spzIssuerId).getName();
                entity.setIssuer(issuerName);
            }
            entity.setRequestdescription(spz.getRequestdescription());
            entity.setKind(spz.getRequesttype());
            entity.setDate(getLastChangeDate(spz.getId(),stateManager));
            entities.add(entity);
        }
        return entities;
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

    private SpzStates getCurrentState(Spz spz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
