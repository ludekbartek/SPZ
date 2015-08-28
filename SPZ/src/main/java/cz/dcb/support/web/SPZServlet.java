/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.web;

import cz.dcb.support.db.jpa.controllers.SpzJpaController;
import cz.dcb.support.db.jpa.controllers.SpzManager;
import cz.dcb.support.db.jpa.entities.Spz;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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
        //request.setAttribute("message", "Use ");
        //request.getRequestDispatcher("/error.jsp").forward(request, response);
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
            System.out.println("Action: " + request.getParameter("action"));
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
            Spz spz = requestParamsToSpz(request.getParameterMap());
            SpzManager manager= new SpzJpaController(emf);
            manager.create(spz);
            List<Spz> spzs = manager.findSpzEntities();
            request.setAttribute("invspz", spz);
            request.setAttribute("spzs", spzs);
            request.getRequestDispatcher("/listSPZ.jsp").forward(request, response);
        }
    }

    private void editSpz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Spz spz = requestParamsToSpz(request.getParameterMap());
        SpzManager manager = new SpzJpaController(emf);
        try {
            manager.edit(spz);
        } catch (Exception ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, "Error editing spz: ", ex);
            throw new ServletException("Error editing spz",ex);
        }
        listSpz(request, response);
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
        SpzManager manager = new SpzJpaController(emf);
        List<Spz> spzs = manager.findSpzEntities();
        request.setAttribute("spzs", spzs);
        request.getRequestDispatcher("/listSPZ.jsp").forward(request, response);
    }

    private boolean checkSpzParams(Map<String, String[]> parameterMap) {
        if(!checkReqNumber(parameterMap)){
            return false;
        }

        if(!checkReqType(parameterMap)){
            return false;
        }
        if(!checkIssueDate(parameterMap)){
            return false;
        }
        
        if(!checkContactPerson(parameterMap)){
            return false;
        }
        
        if(!checkShortName(parameterMap)){
            return false;
        }
        
        if(!checkRequestDescription(parameterMap)){
            return false;
        }
        
        if(!checkImplementationAcceptanceDate(parameterMap)){
            return false;
        }

        return true;
    }

    private Spz requestParamsToSpz(Map<String, String[]> parameterMap) {
        Spz spz = new Spz();
        spz.setReqnumber(parameterMap.get("reqnumber")[0]);
        spz.setRequestdescription(parameterMap.get("requestdescription")[0]);
        spz.setContactperson(parameterMap.get("contactperson")[0]);
        spz.setShortname(parameterMap.get("shortname")[0]);
        String issueDateString = parameterMap.get("issuedate")[0];
        Date issueDate = stringToDate(issueDateString);
        if (issueDate == null) {
            return null;
        }
        spz.setIssuedate(issueDate);
        if(checkImplementationAcceptanceDate(parameterMap)){
        
            String implDateStr = parameterMap.get("implementationacceptancedate")[0];
            Date implDate = stringToDate(implDateStr);
            spz.setImplementationacceptdate(implDate);
        }
        long ts = (new GregorianCalendar()).getTimeInMillis();
        spz.setTs(BigInteger.valueOf(ts));
        String strPriority = parameterMap.get("priority")[0];
        short priority = Short.parseShort(strPriority);
        spz.setPriority(priority);
        
        spz.setRequesttype(parameterMap.get("reqtype")[0]);
        return spz;
    }

    private Date stringToDate(String strVal) {
        DateFormat formater = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("cs"));
        Date value = null;
        try {
            value = formater.parse(strVal);
        } catch (ParseException ex) {
            Logger.getLogger(SPZServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return value;
    }

    private boolean checkReqNumber(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("reqnumber") && !parameterMap.get("reqnumber")[0].isEmpty();
    }

    private boolean checkIssueDate(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("issuedate") && !parameterMap.get("issuedate")[0].isEmpty();
    }

    private boolean checkContactPerson(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("contactperson") && !parameterMap.get("contactperson")[0].isEmpty();
    }

    private boolean checkShortName(Map<String, String[]> parameterMap) {
        return parameterMap.containsKey("shortname") && !parameterMap.get("shortname")[0].isEmpty();
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

}
